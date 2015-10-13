package pl.jsolve.goldenlink.service

import org.mockito.InjectMocks
import org.mockito.Mock
import pl.jsolve.goldenlink.dto.Category
import pl.jsolve.goldenlink.entity.CategoryEntity
import pl.jsolve.goldenlink.repository.CategoryRepository
import pl.jsolve.goldenlink.test.MockitoSpecification

import static org.mockito.BDDMockito.given

class CategoryServiceTest extends MockitoSpecification {

    @Mock
    CategoryRepository mockedCategoryRepository

    @InjectMocks
    CategoryService categoryService

    /*def 'Should create sub-category'() {
        given:
        def parentCategoryPublicId = 'parentId'
        def category = new Category(
                label: 'label',
                hasChildren: 'hasChildren',
                categoryGroup: 'categoryGroup',
                parentPublicId: 'parentPublicId',
                icon: 'icon'
        )

        when:
        categoryService.createSubcategory parentCategoryPublicId, category

        then:
    }*/

    def 'Should retrieve children of a parent'() {
        given:
        def parentId = 'parentId'
        given(mockedCategoryRepository.findByParentId(parentId)).willReturn([
                new CategoryEntity(
                        id: '1stGenerated1dh4sh',
                        label: 'Andy',
                        hasChildren: false,
                        categoryGroup: 'cartoons',
                        parentId: parentId,
                        icon: 'andy-anderson-pic'
                )
        ])

        when:
        def result = categoryService.retrieveChildrenCategories parentId

        then:
        result == [new Category(
                publicId: '1stGenerated1dh4sh',
                label: 'Andy',
                hasChildren: false,
                categoryGroup: 'cartoons',
                parentPublicId: parentId,
                icon: 'andy-anderson-pic'
        )]
    }

    def 'Should retrieve main categories'() {
        given:
        given(mockedCategoryRepository.findMainCategories()).willReturn([
                new CategoryEntity(
                        id: '1stGenerated1dh4sh',
                        label: 'Andy',
                        hasChildren: false,
                        categoryGroup: 'cartoons',
                        parentId: '2ndGenerated1dh4sh',
                        icon: 'andy-anderson-pic'
                ),
                new CategoryEntity(
                        id: '2stGenerated1dh4sh',
                        label: 'The life with Louie',
                        hasChildren: true,
                        categoryGroup: 'cartoons',
                        parentId: null,
                        icon: 'the-life-with-louie-logo'
                )
        ])

        when:
        def retrievedCategories = categoryService.retrieveMainCategories()

        then:
        retrievedCategories == [
                new Category(
                        publicId: '1stGenerated1dh4sh',
                        label: 'Andy',
                        hasChildren: false,
                        categoryGroup: 'cartoons',
                        parentPublicId: '2ndGenerated1dh4sh',
                        icon: 'andy-anderson-pic'
                ),
                new Category(
                        publicId: '2stGenerated1dh4sh',
                        label: 'The life with Louie',
                        hasChildren: true,
                        categoryGroup: 'cartoons',
                        parentPublicId: null,
                        icon: 'the-life-with-louie-logo'
                )
        ]
    }

    def 'Should search categories'() {
        given:
        def term = "life"
        given(mockedCategoryRepository.findByLabelContainingIgnoreCase(term)).willReturn([
                new CategoryEntity(
                        id: '2stGenerated1dh4sh',
                        label: 'The life with Louie',
                        hasChildren: true,
                        categoryGroup: 'cartoons',
                        parentId: null,
                        icon: 'the-life-with-louie-logo'
                )
        ])
        when:
        def foundCategories = categoryService.searchCategories(term)

        then:
        foundCategories == [
                new Category(
                        publicId: '2stGenerated1dh4sh',
                        label: 'The life with Louie',
                        hasChildren: true,
                        categoryGroup: 'cartoons',
                        parentPublicId: null,
                        icon: 'the-life-with-louie-logo'
                )
        ]
    }
}
