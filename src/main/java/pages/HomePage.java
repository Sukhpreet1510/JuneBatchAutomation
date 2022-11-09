package pages;

import com.envision.automation.framework.core.BasePage;
import junit.framework.Assert;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class HomePage extends BasePage {
    WebDriver driver;
    public HomePage(WebDriver driver){
        super(driver);
        this.driver=driver;

    }

    public HomePage checkIfSignOutIsDisplayed() throws IOException, IOException {
        boolean condition = isDisplayed("HomePage.lnkSignOut");
        Assert.assertTrue("SignOut link is not displayed", condition);
        return this;
    }

    public String getUserNameLoggedIn() throws IOException {
        String name = getWebElementText("HomePage.lnkUserLoggedIn");

        return name;
    }

    public HomePage checkIfValidUserIsLoggedIn(String validUserName) throws IOException {
        String actualName=getUserNameLoggedIn();
        Assert.assertEquals(validUserName,actualName);
        return this;
}

}
