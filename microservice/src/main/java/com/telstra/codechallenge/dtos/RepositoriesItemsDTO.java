package com.telstra.codechallenge.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class RepositoriesItemsDTO {
    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("watchers_count")
    private long watchersCount;

    @JsonProperty("language")
    private String language;

    @JsonProperty("description")
    private String description;

    @JsonProperty("name")
    private String name;

    public RepositoriesItemsDTO(String htmlUrl, long watchersCount, String language, String description, String name) {
        this.htmlUrl = htmlUrl;
        this.watchersCount = watchersCount;
        this.language = language;
        this.description = description;
        this.name = name;
    }
}
