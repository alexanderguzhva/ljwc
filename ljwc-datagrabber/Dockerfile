FROM gschw/busybox-java

EXPOSE 11250
EXPOSE 19250

ADD build/distributions/ljwc-datagrabber-0.01.tar /
ADD src/main/resources/application.yaml /

ENTRYPOINT ["/ljwc-datagrabber-0.01/bin/ljwc-datagrabber", "server", "/application.yaml"]
