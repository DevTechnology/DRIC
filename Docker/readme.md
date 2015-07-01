#Scripts Explanation
You will need [CURL](http://curl.haxx.se/docs/manual.html) installed to run some of the scripts.

##update-control-scripts.sh
This script updates all the scripts needed to compile and run the docker instance (it doesn't update itself). Utilizes CURL -O and runs CHMOD to make the scripts rwxr-xr-x permission based.

##build-dric-container.sh
This script builds the docker image. Utilizes CURL -O to download "dockerfile" from github before execution.

Several options exist:
 * *sudo ./build-dric-container.sh prod* - This builds the prod version it downloads the war file specified at the top of the script to the local directory and packages it with the container.
 * *sudo ./build-dric-container.sh debug* - This does not package the war, but exposes debug port 8787 as well as 8080/9990
 * *sudo ./build-dric-container.sh* - This does not package the war, and is the default.

##start-jboss.sh
This script starts the Docker Jboss Wildfly image as name "dric". It looks for the "apikey.properties" file in the same directory and if it exists it will import the openFDA key from that and use that to override the default docker CMD to include the key value as a -D JVM property. It will map the jboss 8080 and 9990 ports to the host (this is fine for quick tests, production environments should look into the normal dynamic port mapping)

It also renames the current "dric" instance with the current timestamp so that rollbacks are easy and history can be kept. Note: if there is no "dric" instance (i.e. first time you run this) it will output a failure for the command, the script does not stop or care about this failure.

###apikey.properties
There are better methods of Adding the API keys during runtime for example encrypted XML files inserted during docker compile time and in a secured GIT instance but due to time this will suffice.

 *apikey.properties format (see sample.apikey.properties file):
  *fdaapikey=ADSAS34fdafDf32

#Compile and Start

These instructions are assumed to be executed on Fedora linux distributions and 
do not cover how to [install Docker](https://docs.docker.com/installation/) or the OS itself. It is also assumed to be running as non-root with a user with sudo priveleges.

Run the Instructions in the listed order.

> 1) Download the Control Scripts by executing the following:
 
```
	> curl -O https://raw.githubusercontent.com/DevTechnology/DRIC/master/Docker/ControlScripts/update-control-scripts.sh
	> sudo chmod 755 *.sh
	> sudo ./update-control-scripts.sh
```

> 2) Make sure all scripts are 755 for permissions by executing the following

```
	> ls -l (each .sh file should be -rwxr-xr-x)
```

> 3) If the scripts are not the correct permissions execute the following

```
	> sudo chmod 755 *.sh (if not correct)
```

> 4) Execute the docker build by doing the following
> * This will download the correct docker file from github and build it
 
```
	> sudo ./build-dric-container.sh prod
		* You can also use the debug and default ones, but you must manually or through maven scripts deploy the war to the container. "prod" parameter downloads the latest official release.
```

> 5) Execute jboss by doing the following
 
```
	> sudo ./start-jboss.sh
		* The first time you run this it will fail on renaming the current instance, this can be ignored.
```

> 6) Verify running state
 
```
	> sudo docker ps
		* You should see the name "dric" running
```

> 7) Connect to the running instance by running the following (make sure to insert your IP).

```
	> http://<your IP address here>:8080/dric
```

Notes: This is a basic Jboss wildfly instance with no SSL or special configurations. For production 
		environments it's recommended to harden the configuration and make it fault tolerant.
