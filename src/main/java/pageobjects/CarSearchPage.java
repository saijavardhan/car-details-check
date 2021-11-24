package pageobjects;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.At;
import net.thucydides.core.annotations.WhenPageOpens;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebElement;

@At("https://cartaxcheck.co.uk/")
public class CarSearchPage extends PageObject {

    @FindBy(css = "#vrm-input")
    private WebElementFacade searchBox;

    @FindBy(xpath = ".//button[text()='Free Car Check']")
    private WebElementFacade freeCheckSearchButton;

    public void enterCarRegistrationNumber(String regNum){
        searchBox.type(regNum);
    }

    public void clickFreeCarCheckButton(){
        freeCheckSearchButton.click();
    }

    @WhenPageOpens
    public void waitUntilCheckout() {
        getDriver().manage().window().maximize();
        freeCheckSearchButton.waitUntilVisible();
    }
}
