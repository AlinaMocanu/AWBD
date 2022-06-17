package com.proiect.rental.service;

import com.proiect.rental.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "commission", url = "localhost:8081")
public interface CommissionServiceProxy {
    @GetMapping("/commission")
    Commission findCommission();
}
