FROM gschw/busybox-java

EXPOSE 11020
EXPOSE 19020

ADD build/distributions/ljwc-htmlparser-0.01.tar /
ADD src/main/resources/application.yaml /

ENTRYPOINT ["/ljwc-htmlparser-0.01/bin/ljwc-htmlparser", "server", "/application.yaml"]
