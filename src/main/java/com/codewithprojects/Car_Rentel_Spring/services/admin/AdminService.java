package com.codewithprojects.Car_Rentel_Spring.services.admin;

import com.codewithprojects.Car_Rentel_Spring.dto.CarDto;

import java.io.IOException;
import java.util.List;

public interface AdminService {

    boolean postCar(CarDto carDto) throws IOException;

    List<CarDto> getAllCars();
}
