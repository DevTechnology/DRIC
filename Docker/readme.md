# Table of Contents
 * [Scripts Explanation](#scripts-explanation)
 * [Compile and Start](#compile-and-start)

# Scripts Explanation
You will need [CURL](http://curl.haxx.se/docs/manual.html) installed to run some of the scripts.

##update-control-scripts.sh
This script updates all of the scripts needed to compile and run the Docker instance (it doesn't update itself). Utilizes CURL -O and runs CHMOD to make the scripts rwxr-xr-x permission based.

##build-dric-container.sh
This script builds the Docker image without packaging a war file and utilizes CURL -O to download "dockerfile" from GitHub before execution. In addition to the default script, you can also run with a "prod" or "debug" argument:
 * *sudo ./build-dric-container.sh prod* - This builds the production version. It downloads the war file specified at the top of the script to the local directory and packages it with the container.
 * *sudo ./build-dric-container.sh debug* - This does not package the war, but exposes debug port 8787 as well as 8080/9990

##start-jboss.sh
This script starts the Docker JBoss Wildfly image as name "dric". It looks for the "apikey.properties" file in the same directory. If it exists it will import the openFDA key from the specified location and use that value to override the default docker CMD to include the key value as a -D JVM property. It will map the JBoss 8080 and 9990 ports to the host. This is fine for quick tests. Production environments should look into the normal dynamic port mapping.

This script also renames the current "dric" instance with the current timestamp so that rollbacks are easy and history is maintained. Note: if there is no "dric" instance (i.e. first time you run this) it will output a failure for the command. The script does not stop or care about this failure.

###apikey.properties
The following is the simple way to insert the key given the short timeframe:

 *apikey.properties format (see sample.apikey.properties file):
  *fdaapikey=ADSAS34fdafDf32

# Compile and Start

These instructions are assumed to be executed on Fedora Linux distributions and 
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

> 4) Execute the Docker build by completing the following
> * This will download the correct Docker file from GitHub and build it
 
```
	> sudo ./build-dric-container.sh prod
		* Using the "prod" parameter downloads the latest official release to deploy to the container. You can also use the debug parameter or run the script with no parameters (default), but you must then deploy the war to the container either by using the Maven scripts or manually.
```

> 5) Execute JBoss by doing the following
 
```
	> sudo ./start-jboss.sh
		* The first time you run this script, it will fail on renaming the current instance. This can be ignored.
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

Notes: This is a basic JBoss Wildfly instance with no SSL or special configurations. For production 
		environments it is recommended to harden the configuration and make it fault tolerant.
