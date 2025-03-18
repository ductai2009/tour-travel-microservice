package com.edo.microservices.product_service.service;

import com.edo.microservices.product_service.dto.request.TourRequest;
import com.edo.microservices.product_service.dto.request.TourUpdateRequest;
import com.edo.microservices.product_service.dto.response.TourResponse;
import com.edo.microservices.product_service.entity.Tour;
import com.edo.microservices.product_service.exception.AppException;
import com.edo.microservices.product_service.exception.ErrorCode;
import com.edo.microservices.product_service.mapper.TourMapper;
import com.edo.microservices.product_service.repository.TourRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TourService {

    @Autowired
    TourMapper tourMapper;

    @Autowired
    TourRepository tourRepository;
    public TourResponse createTour(TourRequest request) {
        Tour tour = tourMapper.toTour(request);
        return tourMapper.toTourResponse(tourRepository.save(tour));
    }
    public TourResponse updateTour(TourUpdateRequest request){
        Tour tour = tourRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_EXISTED));
        Tour tourUpdate = tourMapper.updateTour(tour, request);

        return tourMapper.toTourResponse(tourRepository.save(tourUpdate));
    }


    public List<TourResponse> getAllTour(){
        return tourRepository.findAll().stream().map(tour -> tourMapper.toTourResponse(tour)).toList();
    }
    public TourResponse getTourById(String id){
        return tourMapper.toTourResponse(tourRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TOUR_NOT_EXISTED)));
    }
    public void deleteTour(String id){
        tourRepository.deleteById(id);
    }


}
