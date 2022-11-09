package com.envision.automation.framework.core;

import com.envision.automation.framework.utils.ConfigLoader;
import com.envision.automation.framework.utils.ORUtils;
import com.envision.automation.framework.utils.Reporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasePage {
    static WebDriver baseDriver;

    public BasePage(WebDriver driver) throws IOException {
        this.baseDriver = driver;
    }

    public static WebDriver launchBrowser(String browserType) {
        try {
            if (browserType.equalsIgnoreCase("chrome")) {
                System.setProperty("webdriver.chrome.driver", ConfigLoader.getChromeDriverPath());
                baseDriver = new ChromeDriver();
            } else if (browserType.equalsIgnoreCase("edge")) {
                System.setProperty("webdriver.edge.driver", ConfigLoader.getChromeDriverPath());
            } else if (browserType.equalsIgnoreCase("firefox")) {
                System.setProperty("webdriver.gecko.driver", ConfigLoader.getChromeDriverPath());
                baseDriver = new FirefoxDriver();
            } else {
                throw new UnsupportedOperationException("Browser Type [" + browserType + "] is not supported");
            }
            baseDriver.manage().timeouts().pageLoadTimeout(ConfigLoader.getWaitTime(), TimeUnit.SECONDS);
            baseDriver.manage().timeouts().implicitlyWait(ConfigLoader.getWaitTime(), TimeUnit.SECONDS);
            baseDriver.manage().window().maximize();
            Reporter.logPassedStep("Browser[" + browserType + "] launched and maximized successfully");
        } catch (Exception ex) {
            Reporter.logFailedStep("Unable to launch browser[" + browserType + "], exception occurred:" + ex);
        }
        return baseDriver;
    }

    public void LaunchUrl(String url) {
        try {
            this.baseDriver.get(url);
            Reporter.logPassedStep("Url[" + url + "] launched successfully");
        } catch (Exception e) {
            Reporter.logFailedStep("Unable to launch Url[" + url + "]");
        }
    }

    public By getFindBy(String propertyName) throws IOException {
        By by = null;
        try {
            ORUtils.loadORFile();
            String orElementValue = ORUtils.getORPropertyValue(propertyName);

            String byMode = orElementValue.split("#", 2)[0];
            String byValue = orElementValue.split("#", 2)[1];

            if (byMode.equalsIgnoreCase("id")) {
                by = By.id(byValue);
            } else if (byMode.equalsIgnoreCase("name")) {
                by = By.name(byValue);
            } else if (byMode.equalsIgnoreCase("class")) {
                by = By.className(byValue);
            } else if (byMode.equalsIgnoreCase("tag")) {
                by = By.tagName(byValue);
            } else if (byMode.equalsIgnoreCase("css")) {
                by = By.cssSelector(byValue);
            } else if (byMode.equalsIgnoreCase("xpath")) {
                by = By.xpath(byValue);
            } else if (byMode.equalsIgnoreCase("lt")) {
                by = By.linkText(byValue);
            } else if (byMode.equalsIgnoreCase("plt")) {
                by = By.partialLinkText(byValue);
            } else {
                throw new UnsupportedOperationException("ByMode value is not support, check the OR.properties and pass supported Values only");
            }

        } catch (Exception e) {
            Reporter.logFailedStep("Unable to get By locator[" + by + "]");
        }
        return by;
    }

    public WebElement findWebElement(String orElement) throws IOException {
        WebElement element = null;
        try {
            By by = getFindBy(orElement);
            element = new WebDriverWait(baseDriver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(by));
            element = new WebDriverWait(baseDriver, ConfigLoader.getWaitTime())
                    .until(ExpectedConditions.visibilityOfElementLocated(by));
            Reporter.logPassedStep("OR element[" + orElement + "]found successfully");
        } catch (Exception e) {
            Reporter.logFailedStep("Unable to find the element[" + orElement + "]");
        }
        return element;

    }

    public List<WebElement> findWebElements(String orElement) throws IOException {
        List<WebElement> elements = null;
        try {
            By by = getFindBy(orElement);

            elements = new WebDriverWait(baseDriver, 10)
                    .until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
            Reporter.logPassedStep(" Elements[" + orElement + "]found successfully");
        } catch (Exception e) {
            Reporter.logFailedStep("Unable to find the elements[" + orElement + "]");
        }
        return elements;
    }

    public void clickon(String elementName) throws IOException {
        try {
            WebElement element = findWebElement(elementName);
            new WebDriverWait(baseDriver, ConfigLoader.getWaitTime())
                    .until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            Reporter.logPassedStep(" Clicked on element[" + elementName + "] successfully");
        } catch (Exception e) {
            Reporter.logFailedStep(" Unable to Click on element[" + elementName + "] ");
        }
    }

    public void typeInto(String elementName, String contentToType) throws IOException, InterruptedException {
        try {
            WebElement element = findWebElement(elementName);
            new WebDriverWait(baseDriver, ConfigLoader.getWaitTime())
                    .until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            Thread.sleep(1000);
            element.clear();
            Thread.sleep(1000);
            element.sendKeys(contentToType);
            Reporter.logPassedStep(" Typed [" + contentToType + "]into element [" + elementName + "]");
        } catch (Exception e) {
            Reporter.logFailedStep(" Unable to Type[" + contentToType + "]into element[" + elementName + "] ");
        }
    }

    public String getWebElementText(String elementName) throws IOException {
        WebElement element = null;
        String text = "";
        try {
            element = findWebElement(elementName);
            Reporter.logPassedStep(" Fetched Text[" + text + "] from element [" + elementName + "]");
        } catch (Exception e) {
            Reporter.logFailedStep(" Unable to fetch text from element[" + elementName + "] ");
        }
        return text;
    }

    public boolean getWebElementAttribute(String elementName, String attributeType) throws IOException {
        WebElement element = null;
        String attributeValue = null;

        try {
            element = findWebElement(elementName);
            attributeValue = element.getAttribute(attributeType);
            Reporter.logPassedStep(" Fetched attribute[" + attributeType + "] value [" + attributeValue + "]");
        } catch (Exception e) {
            Reporter.logFailedStep(" Unable to fetch attribute[" + elementName + "] value ");
        }
        return attributeValue;
    }

    public static void closeBrowser() {
        try {
            baseDriver.close();
            Reporter.logPassedStep(" Closed browser successfully");
        } catch (Exception e) {
            Reporter.logPassedStep(" Closed browser successfully"););
        }
    }

    public static void closeAllBrowser() {
        try {
            baseDriver.quit();
            Reporter.logPassedStep(" Closed All browsers and terminated driver session");
        } catch (Exception e) {
            Reporter.logFailedStep(" Unable to close the browser or terminate driver session ");
        }

    }

    public void refreshPage() {
        try {
            this.baseDriver.navigate().refresh();
            Reporter.logPassedStep(" Web Page refreshed");
        } catch (Exception e) {
            Reporter.logFailedStep(" Web Page failed to refresh ");
        }

    }

    public void launchUrl(String url) {
        try {
            this.baseDriver.get(url);
            Reporter.logPassedStep("the current URL of Page is" + url);
        } catch (Exception e) {
            Reporter.logFailedStep(" Unable to fetch current URL of the page ");
        }

    }

    public String getCurrentUrl() {
        String url = null;
        try {
            url = baseDriver.getCurrentUrl();
            Reporter.logPassedStep("the current URL of Page is" + url);
        } catch (Exception e) {
            Reporter.logFailedStep("Unable to fetch current URL of the page ");
        }

        return url;
    }

    public String getTitledOfPage() {
        String title = null;
        try {
            title = baseDriver.getTitle();
            Reporter.logPassedStep(" Title of the Page is :" + title);
        } catch (Exception e) {
            Reporter.logFailedStep(" Unable to fetch the title of the page");
        }

        return title;
    }

    public void selectFromDropdown(String elementName, String how, String valueToSelect) throws IOException {
        try {
            WebElement element = findWebElement(elementName);
            Select dropDownList = new Select(element);
            if (how.equalsIgnoreCase("value")) {
                dropDownList.selectByValue(valueToSelect);
            } else if (how.equalsIgnoreCase("visibleText")) {
                dropDownList.selectByVisibleText(valueToSelect);
            } else if (how.equalsIgnoreCase("index")) {
                dropDownList.selectByIndex(Integer.parseInt(valueToSelect));
            }
            Reporter.logPassedStep(" Value[" + valueToSelect + "]selected from dropdown[" + elementName + "]");
        } catch (Exception e) {
            Reporter.logFailedStep("Unable to select Value[" + valueToSelect + "]selected from dropdown[" + elementName + "]");
        }
    }

    public void selectIndexValueFromDropdown(String elementName, String value) throws IOException {
        try {

            selectFromDropdown(elementName, "index", value);

        } catch (Exception e) {

        }

        public void selectVisibleValueFromDropdown (String elementName, String value) throws IOException {
            try {
                selectFromDropdown(elementName, "visibleText", value);

            } catch (Exception e) {

            }

            public void selectValueFromDropdown (String elementName, String value){
                try {
                    selectFromDropdown(elementName, "value", value);

                } catch (Exception e) {
                }

                public boolean isDisplayed (String elementName) throws IOException WebElement element;
                {
                    element = null;
                }
                try {
                    element = findWebElement(elementName);
                    Reporter.logPassedStep(" Element[" + elementName + "]displayed successfully");
                } catch (Exception e) {
                    Reporter.logFailedStep("Element [" + elementName + "] not displayed");
                }
                return element.isDisplayed();
            }
        }
    }
}
