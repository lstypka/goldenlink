package pl.jsolve.goldenlink.exceptions

class CategoryNotFound extends EntityNotFoundException{

    CategoryNotFound(String id) {
        super("No category with code ${id} was found")
    }
}
