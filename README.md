# ljwc
This is a test project that downloads LiveJournal blogs in a fully distributed mode. Implemented as a Dropwizard-based microservices system. HBase is used for storing data. Docker and k8s(?) are planned to be used for deployment.
0.5 TB of data downloaded already


# things to do:
1. (+) create ljwc test project that is implemented as a dropwizard-based microservices system
2. (+) install HBase on a master machine (one of laptops with hostname linux-xp13). ljwc is able to write data in HBase
3. (+) set up tls-enabled docker on master machine
4. (+) set up tls-enabled private docker registry v2
5. (+) add tasks to gradle build scripts in ljwc that allow to create and push docker images to private docker regsitry.
6. (*) create a vpn using openvpn
  1. (-) set up DNS
  2. (-) wlo1 on linux-xp13 provides no IP when this laptop is outside of WiFi network. This is wrong! There should be an IP available for openvpn bridging tests.
7. (+) create one\several virtual machines using vagrant on every laptop of mine.
- (-) Try to create one master and several minions on linux-xp13, just to make sure that it works fine.
- (-) VMs should have public_network attached to opevpn
- (+) VMs should have CoreOS installed
8. connect dockers for every VM using flannel
- (-) GET RID OF FKING FIREwALL on Fedora OS (linux-c56a) !!!!!! At this time it blocks all incoming connections until an outcoming appears. This screws inter-pings from CoreOS docker busybox
9. (-) Install kubernetes on every VM
- (-) Set up kubernetes DNS.
- (-) Make sure that kubernetes works
10. (*) Run ljwc on kubernetes. I have already tried to run some ljwc services on kubernetes-vagrant of google (it is broken, btw) and everything is fine
- (-) ljwc configuration should use kubernetes-exposed environment variables and DNS for host:port of REST services
11. (-) Find the way to expose ljwc-services from kubernetes to outside world. Is an external proxy (haproxy / nginx) required?
12. (*) make sure that fluentd/logstash + elasticsearch + kibana work as expected
13. (-) configuration service should be used (confd?).
14. ...
100. profit
