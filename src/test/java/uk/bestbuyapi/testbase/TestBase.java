package uk.bestbuyapi.testbase;

import uk.bestbuyapi.constants.Path;
import uk.bestbuyapi.utils.PropertyReader;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import uk.bestbuyapi.constants.Path;
import uk.bestbuyapi.utils.PropertyReader;


public class TestBase {
    public static PropertyReader propertyReader;

    @BeforeClass
    public static void init() {
        propertyReader = PropertyReader.getInstance();
        RestAssured.baseURI = propertyReader.getProperty("baseUrl");
        RestAssured.port = Integer.parseInt(propertyReader.getProperty("port"));
    }
}
