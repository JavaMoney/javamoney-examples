javamoney-javafx-binding
========================

**NOTE: This example is in progress, handle with care for now...**

What is it?
-----------

This is your project! It is a sample, runnable Maven 3 project to help you get your foot in the door developing with JSR 354 on JavaFX. 

It was inspired by a <a href="http://www.ustream.tv/recorded/28943732">Nordic NightHacking</a> session with Stephen Chin in Copenhagen. Please watch the video stream for further reference before starting to use this example.

System requirements
-------------------

All you need to build this project is **Java 8.0 (Java SDK 1.8)** or an equivalent Java version with **JavaFX**, Maven 3.0 or better.

The application this project produces is designed to be run on JavaFX 2 or above.

Setting up the project
-------------------

In order to make Maven build the project, you need to set it to a Java 8 compliant JVM or another runtime environment including JavaFX. 

Steps for Eclipse
- - -
Under "Java Build Path" of "Project Properties" select the JRE System Library in the "Libraries" tab.
![Image](/src/site/resources/images/Eclipse_JRE8_1.png "Java Build Path in Eclipse")


Click "Edit" and select "jdk 1.8.0" or equivalent from the "Alternate JRE" list. Should the "Workspace default JRE" state something like "jdk 1.8.0" or "jre8", you may use that, too. Only if your Eclipse version already supports Java 8 and above, you may also select "JavaSE-1.8" or an appropriate similar value in "Execution environment".

![Image](/src/site/resources/images/Eclipse_JRE8_2.png "Edit Library in Eclipse")