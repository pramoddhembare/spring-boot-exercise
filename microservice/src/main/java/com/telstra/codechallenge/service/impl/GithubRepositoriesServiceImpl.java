package com.telstra.codechallenge.service.impl;

import com.telstra.codechallenge.dtos.GithubRepositoriesDTO;
import com.telstra.codechallenge.dtos.RepositoriesItemsDTO;
import com.telstra.codechallenge.service.GithubRepostoriesService;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GithubRepositoriesServiceImpl implements GithubRepostoriesService {

    private static final Logger log = LoggerFactory.getLogger(GithubRepositoriesServiceImpl.class);

    @Value("${api.github.base.url}")
    private String githubBaseUrl;


    private RestTemplate restTemplate;

    @Autowired
    public GithubRepositoriesServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GithubRepositoriesServiceImpl() {
    }

    /**
     * Return a hottest repositories created in the last week from https://api.github.com/search/.
     *
     * @return -a GithubRepositoriesDTO
     */
    @Override
    public List<RepositoriesItemsDTO> getStarredRepositories(int limit) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        Date todate1 = cal.getTime();
        String fromdate = dateFormat.format(todate1);


        HttpHeaders requestHeaders = new HttpHeaders();
        //set up HTTP Basic Headers
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        //request entity is created with request headers
        HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);


        //URI builder with query  parameter
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(githubBaseUrl + "/repositories")
                .queryParam("q", fromdate)
                .queryParam("sort", "stars")
                .queryParam("order", "desc");

        ResponseEntity<GithubRepositoriesDTO> responseEntity = null;
        //rest call to github API
        try {
            responseEntity = restTemplate.exchange(
                    uriBuilder.toUriString(),
                    HttpMethod.GET,
                    requestEntity,
                    GithubRepositoriesDTO.class
            );
        } catch (HttpClientErrorException exception) {
            log.error("HttpClientErrorException Error ", exception);
        } catch (HttpStatusCodeException exception) {
            log.error("HttpStatusCodeException Error ", exception);
        }
        log.info("Called rest template");
        //handle null pointer exception
        if (responseEntity == null) {
            throw new NullPointerException("RESPONSE_ENTITY_NULL");
        }

        //set size or records
        return responseEntity.getBody().getItems()
                .stream()
                .limit(limit)
                .collect(Collectors.toList());


    }
}