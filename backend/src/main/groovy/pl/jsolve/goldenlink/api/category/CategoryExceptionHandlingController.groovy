package pl.jsolve.goldenlink.api.category
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import pl.jsolve.goldenlink.api.Failure
import pl.jsolve.goldenlink.api.RestControllerAdvice
import pl.jsolve.goldenlink.service.category.RootCategoryDeletionAttempt

import static org.springframework.http.HttpStatus.BAD_REQUEST

@RestControllerAdvice
class CategoryExceptionHandlingController {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(RootCategoryDeletionAttempt)
    def conflict(RootCategoryDeletionAttempt exception) {
        new Failure(
                statusCode: 409,
                message: exception.message,
        )
    }
}
