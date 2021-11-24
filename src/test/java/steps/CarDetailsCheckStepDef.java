package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import model.CarDetails;
import net.thucydides.core.annotations.Managed;
import org.openqa.selenium.WebDriver;
import pageobjects.CarDetailsPage;
import pageobjects.CarSearchPage;
import utilities.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class CarDetailsCheckStepDef {

    @Managed
    WebDriver driver;

    private CarSearchPage carSearchPage;

    private CarDetailsPage carDetailsPage;

    private Set<String> regNumsTocjeck;

    private List<CarDetails> carDetailsList = new ArrayList<>();

    @Given("I have read car registration numbers from files like name {string} in {string} directory")
    public void i_have_read_car_registration_numbers_from_files_like_name_car_input_in_src_main_resources_directory(String fileNamePattern, String sourceFilePath) throws IOException {
        FileUtils file = new FileUtils();
        regNumsTocjeck = file.getRegistrationNumbersFromAllInputFiles(sourceFilePath, fileNamePattern);
        regNumsTocjeck.forEach(k -> System.out.println("++++===" + k));
    }

    @When("I get the car details from cartaxcheck website")
    public void i_get_the_car_details_from_cartaxcheck_website() {
        regNumsTocjeck.forEach(num -> {
            CarDetails car = new CarDetails();
            System.out.println("searching for = " + num);
            carSearchPage.open();
            carSearchPage.enterCarRegistrationNumber(num);
            carSearchPage.clickFreeCarCheckButton();
            carDetailsPage.waitUntilRegistrationNumberIsDisplayed(num);

            car.setRegistrationNumber(carDetailsPage.getCarRegistrationNumber());
            car.setColor(carDetailsPage.getColour());
            car.setMake(carDetailsPage.getMake());
            car.setModel(carDetailsPage.getModel());
            car.setYear(carDetailsPage.getYear());
            carDetailsList.add(car);
        });
    }

    @Then("the car details should match with details in {string} file in {string} directory")
    public void the_car_details_should_match_with_details_in_file(String outFileName, String directory) throws IOException, ClassNotFoundException {
        FileUtils file = new FileUtils();
        List<CarDetails> expectedData = file.convertCSVToCarDetailsObjects(directory, outFileName);
        List<String> actualCarRegNumbers = carDetailsList.stream().map(CarDetails::getRegistrationNumber).collect(Collectors.toList());
        List<String> expectedCarRegNumbers = expectedData.stream().map(CarDetails::getRegistrationNumber).collect(Collectors.toList());
        assertThat("Some cars can't be found in the website or car details missing in output file", actualCarRegNumbers, containsInAnyOrder(expectedCarRegNumbers.toArray()));
        carDetailsList.forEach(actualCar -> {
            expectedData.forEach(expectedCar -> {
                if (expectedCar.getRegistrationNumber().equals(actualCar.getRegistrationNumber())) {
                    assertThat("Colour mismatch for car : " + actualCar.getRegistrationNumber(), actualCar.getColor(), is(expectedCar.getColor()));
                    assertThat("Make mismatch for car : " + actualCar.getRegistrationNumber(), actualCar.getMake(), is(expectedCar.getMake()));
                    assertThat("Model mismatch for car : " + actualCar.getRegistrationNumber(), actualCar.getModel(), is(expectedCar.getModel()));
                    assertThat("Year mismatch for car : " + actualCar.getRegistrationNumber(), actualCar.getYear(), is(expectedCar.getYear()));
                }
            });
        });
    }
}
