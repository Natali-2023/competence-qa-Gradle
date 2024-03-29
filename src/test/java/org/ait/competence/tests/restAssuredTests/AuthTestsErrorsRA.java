package org.ait.competence.tests.restAssuredTests;

import org.ait.competence.dto.ExistEmailResponseDto;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.sql.SQLException;
import static org.hamcrest.Matchers.containsString;

public class AuthTestsErrorsRA extends TestBaseRA {

    @BeforeMethod
    public void preconditionRA() throws SQLException {
        user.registerUser("nata@gmail.com", "Nata2024!");
   }
    @Test()
    public void registerUserWithExistEmailTestRA1() throws SQLException {
        ExistEmailResponseDto existEmail = user.registerUser("nata@gmail.com", "Nata2024!")
                .then()
                .assertThat().statusCode(400)
                .extract().response().as(ExistEmailResponseDto.class);
        System.out.println(existEmail.getMessage());
    }
    @Test()
    public void registerUserWithExistEmailTestRA2() throws SQLException { //пока есть баг в некорректном ответе,
        ExistEmailResponseDto existEmail = user.registerUser("nata@gmail.com", "Nata2024!")
                .then()
                .assertThat().statusCode(400)
                .extract().response().as(ExistEmailResponseDto.class);
        Assert.assertEquals(existEmail.getMessage(), "User with this email already exists");
    }
    @Test()
    public void registerUserWithExistEmailTestRA3() throws SQLException {//пока есть баг в некорректном ответе,
        user.registerUser("nata@gmail.com", "Nata2024!")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message", containsString("User with this email already exists"));
    }

    @AfterMethod
    public void postConditionRA() throws SQLException {
        user.deleteUser("nata@gmail.com");
    }
}
