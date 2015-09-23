package pl.jsolve.goldenlink.rest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import pl.jsolve.goldenlink.rest.dto.Category;
import pl.jsolve.goldenlink.rest.dto.DashboardTile;

@Service
public class DashboardTilesService {

    private List<DashboardTile> tiles = Lists.newArrayList();

 
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

    private int random(int max) {
        return (int) (Math.random() * max);
    }
}
