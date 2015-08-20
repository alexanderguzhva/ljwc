FROM gschw/busybox-java

EXPOSE 11260
EXPOSE 19260

ADD build/distributions/ljwc-ljscheduler-0.01.tar /

ENTRYPOINT ["/ljwc-ljscheduler-0.01/bin/ljwc-ljscheduler", "server", "/ljwc-ljscheduler-0.01/config/application.yaml"]
