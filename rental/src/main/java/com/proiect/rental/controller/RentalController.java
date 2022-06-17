package com.proiect.rental.controller;

import com.proiect.rental.model.*;
import com.proiect.rental.service.CommissionServiceProxy;
import com.proiect.rental.service.RentalService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RentalController {
    @Autowired
    RentalService rentalService;

    @Autowired
    CommissionServiceProxy commissionServiceProxy;

    @GetMapping(value = "/rental/list", produces = {"application/hal+json"})
    public CollectionModel<Rental> findAll() {
        List<Rental> rentals = rentalService.findAll();
        for (final Rental rental : rentals) {
            Link selfLink = linkTo(methodOn(RentalController.class).getRental(rental.getId())).withSelfRel();
            rental.add(selfLink);
            Link deleteLink = linkTo(methodOn(RentalController.class).deleteRental(rental.getId())).withRel("deleteFlat");
            rental.add(deleteLink);
        }
        Link link = linkTo(methodOn(RentalController.class).findAll()).withSelfRel();
        CollectionModel<Rental> result = CollectionModel.of(rentals, link);
        return result;
    }

   /* @GetMapping("/rental/noOfRooms/{noOfRooms}")
    Rental findByNoOfRooms(@PathVariable int noOfRooms) {
        Rental rental = rentalService.findByNoOfRooms(noOfRooms);
        Link selfLink = linkTo(methodOn(RentalController.class).getRental(rental.getId())).withSelfRel();
        rental.add(selfLink);
        return rental;
    }*/


    @PostMapping("/rental")
    public ResponseEntity<Rental> save(@Valid @RequestBody Rental rental) {
        Rental savedRental = rentalService.save(rental);
        URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{subscriptionId}").buildAndExpand(savedRental.getId())
                .toUri();


        return ResponseEntity.created(locationUri).body(savedRental);
    }


    @Operation(summary = "delete rental by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "rental deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Rental.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Rental not found",
                    content = @Content)})
    @DeleteMapping("/rental/{rentalId}")
    public Rental deleteRental(@PathVariable Long rentalId) {

        Rental subscription = rentalService.delete(rentalId);
        return subscription;
    }

    @GetMapping("/rental/{rentalId}")
    public Rental getRental(@PathVariable Long rentalId) {
        Rental rental = rentalService.findById(rentalId);
        return rental;
    }

    @GetMapping("/rental/noOfRooms/{noOfRooms}")
    @CircuitBreaker(name="rentalByNoOfRooms", fallbackMethod = "getRentalFallback")
    Rental findByNoOfRooms(@PathVariable int noOfRooms){
        Rental rental = rentalService.findByNoOfRooms(noOfRooms);
        Commission commission = commissionServiceProxy.findCommission();
        if (rental.getParking()) {
            rental.setPrice((commission.getBonus() + commission.getStandardPercent())/100.0 * rental.getPrice() + rental.getPrice());
        } else {
            rental.setPrice(commission.getStandardPercent()/100 * rental.getPrice() + rental.getPrice());
        }

        return rental;
    }

    private Rental getRentalFallback(int noOfRooms, Throwable throwable) {
        Rental rental = rentalService.findByNoOfRooms(noOfRooms);
        return rental;
    }
}
