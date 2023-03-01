package uk.bestbuyapi.serviceinfo;

import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.bestbuyapi.constants.EndPoints;
import uk.bestbuyapi.model.ServicePojo;
import uk.bestbuyapi.testbase.TestBase;
import uk.bestbuyapi.utils.TestUtils;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ServiceCurdTest extends TestBase {
    static String name = "Dhwanil"+ TestUtils.getRandomValue();
    static Object serviceID;
    @Title("get all Services list")
    @Test
    public void test001() {

        SerenityRest.given()
                .when()
                .log().all()
                .get(EndPoints.GET_ALL_SERVICES)
                .then().log().all().statusCode(200);
    }

    @Title("This will create a new Services")
    @Test
    public void test002() {
        ServicePojo pojo = new ServicePojo();
        pojo.setName(name);
        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(EndPoints.CREATE_NEW_SERVICES)
                .then().log().all().statusCode(201);
    }

    @Title("Verify if Services was created")
    @Test
    public void test003() {
        String part1 = "data.findAll{it.name='";
        String part2 = "'}.get(0)";


        HashMap<String, Object> servicesMapData = SerenityRest.given()
                .log().all()
                .when()
                .get(EndPoints.GET_ALL_SERVICES)
                .then().statusCode(200).extract().path(part1 + name + part2);
        Assert.assertThat(servicesMapData, hasValue(name));
        System.out.println(name);
        serviceID = servicesMapData.get("id");
        System.out.println(serviceID);
    }

    @Title("update the Services and verify the update information")
    @Test
    public void test004() {

        name = name +"11";
        ServicePojo pojo = new ServicePojo();
        pojo.setName(name);
        System.out.println(serviceID);
        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("servicesID",serviceID)
                .body(pojo)
                .when()
                .patch(EndPoints.UPDATE_SERVICES_BY_ID)
                .then()
                .statusCode(200);


        String p1 = "data.findAll{it.name='";
        String p2 = "'}.get(0)";
        HashMap<String, Object> storemap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_SERVICES)
                .then().statusCode(200)
                .extract()
                .path(p1 + name + p2);
        Assert.assertThat(storemap, hasValue(name));
    }

    @Title("Delete the Services and verify if the Services is deleted")
    @Test
    public void test005() {

        SerenityRest.given()
                .pathParam("servicesID", serviceID)
                .when()
                .delete(EndPoints.DELETE_SERVICES_BY_ID)
                .then().log().all().statusCode(200);

        SerenityRest.given()
                .pathParam("servicesID", serviceID)
                .when()
                .get(EndPoints.GET_SINGLE_SERVICES_BY_ID)
                .then().log().all().statusCode(404);
    }
}
