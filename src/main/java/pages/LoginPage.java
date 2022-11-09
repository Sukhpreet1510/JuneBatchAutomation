package pages;

import com.envision.automation.framework.core.BasePage;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class LoginPage extends BasePage {
    WebDriver driver;
    public LoginPage(WebDriver driver){
        super(driver);
        this.driver=driver;

    }

    public LoginPage enterUserName(String userName) throws IOException, InterruptedException {
        typeInto("LoginPage.enterUserName",userName);
        return this;
    }

    public LoginPage enterPassword(String password) throws IOException, InterruptedException {
        typeInto("LoginPage.enterPassword",password);
        return this;
    }

    public HomePage clickOnSignInButton() throws IOException {
        clickOn("LoginPage.clickOnSignInBtn");
        return new HomePage(this.driver);


    }
}
