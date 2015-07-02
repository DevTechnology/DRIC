# Maven Build & Deploy

## Table of Contents
 * [Project Information](#project-information)
 * [Build and Deploy](#build-and-deploy)

##Project Information
This project is a REST API and HTML UI for the Drug Recall Information Center (DRIC)

##How it was originally generated
The project was created using the following Maven command:
mvn archetype:generate -DarchetypeArtifactId=jersey-quickstart-webapp -DarchetypeGroupId=org.glassfish.jersey.archetypes -DinteractiveMode=false -DgroupId=com.devtechnology -DartifactId=dric-api-webapp -Dpackage=com.devtechnology -DarchetypeVersion=2.18

The web.xml and pom.xml were modified to support using org.jboss.resteasy:resteasy-jaxrs:2.3.1.GA due to compatibility issues with JBoss wildfly and Jersey.

##API Information
The REST API (DricApi.java) has a method to get drug recall data with optional filter parameters. The data is gathered from the Open FDA Drug Enforcement service (https://open.fda.gov/drug/enforcement). The second API method uses the RxImage service (http://rximage.nlm.nih.gov/docs/doku.php) to request any image URLs that apply to the passed NDC.


#Build and Deploy
These instructions are assumed to be executed on a system with Maven 3.3.3 installed and configured properly. See: https://maven.apache.org/download.cgi :in order to download and setup a copy.

##Compile:
1. Download all the code from: https://github.com/DevTechnology/DRIC.git repository
2. via Command Line (Dos or Shell) goto the sub directory of  /api/dric-api-webapp
3. Execute the build: mvn package

##Deploy Setup:
You can do one of the following:

* #1 Edit: /api/dric-api-webapp/pom.xml
 * Replace: 		<hostname>${wildfly-hostname}</hostname>	with the correct hostname/IP (or IP)
 * Replace: 		<port>${wildfly-port}</port>				with the correct admin port (docker default: 9990)
 * Replace: 		<username>${wildfly-username}</username>	with the correct username (docker default: admin)
 * Replace: 		<password>${wildfly-password}</password>	with the correct password (docker default: Admin#70365)
* #2 Edit your Maven Settings (settings.xml) by adding in profiles:
 * The settings file is located at one of the following [maven settings](https://maven.apache.org/settings.html): 
   * The Maven install: $M2_HOME/conf/settings.xml
    * A user’s install: ${user.home}/.m2/settings.xml
 * Change the hostname/port/username/password appropriately.
```xml
	<profiles>
		<profile>
			<id>wildfly-remote</id>
			<properties>
				<wildfly-hostname>127.0.0.1</wildfly-hostname>
				<wildfly-port>9990</wildfly-port>
				<wildfly-username>admin</wildfly-username>
				<wildfly-password>Admin#70365</wildfly-password>
			</properties>
		</profile>
		<profile>
			<id>wildfly-local</id>
			<properties>
				<wildfly-hostname>127.0.0.1</wildfly-hostname>
				<wildfly-port>9990</wildfly-port>
				<wildfly-username>admin</wildfly-username>
				<wildfly-password>Admin#70365</wildfly-password>
			</properties>
		</profile>
	</profiles>
```
* #3 Copy: /api/dric-api-webapp/settings.xml to your 
  * The Maven install: $M2_HOME/conf/settings.xml
  * A user’s install: ${user.home}/.m2/settings.xml
		
## Deploy to Local Wildfly
1. Download all the code from: https://github.com/DevTechnology/DRIC.git repository
2. via Command Line (Dos or Shell) goto the sub directory of  /api/dric-api-webapp
3. Execute the build: mvn wildfly:deploy -P wildfly-local
 * -P option on the mvn command line specifies the Profile to use

## Compile and Deploy
1. Download all the code from: https://github.com/DevTechnology/DRIC.git repository
2. via Command Line (Dos or Shell) goto the sub directory of  /api/dric-api-webapp
3. Execute the build: mvn package wildfly:deploy -P wildfly-local
 * -P option on the mvn command line specifies the Profile to use
