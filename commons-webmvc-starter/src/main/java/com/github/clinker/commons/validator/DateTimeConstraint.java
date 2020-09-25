package com.github.clinker.commons.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 日期时间验证器。null视为有效。
 */
@Documented
@Constraint(validatedBy = DateTimeValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DateTimeConstraint {

	Class<?>[] groups() default {};

	String message() default "日期时间无效";

	Class<? extends Payload>[] payload() default {};

}
