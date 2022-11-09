package com.envision.automation.tests;

import com.envision.automation.framework.core.BaseTest;
import pages.HomePage;
import pages.LandingPage;
import pages.LoginPage;

import java.io.IOException;

public class LoginTests extends BaseTest {
    public  void  validateSuccessfulLoginToApplication() throws IOException, InterruptedException {
        JSONObject jsO = js.getJsonObject(js.mainJsonObject, "LoginData");
        String username = js.getJsonObjectValue(jsO, "userName");
        String password = js.getJsonObjectValue(jsO, "password");
        String valid_UserName = js.getJsonObjectValue(jsO, "valid_UserName");
        LandingPage landingPage = new LandingPage(driver);
        LoginPage loginPage =
                landingPage.
                        launchAutomationPractiseApplication().
                        clickOnSignInLink();

        HomePage homePage =
                loginPage
                        .enterUserName(username)
                        .enterPassword(password)
                        .clickOnSignInButton();

        homePage
                .checkIfSignOutIsDisplayed()
                .checkIfValidUserIsLoggedIn(valid_UserName);
    }
}
