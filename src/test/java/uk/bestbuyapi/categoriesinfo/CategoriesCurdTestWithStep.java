package uk.bestbuyapi.categoriesinfo;


import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;

import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.bestbuyapi.stepsinfo.CategoriesSteps;
import uk.bestbuyapi.testbase.TestBase;
import uk.bestbuyapi.utils.TestUtils;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;


@RunWith(SerenityRunner.class)
public class CategoriesCurdTestWithStep extends TestBase {
    static String name = "Dhwanil" + TestUtils.getRandomValue();
    static String id = "abcd" + TestUtils.getRandomValue();
    static Object categoryId;

    @Steps
    CategoriesSteps categoriesSteps;

    @Title("This will create a new categories")
    @Test
    public void test001() {

        ValidatableResponse response =categoriesSteps.createCategories(name,id);
        response.statusCode(201);

    }

    @Title("verify if categories is created")
    @Test
    public void test002() {
        HashMap<String, Object> categoriesMapData =categoriesSteps.getCategoriesInfoByName(name);
        Assert.assertThat(categoriesMapData,hasValue(name));
        categoryId= categoriesMapData.get("id");
    }

    @Title("update the categories information")
    @Test
    public void test003() {
        name = name +"11";

        categoriesSteps.updateCategories(categoryId,name,id);
        HashMap<String,Object> mapData= categoriesSteps.getCategoriesInfoByName(name);
        Assert.assertThat(mapData,hasValue(name));

    }
    @Title("Delete categories info by StudentID and verify its deleted")
    @Test
    public void test004(){
        categoriesSteps.deleteCategoriesInfoByID(categoryId).statusCode(200);
        categoriesSteps.getCategoriesInfoByCategoriesId(categoryId).statusCode(404);
    }
}
