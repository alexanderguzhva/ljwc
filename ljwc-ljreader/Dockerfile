FROM gschw/busybox-java

EXPOSE 11090
EXPOSE 19090

ADD build/distributions/ljwc-ljreader-0.01.tar /

ENTRYPOINT ["/ljwc-ljreader-0.01/bin/ljwc-ljreader", "server", "/ljwc-ljreader-0.01/config/application.yaml"]
