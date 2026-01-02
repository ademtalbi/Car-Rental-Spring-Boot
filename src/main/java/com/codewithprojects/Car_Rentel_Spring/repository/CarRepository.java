package com.codewithprojects.Car_Rentel_Spring.repository;

import com.codewithprojects.Car_Rentel_Spring.entity.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CarRepository extends JpaRepository<Car,Long> {

}
