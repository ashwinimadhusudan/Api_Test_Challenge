package com.weather.city;

import com.jayway.restassured.response.Response;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import utilityClasses.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WeatherApi  {

    private static final Logger logger = LogManager.getLogger("WeatherApi.class");
    private List<HashMap<String, String>> responseList;
    private Response resp;
    private String key;
    private String postCode;
    private String latitude;
    private String longitude;
    private CallApiUtil callApiObject = new CallApiUtil();

    private Properties prop = new Properties();
    private String getValueFromConfig(String key) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("TestData.properties");
        if (inputStream != null) {
            prop.load(inputStream);
            return prop.getProperty(key);
        }
            else
                throw new FileNotFoundException("Property File not found in classpath");
    }

    /**
     *
     * @throws IOException
     */
    @Given("^I have the api key$")
    public void iHaveApiKey() throws IOException {
        key = getValueFromConfig("key");
        logger.info("Key is: " +key);
    }

    /**
     *
     * @param lat
     * @param longtd
     */
    @And("^I have Latitude as (.+?) and longitude as (.+?)$")
    public void iProvideLatitudeAndLongitude(String lat, String longtd) {
        latitude = lat;
        longitude = longtd;
        logger.info("longitude: " +longitude +"  latitude: " +latitude);
    }

    /**
     *
     * @param pCode
     */
    @And("^I have the post code (.+?) to check three hourly weather report$")
    public void iHavePostCode(String pCode) {
        postCode = pCode;
        logger.info("Postcode is: " +postCode);
    }

    /**
     *
     * @param serviceKey
     * @throws IOException
     */
    @And("^I call GET on (.+?) with values$")
    public void iDoGetOnEndPoint(String serviceKey) throws IOException {
        String endPoint=getValueFromConfig(serviceKey);
        logger.info("endPoint is: " +endPoint);
        resp = callApiObject.getWeatherByPostCode(endPoint, latitude, longitude, key);
    }

    /**
     *
     * @param serviceKey
     * @throws IOException
     */
    @And("^I do GET on (.+?) with postcode$")
    public void getWeatherReport(String serviceKey) throws IOException {
        String endPoint=getValueFromConfig(serviceKey);
        logger.info("endPoint is: " +endPoint);
        resp = callApiObject.getThreeHourlyWeather(endPoint, postCode, key);
    }

    /**
     *
     * @param stateCode
     */
    @Then("^I receive the weather forecast with state code as (.+?)$")
    public void iGetStateCode(String stateCode) {
        String responseStateCode = resp.jsonPath().get("data[0].state_code");
        logger.info("Assertion passed...state code is: "+responseStateCode);
        Assert.assertTrue(stateCode.equalsIgnoreCase(responseStateCode));
    }

    /**
     * This method asserts that response does contain required data
     */
    @Then("^I receive the utc time stamp$")
    public void iGetTimeStamp() {
        responseList = resp.jsonPath().getList("data");
        for (int i = 0; i < responseList.size(); i++) {
            Assert.assertTrue(responseList.get(i).containsKey("timestamp_utc"));
            logger.info("timestamp_utc for 3 hourly update: " + responseList.get(i).get("timestamp_utc"));
        }
    }

    /**
     * This method asserts that the response contains the weather details
     */
    @Then("^I receive the weather$")
    public void iGetWeather() {
        for (int i = 0; i < responseList.size(); i++) {
            Assert.assertTrue(responseList.get(i).containsKey("weather"));
            logger.info("weather for 3 hourly update: " + "icon:" + resp.jsonPath().get("data[" + i + "].weather.icon")
                    + "  code: " + resp.jsonPath().get("data[" + i + "].weather.code")
                    + "  description: " + resp.jsonPath().get("data[" + i + "].weather.description"));
            //SYSO for demonstration only Result is stored in the logs
            System.out.println("weather for 3 hourly update: " + "icon:" + resp.jsonPath().get("data[" + i + "].weather.icon")
                    + "  code: " + resp.jsonPath().get("data[" + i + "].weather.code")
                    + "  description: " + resp.jsonPath().get("data[" + i + "].weather.description"));
        }
    }
}
