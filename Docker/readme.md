These instructions are assumed to be executed on fedora core linux distributions and 
do not cover how to install Docker or the OS itself. It is also assumed to be running as
non root with a user with sudo priveleges.

1. On a linux fedora core distro: Install Docker (https://docs.docker.com/installation/)
2. Download the Control Scripts at: https://github.com/DevTechnology/DRIC/tree/master/Docker/ControlScripts
	1. curl -O https://raw.githubusercontent.com/DevTechnology/DRIC/master/Docker/ControlScripts/update-control-scripts.sh
	2. sudo chmod 755 *.sh
	3. sudo ./update-control-scripts.sh
3. Make sure all scripts are 755 for permissions
	1. ls -l (each .sh file should be -rwxr-xr-x)
	2. sudo chmod 755 *.sh (if not correct)
4. Execute the docker build (this will download the docker file and build it).
	1. sudo ./build-dric-container.sh
5. Execute jboss, this will map the jboss 8080 and 9990 ports to the host (this is fine for quick tests, 
	production environments should look into the normal dynamic port mapping)
	1. sudo ./start-jboss.sh
6. Verify running state
	1. sudo docker ps
		* You should see the name "dric" running
	2. http://<IP>:8080 (default jboss welcome) and http://<IP>:8080/dric

Notes: This is a basic Jboss wildfly instance with no SSL or special configurations. For production 
		environments it's recommended to harden the configuration and make it fault tolerant.
