package uk.bestbuyapi.productinfo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.jcp.xml.dsig.internal.dom.Utils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.bestbuyapi.constants.EndPoints;
import uk.bestbuyapi.constants.Path;
import uk.bestbuyapi.model.ProductPojo;
import uk.bestbuyapi.testbase.TestBase;
import uk.bestbuyapi.utils.TestUtils;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ProductCurdTest extends TestBase {

    static String name = "Dhwanil" + TestUtils.getRandomValue();
    static String type = "standard" + TestUtils.getRandomValue();
    static String model = "BMW" + TestUtils.getRandomValue();
    static Object productsID;

    @Title("get all product list")
    @Test
    public void test001() {

        SerenityRest.given()
                .when()
                .log().all()
                .get(EndPoints.GET_ALL_PRODUCTS)
                .then().log().all().statusCode(200);
    }

    @Title("create new product")
    @Test
    public void test002() {

        ProductPojo pojo = new ProductPojo();
        pojo.setName(name);
        pojo.setType(type);
        pojo.setPrice(1700);
        pojo.setShipping(15);
        pojo.setUpc("af");
        pojo.setDescription("egwe");
        pojo.setManufacturer("regsd");
        pojo.setModel(model);
        pojo.setUrl("aerg");
        pojo.setImage("yjnyu");


        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(EndPoints.CREATE_NEW_PRODUCTS)
                .then().log().all()
                .statusCode(201);
        System.out.println(name);
    }

    @Title("Verify if product was created")
    @Test
    public void test003() {

        String p1 = "data.findAll{it.name='";
        String p2 = "'}.get(0)";
        HashMap<String, ?> productMapData = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_PRODUCTS)
                .then().statusCode(200)
                .extract()
                .path(p1 + name + p2);
        System.out.println();
        Assert.assertThat(productMapData, hasValue(name));
        productsID = productMapData.get("id");
    }

    @Title("update the product and verify the update information")
    @Test
    public void test004() {

        name = name + "abc";
        ProductPojo pojo = new ProductPojo();
        pojo.setName(name);
        pojo.setType(type);
        pojo.setPrice(1800);
        pojo.setShipping(10);
        pojo.setUpc("af");
        pojo.setDescription("egwe");
        pojo.setManufacturer("regsd");
        pojo.setModel(model);
        pojo.setUrl("aerg");
        pojo.setImage("yjnyu");

        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("productsID", productsID)
                .body(pojo)
                .when()
                .patch(EndPoints.UPDATE_PRODUCT_BY_ID)
                .then()
                .statusCode(200);


        String p1 = "data.findAll{it.name='";
        String p2 = "'}.get(0)";
        HashMap<String, Object> promap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_PRODUCTS)
                .then().statusCode(200)
                .extract()
                .path(p1 + name + p2);
        System.out.println(promap);
        Assert.assertThat(promap, hasValue(name));
    }

    @Title("Delete the product and verify if the product is deleted")
    @Test
    public void test005() {

        SerenityRest.given()
                .pathParam("productsID", productsID)
                .when()
                .delete(EndPoints.DELETE_PRODUCT_BY_ID)
                .then().log().all().statusCode(200);

        SerenityRest.given()
                .pathParam("productsID", productsID)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then().log().all().statusCode(404);
    }
}