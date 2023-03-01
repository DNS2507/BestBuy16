package uk.bestbuyapi.stepsinfo;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import uk.bestbuyapi.constants.EndPoints;
import uk.bestbuyapi.model.ProductPojo;

import java.util.HashMap;

public class Productsteps {
    @Step("getting all information :{0}")
    public ValidatableResponse getAllProductsInfo() {
        return SerenityRest.given()
                .when()
                .get(EndPoints.GET_ALL_PRODUCTS)
                .then();

    }

    @Step("creating products with name :{0},id: {1}")
    public ValidatableResponse createProducts(String name, String type, int price, int ship, String upc, String disc,String manufac,String model,String url, String img) {


        ProductPojo pojo = new ProductPojo();
        pojo.setName(name);
        pojo.setType(type);
        pojo.setPrice(ship);
        pojo.setShipping(price);
        pojo.setUpc(upc);
        pojo.setDescription(disc);
        pojo.setManufacturer(manufac);
        pojo.setModel(model);
        pojo.setUrl(url);
        pojo.setImage(img);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(EndPoints.CREATE_NEW_PRODUCTS)
                .then().log().all().statusCode(201);
    }

    @Step("getting products info by name:{0}")
    public HashMap<String, Object> getProductsInfoByName(String name) {
        String p1 = "data.findAll{it.name='";
        String p2 = "'}.get(0)";

        return SerenityRest.given()
                .log().all()
                .when()
                .get(EndPoints.GET_ALL_PRODUCTS)
                .then()
                .statusCode(200)
                .extract().path(p1 + name + p2);
    }

    @Step("update products info with productsID:{0},name :{1},id {2}}")
    public ValidatableResponse updateProducts(Object productsID,String name, String type, int price, int ship, String upc, String disc,String manufac,String model,String url, String img) {

        ProductPojo pojo = new ProductPojo();
        pojo.setName(name);
        pojo.setType(type);
        pojo.setPrice(ship);
        pojo.setShipping(price);
        pojo.setUpc(upc);
        pojo.setDescription(disc);
        pojo.setManufacturer(manufac);
        pojo.setModel(model);
        pojo.setUrl(url);
        pojo.setImage(img);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("productsID", productsID)
                .body(pojo)
                .when()
                .patch(EndPoints.UPDATE_PRODUCT_BY_ID)
                .then()
                .statusCode(200);
    }

    @Step("deleteing products information with productsID:{0}")
    public ValidatableResponse deleteProductsInfoByID(Object productsID) {
        return SerenityRest.given()
                .pathParam("productsID", productsID)
                .when()
                .delete(EndPoints.DELETE_PRODUCT_BY_ID)
                .then().log().all().statusCode(200);
    }

    @Step("getting products info By productsID:{0}")
    public ValidatableResponse getProductsInfoByProductsId(Object productsID) {
        return SerenityRest.given()
                .pathParam("productsID", productsID)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then().log().all().statusCode(404);
    }
}
