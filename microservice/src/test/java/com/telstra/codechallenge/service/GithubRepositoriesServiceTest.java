package com.telstra.codechallenge.service;

import com.telstra.codechallenge.dtos.GithubRepositoriesDTO;
import com.telstra.codechallenge.dtos.RepositoriesItemsDTO;
import com.telstra.codechallenge.service.impl.GithubRepositoriesServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class GithubRepositoriesServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @MockBean
    private GithubRepostoriesService githubRepostoriesService;


    @Before
    public void setUp() {
        restTemplate = Mockito.mock(RestTemplate.class);
        githubRepostoriesService = new GithubRepositoriesServiceImpl(restTemplate);
    }

    @Test
    public void getStarredRepositories() {
        List<RepositoriesItemsDTO> repositoriesItemsDTO = new ArrayList<>();
        //test data added
        RepositoriesItemsDTO obj = new RepositoriesItemsDTO("https://github.com/mrdrag1/ALI-ATTACKER"
                , 8, "shell", "test description", "log");
        repositoriesItemsDTO.add(obj);

        GithubRepositoriesDTO githubRepositoriesDTO = new GithubRepositoriesDTO();
        githubRepositoriesDTO.setItems(repositoriesItemsDTO);

        HttpHeaders requestHeaders = new HttpHeaders();
        //set up HTTP Basic Headers
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        //request entity is created with request headers
        HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);

        //URI builder
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("https://api.github.com/search/repositories")
                .queryParam("q", "2020-07-19")
                .queryParam("sort", "stars")
                .queryParam("order", "desc");

        //response entity
        ResponseEntity<GithubRepositoriesDTO> responseEntity = new ResponseEntity<>(githubRepositoriesDTO, HttpStatus.OK);

        //rest template with mockito
        Mockito.when(restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                GithubRepositoriesDTO.class)).thenReturn(responseEntity);

        githubRepostoriesService.getStarredRepositories(1);


        //assert status code
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
    }
}
