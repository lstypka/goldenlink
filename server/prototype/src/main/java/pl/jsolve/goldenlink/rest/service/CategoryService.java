package pl.jsolve.goldenlink.rest.service;

import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import pl.jsolve.goldenlink.rest.dto.Category;

@Service
public class CategoryService {

    public static List<Category> categories;

    private static boolean shouldInit = true;

    @Autowired
    private DashboardTilesService dashboardTilesService;

    static List<String> icons = Lists.newArrayList("fa-asterisk", "fa-bomb", "fa-bell", "fa-book", "fa-bus", "fa-bed",
            "fa-ban", "fa-check", "fa-dashboard", "fa-diamond", "fa-edit");

    public List<Category> getMainCategories() {
        return Lists.newArrayList(Collections2.filter(categories, new Predicate<Category>() {

            @Override
            public boolean apply(Category input) {
                return input.getParentPublicId() == null;
            }

        }));
    }

    public List<Category> getChildrenCategories(final String parentPublicId) {
        return Lists.newArrayList(Collections2.filter(categories, new Predicate<Category>() {

            @Override
            public boolean apply(Category input) {
                return input.getParentPublicId() != null && input.getParentPublicId().equals(parentPublicId);
            }

        }));
    }

    public Category updateCategory(String publicId, Category categoryToUpdate) {
        Category foundCategory = findCategory(publicId);
        foundCategory.setLabel(categoryToUpdate.getLabel());
        foundCategory.setIcon(categoryToUpdate.getIcon());

        dashboardTilesService.updateTilesAfterCategoryUpdate(foundCategory);
        return categoryToUpdate;
    }

    public Category createSubcategory(String parentPublicId, Category categoryToAdd) {
        Category parentCategory = findCategory(parentPublicId);
        Category subcategory = new Category(generateId(), categoryToAdd.getLabel(), false, parentPublicId,
                parentCategory.getCategoryGroup(), categoryToAdd.getIcon());
        categories.add(subcategory);
        if (!parentCategory.isHasChildren()) {
            parentCategory.setHasChildren(true);
        }

        return subcategory;
    }

    public void deleteCategory(String publicId) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getPublicId().equals(publicId)) {
                Category parentCategory = findCategory(categories.get(i).getParentPublicId());
                int numberOfChildren = 0;
                for (Category category : categories) {
                    if (category.getParentPublicId() != null
                            && category.getParentPublicId().equals(parentCategory.getPublicId())) {
                        numberOfChildren++;
                    }
                }
                if (numberOfChildren == 1) {
                    parentCategory.setHasChildren(false);
                }
                categories.remove(i);
            }
        }
    }

    public List<Category> getBreadcrumbs(String parentPublicId) {
        List<Category> breadcrumbs = Lists.newArrayList();
        while (parentPublicId != null) {
            boolean found = false;
            for (Category category : categories) {
                if (category.getPublicId().equals(parentPublicId)) {
                    breadcrumbs.add(0, category);
                    parentPublicId = category.getParentPublicId();
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Nie znalazlem zadnego :(");
                break;
            }
        }

        return breadcrumbs;
    }

    private Category findCategory(final String publicId) {
        for (Category category : categories) {
            if (category.getPublicId().equals(publicId)) {
                return category;

            }
        }
        return null;
    }

    @PostConstruct
    void postConstruct() {
        if (shouldInit) {
            setUp();
        }
    }

    public static void setUp() {
        shouldInit = false;
        categories = Lists.newArrayList();

        // icons

        // main categories
        categories.add(new Category(generateId(), "Linki", true, null, "links", randomIcon()));
        categories.add(new Category(generateId(), "Zdjęcia", true, null, "photos", randomIcon()));
        categories.add(new Category(generateId(), "Filmy", true, null, "videos", randomIcon()));
        categories.add(new Category(generateId(), "Youtube", true, null, "youtube", randomIcon()));
        categories.add(new Category(generateId(), "Notatki", true, null, "notes", randomIcon()));

        // inner categories
        for (int i = 0; i < 10; i++) {
            int generateChildren = generateChildren(categories.get(i).getPublicId(), 0, categories.get(i)
                    .getCategoryGroup());
            if (generateChildren == 0) {
                categories.get(i).setHasChildren(false);
            }
        }
    }

    private static String randomIcon() {
        return icons.get(random(icons.size() - 1));
    }

    private static int generateChildren(String parentId, int level, String categoryGroup) {
        if (level > 6) {
            return 0;
        }
        int numberOfChildren = randomNumberOfChildren();
        for (int j = 0; j < numberOfChildren; j++) {

            String publicId = generateId();
            int children = generateChildren(publicId, level + 1, categoryGroup);
            categories.add(new Category(publicId, publicId, children > 0, parentId, categoryGroup, randomIcon()));
        }
        return numberOfChildren;
    }

    private static int randomNumberOfChildren() {
        return random(4);
    }

    private static int random(int max) {
        return (int) (Math.random() * max);
    }

    private static String generateId() {
        return UUID.randomUUID().toString().substring(0, 32);
    }

}
