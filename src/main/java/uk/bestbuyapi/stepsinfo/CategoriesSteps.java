package uk.bestbuyapi.stepsinfo;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import uk.bestbuyapi.constants.EndPoints;
import uk.bestbuyapi.model.CategoriesPojo;

import java.util.HashMap;

public class CategoriesSteps {
    @Step("getting all information :{0}")
    public ValidatableResponse getAllCategoriesInfo() {
        return SerenityRest.given()
                .when()
                .get(EndPoints.GET_ALL_CATEGORIES)
                .then();

    }

    @Step("creating categories with name :{0},id: {1}")
    public ValidatableResponse createCategories(String name, String id) {


        CategoriesPojo pojo = new CategoriesPojo();
        pojo.setName(name);
        pojo.setId(id);

        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(EndPoints.CREATE_NEW_CATEGORIES)
                .then().log().all().statusCode(201);
    }

    @Step("getting Categories info by name:{0}")
    public HashMap<String, Object> getCategoriesInfoByName(String name) {
        String p1 = "data.findAll{it.name='";
        String p2 = "'}.get(0)";

        return SerenityRest.given()
                .log().all()
                .when()
                .get(EndPoints.GET_ALL_CATEGORIES)
                .then()
                .statusCode(200)
                .extract().path(p1 + name + p2);
    }

    @Step("update Categories info with categoriesID:{0},name :{1},id {2}}")
    public ValidatableResponse updateCategories(Object categoryId, String name, String id) {

        CategoriesPojo pojo = new CategoriesPojo();
        pojo.setName(name);
        pojo.setId(id);

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("categoriesID", categoryId)
                .body(pojo)
                .when()
                .patch(EndPoints.UPDATE_CATEGORIES_BY_ID)
                .then()
                .statusCode(200);
    }

    @Step("deleteing Categories information with categoriesID:{0}")
    public ValidatableResponse deleteCategoriesInfoByID(Object categoryId) {
        return SerenityRest.given()
                .pathParam("categoriesID", categoryId)
                .when()
                .delete(EndPoints.DELETE_CATEGORIES_BY_ID)
                .then().log().all().statusCode(200);
    }

    @Step("getting Categories info By categoriesID:{0}")
    public ValidatableResponse getCategoriesInfoByCategoriesId(Object categoryId) {
        return SerenityRest.given()
                .pathParam("categoriesID", categoryId)
                .when()
                .get(EndPoints.GET_SINGLE_CATEGORIES_BY_ID)
                .then().log().all().statusCode(404);
    }
}
