FROM gschw/busybox-java

EXPOSE 11270
EXPOSE 19270

ADD build/distributions/ljwc-ljagent-0.01.tar /
ADD src/main/resources/application.yaml /

ENTRYPOINT ["/ljwc-ljagent-0.01/bin/ljwc-ljagent", "server", "/application.yaml"]
