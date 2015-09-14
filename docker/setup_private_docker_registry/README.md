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

# Saving login credentials
/etc/docker/certs.d/registry/registry.ca needs to be copied to /etc/docker/certs.d/linux-xp13.vpn:55000/ca.crt and /etc/docker/certs.d/linux-xp13:55000/ca.crt on every local, remote and virtual machine

Run
```sh
docker login linux-xp13.vpn:55000
```

As a result, ~/.docker/config.json will be produced

Next, .dockercfg needs to be produced. Somewhy, k8s wants old formats.
So, ~/.docker/config.json looks like this:
```
{
    "auths": {
        "https://linux-xp13:55000": {
            "auth": "somebase64",
            "email": "foo@bar.com"
        },
        "linux-xp13.vpn:55000": {
            "auth": "somebase64",
            "email": "foo@bar.com"
        }
    }
}
```

It needs to be converted into ~/.dockercfg:
```
{
    "https://linux-xp13:55000": {
        "auth": "somebase64",
        "email": "foo@bar.com"
    },
    "linux-xp13.vpn:55000": {
        "auth": "somebase64",
        "email": "foo@bar.com"
    }
}
```





