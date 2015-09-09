package pl.jsolve.goldenlink.rest.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import pl.jsolve.goldenlink.rest.dto.Category;
import pl.jsolve.goldenlink.rest.dto.DashboardTile;

import com.google.common.collect.Lists;

@Service
public class DashboardTilesService {

    private List<DashboardTile> tiles;

    List<String> colours = Lists.newArrayList("panel-blue", "panel-red", "panel-green", "panel-orange", "panel-yellow",
            "panel-lavender", "panel-olivedrab", "panel-khaki", "panel-purple", "panel-grey", "panel-blue-grey",
            "panel-pink", "panel-indigo", "panel-green-lighten", "panel-green-darken", "panel-lime");

    public List<DashboardTile> getTiles() {
        return tiles;
    }

    public List<DashboardTile> updateTiles(List<DashboardTile> tiles) {
        this.tiles = tiles;
        return tiles;
    }

    public DashboardTile createTile(DashboardTile tile) {

        DashboardTile newTile = new DashboardTile(tile.getPublicId(), tile.getLabel(), tile.getColour(), random(1000),
                tile.getCategoryGroup(), tile.getIcon());

        // w prawdziwej aplikacji informacje powinny byc brane z bazy (label, categoryGroup)
        this.tiles.add(newTile);
        return newTile;
    }

    public void updateTilesAfterCategoryUpdate(Category updatedCategory) {
        for (DashboardTile dashboardTile : tiles) {
            if (dashboardTile.getPublicId() != null
                    && dashboardTile.getPublicId().equals(updatedCategory.getPublicId())) {
                dashboardTile.setLabel(updatedCategory.getLabel());
                dashboardTile.setIcon(updatedCategory.getIcon());
            }
        }
    }

    @PostConstruct
    void setUp() {
        tiles = Lists.newArrayList();

        if (CategoryService.categories == null) {
            CategoryService.setUp();
        }

        List<Category> categories = CategoryService.categories;
        int random = random(categories.size());
        if (random > 100) {
            random = 100;
        }

        for (int i = 0; i < random; i++) {
            Category category = categories.get(i);
            tiles.add(new DashboardTile(category.getPublicId(), category.getLabel(), randomColour(), random(1000),
                    category.getCategoryGroup(), category.getIcon()));
        }

    }

    private String randomColour() {
        return colours.get(random(colours.size() - 1));
    }

    private int random(int max) {
        return (int) (Math.random() * max);
    }
}
