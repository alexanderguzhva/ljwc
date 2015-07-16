FROM gschw/busybox-java

EXPOSE 11240
EXPOSE 19240

ADD build/distributions/ljwc-uploader-0.01.tar /
ADD src/main/resources/application.yaml /

ENTRYPOINT ["/ljwc-uploader-0.01/bin/ljwc-uploader", "server", "/application.yaml"]
