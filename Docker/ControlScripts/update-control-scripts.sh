 #!/bin/bash
 
#Download from GIT
curl -O  https://raw.githubusercontent.com/DevTechnology/DRIC/DRIC-v1.1-Beta-Release/Docker/ControlScripts/build-dric-container.sh
curl -O  https://raw.githubusercontent.com/DevTechnology/DRIC/DRIC-v1.1-Beta-Release/Docker/ControlScripts/start-jboss.sh
curl -O  https://raw.githubusercontent.com/DevTechnology/DRIC/DRIC-v1.1-Beta-Release/Docker/ControlScripts/stop-jboss.sh

#Make the scripts excutable
chmod 755 *.sh
