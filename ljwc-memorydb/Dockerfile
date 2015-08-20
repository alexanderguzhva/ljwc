FROM gschw/busybox-java

EXPOSE 11010
EXPOSE 19010

ADD build/distributions/ljwc-memorydb-0.01.tar /

ENTRYPOINT ["/ljwc-memorydb-0.01/bin/ljwc-memorydb", "server", "/ljwc-memorydb-0.01/config/application.yaml"]
