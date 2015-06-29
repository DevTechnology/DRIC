These instructions are assumed to be executed on a system with Maven 3.3.3 installed and configured properly. See: https://maven.apache.org/download.cgi :in order to download and setup a copy.

Compile:
1. Download all the code from: https://github.com/DevTechnology/DRIC.git repository
2. via Command Line (Dos or Shell) goto the sub directory of  /api/dric-api-webapp
3. Execute the build: mvn package

Deploy:
You can do one of the following:
1. Edit: /api/dric-api-webapp/pom.xml
	* Replace: 		<hostname>${wildfly-hostname}</hostname>	with the correct hostname/IP (or IP)
	* Replace: 		<port>${wildfly-port}</port>				with the correct admin port (docker default: 9990)
	* Replace: 		<username>${wildfly-username}</username>	with the correct username (docker default: admin)
	* Replace: 		<password>${wildfly-password}</password>	with the correct password (docker default: Admin#70365)
2. Edit your Maven Settings (settings.xml) by adding in:
	'<profiles>
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
	</profiles>'

	* The settings file is located at one of the following [maven settings](https://maven.apache.org/settings.html): 
		* The Maven install: $M2_HOME/conf/settings.xml
		* A user’s install: ${user.home}/.m2/settings.xml
	* Change the hostname/port/username/password appropriately.
3. 	Copy: /api/dric-api-webapp/settings.xml to your 
	* The Maven install: $M2_HOME/conf/settings.xml
	* A user’s install: ${user.home}/.m2/settings.xml
		



