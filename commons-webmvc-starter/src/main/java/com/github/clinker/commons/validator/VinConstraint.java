package com.github.clinker.commons.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * VIN验证器。
 */
@Documented
@Constraint(validatedBy = VinValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface VinConstraint {

	Class<?>[] groups() default {};

	String message() default "车架号无效";

	Class<? extends Payload>[] payload() default {};

}
