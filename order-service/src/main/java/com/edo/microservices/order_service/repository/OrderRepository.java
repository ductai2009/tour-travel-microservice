package com.edo.microservices.order_service.repository;

import com.edo.microservices.order_service.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, String> {

    boolean existsByOrderNumber(Integer orderNumber);


    void deleteByOrderNumber(Integer orderNumber);

    Optional<Orders> findByOrderNumber(Integer orderNumber);
}
