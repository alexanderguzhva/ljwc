[use https, luke](https://github.com/kubernetes/kubernetes/issues/7954)

also, ~/.docker/config.json should be moved to ~/.dockercfg and edited

then, run "cat .dockercfg | base64" and paste it into as a secret. RTFM.