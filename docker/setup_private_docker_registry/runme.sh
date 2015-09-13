openssl req \
   -newkey rsa:4096 -nodes -sha256 -keyout registry.key \
   -x509 -days 365 -out registry.crt -subj "/CN=linux-xp13/CN=linux-xp13.vpn"




