# Set up vagrant machines
The main difference is that I want to have several computers with Vagrant installed, not a single one.

[source 1](https://github.com/coreos/coreos-vagrant)

[source 2](https://github.com/pires/kubernetes-vagrant-coreos-cluster)

Check master/ for master node config.

Check minion/ for minion node config.

Ubuntu 14.04 does not work as expected, vagrant (virtualbox) does not assign address as expected.
For example, on fedora 21 this config works almost as expected, both VM and my wifi-router work fine.
However, there is something wrong on Ubuntu 14. Maybe, this is because of wlan-interface on my linux-xp13.
```
      config.vm.network "public_network", bridge: "wlo1", nictype: "82545EM", ip: "192.168.1.26", :mac => "5A1122330011"
```




