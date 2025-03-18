package com.edo.microservices.product_service.mapper;
import com.edo.microservices.product_service.dto.request.TourRequest;
import com.edo.microservices.product_service.dto.request.TourUpdateRequest;
import com.edo.microservices.product_service.dto.response.TourResponse;
import com.edo.microservices.product_service.entity.Tour;
import org.mapstruct.*;


//@Mapper(componentModel = "spring")
@Mapper(componentModel = "spring", uses = ObjectIdMapper.class)
public interface TourMapper {

    @Mapping(target = "id", ignore = true)
    Tour toTour(TourRequest request);

    TourResponse toTourResponse(Tour tour);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    Tour updateTour(@MappingTarget Tour Tour, TourUpdateRequest request);
}
