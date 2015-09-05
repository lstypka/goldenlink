package pl.jsolve.goldenlink.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Category {

    private final String publicId;
    private String label;
    private boolean hasChildren;
    private final String categoryGroup;
    private final String parentPublicId;
    private String icon;

    @JsonCreator
    public Category(@JsonProperty("publicId") String publicId, @JsonProperty("label") String label,
            @JsonProperty("hasChildren") boolean hasChildren, @JsonProperty("parentPublicId") String parentPublicId,
            @JsonProperty("categoryGroup") String categoryGroup, @JsonProperty("icon") String icon) {
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

    public void setLabel(String label) {
        this.label = label;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
