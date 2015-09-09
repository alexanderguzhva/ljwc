# based on https://github.com/kubernetes/kubernetes/blob/master/docs/getting-started-guides/docker-multinode/master.sh
# --restart=always was a MISTAKE
# master machine is ubunty 14.04

sudo sh -c 'docker -d -H unix:///var/run/docker-bootstrap.sock -p /var/run/docker-bootstrap.pid --iptables=false --ip-masq=false --bridge=none --graph=/var/lib/docker-bootstrap 2> /var/log/docker-bootstrap.log 1> /dev/null &'

sleep 5

# this shit is required otherwise nothing works
docker -H unix:///var/run/docker-bootstrap.sock stop myetcd myflanneld
docker -H unix:///var/run/docker-bootstrap.sock rm myetcd myflanneld


# check if myetcd container exists
if [ -z "$(docker -H unix:///var/run/docker-bootstrap.sock ps -a | grep myetcd)" ]; then
  echo "myetcd does not exist"
  sudo docker -H unix:///var/run/docker-bootstrap.sock run --name=myetcd --net=host -d gcr.io/google_containers/etcd:2.0.12 /usr/local/bin/etcd --addr=127.0.0.1:4001 --bind-addr=0.0.0.0:4001 --data-dir=/var/etcd/data
  sleep 5
fi

sudo docker -H unix:///var/run/docker-bootstrap.sock run --rm --net=host gcr.io/google_containers/etcd:2.0.12 etcdctl set /coreos.com/network/config '{ "Network": "10.1.0.0/16" }'

# check if myflanneld container exists
if [ -z "$(docker -H unix:///var/run/docker-bootstrap.sock ps -a | grep myflanneld)" ]; then
  echo "myflanneld does not exist"
  flannelCID=$(docker -H unix:///var/run/docker-bootstrap.sock run --name=myflanneld -d --net=host --privileged -v /dev/net:/dev/net quay.io/coreos/flannel:0.5.0 /opt/bin/flanneld -iface="wlan1")
  sleep 8
else
  echo "myflanneld exists"
  flannelCID=$(docker -H unix:///var/run/docker-bootstrap.sock inspect -f '{{ .Id }}' myflanneld)
  echo ${flannelCID}
fi


docker -H unix:///var/run/docker-bootstrap.sock cp ${flannelCID}:/run/flannel/subnet.env .
source subnet.env

DOCKER_OPTS="--tlsverify --tlscacert=/etc/docker/certs.d/ca.pem --tlscert=/etc/docker/certs.d/server-cert.pem --tlskey=/etc/docker/certs.d/server-key.pem -H=0.0.0.0:2376 -H unix:///var/run/docker.sock"
DOCKER_OPTS="$DOCKER_OPTS --mtu=${FLANNEL_MTU} --bip=${FLANNEL_SUBNET}"

sudo service docker stop 

ifconfig docker0 down
brctl delbr docker0

sleep 5

docker daemon $DOCKER_OPTS

