package com.telstra.codechallenge.controllers;

import com.telstra.codechallenge.controller.GithubRepositoriesController;
import com.telstra.codechallenge.dtos.RepositoriesItemsDTO;
import com.telstra.codechallenge.service.GithubRepostoriesService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(GithubRepositoriesController.class)
public class GithubRepositoriesControllerTest {

    private static final Logger log = LoggerFactory.getLogger(GithubRepositoriesControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GithubRepostoriesService githubRepostoriesService;

    @Before
    public void setUp() {
        List<RepositoriesItemsDTO> repositoriesItemsDTO = new ArrayList<>();
        RepositoriesItemsDTO obj = new RepositoriesItemsDTO("https://github.com/mrdrag1/ALI-ATTACKER"
                , 8, "shell", "test description", "log");
        repositoriesItemsDTO.add(obj);
        int size = 1;
        Mockito.when(githubRepostoriesService.getStarredRepositories(size)).thenReturn(repositoriesItemsDTO);
    }


    @Test
    public void getRepositories() throws Exception {
        //mockMvc with endpoints
        MvcResult result = mockMvc.perform(get("/github/repositories")
                .param("size", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].html_url", Matchers.is("https://github.com/mrdrag1/ALI-ATTACKER")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].watchers_count", Matchers.is(8)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].language", Matchers.is("shell")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description", Matchers.is("test description")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("log")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        //assert status code
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }


}
