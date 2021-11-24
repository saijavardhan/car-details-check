package model;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CarDetails {
    @CsvBindByName(column = "REGISTRATION")
    String registrationNumber;
    @CsvBindByName(column = "MODEL")
    String model;
    @CsvBindByName(column = "MAKE")
    String make;
    @CsvBindByName(column = "COLOR")
    String color;
    @CsvBindByName(column = "YEAR")
    String year;
}
