FROM gschw/busybox-java

EXPOSE 11210
EXPOSE 19210

ADD build/distributions/ljwc-httprnggenerator-0.01.tar /
ADD src/main/resources/application.yaml /

ENTRYPOINT ["/ljwc-httprnggenerator-0.01/bin/ljwc-httprnggenerator", "server", "/application.yaml"]
