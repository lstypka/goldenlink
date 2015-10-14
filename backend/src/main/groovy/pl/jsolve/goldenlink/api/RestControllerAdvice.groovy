package pl.jsolve.goldenlink.api

import groovy.transform.AnnotationCollector
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ResponseBody

@ControllerAdvice
@ResponseBody
@AnnotationCollector
@interface RestControllerAdvice {}