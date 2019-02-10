package com.weather.city;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin={"pretty", "html:target/cucumberReports"},
        features = {"src/test/resources/features"})

public class Runner {

}
