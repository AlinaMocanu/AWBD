package com.proiect.rental.service;

import com.proiect.rental.exceptions.RentalNotFound;
import com.proiect.rental.model.Rental;
import com.proiect.rental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    @Autowired
    RentalRepository rentalRepository;

    public List<Rental> findAll(){
        List<Rental> flats = new LinkedList<>();
        rentalRepository.findAll().iterator().forEachRemaining(flats::add);
        return flats;
    }

    public Rental findById(Long id) {
        Optional<Rental> flatOptional = rentalRepository.findById(id);
        if (! flatOptional.isPresent())
            throw new RentalNotFound("Rental " + id + " not found!");
        return flatOptional.get();
    }

    public Rental save(Rental rental) {
        Rental rentalSave = rentalRepository.save(rental);
        return rentalSave;
    }

    public Rental findByNoOfRooms(int noOfRooms) {
        Rental rental = rentalRepository.findByNoOfRooms(noOfRooms);
        return rental;
    }

    public Rental delete(Long id){
        Optional<Rental> subscriptionOptional = rentalRepository.findById(id);
        if (! subscriptionOptional.isPresent())
            throw new RentalNotFound("Rental " + id + " not found!");
        rentalRepository.delete(subscriptionOptional.get());
        return subscriptionOptional.get();
    }
}
