package com.edo.microservices.product_service.controller;

import com.edo.microservices.product_service.dto.request.TourRequest;
import com.edo.microservices.product_service.dto.request.TourUpdateRequest;
import com.edo.microservices.product_service.dto.response.ResponseData;
import com.edo.microservices.product_service.dto.response.TourResponse;
import com.edo.microservices.product_service.service.TourService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/tours")
public class TourController {
    @Autowired
    TourService tourService;

    @PostMapping
    ResponseData<TourResponse> createTour(@Valid @RequestBody TourRequest request) {
        ResponseData<TourResponse> response = new ResponseData<>();

        response.setCode(1000);
        response.setMessage("Tour created successfully");
        response.setResult(tourService.createTour(request));

        return response;
    }
    @PostMapping("/update")
    ResponseData<TourResponse> updateTour(@Valid @RequestBody TourUpdateRequest request) {
        TourResponse response = tourService.updateTour(request);

        return ResponseData.<TourResponse>builder()
                .result(response)
                .message("Update tour successfully")
                .build();
    }
    @GetMapping
    ResponseData<List<TourResponse>> getAllTour() {
        ResponseData<List<TourResponse> > response = new ResponseData<>();

        response.setCode(1000);
        response.setMessage("Get all tour successfully");
        response.setResult(tourService.getAllTour());

        return response;
    }
    @GetMapping("/{id}")
    ResponseData<TourResponse> getTourById(@PathVariable("id") String id) {
        TourResponse response = tourService.getTourById(id);

        return ResponseData.<TourResponse>builder()
                .result(response)
                .message("Get tour successfully")
                .build();
    }
    @DeleteMapping("/{id}")
    ResponseData<String> deleteById(@PathVariable("id") String id) {
        tourService.deleteTour(id);
        return ResponseData.<String>builder()
                .message("Delete tour successfully")
                .build();
    }

}
