package pl.jsolve.goldenlink.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Author {

    private final String publicId;
    private final String name;

    @JsonCreator
    public Author(@JsonProperty("publicId") String publicId, @JsonProperty("name") String name) {
        this.publicId = publicId;
        this.name = name;
    }

    public String getPublicId() {
        return publicId;
    }

    public String getName() {
        return name;
    }

}
