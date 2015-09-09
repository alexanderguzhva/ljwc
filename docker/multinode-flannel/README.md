connect two machines using flannel and etcd

I should have used 'weave' instead 8\

ubuntu 14 is the master

fedora 20 is the slave. UDP port 8285 (flannel) should be opened on it (and TCP 4001 (etcd), I guess)