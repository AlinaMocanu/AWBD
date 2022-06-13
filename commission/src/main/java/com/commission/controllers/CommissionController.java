package com.commission.controllers;

import com.commission.config.PropertiesConfig;
import com.commission.models.Commission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CommissionController {
    @Autowired
    private PropertiesConfig configuration;

    @Operation(summary = "Get commission values")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "commission values found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Commission.class))}),
            @ApiResponse(responseCode = "404", description = "Commissions not found",
                    content = @Content)})
    @GetMapping("/commission")
    public Commission getCommission() {
        Commission commission = new Commission(configuration.getStandardPercent(), configuration.getBonus());
        //List<Commission> commissionList = asList(commission);
        //Link link = linkTo(methodOn(CommissionController.class).getCommissionWithParking()).withRel("getCommissionWithParking");
        //CollectionModel<Commission> result = CollectionModel.of(commissionList, link);

        return commission;
    }

    @Operation(summary = "Get commission for apartment with park slot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "total commission value retrieved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Commission.class))}),
            @ApiResponse(responseCode = "404", description = "Total commission value not found",
                    content = @Content)})
    @GetMapping("/commission/1")
    public int getCommissionWithParking(){
        return configuration.getStandardPercent() + configuration.getBonus();
    }

    @Operation(summary = "Get commission for apartment with park slot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "total commission value retrieved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Commission.class))}),
            @ApiResponse(responseCode = "404", description = "Total commission value not found",
                    content = @Content)})
    @GetMapping("/commission/0")
    public int getCommissionWithoutParking(){
        return configuration.getStandardPercent();
    }
}

