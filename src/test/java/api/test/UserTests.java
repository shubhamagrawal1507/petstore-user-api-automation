package api.test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
    
    public Faker faker;
    public User user_pay_load;
    public Logger logger;

    @BeforeClass
    public void setUp(){

        faker = new Faker();
        user_pay_load = new User();

        user_pay_load.setId(faker.idNumber().hashCode());
        user_pay_load.setUsername(faker.name().username());
        user_pay_load.setFirstName(faker.name().firstName());
        user_pay_load.setFirstName(faker.name().lastName());
        user_pay_load.setEmail(faker.internet().safeEmailAddress());
        user_pay_load.setPassword(faker.internet().password(5,10));
        user_pay_load.setPhone(faker.phoneNumber().cellPhone());

        //logs
        logger = LogManager.getLogger(this.getClass());


    }

    @Test(priority = 1)
    public void testPostUser(){
        logger.info("****Creating User*****");
        Response response = UserEndPoints.createUser(user_pay_load);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
        logger.info("****User Created*****");
    }

    @Test(priority = 2)
    public void testGetUserByName(){
        logger.info("****Finding User*****");
        Response response = UserEndPoints.readUser(this.user_pay_load.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(),200);
        logger.info("****User Found*****");
    }

    @Test(priority = 3)
    public void testUpdateUser(){
        logger.info("****Updating User*****");
        //Updating Test Values
        user_pay_load.setFirstName(faker.name().firstName());
        user_pay_load.setFirstName(faker.name().lastName());
        user_pay_load.setEmail(faker.internet().safeEmailAddress());

        Response response = UserEndPoints.updateUser(this.user_pay_load.getUsername(), user_pay_load);
        response.then().log().body();

        Assert.assertEquals(response.getStatusCode(), 200);

        //Check Data is updated

        Response responseAfterUpdate = UserEndPoints.readUser(this.user_pay_load.getUsername());
        responseAfterUpdate.then().log().all();
        Assert.assertEquals(responseAfterUpdate.getStatusCode(),200);
        logger.info("****User updated*****");
    }

    @Test(priority = 4)
    public void testDeleteUserbyName(){
        logger.info("****Deleting User*****");
        Response response = UserEndPoints.deleteUser(this.user_pay_load.getUsername());
        Assert.assertEquals(response.getStatusCode(),200);
        logger.info("****User Deleted*****");
    }
    
}
