package pl.jsolve.goldenlink.service

import com.google.common.collect.Lists
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import pl.jsolve.goldenlink.dto.Category

import javax.annotation.PostConstruct

import static java.util.stream.Collectors.toList

@Service
class CategoryService {

    static List<Category> categories

    private static boolean shouldInit = true

    @Autowired
    private DashboardTilesService dashboardTilesService

    static def icons = ["fa-asterisk",
                        "fa-bomb",
                        "fa-bell",
                        "fa-book",
                        "fa-bus",
                        "fa-bed",
                        "fa-ban",
                        "fa-check",
                        "fa-dashboard",
                        "fa-diamond",
                        "fa-edit"]

    def getMainCategories() {
        categories.stream()
                .filter { it.parentPublicId == null }
                .collect toList()
    }

    def searchCategories(String term) {
        categories.stream()
                .filter { it.label.toLowerCase().contains term.toLowerCase() }
                .collect toList()
    }

    def getChildrenCategories(String parentPublicId) {
        categories.stream()
                .filter { it?.parentPublicId == parentPublicId }
                .collect toList()
    }

    def updateCategory(String publicId, Category categoryToUpdate) {
        def foundCategory = findCategory(publicId).orElseThrow {
            throw new IllegalArgumentException("Could not find category with id ${publicId}")
        }

        foundCategory.label = categoryToUpdate.label
        foundCategory.icon = categoryToUpdate.icon

        dashboardTilesService.updateTilesAfterCategoryUpdate foundCategory

        categoryToUpdate
    }

    def createSubcategory(String parentPublicId, Category categoryToAdd) {
        Category parentCategory = findCategory(parentPublicId).orElseThrow {
            throw new IllegalArgumentException("Could not find category with id ${parentPublicId}")
        }
        Category subcategory = new Category(
                publicId: generateId(),
                label: categoryToAdd.label,
                hasChildren: false,
                categoryGroup: parentPublicId,
                parentPublicId: parentCategory.categoryGroup,
                icon: categoryToAdd.icon
        )
        categories.add subcategory
        parentCategory.hasChildren = true

        subcategory
    }

    def deleteCategory(String publicId) {
        findCategory(publicId).ifPresent {

            findCategory(it.parentPublicId).ifPresent {
                def numberOfChildren = getChildrenCategories(it.publicId).size()
                if (numberOfChildren == 1) {
                    it.hasChildren = false
                }
            }
            categories.remove it
        }
    }

    // ??
    def getBreadcrumbs(String parentPublicId) {
        List<Category> breadcrumbs = Lists.newArrayList()
        while (parentPublicId != null) {
            boolean found = false
            for (Category category : categories) {
                if (category.getPublicId().equals(parentPublicId)) {
                    breadcrumbs.add(0, category)
                    parentPublicId = category.getParentPublicId()
                    found = true
                    break
                }
            }
            if (!found) {
                System.out.println("Nie znalazlem zadnego :(")
                break
            }
        }

        return breadcrumbs
    }

    def findCategory(String publicId) {
        Optional.ofNullable(categories.find { it.publicId == publicId })
    }

    @PostConstruct
    def postConstruct() {
        if (shouldInit) {
            setUp()
        }
    }

    static def setUp() {
        shouldInit = false
        categories = [
                predefinedCategory("LINKS"),
                predefinedCategory("VIDEOS"),
                predefinedCategory("YOUTUBE")
        ]

        (0..10).each {
            def category = categories.get(it)
            def generateChildren = generateChildren(category.publicId, 0, category.categoryGroup)
            if (generateChildren == 0) {
                category.hasChildren = false
            }
        }
    }

    private static Category predefinedCategory(String name) {
        new Category(
                publicId: generateId(),
                label: name,
                hasChildren: true,
                categoryGroup: null,
                parentPublicId: name,
                icon: randomIcon())
    }

    private static def randomIcon() {
        icons.get random(icons.size() - 1)
    }

    private static def generateChildren(String parentId, int level, String categoryGroup) {
        if (level > 6) {
            return 0
        }
        int numberOfChildren = randomNumberOfChildren()
        for (int j = 0; j < numberOfChildren; j++) {

            String publicId = generateId()
            int children = generateChildren(publicId, level + 1, categoryGroup)
            categories.add(new Category(publicId, publicId, children > 0,
                    parentId, categoryGroup, randomIcon()))
        }
        return numberOfChildren
    }

    private static int randomNumberOfChildren() {
        return random(4)
    }

    private static int random(int max) {
        return (int) (Math.random() * max)
    }

    private static String generateId() {
        return UUID.randomUUID().toString().substring(0, 32)
    }
}
