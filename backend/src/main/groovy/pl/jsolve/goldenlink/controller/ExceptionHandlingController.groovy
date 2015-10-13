package pl.jsolve.goldenlink.controller

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import pl.jsolve.goldenlink.dto.Failure
import pl.jsolve.goldenlink.exceptions.EntityNotFoundException
import pl.jsolve.goldenlink.exceptions.RootCategoryDeletionAttempt

import static org.springframework.http.HttpStatus.CONFLICT
import static org.springframework.http.HttpStatus.NOT_FOUND

@ControllerAdvice
class ExceptionHandlingController {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException)
    void notFound(EntityNotFoundException exception) {
        new Failure(
                statusCode: 404,
                message: exception.message
        )
    }

    @ResponseStatus(value = CONFLICT)
    @ExceptionHandler(RootCategoryDeletionAttempt)
    void conflict(RootCategoryDeletionAttempt exception) {
        new Failure(
                statusCode: 409,
                message: exception.message,
        )
    }
}
