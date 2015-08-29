VERSION=0.01

function runit {
    xterm -e $1/build/distributions/$1-$VERSION/bin/$1 server $1/build/distributions/$1-$VERSION/config/$2 &
}

runit ljwc-datagrabber application.yaml
runit ljwc-gateway application.yaml
runit ljwc-htmlparser application.yaml
runit ljwc-hbasebridge application.yaml
runit ljwc-ljagent application.yaml
runit ljwc-ljcalendaragent application.yaml
runit ljwc-ljexecutor application.yaml
runit ljwc-ljreader application_hbase.yaml
runit ljwc-ljscheduler application.yaml
runit ljwc-ljuploader application_hbase.yaml
