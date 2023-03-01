package uk.bestbuyapi.stepsinfo;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import uk.bestbuyapi.constants.EndPoints;
import uk.bestbuyapi.model.ServicePojo;

import java.util.HashMap;

public class ServiceSteps {
    @Step("getting all information :{0}")
    public ValidatableResponse getAllServicesInfo() {
        return SerenityRest.given()
                .when()
                .get(EndPoints.GET_ALL_SERVICES)
                .then();

    }

    @Step("creating services with name :{0},id: {1}")
    public ValidatableResponse createServices(String name) {


        ServicePojo pojo = new ServicePojo();
        pojo.setName(name);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(EndPoints.CREATE_NEW_SERVICES)
                .then().log().all().statusCode(201);
    }

    @Step("getting services info by name:{0}")
    public HashMap<String, Object> getServicesInfoByName(String name) {
        String p1 = "data.findAll{it.name='";
        String p2 = "'}.get(0)";

        return SerenityRest.given()
                .log().all()
                .when()
                .get(EndPoints.GET_ALL_SERVICES)
                .then()
                .statusCode(200)
                .extract().path(p1 + name + p2);
    }

    @Step("update services info with categoriesID:{0},name :{1},id {2}}")
    public ValidatableResponse updateServices(Object serviceID, String name) {

        ServicePojo pojo = new ServicePojo();
        pojo.setName(name);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("servicesID", serviceID)
                .body(pojo)
                .when()
                .patch(EndPoints.UPDATE_SERVICES_BY_ID)
                .then()
                .statusCode(200);
    }

    @Step("deleteing services information with categoriesID:{0}")
    public ValidatableResponse deleteServicesInfoByID(Object serviceID) {
        return SerenityRest.given()
                .pathParam("servicesID", serviceID)
                .when()
                .delete(EndPoints.DELETE_SERVICES_BY_ID)
                .then().log().all().statusCode(200);
    }

    @Step("getting services info By categoriesID:{0}")
    public ValidatableResponse getServicesInfoByServicessId(Object serviceID) {
        return SerenityRest.given()
                .pathParam("servicesID", serviceID)
                .when()
                .get(EndPoints.GET_SINGLE_SERVICES_BY_ID)
                .then().log().all().statusCode(404);
    }
}
