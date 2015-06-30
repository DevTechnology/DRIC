 #!/bin/bash
 
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
 
#Download from GIT
curl -O  https://raw.githubusercontent.com/DevTechnology/DRIC/master/Docker/$dockerfile

#Execute the build
docker build -f $dockerfile --tag=dtg/dric .

