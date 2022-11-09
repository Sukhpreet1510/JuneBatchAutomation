package com.envision.automation.framework.core;

import com.envision.automation.framework.utils.ConfigLoader;
import com.envision.automation.framework.utils.ExtentManager;
import com.envision.automation.framework.utils.ExtentTestManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

public class BaseTest {
    public WebDriver driver;
    public BasePage basePage;

    @BeforeSuite
    public void loadConfigurations() throws IOException {
        ConfigLoader.loadConfigurations();
        ExtentManager.getReporter();
    }

    @BeforeMethod
    public void loadBrowser() throws IOException {
        ExtentTestManager.startTest(methodName.getName(),"");
        this.driver = basePage.launchBrowser(ConfigLoader.getBrowserType());
    }

    @AfterMethod
    public void tearDownBrowser() {
        BasePage.closeBrowser();
        ExtentTestManager.stopTest();
    }

    @AfterSuite
    public void tearDownConfigurations() {
        driver = null;
        ExtentManager.getReporter().flush();
        ExtentManager.getReporter().close();
    }
}

