package com.telstra.codechallenge.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RepositoriesItemsDTO {
    @JsonProperty("html_url")
    private String html_url;

    @JsonProperty("watchers_count")
    private long watchers_count;

    @JsonProperty("language")
    private String language;

    @JsonProperty("description")
    private String description;

    @JsonProperty("name")
    private String name;

}
