package pl.jsolve.goldenlink.service

class EntityNotFoundException extends ExposedServiceException {

    EntityNotFoundException(String message) {
        super(message)
    }
}
