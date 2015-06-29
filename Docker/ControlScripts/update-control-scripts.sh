 #!/bin/bash
 
#Download from GIT
curl -O  https://raw.githubusercontent.com/DevTechnology/DRIC/master/Docker/ControlScripts/build-dric-container.sh
curl -O  https://raw.githubusercontent.com/DevTechnology/DRIC/master/Docker/ControlScripts/start-jboss.sh
curl -O  https://raw.githubusercontent.com/DevTechnology/DRIC/master/Docker/ControlScripts/stop-jboss.sh

#Make the scripts excutable
chmod 755 *.sh