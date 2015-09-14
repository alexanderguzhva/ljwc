# Protect the Docker daemon socket
[source](https://github.com/docker/docker/blob/master/docs/articles/https.md)

- runme.sh generates certificates
- runme_copy_certs.sh copies certificates to /etc/...
- docker_opts contains additional command line arguments for docker daemon. In case of Ubuntu 14.04, one needs to add arguments to /etc/default/docker

# Verify
In order to be able to connect to docker one needs to ca.pem, cert.pem and key.pem to /home/username/.docker