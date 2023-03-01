package uk.bestbuyapi.storeinfo;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.bestbuyapi.stepsinfo.StoreSteps;
import uk.bestbuyapi.testbase.TestBase;
import uk.bestbuyapi.utils.TestUtils;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;
@RunWith(SerenityRunner.class)
public class StoreCurdTestWithTest extends TestBase {
    static String name = "Dhwanil" + TestUtils.getRandomValue();
    static String type = "standard" + TestUtils.getRandomValue();

    static String add = "Wembley";
    static String add2 = "Ealing";
    static String city= "London";
    static String state = "England";
    static String zip = "HA0 4SD";
    static int lat = 1;
    static int lng = 6;
    static String hours = "12:00";
    static Object storesID;


    @Steps
    StoreSteps storeSteps;

    @Title("This will create a new stores")
    @Test
    public void test001() {

        ValidatableResponse response =storeSteps.createStores( name,  type,   add,  add2, city, state, zip,  lat,  lng, hours);
        response.statusCode(201);

    }

    @Title("verify if stores is created")
    @Test
    public void test002() {
        HashMap<String, Object> productsMapData =storeSteps.getStoresInfoByName(name);
        Assert.assertThat(productsMapData,hasValue(name));
        storesID= productsMapData.get("id");
    }

    @Title("update the stores information")
    @Test
    public void test003() {
        name = name +"11";

        storeSteps.updateStores(storesID,name,  type,  lat,  lng,  add,  add2, city, state, zip,  hours);
        HashMap<String,Object> mapData= storeSteps.getStoresInfoByName(name);
        Assert.assertThat(mapData,hasValue(name));

    }
    @Title("Delete stores info by storeID and verify its deleted")
    @Test
    public void test004(){
        storeSteps.deleteStoresInfoByID(storesID).statusCode(200);
        storeSteps.getStoresInfoByStoresId(storesID).statusCode(404);
    }
}
