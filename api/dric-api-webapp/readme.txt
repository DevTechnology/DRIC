This project is a REST API and HTML UI for the Drug Recall Information Center (DRIC)

The project was creating using the following Maven command:
mvn archetype:generate -DarchetypeArtifactId=jersey-quickstart-webapp -DarchetypeGroupId=org.glassfish.jersey.archetypes -DinteractiveMode=false -DgroupId=com.devtechnology -DartifactId=dric-api-webapp -Dpackage=com.devtechnology -DarchetypeVersion=2.18

The web.xml and pom.xml were modified to support using org.jboss.resteasy:resteasy-jaxrs:2.3.1.GA due to compatibility issues with JBoss 7.1 and Jersey.

The REST API (DricApi.java) has a method to get drug recall data with optional filter parameters. The data is gathered from the Open FDA Drug Enforcement service (https://open.fda.gov/drug/enforcement).
The second API method uses the RxImage service (http://rximage.nlm.nih.gov/docs/doku.php) to request any image URLs that apply to the passed NDC.
