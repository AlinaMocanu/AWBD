package com.proiect.rental.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Commission {
    private String instanceId;
    private int standardPercent;
    private int bonus;
}
