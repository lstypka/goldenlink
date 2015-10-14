package pl.jsolve.goldenlink.service.category

import pl.jsolve.goldenlink.service.ExposedServiceException

class RootCategoryDeletionAttempt extends ExposedServiceException {
    RootCategoryDeletionAttempt(String id) {
        super("Cannot delete root category with ID: ${id}")
    }
}
