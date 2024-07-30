package com.example.infrastructure.dispatcher_servlet;

import com.example.infrastructure.dispatcher_servlet.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    HttpMethod method();
    String path() default "";
}
