package com.edo.microservices.order_service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;



@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

@Constraint(
        validatedBy = {DateValidator.class}
)
public @interface DateConstrainValidator {
    String message() default "{jakarta.validation.constraints.Size.message}";
    int min() default 0;
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
