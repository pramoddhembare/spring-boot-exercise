package com.telstra.codechallenge.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GithubRepositoriesDTO {

    @JsonProperty("items")
    private List<RepositoriesItemsDTO> items;

    public List<RepositoriesItemsDTO> getItems() {
        return items;
    }

    public void setItems(List<RepositoriesItemsDTO> items) {
        this.items = items;
    }
}
