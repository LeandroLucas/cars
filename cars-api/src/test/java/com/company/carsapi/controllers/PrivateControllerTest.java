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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getMeSuccess() throws Exception {
        String token = this.signIn();
        this.mockMvc
                .perform(get("/api/me")
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
                .perform(get("/api/me")
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
                .perform(get("/api/me")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errorCode", is(403)))
                .andExpect(jsonPath("$.message", is("Unauthorized")));
    }

    @Test
    public void listCarsSuccess() throws Exception {
        String token = this.signIn();
        this.mockMvc
                .perform(get("/api/cars")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));
    }

    @Test
    public void listCarsInvalidSession() throws Exception {
        this.mockMvc
                .perform(get("/api/cars")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "token")
                )
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errorCode", is(403)))
                .andExpect(jsonPath("$.message", is("Unauthorized - invalid session")));
    }

    @Test
    public void getCarSuccess() throws Exception {
        String token = this.signIn();
        int carId = 1;
        this.mockMvc
                .perform(get("/api/cars/" + carId)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(carId)))
                .andExpect(jsonPath("$.model", is("Audi")));
    }

    @Test
    public void getCarNotFound() throws Exception {
        String token = this.signIn();
        int carId = 3;
        this.mockMvc
                .perform(get("/api/cars/" + carId)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode", is(404)))
                .andExpect(jsonPath("$.message", is("Car not found")));
    }

    @Test
    public void deleteCarSuccess() throws Exception {
        String token = this.signIn();
        int carId = 1;
        this.mockMvc
                .perform(delete("/api/cars/" + carId)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCarNotFound() throws Exception {
        String token = this.signIn();
        int carId = 3;
        this.mockMvc
                .perform(delete("/api/cars/" + carId)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, token)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode", is(404)))
                .andExpect(jsonPath("$.message", is("Car not found")));
    }

    public String signIn() throws Exception {
        this.createUser();
        MvcResult result = this.mockMvc
                .perform(post("/api/signin")
                        .content(AuthControllerTest.VALID_USER_CREDENTIALS)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token", notNullValue()))
                .andReturn();
        String responseString = result.getResponse().getContentAsString();
        return this.objectMapper.readTree(responseString).get("token").asText();
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
