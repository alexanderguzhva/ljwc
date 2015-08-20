FROM gschw/busybox-java

EXPOSE 11020
EXPOSE 19020

ADD build/distributions/ljwc-htmlparser-0.01.tar /

ENTRYPOINT ["/ljwc-htmlparser-0.01/bin/ljwc-htmlparser", "server", "/ljwc-htmlparser-0.01/config/application.yaml"]
