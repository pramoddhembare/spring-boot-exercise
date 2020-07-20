package com.telstra.codechallenge.controller;

import com.telstra.codechallenge.dtos.RepositoriesItemsDTO;
import com.telstra.codechallenge.exceptions.BasicClientValidationException;
import com.telstra.codechallenge.service.GithubRepostoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class GithubRepositoriesController {


    private GithubRepostoriesService githubRepositoriesService;

    @Autowired
    public GithubRepositoriesController(
            GithubRepostoriesService githubRepositoriesService) {
        this.githubRepositoriesService = githubRepositoriesService;
    }


    @GetMapping(path = "/github/repositories")
    public List<RepositoriesItemsDTO> getRepositories(@NotNull @RequestParam("size") int size)
            throws BasicClientValidationException {
        //handle client validation exception
        if (size < 0) {
            throw new BasicClientValidationException("INVALID_SIZE");
        }
        return githubRepositoriesService.getStarredRepositories(size);
    }

}
