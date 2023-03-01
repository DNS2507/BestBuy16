package uk.bestbuyapi.serviceinfo;

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
import uk.bestbuyapi.model.ServicePojo;
import uk.bestbuyapi.stepsinfo.ServiceSteps;
import uk.bestbuyapi.testbase.TestBase;
import uk.bestbuyapi.utils.TestUtils;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class ServiceCurdTestWithStep extends TestBase {
    static String name = "Dhwanil"+ TestUtils.getRandomValue();
    static Object serviceID;

    @Steps
    ServiceSteps serviceSteps;

    @Title("This will create a new categories")
    @Test
    public void test001() {

        ValidatableResponse response = serviceSteps.createServices(name);
        response.statusCode(201);

    }

    @Title("verify if categories is created")
    @Test
    public void test002() {
        HashMap<String, Object> categoriesMapData =serviceSteps.getServicesInfoByName(name);
        Assert.assertThat(categoriesMapData,hasValue(name));
        serviceID= categoriesMapData.get("id");
    }

    @Title("update the categories information")
    @Test
    public void test003() {
        name = name +"11";

        serviceSteps.updateServices(serviceID,name);
        HashMap<String,Object> mapData= serviceSteps.getServicesInfoByName(name);
        Assert.assertThat(mapData,hasValue(name));

    }
    @Title("Delete categories info by StudentID and verify its deleted")
    @Test
    public void test004(){
        serviceSteps.deleteServicesInfoByID(serviceID).statusCode(200);
        serviceSteps.getServicesInfoByServicessId(serviceID).statusCode(404);
    }
}
