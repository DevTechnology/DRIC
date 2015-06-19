#!/bin/bash  

# Read a property from a property file
# @param propertyName
# @param fileName
function getPropertyFromFile()
{
# substitute "." with "\." so that we can use it as sed expression
fileName=$2;
cat $fileName | sed -n -e "s/^[ ]*//g;/^#/d;s/^$1=//p" | tail -1
echo "bob"
}

#API Key File
file="apikey.properties"

#Read the API keys if they exist, due to time not iterative but declared
if [ -f "$file" ]
then
	fdaapikey=$(getPropertyFromFile openfda $file)
fi

#Broken in Docker 1.6.0, need 1.6.2 binary
#sudo docker run -d -p 8080:8080 -p 9990:9990 -v /usr/jboss/deployments:/opt/jboss/wildfly/standalone/deployments/:rw dtg/dric

#Grab the current time
current_time=$(date "+%Y.%m.%d-%H.%M.%S")

#Rename the previous started instance to X_time, for historical record keeping
sudo docker rename dric dric_$current_time

#Run it - Override the dockerfile CMD line with a custom -D, this allows the most flexability to
# input the API keys during runtime. There are better methods (encrypted XML files inserted during 
# docker compile time and stored in a secured GIT instance), but due to time this will suffice.
if [ -f "$file" ]
then
echo sudo docker run -d -p 8080:8080 -p 9990:9990 --name dric dtg/dric /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0 -DopenFdaApiKey=$fdaapikey
else
sudo docker run -d -p 8080:8080 -p 9990:9990 --name dric dtg/dric
fi