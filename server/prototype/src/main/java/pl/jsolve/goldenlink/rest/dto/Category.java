package pl.jsolve.goldenlink.rest.dto;

public class Category {

    private final String publicId;
    private final String label;
    private boolean hasChildren;
    private final String categoryGroup;
    private final String parentPublicId;
    private final String icon;

    public Category(String publicId, String label, boolean hasChildren, String parentPublicId, String categoryGroup,
            String icon) {
        this.publicId = publicId;
        this.label = label;
        this.parentPublicId = parentPublicId;
        this.hasChildren = hasChildren;
        this.categoryGroup = categoryGroup;
        this.icon = icon;
    }

    public String getPublicId() {
        return publicId;
    }

    public String getLabel() {
        return label;
    }

    public String getParentPublicId() {
        return parentPublicId;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public String getCategoryGroup() {
        return categoryGroup;
    }

    public String getIcon() {
        return icon;
    }

}
