javamoney-tradingapp
========================

**NOTE: This example is in progress, handle with care for now...**

What is it?
-----------

This is your project! It is a sample, deployable Maven 3 project to help you get your foot in the door developing with Spring and Java Financial Library [link text here (jfl-USAGE.txt) on Java Enterprise Edition. 

This project is setup to allow you to create a compliant Spring 3.1 application using Spring MVC.

System requirements
-------------------

All you need to build this project is Java 6.0 (Java SDK 1.6) or higher, Maven 3.0 or higher.

The application this project produces is designed to be run on a **Java EE 6** container server like Tomcat, Jetty or JBoss AS 7.1. 

Start JBoss Enterprise Application Platform 6 or JBoss AS 7.1
-------------------------

1. Open a command line and navigate to the root of the JBoss server directory.
2. The following shows the command line to start the server with the web profile:

        For Linux:   JBOSS_HOME/bin/standalone.sh
        For Windows: JBOSS_HOME\bin\standalone.bat


Build and Deploy the Application
-------------------------

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. Type this command to build and deploy the archive:

        mvn clean package jboss-as:deploy

4. This will deploy `target/javamoney-tradingapp.war` to the running instance of the server.


Access the application 
---------------------
 
The application will be running at the following URL: <http://localhost:8080/javamoney-tradingapp/>.


Undeploy the Archive
--------------------

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. When you are finished testing, type this command to undeploy the archive:

        mvn jboss-as:undeploy


Debug the Application
------------------------------------

If you want to debug the source code or look at the Javadocs of any library in the project, run either of the following commands to pull them into your local repository. The IDE should then detect them.

        mvn dependency:sources
        mvn dependency:resolve -Dclassifier=javadoc

