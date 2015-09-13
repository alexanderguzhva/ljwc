# Protect the Docker daemon socket
[source](https://github.com/docker/docker/blob/master/docs/articles/https.md)

- runme.sh generates certificates
- runme_copy_certs.sh copies certificates to /etc/...
- docker_opts contains additional command line arguments for docker daemon