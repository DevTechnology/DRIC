 #!/bin/bash
 
 #CD and Download from GIT
cd ~/dric/container
curl -O  https://raw.githubusercontent.com/DevTechnology/DRIC/master/Docker/dockerfile

#Execute the build
sudo docker build -f /home/centos/dric/container/dockerfile --tag=dtg/dric .

#Go back home
cd ~
