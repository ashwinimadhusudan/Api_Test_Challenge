Objective
1. Get the weather details depending on the query parameter

Technology Stack
1. RestAssured on JAVA
2. Cucumber
3. Javadoc

Features
1. Test gets the weather forecast from https:/weatherbit.io
2. The test data like api key, end points are stored in a property file.
3. City and the Country code are passed from the feature file. Any more test sets can be added in the scenario outline.

Running the test

Clone the project on to the local machine
  - Setup MavenPath and Java Path (if required)
  - Go into the repo folder and execute the command

  mvn clean install

- Execute(Run) Runner.java  (src/test/java) to run as JUnit tests
Execute "mvn test" to run all the scenarios as maven tests
         "test -DTags=@test" to run tests having specific tags such as @test
- The 2 scenarios in the Weather.feature will execute by taking the test data from the TestData.properties
- utilityClasses/CallApiUtil.java contains the rest assured implementation of calling the API
- WeatherApi.java contains the step definition implementation for the feature file.
- logs are available in logs/logfile.log
- For demonstration, the assertion details are displayed on the screen,  and also put in the log file
- html report will be available under target/cucumberReports
