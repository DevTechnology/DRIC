#!/bin/bash  

#Broken in Docker 1.6.0, need 1.6.2 binary
#sudo docker run -d -p 8080:8080 -p 9990:9990 -v /usr/jboss/deployments:/opt/jboss/wildfly/standalone/deployments/:rw dtg/dric

#Grab the current time
current_time=$(date "+%Y.%m.%d-%H.%M.%S")

#Rename the previous started instance to X_time, for historical record keeping
sudo docker rename dric dric_$current_time

#Run it
sudo docker run -d -p 8080:8080 -p 9990:9990 --name dric dtg/dric
