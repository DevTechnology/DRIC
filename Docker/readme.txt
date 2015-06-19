Install Docker (https://docs.docker.com/installation/)
Download the Control Scripts at: https://github.com/DevTechnology/DRIC/tree/master/Docker/ControlScripts
Place in a directory on target machine (normally just ~)
Make sure all scripts are 755 for permissions (chmod 755 *.sh)
Execute ./build-dric-container.sh , this will download the dockerfile and run the build command.
Execute ./start-jboss.sh, this will map the jboss 8080 and 9990 ports to the host (this is fine for quick tests, production environments should look into the normal dynamic port mapping)