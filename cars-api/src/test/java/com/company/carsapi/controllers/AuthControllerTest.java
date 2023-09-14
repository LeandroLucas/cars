package com.company.carsapi.controllers;

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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthControllerTest {

    public static final String VALID_USER_CREDENTIALS = "{\"login\":\"hello.world\",\"password\":\"h3ll0\"}";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void signInSuccess() throws Exception {
        this.createUser();
        this.mockMvc
                .perform(post("/api/signin")
                        .content(VALID_USER_CREDENTIALS)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token", notNullValue()));
    }

    @Test
    public void signInFail() throws Exception {
        this.mockMvc
                .perform(post("/api/signin")
                        .content(VALID_USER_CREDENTIALS)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errorCode", is(401)))
                .andExpect(jsonPath("$.message", is("Invalid login or password")));
    }

    @Test
    public void signOutFail() throws Exception {
        this.mockMvc
                .perform(delete("/api/signout")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "123")
                )
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errorCode", is(403)))
                .andExpect(jsonPath("$.message", is("Unauthorized - invalid session")));
    }

    private void createUser() throws Exception {
        this.mockMvc
                .perform(post("/api/users")
                        .content(PublicControllerTest.VALID_CREATE_USER_JSON)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.firstName", is("Hello")))
                .andExpect(jsonPath("$.lastName", is("World")))
                .andExpect(jsonPath("$.email", is("hello@world.com")));
    }
}
