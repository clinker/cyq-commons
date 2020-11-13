package com.github.clinker.commons.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 身份证号码验证器。
 */
@Documented
@Constraint(validatedBy = IdCardNoValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IdCardNoConstraint {

	Class<?>[] groups() default {};

	String message() default "身份证号码无效";

	Class<? extends Payload>[] payload() default {};

}
