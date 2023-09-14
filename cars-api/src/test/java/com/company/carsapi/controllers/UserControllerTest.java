package com.company.carsapi.controllers;

import com.company.carsapi.constants.ValidationConstants;
import org.junit.jupiter.api.DisplayName;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerTest {

    public static final String VALID_CREATE_USER_JSON = "{\"firstName\":\"Hello\",\"lastName\":\"World\",\"email\":\"hello@world.com\",\"birthday\":\"1990-05-01\",\"login\":\"hello.world\",\"password\":\"h3ll0\",\"phone\":\"988888888\",\"cars\":[{\"year\":2018,\"licensePlate\":\"PDV-0625\",\"model\":\"Audi\",\"color\":\"White\"}]}";

    private final String CREATE_USER_JSON_MISSING_PHONE = "{\"firstName\":\"Hello\",\"lastName\":\"World\",\"email\":\"hello@world.com\",\"birthday\":\"1990-05-01\",\"login\":\"hello.world\",\"password\":\"h3ll0\",\"cars\":[{\"year\":2018,\"licensePlate\":\"PDV-0625\",\"model\":\"Audi\",\"color\":\"White\"}]}";

    private final String CREATE_USER_JSON_INVALID_PHONE = "{\"firstName\":\"Hello\",\"lastName\":\"World\",\"email\":\"hello@world.com\",\"birthday\":\"abcv\",\"login\":\"hello.world\",\"password\":\"h3ll0\",\"phone\":\"asd\",\"cars\":[{\"year\":2018,\"licensePlate\":\"PDV-0625\",\"model\":\"Audi\",\"color\":\"White\"}]}";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Create user success")
    public void createUserSuccess() throws Exception {
        this.mockMvc
                .perform(post("/users")
                        .content(VALID_CREATE_USER_JSON)
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

    @Test
    @DisplayName("Create user with missing fields")
    public void createUserMissingFields() throws Exception {
        this.mockMvc
                .perform(post("/users")
                        .content(CREATE_USER_JSON_MISSING_PHONE)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", is(400)))
                .andExpect(jsonPath("$.message", is(ValidationConstants.MISSING_FIELDS_MESSAGE)));
    }

    @Test
    @DisplayName("Create user with invalid fields")
    public void createUserInvalidFields() throws Exception {
        this.mockMvc
                .perform(post("/users")
                        .content(CREATE_USER_JSON_INVALID_PHONE)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", is(400)))
                .andExpect(jsonPath("$.message", is(ValidationConstants.INVALID_FIELDS_MESSAGE)));
    }

    @Test
    @DisplayName("Create user when login already exists")
    public void createUserWhenLoginAlreadyExists() throws Exception {
        this.createUserSuccess();
        this.mockMvc
                .perform(post("/users")
                        .content(VALID_CREATE_USER_JSON)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode", is(400)))
                .andExpect(jsonPath("$.message", is("login already exists")));
    }

    @Test
    @DisplayName("Get user success")
    public void getUserSuccess() throws Exception {
        this.createUserSuccess();
        this.mockMvc
                .perform(get("/users/1")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.login", is("hello.world")))
                .andExpect(jsonPath("$.firstName", is("Hello")))
                .andExpect(jsonPath("$.lastName", is("World")))
                .andExpect(jsonPath("$.email", is("hello@world.com")))
                .andExpect(jsonPath("$.birthday", is("1990-05-01")));
    }

    @Test
    @DisplayName("Get user not found")
    public void getUserNotFound() throws Exception {
        this.mockMvc
                .perform(get("/users/1")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode", is(404)))
                .andExpect(jsonPath("$.message", is("User not found")));
    }

    @Test
    @DisplayName("Delete user success")
    public void deleteUserSuccess() throws Exception {
        this.createUserSuccess();
        this.mockMvc
                .perform(delete("/users/1")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("Update user success")
    public void updateUserSuccess() throws Exception {
        this.createUserSuccess();
        this.mockMvc
                .perform(put("/users/1")
                        .content(VALID_CREATE_USER_JSON)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("List user success")
    public void listUserSuccess() throws Exception {
        this.createUserSuccess();
        this.mockMvc
                .perform(get("/users")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.length()", is(1)));
    }
}
