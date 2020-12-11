package com.example.bank;

import com.example.bank.operations.model.CreditCard;
import com.example.bank.operations.model.User;
import com.example.bank.operations.model.Withdrawal;
import com.example.bank.operations.OperationsApplication;
import com.example.bank.operations.pojo.CreditCardPOJO;
import com.example.bank.operations.service.CreditCardService;
import com.example.bank.operations.service.UserService;
import com.example.bank.operations.service.WithdrawalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = OperationsApplication.class)
public class CreditCardControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private WithdrawalService withdrawalService;

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

    CreditCard createCreditCard(User user) {
        CreditCard creditCard = new CreditCard();
        creditCard.setId(123L);
        creditCard.setUser(user);
        creditCard.setInitialLimit(1000000L);
        creditCard.setUsedLimit(0L);

        user.setCreditCard(creditCard);
        this.userService.save(user);
        return this.creditCardService.findById(creditCard.getId());
    }

    void withdrawManual(CreditCard creditCard) {
        BigDecimal amount = Math.max(0L, creditCard.getInitialLimit() - creditCard.getUsedLimit());
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setId(123L);
        withdrawal.setCreditCardW(creditCard);
        withdrawal.setAmount(amount);
        withdrawal.setDateTime(OffsetDateTime.now());
        this.withdrawalService.save(withdrawal);
        creditCard.withdraw(amount);
        this.creditCardService.save(creditCard);
    }

    @Test
    void contextLoads() {

    }

    @Test
    void getCreditCard() throws Exception {
        String uri = "/credit-card/";
        User user = setUpUser();
        CreditCard creditCard = createCreditCard(user);

        this.mockMvc.perform(get(uri + (creditCard.getId() + 1L)))
                .andExpect(status().isBadRequest());

        ResultActions resultActions = this.mockMvc.perform(get(uri + creditCard.getId()))
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        CreditCard obtained = this.objectMapper.readValue(response, CreditCard.class);

        assertEquals(creditCard.getId(), obtained.getId());
        assertEquals(creditCard.getInitialLimit(), obtained.getInitialLimit());
        assertEquals(creditCard.getUsedLimit(), obtained.getUsedLimit());

        user.setCreditCard(null);
        this.userService.save(user);
        this.creditCardService.deleteById(creditCard.getId());
        this.userService.deleteById(user.getUsername());
    }

    @Test
    void getCreditCardWithdrawals() throws Exception {
        String uri1 = "/credit-card/";
        String uri2 = "/withdrawals";
        User user = setUpUser();
        CreditCard creditCard = createCreditCard(user);
        withdrawManual(creditCard);

        this.mockMvc.perform(get(uri1 + (creditCard.getId() + 1L) + uri2))
                .andExpect(status().isBadRequest());

        ResultActions resultActions = this.mockMvc.perform(get(uri1 + creditCard.getId() + uri2))
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        CollectionType javaList = this.objectMapper.getTypeFactory().constructCollectionType(List.class, Withdrawal.class);
        List<Withdrawal> withdrawals = this.objectMapper.readValue(response, javaList);
        assertNotNull(withdrawals);
        assertEquals(1, withdrawals.size());

        user.setCreditCard(null);
        this.userService.save(user);
        this.withdrawalService.deleteAllByCreditCard(creditCard);
        this.creditCardService.deleteById(creditCard.getId());
        this.userService.deleteById(user.getUsername());
    }

    @Test
    void withdraw() throws Exception {
        String uri1 = "/credit-card/";
        String uri2 = "/withdraw/";
        String uri3 = "/";
        User user = setUpUser();
        CreditCard creditCard = createCreditCard(user);

        this.mockMvc.perform(patch(uri1 + (creditCard.getId() + 1) + uri2 + "1" + uri3 + creditCard.getInitialLimit()))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(patch(uri1 + creditCard.getId() + uri2 + "1" + uri3 + (creditCard.getInitialLimit() + 1L)))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(patch(uri1 + creditCard.getId() + uri2 + "1" + uri3 + creditCard.getInitialLimit()))
                .andExpect(status().isOk());

        CreditCard modified = this.creditCardService.findById(creditCard.getId());
        assertEquals(creditCard.getInitialLimit(), modified.getUsedLimit());

        user.setCreditCard(null);
        this.userService.save(user);
        this.withdrawalService.deleteAllByCreditCard(creditCard);
        this.creditCardService.deleteById(creditCard.getId());
        this.userService.deleteById(user.getUsername());
    }

    @Test
    void chargeBack() throws Exception {
        String uri1 = "/credit-card/";
        String uri2 = "/charge-back/";
        User user = setUpUser();
        CreditCard creditCard = createCreditCard(user);
        withdrawManual(creditCard);

        this.mockMvc.perform(patch(uri1 + (creditCard.getId() + 1) + uri2 + creditCard.getInitialLimit()))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(patch(uri1 + creditCard.getId() + uri2 + (creditCard.getInitialLimit() + 1L)))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(patch(uri1 + creditCard.getId() + uri2 + creditCard.getInitialLimit()))
                .andExpect(status().isOk());

        CreditCard modified = this.creditCardService.findById(creditCard.getId());
        assertEquals(0L, modified.getUsedLimit());
        assertEquals(creditCard.getInitialLimit(), modified.getInitialLimit() - modified.getUsedLimit());

        user.setCreditCard(null);
        this.userService.save(user);
        this.withdrawalService.deleteAllByCreditCard(creditCard);
        this.creditCardService.deleteById(creditCard.getId());
        this.userService.deleteById(user.getUsername());
    }

    @Test
    void createCreditCard() throws Exception {
        String uri = "/credit-card/create/";
        User user = setUpUser();

        CreditCardPOJO creditCardPOJO = new CreditCardPOJO();
        creditCardPOJO.setId(2L);
        creditCardPOJO.setInitialLimit(1000000L);

        this.mockMvc.perform(post(uri + user.getUsername() + "a")
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(this.objectMapper.writeValueAsString(creditCardPOJO)))
                .andExpect(status().isBadRequest());

        User newUser = new User();
        newUser.setUsername("newUser");
        newUser.setFirstname("New");
        newUser.setLastname("User");
        newUser.setCardId(654321L);
        this.userService.save(newUser);
        CreditCard creditCard = createCreditCard(newUser);

        this.mockMvc.perform(post(uri + newUser.getUsername())
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(this.objectMapper.writeValueAsString(creditCardPOJO)))
                .andExpect(status().isBadRequest());

        creditCardPOJO.setId(null);

        this.mockMvc.perform(post(uri + user.getUsername())
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(this.objectMapper.writeValueAsString(creditCardPOJO)))
                .andExpect(status().isBadRequest());

        creditCardPOJO.setId(1L);

        this.mockMvc.perform(post(uri + user.getUsername())
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(this.objectMapper.writeValueAsString(creditCardPOJO)))
                .andExpect(status().isOk());

        CreditCard created = this.creditCardService.findById(creditCardPOJO.getId());
        assertEquals(creditCardPOJO.getId(), created.getId());
        assertEquals(creditCardPOJO.getInitialLimit(), created.getInitialLimit());
        assertEquals(0L, created.getUsedLimit());

        user.setCreditCard(null);
        newUser.setCreditCard(null);
        this.userService.save(user);
        this.userService.save(newUser);
        this.withdrawalService.deleteAllByCreditCard(created);
        this.withdrawalService.deleteAllByCreditCard(creditCard);
        this.creditCardService.deleteById(created.getId());
        this.creditCardService.deleteById(creditCard.getId());
        this.userService.deleteById(user.getUsername());
        this.userService.deleteById(newUser.getUsername());
    }

    @Test
    void deleteCreditCard() throws Exception {
        String uri1 = "/credit-card/";
        String uri2 = "/delete/";
        User user = setUpUser();
        CreditCard creditCard = createCreditCard(user);

        this.mockMvc.perform(delete(uri1 + (creditCard.getId() + 1) + uri2 + user.getUsername()))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(delete(uri1 + creditCard.getId() + uri2 + user.getUsername() + "a"))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(delete(uri1 + creditCard.getId() + uri2 + user.getUsername()))
                .andExpect(status().isOk());

        assertNull(this.creditCardService.findById(creditCard.getId()));

        this.userService.deleteById(user.getUsername());
    }

}
