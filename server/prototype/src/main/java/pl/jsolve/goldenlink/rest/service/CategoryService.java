package pl.jsolve.goldenlink.rest.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.jsolve.goldenlink.rest.dto.Category;
import pl.jsolve.goldenlink.rest.dto.DashboardTile;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

@Service
public class CategoryService {

	List<String> colours = Lists.newArrayList("panel-blue", "panel-red", "panel-green", "panel-orange", "panel-yellow", "panel-lavender", "panel-olivedrab", "panel-khaki", "panel-purple",
			"panel-grey", "panel-blue-grey", "panel-pink", "panel-indigo", "panel-green-lighten", "panel-green-darken", "panel-lime");

	public static List<Category> categories = Lists.newArrayList();

	@Autowired
	private DashboardTilesService dashboardTilesService;

	public List<Category> getMainCategories() {
		return Lists.newArrayList(Collections2.filter(categories, new Predicate<Category>() {

			@Override
			public boolean apply(Category input) {
				return input.getParentPublicId() == null;
			}

		}));
	}

	public List<Category> searchCategories(final String term) {
		return Lists.newArrayList(Collections2.filter(categories, new Predicate<Category>() {

			@Override
			public boolean apply(Category input) {
				return input.getLabel().toLowerCase().contains(term.toLowerCase());
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

	public Category createMainCategory(Category categoryToAdd) {
		categories.add(categoryToAdd);
		return categoryToAdd;
	}

	public Category createSubcategory(String parentPublicId, Category categoryToAdd) {
		Category parentCategory = findCategory(parentPublicId);
		Category subcategory = new Category(generateId(), categoryToAdd.getLabel(), false, parentPublicId, parentCategory.getCategoryGroup(), categoryToAdd.getIcon());
		categories.add(subcategory);
		if (!parentCategory.isHasChildren()) {
			parentCategory.setHasChildren(true);
		}

		dashboardTilesService.createTile(new DashboardTile(subcategory.getPublicId(), subcategory.getLabel(), randomColour(), random(100), subcategory.getCategoryGroup(), subcategory.getIcon()));
		return subcategory;
	}

	private String randomColour() {
		return colours.get(random(colours.size() - 1));
	}

	private static int random(int max) {
		return (int) (Math.random() * max);
	}

	public void deleteCategory(String publicId) {
		for (int i = 0; i < categories.size(); i++) {
			if (categories.get(i).getPublicId().equals(publicId)) {
				Category parentCategory = findCategory(categories.get(i).getParentPublicId());
				int numberOfChildren = 0;
				for (Category category : categories) {
					if (category.getParentPublicId() != null && category.getParentPublicId().equals(parentCategory.getPublicId())) {
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

	public Category findCategory(final String publicId) {
		for (Category category : categories) {
			if (category.getPublicId().equals(publicId)) {
				return category;

			}
		}
		return null;
	}

	private static String generateId() {
		return UUID.randomUUID().toString().substring(0, 32);
	}

}
