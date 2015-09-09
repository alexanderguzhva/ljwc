# based on https://github.com/kubernetes/kubernetes/blob/master/docs/getting-started-guides/docker-multinode/worker.sh
# --restart=always was a MISTAKE
# this machine is fedora 20

sudo sh -c 'docker -d -H unix:///var/run/docker-bootstrap.sock -p /var/run/docker-bootstrap.pid --iptables=false --ip-masq=false --bridge=none --graph=/var/lib/docker-bootstrap 2> /var/log/docker-bootstrap.log 1> /dev/null &'

sleep 5

# this shit is required also
docker -H unix:///var/run/docker-bootstrap.sock stop myflanneld
docker -H unix:///var/run/docker-bootstrap.sock rm myflanneld

# check if myflanneld exists
if [ -z "$(docker -H unix:///var/run/docker-bootstrap.sock ps -a | grep myflanneld)" ]; then
  echo "myflanneld does not exist"
  flannelCID=$(sudo docker -H unix:///var/run/docker-bootstrap.sock run --name=myflanneld -d --net=host --privileged -v /dev/net:/dev/net quay.io/coreos/flannel:0.5.0 /opt/bin/flanneld --etcd-endpoints=http://linux-xp13:4001 -iface="wlo1")
  sleep 8
else
  echo "myflannel exists"
  flannelCID=$(docker -H unix:///var/run/docker-bootstrap.sock inspect -f '{{ .Id }}' myflanneld)
  echo ${flannelCID}
fi


sudo docker -H unix:///var/run/docker-bootstrap.sock cp ${flannelCID}:/run/flannel/subnet.env .
source subnet.env

systemctl stop docker

ifconfig docker0 down

brctl delbr docker0 

docker -d --selinux-enabled --mtu=${FLANNEL_MTU} --bip=${FLANNEL_SUBNET}

#kill $(cat /var/run/docker-bootstrap.pid)