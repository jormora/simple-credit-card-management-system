package com.example.bank;

import com.example.bank.operations.OperationsApplication;
import com.example.bank.operations.model.CreditCard;
import com.example.bank.operations.pojo.CreditCardPOJO;
import com.example.bank.operations.service.CreditCardService;
import com.example.bank.operations.service.WithdrawalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MimeTypeUtils;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = OperationsApplication.class)
public class CreditCardControllerTests {

    @Autowired
    private Sink sink;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CreditCardService creditCardService;

    private final Jackson2ObjectMapperBuilder mapperBuilder = new Jackson2ObjectMapperBuilder();

    private final ObjectMapper objectMapper = mapperBuilder.build();

    CreditCard createCreditCard() {
        CreditCard creditCard = new CreditCard();
        creditCard.setCardNo("1");
        creditCard.setUserId("user");
        creditCard.setInitialLimit(BigDecimal.valueOf(1000000L));
        creditCard.setUsedLimit(BigDecimal.ZERO);

        return this.creditCardService.save(creditCard);
    }

    @Test
    void contextLoads() {

    }

    @Test
    void getCreditCard() throws Exception {
        String uri = "/credit-card/";
        CreditCard creditCard = createCreditCard();

        this.mockMvc.perform(get(uri + (creditCard.getCardNo() + "1")))
                .andExpect(status().isBadRequest());

        ResultActions resultActions = this.mockMvc.perform(get(uri + creditCard.getCardNo()))
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        CreditCard obtained = this.objectMapper.readValue(response, CreditCard.class);

        assertEquals(creditCard.getCardNo(), obtained.getCardNo());
        assertTrue(creditCard.getInitialLimit().compareTo(obtained.getInitialLimit()) == 0);
        assertTrue(creditCard.getUsedLimit().compareTo(obtained.getUsedLimit()) == 0);

        this.creditCardService.deleteByCardNo(creditCard.getCardNo());
    }

    @Test
    void deleteCreditCard() throws Exception {
        String uri1 = "/credit-card/";
        String uri2 = "/delete";
        CreditCard creditCard = createCreditCard();

        this.mockMvc.perform(delete(uri1 + (creditCard.getCardNo() + "1") + uri2))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(delete(uri1 + creditCard.getCardNo() + uri2))
                .andExpect(status().isOk());

        assertNull(this.creditCardService.findByCardNo(creditCard.getCardNo()));
    }

    @Test
    void createCreditCardAfterMessageFromUsersModule() {
        CreditCardPOJO creditCardPOJO = new CreditCardPOJO();
        creditCardPOJO.setCardNo("1");
        creditCardPOJO.setInitialLimit(BigDecimal.valueOf(1000000L));
        creditCardPOJO.setUserId("user");

        this.sink.input().send(MessageBuilder.withPayload(creditCardPOJO)
            .setHeader("type", "create")
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE).build());

        assertNotNull(this.creditCardService.findByCardNo(creditCardPOJO.getCardNo()));
        this.creditCardService.findByCardNo(creditCardPOJO.getCardNo());
    }

}
