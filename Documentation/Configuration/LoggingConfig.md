#Description:
Level 1 Logging is handled through AWS Services.

##Level 1   
Cloudwatch is used for collecting the logs. Currently only Network Flow Logs are captured, http://docs.aws.amazon.com/AmazonVPC/latest/UserGuide/flow-logs.html .

###AWS EC2:
* Flow Log:
   * Accept Flow Log on Instance 1 & 2
   * Reject Flow Log on Instance 1 & 2

###AWS ELB:
  * Flow Log:
   * Accept Flow Log on Availability Zone 1 & 2
   * Reject Flow Log on Availability Zone 1 & 2
   
## Level 2 (Not Implimented)
This level is a future item, this revolves around setting up AWS Clouwatch output of JBoss/Docker and System Logs to S3.
