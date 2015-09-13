# Set up local Docker
see setup_docker_tls/

# Set up registry
see setup_private_docker_registry/

# local base java image
see gschw.busybox-java/

#

# Setting up local docker
## Installing docker
Check docker site, ubuntu did not provide me with the recent version, while fedora did.

## HTTPS
Followed [instructions](https://docs.docker.com/articles/https/) from an official site. Used my hostname as CN.

Also, check that it is possible to connect from the different machine to this one to port 2376.



# Installing private registry
## Downloading one
Downloaded as a [manual](https://github.com/docker/distribution/blob/master/docs/deploying.md) says
``` sh
docker pull registry:2
```

## Self-signed certificate
Generated self-signed certificate (check [here](https://github.com/docker/distribution/blob/master/docs/insecure.md))

Copied domain.* to /etc/docker/certs.d/registry/

## HTTP password
Generated an HTTP-password as [manual](https://github.com/docker/distribution/blob/master/docs/deploying.md) says.

Copied htpasswd to /opt/docker-registry/auth/

## Starting an image
Installed docker-compose ([manual](https://docs.docker.com/compose/install/))

Ok, followed further instruction from the [manual](https://github.com/docker/distribution/blob/master/docs/deploying.md)
So, my docker-compose.yml looks like this
```
registry:
  restart: always
  image: registry:2
  ports:
    - 55000:5000
  environment:
    REGISTRY_HTTP_TLS_CERTIFICATE: /certs/domain.crt
    REGISTRY_HTTP_TLS_KEY: /certs/domain.key
    REGISTRY_STORAGE_FILESYSTEM_ROOTDIRECTORY: /var/lib/registry
    REGISTRY_AUTH_HTPASSWD_PATH: /auth/htpasswd
    REGISTRY_AUTH_HTPASSWD_REALM: Registry Realm
    REGISTRY_AUTH: htpasswd
  volumes:
    - /opt/docker-registry/data:/var/lib/registry
    - /etc/docker/certs.d/registry:/certs
    - /opt/docker-registry/auth:/auth
```

Run docker-compose up -d


