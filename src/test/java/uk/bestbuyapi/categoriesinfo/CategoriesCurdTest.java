package uk.bestbuyapi.categoriesinfo;

import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.bestbuyapi.constants.EndPoints;
import uk.bestbuyapi.model.CategoriesPojo;
import uk.bestbuyapi.testbase.TestBase;
import uk.bestbuyapi.utils.TestUtils;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class CategoriesCurdTest extends TestBase {
    static String name = "Dhwanil" + TestUtils.getRandomValue();
    static String id = "abc" + TestUtils.getRandomValue();
    static Object categoryId;


    @Title("get all categories list")
    @Test
    public void test001() {

        SerenityRest.given()
                .when()
                .log().all()
                .get(EndPoints.GET_ALL_CATEGORIES)
                .then().log().all().statusCode(200);
    }

    @Title("This will create a new categories")
    @Test
    public void test002() {
        CategoriesPojo pojo = new CategoriesPojo();
        pojo.setName(name);
        pojo.setId(id);

        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(EndPoints.CREATE_NEW_CATEGORIES)
                .then().log().all().statusCode(201);
    }

    @Title("Verify if categories was created")
    @Test
    public void test003() {
        String part1 = "data.findAll{it.name='";
        String part2 = "'}.get(0)";


        HashMap<String, Object> categoriesMapData = SerenityRest.given()
                .log().all()
                .when()
                .get(EndPoints.GET_ALL_CATEGORIES)
                .then().statusCode(200).extract().path(part1 + name + part2);
        Assert.assertThat(categoriesMapData, hasValue(name));
        System.out.println(name);
        categoryId = categoriesMapData.get("id");
        System.out.println(categoryId);
    }

    @Title("update the categories and verify the update information")
    @Test
    public void test004() {

        name = name +"11";
        CategoriesPojo pojo = new CategoriesPojo();
        pojo.setName(name);
        pojo.setId(id);
        System.out.println(categoryId);
        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("categoriesID",categoryId)
                .body(pojo)
                .when()
                .patch(EndPoints.UPDATE_CATEGORIES_BY_ID)
                .then()
                .statusCode(200);


        String p1 = "data.findAll{it.name='";
        String p2 = "'}.get(0)";
        HashMap<String, Object> storemap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_CATEGORIES)
                .then().statusCode(200)
                .extract()
                .path(p1 + name + p2);
        Assert.assertThat(storemap, hasValue(name));
    }

    @Title("Delete the categories and verify if the categories is deleted")
    @Test
    public void test005() {

        SerenityRest.given()
                .pathParam("categoriesID", categoryId)
                .when()
                .delete(EndPoints.DELETE_CATEGORIES_BY_ID)
                .then().log().all().statusCode(200);

        SerenityRest.given()
                .pathParam("categoriesID", categoryId)
                .when()
                .get(EndPoints.GET_SINGLE_CATEGORIES_BY_ID)
                .then().log().all().statusCode(404);
    }
}
