This project is a REST API for DRIC services

The project was creating using the following Maven command:
mvn archetype:generate -DarchetypeArtifactId=jersey-quickstart-webapp -DarchetypeGroupId=org.glassfish.jersey.archetypes -DinteractiveMode=false -DgroupId=com.devtechnology -DartifactId=dric-api-webapp -Dpackage=com.devtechnology -DarchetypeVersion=2.18

The web.xml and pom.xml were modified to support using org.jboss.resteasy:resteasy-jaxrs:2.3.1.GA due to compatibility issues with JBoss 7.1