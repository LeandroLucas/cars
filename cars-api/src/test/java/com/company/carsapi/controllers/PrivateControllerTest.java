package com.company.carsapi.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PrivateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getMeSuccess() throws Exception {
        MvcResult result = this.signInSuccess();
        String responseString = result.getResponse().getContentAsString();
        String token = this.objectMapper.readTree(responseString).get("token").asText();
        this.mockMvc
                .perform(get("/me")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.login", is("hello.world")))
                .andExpect(jsonPath("$.firstName", is("Hello")))
                .andExpect(jsonPath("$.lastName", is("World")))
                .andExpect(jsonPath("$.email", is("hello@world.com")))
                .andExpect(jsonPath("$.birthday", is("1990-05-01")));
    }

    @Test
    public void getMeInvalidSession() throws Exception {
        this.mockMvc
                .perform(get("/me")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "token")
                )
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errorCode", is(403)))
                .andExpect(jsonPath("$.message", is("Unauthorized - invalid session")));
    }

    @Test
    public void getMeNoAuthorization() throws Exception {
        this.mockMvc
                .perform(get("/me")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errorCode", is(403)))
                .andExpect(jsonPath("$.message", is("Unauthorized")));
    }

    public MvcResult signInSuccess() throws Exception {
        this.createUser();
        return this.mockMvc
                .perform(post("/signin")
                        .content(AuthControllerTest.VALID_USER_CREDENTIALS)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token", notNullValue()))
                .andReturn();
    }

    private void createUser() throws Exception {
        this.mockMvc
                .perform(post("/users")
                        .content(PublicControllerTest.VALID_CREATE_USER_JSON)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.login", is("hello.world")))
                .andExpect(jsonPath("$.firstName", is("Hello")))
                .andExpect(jsonPath("$.lastName", is("World")))
                .andExpect(jsonPath("$.email", is("hello@world.com")))
                .andExpect(jsonPath("$.birthday", is("1990-05-01")));
    }
}
