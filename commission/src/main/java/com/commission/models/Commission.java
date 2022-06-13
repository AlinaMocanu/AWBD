package com.commission.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
@AllArgsConstructor
public class Commission extends RepresentationModel<Commission> {
    private int standardPercent;
    private int bonus;
}
