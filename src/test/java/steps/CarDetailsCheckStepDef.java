package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.thucydides.core.annotations.Managed;
import org.openqa.selenium.WebDriver;
import utilities.FileReader;

import java.io.IOException;

public class CarDetailsCheckStepDef {

    @Managed
    WebDriver driver;

    @Given("I have read car registration numbers from files like name {string} in {string} directory")
    public void i_have_read_car_registration_numbers_from_files_like_name_car_input_in_src_main_resources_directory(String fileNamePattern, String sourceFilePath) throws IOException {
        FileReader file = new FileReader();
        System.out.println(file.getRegistrationNumbersFromAllInputFiles(sourceFilePath, fileNamePattern));
    }

    @When("I get the car details from cartaxcheck website")
    public void i_get_the_car_details_from_cartaxcheck_website() {

    }

    @Then("the car details should match with details in {string} file")
    public void the_car_details_should_match_with_details_in_file(String string) {

    }
}
