package pageobjects;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.At;
import net.thucydides.core.annotations.WhenPageOpens;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.support.ui.ExpectedConditions;

@At("https://cartaxcheck.co.uk/")
public class CarDetailsPage extends PageObject {

    @FindBy(xpath = "//dt[text()='Registration']/ancestor::dl/dd")
    private WebElementFacade registrationNumber;

    @FindBy(xpath = "//dt[text()='Make']/ancestor::dl/dd")
    private WebElementFacade make;

    @FindBy(xpath = "//dt[text()='Model']/ancestor::dl/dd")
    private WebElementFacade model;

    @FindBy(xpath = "//dt[text()='Colour']/ancestor::dl/dd")
    private WebElementFacade colour;

    @FindBy(xpath = "//dt[text()='Year']/ancestor::dl/dd")
    private WebElementFacade year;

    @WhenPageOpens
    public void waitUntilCheckout() {
        registrationNumber.waitUntilPresent();
    }

    public void waitUntilRegistrationNumberIsDisplayed(String regNum){
        waitForCondition().until(
                ExpectedConditions.textToBePresentInElement(registrationNumber, regNum)
        );
    }

    public String getCarRegistrationNumber(){
        return registrationNumber.getText();
    }

    public String getMake(){
        return make.getText();
    }

    public String getColour(){
        return colour.getText();
    }

    public String getYear(){
        return year.getText();
    }

    public String getModel(){
        return model.getText();
    }
}
