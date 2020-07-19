package com.telstra.codechallenge.service;

import com.telstra.codechallenge.dtos.RepositoriesItemsDTO;

import java.util.List;

public interface GithubRepostoriesService {

    List<RepositoriesItemsDTO> getStarredRepositories(int size);

}
