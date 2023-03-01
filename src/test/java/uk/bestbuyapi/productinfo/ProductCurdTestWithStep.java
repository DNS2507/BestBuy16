package uk.bestbuyapi.productinfo;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.bestbuyapi.constants.EndPoints;
import uk.bestbuyapi.model.ProductPojo;
import uk.bestbuyapi.stepsinfo.Productsteps;
import uk.bestbuyapi.testbase.TestBase;
import uk.bestbuyapi.utils.TestUtils;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ProductCurdTestWithStep extends TestBase {
    static String name = "Dhwanil" + TestUtils.getRandomValue();
    static String type = "standard" + TestUtils.getRandomValue();
    static String model = "BMW" + TestUtils.getRandomValue();
    static int price = 123;
    static int ship = 122;
    static String upc = "sdfsf";
    static String disc = "dfbgd";
    static String manufac= "fdvfdv";
    static String url = "dfvdfdffd";
    static String img = "gredgtg";
    static Object productsID;


    @Steps
    Productsteps productSteps;

    @Title("This will create a new products")
    @Test
    public void test001() {

        ValidatableResponse response =productSteps.createProducts( name, type, price, ship, upc, disc, manufac, model,url,img);
        response.statusCode(201);

    }

    @Title("verify if products is created")
    @Test
    public void test002() {
        HashMap<String, Object> productsMapData =productSteps.getProductsInfoByName(name);
        Assert.assertThat(productsMapData,hasValue(name));
        productsID= productsMapData.get("id");
    }

    @Title("update the products information")
    @Test
    public void test003() {
        name = name +"11";

        productSteps.updateProducts(productsID,name, type, price, ship, upc, disc, manufac, model,url,img);
        HashMap<String,Object> mapData= productSteps.getProductsInfoByName(name);
        Assert.assertThat(mapData,hasValue(name));

    }
    @Title("Delete products info by productsID and verify its deleted")
    @Test
    public void test004(){
        productSteps.deleteProductsInfoByID(productsID).statusCode(200);
        productSteps.getProductsInfoByProductsId(productsID).statusCode(404);
    }

}
