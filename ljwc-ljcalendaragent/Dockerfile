FROM gschw/busybox-java

EXPOSE 11170
EXPOSE 19170

ADD build/distributions/ljwc-ljcalendaragent-0.01.tar /

ENTRYPOINT ["/ljwc-ljcalendaragent-0.01/bin/ljwc-ljcalendaragent", "server", "/ljwc-ljcalendaragent-0.01/config/application.yaml"]
