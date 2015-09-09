package pl.jsolve.goldenlink.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Tag {

    private final String publicId;
    private final String label;

    @JsonCreator
    public Tag(@JsonProperty("publicId") String publicId, @JsonProperty("label") String label) {
        this.publicId = publicId;
        this.label = label;
    }

    public String getPublicId() {
        return publicId;
    }

    public String getLabel() {
        return label;
    }

}
