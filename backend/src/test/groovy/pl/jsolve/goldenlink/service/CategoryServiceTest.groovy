package pl.jsolve.goldenlink.service
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.jsolve.goldenlink.infrastructure.category.CategoryEntity
import pl.jsolve.goldenlink.infrastructure.category.CategoryRepository
import pl.jsolve.goldenlink.service.category.CategoryService
import pl.jsolve.goldenlink.test.specs.MockitoSpecification

import static org.mockito.BDDMockito.given
import static org.mockito.Matchers.any
import static org.mockito.Mockito.mock

class CategoryServiceTest extends MockitoSpecification {

    @Mock
    CategoryRepository mockedCategoryRepository

    @InjectMocks
    CategoryService categoryService

    def 'Should get breadcrumbs'() {
        given: 'relationship root -> c -> b -> a'
        def root = new CategoryEntity(id: 'rootId', parentId: null)
        def c = new CategoryEntity(id: 'cId', parentId: root.id)
        def b = new CategoryEntity(id: 'bId', parentId: c.id)
        def a = new CategoryEntity(id: 'aId', parentId: b.id)
        given(mockedCategoryRepository.findOne(root.id)).willReturn(root)
        given(mockedCategoryRepository.findOne(c.id)).willReturn(c)
        given(mockedCategoryRepository.findOne(b.id)).willReturn(b)
        given(mockedCategoryRepository.findOne(a.id)).willReturn(a)

        when:
        def breadcrumbs = categoryService.getBreadcrumbs(a.id)

        then:
        breadcrumbs == [root, c, b, a]
    }

    def 'Should create sub-category'() {
        given:
        def parentCategoryPublicId = 'parentId'
        def subcategory = new CategoryEntity(
                label: 'label',
                hasChildren: true,
                categoryGroup: 'categoryGroup',
                parentId: 'parentPublicId',
                icon: 'icon'
        )
        def parentCategory = mock(CategoryEntity)
        given(mockedCategoryRepository.findOne(parentCategoryPublicId)).willReturn(parentCategory)
        given(mockedCategoryRepository.save(any(CategoryEntity))).willReturn(subcategory)

        when:
        def result = categoryService.createSubcategory parentCategoryPublicId, subcategory

        then:
        result == subcategory
    }
}
