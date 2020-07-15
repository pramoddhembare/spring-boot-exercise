package com.telstra.codechallenge.quotes;

import java.util.Arrays;
import java.util.List;

import com.telstra.codechallenge.dtos.AccountItemDTO;
import com.telstra.codechallenge.dtos.RepositoriesItemsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringBootQuotesController {

    private SpringBootQuotesService springBootQuotesService;

    @Autowired
    public SpringBootQuotesController(
            SpringBootQuotesService springBootQuotesService) {
        this.springBootQuotesService = springBootQuotesService;
    }

    @RequestMapping(path = "/quotes", method = RequestMethod.GET)
    public List<Quote> quotes() {
        return Arrays.asList(springBootQuotesService.getQuotes());
    }

    @RequestMapping(path = "/quotes/random", method = RequestMethod.GET)
    public Quote quote() {
        return springBootQuotesService.getRandomQuote();
    }

    @RequestMapping(path = "/github/repositories", method = RequestMethod.GET)
    public List<RepositoriesItemsDTO> getRepositories(@RequestParam("size") int size) {
        return springBootQuotesService.getStarredRepositories(size);
    }

    @RequestMapping(path = "/github/accounts", method = RequestMethod.GET)
    public List<AccountItemDTO> getGithubAccounts(@RequestParam("size") int size) {
        return springBootQuotesService.getGithubAccounts(size);
    }
}
