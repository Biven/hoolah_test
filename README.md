# Building process
Apache Maven is using for building such application. You can run `mvn clean package` command for build application with running tests

#Running process
- First available option to test application is to run generated .jar file `test-1.0-SNAPSHOT-jar-with-dependencies.jar` from target directory.
Command: `java -jar test-1.0-SNAPSHOT-jar-with-dependencies.jar`. Application starts and provides availability to enter input params(csv file path, from date, to date, merchant)

- Second option is to run through development IDE(e.g. Intellij IDEA =). There is TransactionReporterTest class which contains several small methods for testing functionality.
For testing purposes you can create new test method or modify existing one. Test csv file contains in `test/resources` directory. You can put additional csv file to same directory and test changes will be minimal.
<p/><b>Important note:</b> For IDE run it could be required to install Lombok Plugin https://plugins.jetbrains.com/plugin/6317-lombok/. 