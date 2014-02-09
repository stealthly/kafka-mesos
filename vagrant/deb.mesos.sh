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
apt-get install -y vim git wget screen curl python-setuptools python-protobuf curl default-jre

#######################################################################################################################
apt-get -y update
apt-get install -y software-properties-common python-software-properties
add-apt-repository -y ppa:webupd8team/java
apt-get -y update
/bin/echo debconf shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections
apt-get -y install oracle-java7-installer oracle-java7-set-default
#######################################################################################################################

curl -sSfL http://downloads.mesosphere.io/master/ubuntu/12.04/mesos_0.16.0_amd64.deb --output /tmp/mesos.deb
dpkg -i /tmp/mesos.deb

#sudo /usr/local/sbin/mesos-master --zk=zk://192.168.57.10:2181,/etc --ip=192.168.57.10

#sudo /usr/local/sbin/mesos-slave --master=zk://192.168.57.10:2181,/etc --ip=192.168.57.5

exitscript
