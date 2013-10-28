javamoney-ez
========================

What is it?
-----------

This is an improved successor to [EZ Money](http://www.downloadcollection.com/ez_money.htm "EZ Money"). 

System requirements
-------------------

All you need to build this project is **Java 6.0**, Maven 3.0 or better.

The application this project produces is designed to be run on Java Swing.

Setting up the project
-------------------

In order to make Maven build the project, you need to select a Java 8 compliant JVM or another runtime environment including JavaFX. 

###Steps for Command Line
If you run Maven from the command line instead of an IDE like Eclipse or NetBeans, please ensure, the `JAVA_HOME` environment variable points to an appropriate version of Java 8 or above. Either JRE or JDK as long as it contains JavaFX.

###Steps for Eclipse
Add "jdk 1.8.0" or equivalent like "jre8" to your "Installed JREs" in Eclipse, if they don't exist there. 
![Image](../src/site/resources/images/Eclipse_JRE8_0_1.png "Add JRE to Eclipse")