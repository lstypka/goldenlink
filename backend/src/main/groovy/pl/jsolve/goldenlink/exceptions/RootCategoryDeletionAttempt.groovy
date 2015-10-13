package pl.jsolve.goldenlink.exceptions

class RootCategoryDeletionAttempt extends ExposedException {
    RootCategoryDeletionAttempt(String id) {
        super("Cannot delete root category ID: ${id}")
    }
}
