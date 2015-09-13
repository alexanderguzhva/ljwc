sudo mkdir -p /etc/docker/certs.d
sudo /bin/cp -rf ca.pem /etc/docker/certs.d/
sudo /bin/cp -rf server-cert.pem /etc/docker/certs.d/
sudo /bin/cp -rf server-key.pem /etc/docker/certs.d/
