package pl.jsolve.goldenlink.rest.dto;

import java.util.List;

public class Category {

    private final String publicId;
    private final String label;
    private boolean hasChildren;
    private final List<Category> children;

    public Category(String publicId, String label, boolean hasChildren,List<Category> children) {
        this.publicId = publicId;
        this.label = label;
        this.children = children;
        this.hasChildren = hasChildren;
    }

    public String getPublicId() {
        return publicId;
    }

    public String getLabel() {
        return label;
    }

    public List<Category> getChildren() {
        return children;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

}
