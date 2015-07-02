#Description:
Level 1 Monitoring is handled through AWS Services.

##Level 1
Cloudwatch is used for System and EBS Volume Metrics.
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
This level is a future item, this revolves around setting up Nagios and monitoring the internal logging state of Docker/Jboss and Application logs and other items. Utitilizing Logging Config Level 2, filters and alerts can be created on the Cloudwatch Log Groups
