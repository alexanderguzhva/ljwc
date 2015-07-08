FROM gschw/busybox-java

EXPOSE 11010
EXPOSE 19010

ADD build/distributions/ljwc-memorydb-0.01.tar /
ADD src/main/resources/application.yaml /

ENTRYPOINT ["/ljwc-memorydb-0.01/bin/ljwc-memorydb", "server", "/application.yaml"]
