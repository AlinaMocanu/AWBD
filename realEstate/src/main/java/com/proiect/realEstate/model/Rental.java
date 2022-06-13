package com.proiect.realEstate.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "rental")
public class Rental extends RepresentationModel<Rental> {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "no_of_rooms")
    private int noOfRooms;

    @Column(name = "price")
    private double price;

    @Column(name = "parking")
    private Boolean parking;

}
