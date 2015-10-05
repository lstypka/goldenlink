package pl.jsolve.goldenlink.repository

import org.springframework.beans.factory.annotation.Autowired
import pl.jsolve.goldenlink.entity.CategoryEntity
import pl.jsolve.goldenlink.test.DatabaseIntegrationSpecification

class CategoryRepositoryIntegrationTest extends DatabaseIntegrationSpecification {

    @Autowired
    CategoryRepository categoryRepository

    def 'Should save a category'() {
        given:
        def andysCheesecake = new CategoryEntity(
                label: "Andy's cheesecake",
                hasChildren: false,
                categoryGroup: 'Fish named Pepper',
                icon: 'pepper'
        )

        when:
        def persistedCake = categoryRepository.save andysCheesecake

        then:
        !persistedCake.id.isEmpty()
        persistedCake.label == andysCheesecake.label
        persistedCake.hasChildren == andysCheesecake.hasChildren
        persistedCake.categoryGroup == andysCheesecake.categoryGroup
        persistedCake.icon == andysCheesecake.icon
    }

    def 'Should retrieve a category'() {
        given:
        def cheesecake = categoryRepository.save new CategoryEntity(
                label: "Andy's cheesecake",
                hasChildren: false,
                categoryGroup: 'Fish named Pepper',
                icon: 'pepper'
        )

        when:
        def foundCategory = categoryRepository.findOne cheesecake.id

        then:
        foundCategory == cheesecake
    }

    def 'Should retrieve all categories'() {
        given:
        def cheesecake = categoryRepository.save new CategoryEntity(
                label: "Andy's cheesecake",
        )
        def bar = categoryRepository.save new CategoryEntity(
                label: "Louie's bar",
        )

        when:
        def foundCategories = categoryRepository.findAll()

        then:
        foundCategories*.label == [cheesecake.label, bar.label]
    }

    def 'Should delete Andy Anderson as an author'() {
        given:
        def cheesecake = categoryRepository.save new CategoryEntity(
                label: "Andy's cheesecake",
        )
        def bar = categoryRepository.save new CategoryEntity(
                label: "Louie's bar",
        )

        when:
        categoryRepository.delete bar

        then:
        categoryRepository.findAll() == [cheesecake]
    }

    def "Should update `Andee's chesecake` label"() {
        given:
        def cheesecake = categoryRepository.save new CategoryEntity(
                label: "Andee's cheesecake",
        )

        when:
        cheesecake.label = "Andy's cheesecake"
        def updatedCheesecake = categoryRepository.save cheesecake

        then:
        updatedCheesecake.label == "Andy's cheesecake"
    }
}
