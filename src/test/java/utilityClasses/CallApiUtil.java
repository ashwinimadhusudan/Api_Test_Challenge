package utilityClasses;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CallApiUtil {
    Response response;
    private static final Logger logger = LogManager.getLogger("CallApiUtil.class");
    /**
     * Function to get Weather by PostCode
     * @param endPoint
     * @param latitude
     * @param longitude
     * @param key
     * @return
     */
    public Response getWeatherByPostCode(String endPoint, String latitude, String longitude, String key) {
        response = RestAssured.given().config(RestAssured.config.sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                .pathParam("lat", latitude)
                .pathParam("lon", longitude)
                .pathParam("key", key)
                .when().log().all()
                .get(endPoint + "?lat={lat}&lon={lon}&key={key}")
                .then()
                .extract().response();
        logger.info("getWeatherByPostCode response is: " +response.getBody().asString());
        return response;
    }

    /**
     * Function to get 3 Hourly Weather
     * @param endPoint
     * @param postCode
     * @param key
     * @return
     */
    public Response getThreeHourlyWeather(String endPoint, String postCode, String key) {
        response = RestAssured.given().config(RestAssured.config.sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                .pathParam("postal_code", postCode)
                .pathParam("key", key)
                .when().log().all()
                .get(endPoint + "?postal_code={postal_code}&key={key}")
                .then()
                .extract().response();
        logger.info("getThreeHourlyWeather response is: " +response.getBody().asString());
        return response;
    }
}
