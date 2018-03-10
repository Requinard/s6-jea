package interceptors.bindings

import javax.interceptor.InterceptorBinding
import java.lang.annotation.Inherited
import java.lang.annotation.Retention

import java.lang.annotation.RetentionPolicy.RUNTIME

@Inherited
@InterceptorBinding
@Retention(RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class CensorKweetInterceptorBinding
