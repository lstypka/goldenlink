package pl.jsolve.goldenlink.service.category

import pl.jsolve.goldenlink.service.EntityNotFoundException

class CategoryNotFound extends EntityNotFoundException{

    CategoryNotFound(String id) {
        super("No category with code ${id} was found")
    }
}
