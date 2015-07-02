#Description:
There are 3 Logging Levels, The first one is basic Networking. The second one is System and the third is Application. Cloudwatch is used for collecting the log in a singular spot.

##Level 1   
Currently only Network Flow Logs are captured, http://docs.aws.amazon.com/AmazonVPC/latest/UserGuide/flow-logs.html .

###AWS EC2:
* Flow Log:
   * Accept Flow Log on Instance 1 & 2
   * Reject Flow Log on Instance 1 & 2

###AWS ELB:
  * Flow Log:
   * Accept Flow Log on Availability Zone 1 & 2
   * Reject Flow Log on Availability Zone 1 & 2
   
## Level 2 (Not Implimented)
This level is a future item, this revolves around setting up AWS Clouwatch output of System Logs to S3.

## Level 3 (Not Implimented)
This level is a future item, this revolves around setting up AWS Clouwatch output of JBoss/Docker Logs to S3.
