 #!/bin/bash
 
#Download from GIT
curl -O  https://raw.githubusercontent.com/DevTechnology/DRIC/master/Docker/dockerfile

#Execute the build
docker build -f dockerfile --tag=dtg/dric .

