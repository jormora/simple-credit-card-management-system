package com.example.bank;

import com.example.bank.operations.model.User;
import com.example.bank.operations.OperationsApplication;
import com.example.bank.operations.pojo.UserPOJO;
import com.example.bank.operations.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = OperationsApplication.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private final Jackson2ObjectMapperBuilder mapperBuilder = new Jackson2ObjectMapperBuilder();

    private final ObjectMapper objectMapper = mapperBuilder.build();

    User setUpUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setFirstname("User");
        user.setLastname("Test");
        user.setCardId(123456L);
        return this.userService.save(user);
    }

    @Test
    void contextLoads() {

    }

    @Test
    void getUser() throws Exception {
        String uri = "/user/";
        User user = setUpUser();

        this.mockMvc.perform(get(uri + user.getUsername() + "a"))
                .andExpect(status().isBadRequest());

        ResultActions resultActions = this.mockMvc.perform(get(uri + user.getUsername()))
                .andExpect(status().isOk());

        MvcResult mvcResult = resultActions.andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        User obtained = this.objectMapper.readValue(response, User.class);

        assertEquals(user.getUsername(), obtained.getUsername());
        assertEquals(user.getFirstname(), obtained.getFirstname());
        assertEquals(user.getLastname(), obtained.getLastname());
        assertEquals(user.getCardId(), obtained.getCardId());
        assertNull(user.getCreditCard());

        this.userService.deleteById(user.getUsername());
    }

    @Test
    void createUser() throws Exception {
        String uri = "/user/create";
        UserPOJO userPOJO = new UserPOJO();
        userPOJO.setUsername("testUser");
        userPOJO.setFirstname("User");
        userPOJO.setLastname("Test");

        this.mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(this.objectMapper.writeValueAsString(userPOJO))).andExpect(status().isBadRequest());

        userPOJO.setCardId(123456L);

        this.mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.objectMapper.writeValueAsString(userPOJO))).andExpect(status().isOk());

        User user = this.userService.findByUsername(userPOJO.getUsername());

        assertNotNull(user);
        assertEquals(userPOJO.getUsername(), user.getUsername());
        assertEquals(userPOJO.getFirstname(), user.getFirstname());
        assertEquals(userPOJO.getLastname(), user.getLastname());
        assertEquals(userPOJO.getCardId(), user.getCardId());
        assertNull(user.getCreditCard());

        this.userService.deleteById(user.getUsername());
    }

    @Test
    void deleteUser() throws Exception {
        String uri1 = "/user/";
        String uri2 = "/delete";
        User user = setUpUser();

        this.mockMvc.perform(delete(uri1 + user.getUsername() + "a" + uri2))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(delete(uri1 + user.getUsername() + uri2))
                .andExpect(status().isOk());

        assertNull(this.userService.findByUsername(user.getUsername()));
    }

}
