#Description:
There are 3 Levels to monitoring, The first level is basic system/application and volume statics. The second level requires more advanced intigration with the Logging Configuration (monitoring the logs via Cloudwatch). The 3rd level is integration with chef and nagios for more extensive system recovery and monitoring of specific application states.

##Level 1
Level 1 is configured purely as a default first step using AWS Cloudwatch Alarms.
http://docs.aws.amazon.com/AmazonCloudWatch/latest/DeveloperGuide/ConsoleAlarms.html

###AWS EC2:
  * NetworkOut >= 104,857,600 for 5 minutes
    * Actions:
     * Send message to topic "DEV_IT"  
  * NetworkIn >= 104,857,600 for 5 minutes
    * Actions:
     * Send message to topic "DEV_IT"  
  * CPUUtilization >= 90 for 10 minutes
    * Actions:
     * Send message to topic "DEV_IT"
  * StatusCheckFailed_System >= 1 for 2 minutes
    * Actions:
     * Action recover
     * Send message to topic "DEV_IT"
    
###AWS EBS Volume:

  * VolumeTotalReadTime >= 2 for 1 minute
    * Actions:
     * Send message to topic "DEV_IT"  
  * VolumeQueueLength >= 25 for 1 minute
    * Actions:
     * Send message to topic "DEV_IT"  

###AWS ELB:  

  * BackendConnectionErrors >= 2 for 3 minutes
    * Actions:
     * Send message to topic "DEV_IT"    
  * SurgeQueueLength >= 200 for 5 minutes
    * Actions:
     * Send message to topic "DEV_IT"      
  * Latency >= 60 for 1 minute
    * Actions:
     * Send message to topic "DEV_IT"      
  * UnHealthyHostCount < 0.5 for 3 minutes
    * Actions:
     * Send message to topic "DEV_IT"   
     
## Level 2 (Not Implimented)
This level is a future item, This revolves around integrating logging (level 1/2) into the cloudwatch alarms.

## Level 3 (Not Implimented)
This level is a future item, This revolves around setting up Nagios and monitoring the internal logging state of Docker/Jboss and Application logs and other items.
