 #!/bin/bash
 
# These 2 properties specify the version to build, change if wanted/necessary
file="dric-api-webapp-1.1-SNAPSHOT.war"
version="v1.1"

# You can specify the type of file you want to use, test/debug or prod. Valid parms are "debug", "prod" or no parameter.
# Example: sudo ./build-dric-container.sh debug
if [ -z "$1" ]
  then
    dockerfile=dockerfile
  elif [ "$1" == "debug" ]
  		then
    		dockerfile=dockerfile.debug
  elif [ "$1" == "prod" ]
  		then
    		dockerfile=dockerfile.prod
  else
  	echo "$1 is not a valid paramater, only debug or prod supported."
  	exit
fi
 
#Download dockerfile from GIT
curl -O  https://raw.githubusercontent.com/DevTechnology/DRIC/master/Docker/$dockerfile

#Download war from GIT if PROD
if [ "$dockerfile" == "dockerfile.prod" ]
  		then
    		curl -LO https://github.com/DevTechnology/DRIC/releases/download/$version/$file
    		if [ ! -f "$file" ]
    			then
    				echo "$file not found"
    				exit
    			else
    				chmod 755 $file
    				mv $file dric-api-webapp.war
    		fi
fi


#Execute the build
docker build -f $dockerfile --tag=dtg/dric .

