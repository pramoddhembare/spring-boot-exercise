package com.telstra.codechallenge.quotes;

import com.telstra.codechallenge.dtos.GithubAccountsDTO;
import com.telstra.codechallenge.dtos.GithubRepositoriesDTO;
import com.telstra.codechallenge.dtos.AccountItemDTO;
import com.telstra.codechallenge.dtos.RepositoriesItemsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SpringBootQuotesService {

    @Value("${quotes.base.url}")
    private String quotesBaseUrl;

    @Value("${api.github.base.url}")
    private String githubBaseUrl;

    private RestTemplate restTemplate;

    @Autowired
    public SpringBootQuotesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Returns an array of spring boot quotes. Taken from https://spring.io/guides/gs/consuming-rest/.
     *
     * @return - a quote array
     */
    public Quote[] getQuotes() {

        return restTemplate.getForObject(quotesBaseUrl + "/api", Quote[].class);
    }

    /**
     * Returns a random spring boot quote. Taken from https://spring.io/guides/gs/consuming-rest/.
     *
     * @return - a quote
     */
    public Quote getRandomQuote() {
        return restTemplate.getForObject(quotesBaseUrl + "/api/random", Quote.class);
    }

    /**
     * Return a hottest repositories created in the last week from https://api.github.com/search/.
     *
     * @return -a GithubRepositoriesDTO
     */
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


        //query  parameter
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(githubBaseUrl + "/repositories")
                .queryParam("q", fromdate)
                .queryParam("sort", "stars")
                .queryParam("order", "desc");


        //rest call to github API
        ResponseEntity<GithubRepositoriesDTO> responseEntity = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                GithubRepositoriesDTO.class
        );

        //set size or records
        return responseEntity.getBody().getItems()
                .stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * Return a  oldest user accounts with zero followers from https://api.github.com/search/.
     *
     * @return -a GithubAccountsDTO
     */
    public List<AccountItemDTO> getGithubAccounts(int limit) {
        HttpHeaders requestHeaders = new HttpHeaders();

        //set up HTTP Basic Authentication Header
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

        //request entity is created with request headers
        HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);

        //query parameter
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(githubBaseUrl + "/users")
                .queryParam("q", 0)
                .queryParam("sort", "joined")
                .queryParam("order", "asc");

        //rest api call using rest template
        ResponseEntity<GithubAccountsDTO> responseEntity = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                GithubAccountsDTO.class
        );

        //set numbers of records
        return responseEntity.getBody().getItems()
                .stream()
                .limit(limit)
                .collect(Collectors.toList());
    }
}
