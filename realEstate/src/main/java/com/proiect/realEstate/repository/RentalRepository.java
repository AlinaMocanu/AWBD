package com.proiect.realEstate.repository;

import com.proiect.realEstate.model.Rental;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RentalRepository extends CrudRepository<Rental, Long> {
    List<Rental> findAll();

    Optional<Rental> findById(Long id);
    Rental findByNoOfRooms(int noOfRooms);
}
