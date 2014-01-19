# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
# 
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

#!/bin/sh -Eux

#  Trap non-normal exit signals: 1/HUP, 2/INT, 3/QUIT, 15/TERM, ERR
trap founderror 1 2 3 15 ERR

founderror()
{
        exit 1
}

exitscript()
{
        #remove lock file
        #rm $lockfile
        exit 0
}

apt-get update
apt-get install -y vim git wget screen curl

#######################################################################################################################
sudo apt-get -y update
sudo apt-get install -y software-properties-common python-software-properties
sudo add-apt-repository -y ppa:webupd8team/java
sudo apt-get -y update
sudo /bin/echo debconf shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
sudo apt-get -y install oracle-java7-installer oracle-java7-set-default
#######################################################################################################################

sudo chmod a+rw -R /opt
sudo mkdir -p /opt/apache
sudo chmod a+rw -R /opt/apache
sudo apt-get install -y g++ python2.7-dev libcppunit-dev libunwind7-dev git
sudo apt-get update && sudo apt-get install -y libsasl2-modules libsasl2-dev autotools-dev libltdl-dev libtool 
sudo apt-get update && sudo apt-get install -y autoconf autopoint make libcurl4-openssl-dev
cd /opt/apache
git clone git://git.apache.org/mesos.git
cd /opt/apache/mesos
sudo ./bootstrap
sudo ./configure
sudo make
sudo chmod a+rw /opt/apache
curl -fL https://raw.github.com/mesosphere/mesos-docker/master/bin/mesos-docker-setup | sudo bash

curl -s https://get.docker.io/ubuntu/ | sudo sh

exitscript
