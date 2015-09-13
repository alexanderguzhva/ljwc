# Installing private registry
## Downloading one
Downloaded as a [manual](https://github.com/docker/distribution/blob/master/docs/deploying.md) says
``` sh
docker pull registry:2
```

## Self-signed certificate
- runme.sh generates self-signed certificates (check [here](https://github.com/docker/distribution/blob/master/docs/insecure.md))
- e

Copied domain.* to /etc/docker/certs.d/registry/

