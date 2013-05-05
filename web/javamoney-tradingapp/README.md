javamoney-tradingapp
========================

**NOTE: This example is in progress, handle with care for now...**

What is it?
-----------

This is your project! It is a sample, deployable Maven 3 project to help you get your foot in the door developing with Spring and [Java Financial Library](#java-financial-library---api-usage) on Java Enterprise Edition using JSR 354

This project is setup to allow you to create a compliant Spring 3.1 application using Spring MVC.

System requirements
-------------------

All you need to build this project is Java 6.0 (Java SDK 1.6) or higher, Maven 3.0 or higher.

The application this project produces is designed to be run on a **Java EE 6** container server like Tomcat, Jetty or JBoss AS 7.1. 



1. **Maven 3rd party JARs**


   There are two 3rd Party JARs currently not available in MavenCentral or a similar Nexus. Therefore you should install them into your local repository following the [Guide to installing 3rd party JARs](http://maven.apache.org/guides/mini/guide-3rd-party-jars-local.html)

    1.1 SDJ-ID

        mvn install:install-file -Dfile=<path-to-project>/src/etc/lib/sdj2-id.jar \
        -DgroupId=com.surveycom.sdj  -DartifactId=sdj2-id -Dversion=0.9.30 -Dpackaging=jar

    1.2. JFL [see](#java-financial-library---api-usage)

        mvn install:install-file -Dfile=<path-to-project>/src/etc/lib/jfl-1.6.1.jar \
        -DgroupId=jfl  -DartifactId=jfl -Dversion=1.6.1 -Dpackaging=jar


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


Java Financial Library - API Usage
---------------------------------------------------------------------------


Currency Functions - import net.neurotech.currency.*
-------------------------------------------------------

The API to use the JFL functions is very simple.  To convert currencies,
instantiate a Currency object and use the convert function like so:

	Currency myCur = new Currency();
	float result = Currency.convert(10, "CAD", "USD");

To get the mappings between names and symbols, get the hashtable of 
entries using:

	Lister myList = new Lister();
	Hashtable maps = Lister.getTable();
	
And then enumerate through the hash table.


Stock Quote Functions - import net.neurotech.quotes.*
-------------------------------------------------------

There are two main functions of the Quotes package inside the JFL.  To
retrieve stock quotes, use the QuoteFactory.  The first step is to
instantiate a QuoteFactory object:

	QuoteFactory qf = new QuoteFactory();

This creates a QuoteFactory that will pull it's quote information from
the default data source - Yahoo Finance.  Now to grab an actual Quote
object, use the factory like so:

	Quote microsoft = qf.getQuote("MSFT");

This method can throw a QuoteException when there's an error in the
retrieval process.

Now you can access information from the new Quote object:

	float price = microsoft.getPrice();
	long volume = microsoft.getVolume();

The other main function of the Quotes package is to search for symbols
given a company name.  First, instantiate the Searcher object:

	Searcher searcher = new Searcher();

Then use the Search method, which returns a LinkedList:

	LinkedList hits = searcher.Search("VA Research");

Note that this method will throw a SearchException if there is a problem
making the query.

Now the linked list will now contain a bunch of SearchHit objects, which
have two methods:

	searchhit.getSymbol();
	searchhit.getResult();

You can go through this list and allow the user to choose the closest
match for their search.


