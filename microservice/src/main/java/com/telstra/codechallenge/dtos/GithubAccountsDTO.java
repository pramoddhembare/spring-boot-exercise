package com.telstra.codechallenge.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GithubAccountsDTO {

    @JsonProperty("items")
    private List<AccountItemDTO> items;

    public List<AccountItemDTO> getItems() {
        return items;
    }

    public void setItems(List<AccountItemDTO> items) {
        this.items = items;
    }
}


