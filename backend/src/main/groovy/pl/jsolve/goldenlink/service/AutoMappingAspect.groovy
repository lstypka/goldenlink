package pl.jsolve.goldenlink.service

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

import static java.util.Arrays.stream
import static pl.jsolve.oven.annotationdriven.AnnotationDrivenMapper.isMappableToTargetClass
import static pl.jsolve.oven.annotationdriven.AnnotationDrivenMapper.map

@Aspect
@Component
class AutoMappingAspect {

    @Around(value = "@within(autoMap) || @annotation(autoMap)")
    def autoMap(ProceedingJoinPoint pjp, AutoMap autoMap) {

        def arguments = stream(pjp.getArgs()).map {
            if (isMappableToTargetClass(it, autoMap.argument())) {
                map it, autoMap.argument()
            } else {
                it
            }
        }.toArray()

        Object result = pjp.proceed(arguments)

        if (result instanceof Collection) {
            result.collect {
                map it, autoMap.result()
            }
        } else {
            map result, autoMap.result();
        }
    }
}
