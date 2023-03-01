package uk.bestbuyapi.stepsinfo;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import uk.bestbuyapi.constants.EndPoints;
import uk.bestbuyapi.model.StorePojo;

import java.util.HashMap;

public class StoreSteps {
    @Step("getting all information :{0}")
    public ValidatableResponse getAllProductsInfo() {
        return SerenityRest.given()
                .when()
                .get(EndPoints.GET_ALL_STORES)
                .then();

    }

    @Step("creating Store with name :{0},id: {1}")
    public ValidatableResponse createStores(String name, String type, String add, String add2,String city,String state,String zip,int lat, int lng, String hours) {


        StorePojo pojo = new StorePojo();
        pojo.setName(name);
        pojo.setType(type);
        pojo.setAddress(add);
        pojo.setAddress2(add2);
        pojo.setCity(city);
        pojo.setState(state);
        pojo.setZip(zip);
        pojo.setLat(lat);
        pojo.setLng(lng);
        pojo.setHours(hours);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(EndPoints.CREATE_NEW_STORES)
                .then().log().all().statusCode(201);
    }

    @Step("getting Stores info by name:{0}")
    public HashMap<String, Object> getStoresInfoByName(String name) {
        String p1 = "data.findAll{it.name='";
        String p2 = "'}.get(0)";

        return SerenityRest.given()
                .log().all()
                .when()
                .get(EndPoints.GET_ALL_STORES)
                .then()
                .statusCode(200)
                .extract().path(p1 + name + p2);
    }

    @Step("update Stores info with StoreID:{0},name :{1},id {2}}")
    public ValidatableResponse updateStores(Object storesID,String name, String type, int lat, int lng, String add, String add2,String city,String state,String zip, String hours) {

        StorePojo pojo = new StorePojo();
        pojo.setName(name);
        pojo.setType(type);
        pojo.setAddress(add);
        pojo.setAddress2(add2);
        pojo.setCity(city);
        pojo.setState(state);
        pojo.setZip(zip);
        pojo.setLat(lat);
        pojo.setLng(lng);
        pojo.setHours(hours);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("storesID", storesID)
                .body(pojo)
                .when()
                .patch(EndPoints.UPDATE_STORES_BY_ID)
                .then()
                .statusCode(200);
    }

    @Step("deleteing Stores information with StoresID:{0}")
    public ValidatableResponse deleteStoresInfoByID(Object storesID) {
        return SerenityRest.given()
                .pathParam("storesID", storesID)
                .when()
                .delete(EndPoints.DELETE_STORES_BY_ID)
                .then().log().all().statusCode(200);
    }

    @Step("getting Stores info By StoresID:{0}")
    public ValidatableResponse getStoresInfoByStoresId(Object storesID) {
        return SerenityRest.given()
                .pathParam("storesID", storesID)
                .when()
                .get(EndPoints.GET_SINGLE_STORES_BY_ID)
                .then().log().all().statusCode(404);
    }
}
