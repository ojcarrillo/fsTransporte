#!/bin/bash
echo "compila aplicacion"
mvn install package -Dmaven.test.skip=true
echo "======================================================="
echo "compila imagen docker - dk_fstransporte"
docker build -t dk_fstransporte .
echo "======================================================="
echo "corre imagen del contenedor"
docker run --name dk_fstransporte \
-p 2021:21 -p 2020:20 -p 12020:12020 -p 12021:12021 -p 12022:12022 -p 12023:12023 -p 12024:12024 -p 12025:12025  \
-e "USER=touresbalon" -e "PASS=verysecretpwd" \
-v /data/ftp:/ftp \
-it dk_fstransporte
