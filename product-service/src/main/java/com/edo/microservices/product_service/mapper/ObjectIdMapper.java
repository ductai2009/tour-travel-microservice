package com.edo.microservices.product_service.mapper;

import org.bson.types.ObjectId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ObjectIdMapper {

    default String toString(ObjectId objectId) {
        return (objectId != null) ? objectId.toHexString() : null;
    }

    default ObjectId toObjectId(String id) {
        return (id != null && ObjectId.isValid(id)) ? new ObjectId(id) : null;
    }
}