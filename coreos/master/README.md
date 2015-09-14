# Set up master node

[source 1](https://github.com/coreos/coreos-vagrant)

Master node has IP 192.168.1.6. Hostname is core-linux-xp13-01. VM is located on linux-xp13.

## Vagrantfile
Something is still wrong
```
config.vm.network "public_network", bridge: "wlan1", nictype: "82545EM", ip: "192.168.1.6", :mac => "5A1122330001"
```

### Provision
* client certificates for connecting to Docker on host
```
[core@docker -H linux-xp13.vpn:2376 --tlsverify version
```

* ~/.docker/config.json from the host machine. I executed the following command on the host node (linux-xp13):
```
docker login linux-xp13.vpn:55000
```

* certificate for connecting to registry on the host machine
```
docker pull linux-xps.vpn:55000/busybox:latest
```





