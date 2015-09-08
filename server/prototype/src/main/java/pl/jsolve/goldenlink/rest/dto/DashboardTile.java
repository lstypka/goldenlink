package pl.jsolve.goldenlink.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DashboardTile {

    private final String publicId;
    private String label;
    private final String colour;
    private final Integer numberOfLinks;
    private final String categoryGroup;
    private String icon;

    @JsonCreator
    public DashboardTile(@JsonProperty("publicId") String publicId, @JsonProperty("label") String label,
            @JsonProperty("colour") String colour, @JsonProperty("numberOfLinks") Integer numberOfLinks,
            @JsonProperty("categoryGroup") String categoryGroup, @JsonProperty("icon") String icon) {
        this.publicId = publicId;
        this.label = label;
        this.colour = colour;
        this.numberOfLinks = numberOfLinks;
        this.categoryGroup = categoryGroup;
        this.icon = icon;
    }

    public String getPublicId() {
        return publicId;
    }

    public String getLabel() {
        return label;
    }

    public String getColour() {
        return colour;
    }

    public Integer getNumberOfLinks() {
        return numberOfLinks;
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
