# WTF is that
This document briefly describes how to set up private docker registry.

# Download Docker image
Downloaded as a [manual](https://github.com/docker/distribution/blob/master/docs/deploying.md) says
``` sh
docker pull registry:2
```

# Generate self-signed certificate
- runme.sh generates self-signed certificates (check [here](https://github.com/docker/distribution/blob/master/docs/insecure.md))
- runme_copy_certs.sh copies self-signed certificates to /etc/...


# Set up HTTP native basic auth
[source](https://github.com/docker/distribution/blob/master/docs/deploying.md).
```sh
sudo mkdir -p /opt/docker-registry/auth
docker run --rm --entrypoint htpasswd registry:2 -Bbn testuser testpassword > htpasswd
sudo /bin/cp -rf htpasswd /opt/docker-registry/auth/htpasswd
```

# Starting the image
Install docker-compose ([manual](https://docs.docker.com/compose/install/))

Followed further instructions from the [manual](https://github.com/docker/distribution/blob/master/docs/deploying.md)

docker-compose.yml contains instructions for docker-compose

Run
```sh
docker-compose up -d
```






