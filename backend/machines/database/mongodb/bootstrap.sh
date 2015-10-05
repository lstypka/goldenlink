#!/usr/bin/env bash
##
# Installs MongoDB for Ubuntu 14.04 (trusty)
##
echo '## '
echo '# Update repository list'
echo '## '
sudo apt-get update

echo '## '
echo '# Import the public key'
echo '## '
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 7F0CEB10

echo '## '
echo '# Create a list file for MongoDB'
echo '## '
echo "deb http://repo.mongodb.org/apt/ubuntu trusty/mongodb-org/3.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.0.list

echo '## '
echo '# Reload local package database'
echo '## '
sudo apt-get update

echo '## '
echo '# Install the MongoDB packages'
echo '## '
sudo apt-get install -y mongodb-org

echo '## '
echo '# Listen on all interfaces'
echo '## '
sed -i -- 's/bind_ip = 127.0.0.1/#bind_ip = 127.0.0.1/g' /etc/mongod.conf

echo '## '
echo '# Restart MongoDB'
echo '## '
sudo service mongod restart