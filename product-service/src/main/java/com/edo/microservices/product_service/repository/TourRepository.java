package com.edo.microservices.product_service.repository;
import com.edo.microservices.product_service.entity.Tour;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TourRepository extends MongoRepository<Tour, String> {

}
