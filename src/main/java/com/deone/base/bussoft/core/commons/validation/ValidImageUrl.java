package com.deone.base.bussoft.core.commons.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import com.deone.base.bussoft.core.commons.validation.impl.ValidImageUrlValidator;

@Documented
@Constraint(validatedBy = ValidImageUrlValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidImageUrl {
    String message() default "La URL proporcionada no es una imagen válida.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
