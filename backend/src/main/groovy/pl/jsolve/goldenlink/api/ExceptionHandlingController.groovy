package pl.jsolve.goldenlink.api
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import pl.jsolve.goldenlink.service.EntityNotFoundException

import static org.springframework.http.HttpStatus.NOT_FOUND

@RestControllerAdvice
class ExceptionHandlingController {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException)
    def notFound(EntityNotFoundException exception) {
        new Failure(
                statusCode: 404,
                message: exception.message
        )
    }
}
