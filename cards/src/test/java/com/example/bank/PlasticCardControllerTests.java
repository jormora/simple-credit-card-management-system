package com.example.bank;

import com.example.bank.cards.CardsApplication;
import com.example.bank.cards.model.PlasticCard;
import com.example.bank.cards.pojo.PlasticCardPOJO;
import com.example.bank.cards.service.PlasticCardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MimeTypeUtils;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = CardsApplication.class)
public class PlasticCardControllerTests {

    @Autowired
    private Sink sink;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlasticCardService plasticCardService;

    private final Jackson2ObjectMapperBuilder mapperBuilder = new Jackson2ObjectMapperBuilder();

    private final ObjectMapper objectMapper = mapperBuilder.build();

    PlasticCard createPlasticCard() {
        PlasticCard plasticCard = new PlasticCard();
        plasticCard.setCardNo("1");
        plasticCard.setPin(1234);
        plasticCard.setUserId("user");
        plasticCard.setColor("color");
        plasticCard.setImageURL("URL");
        plasticCard.setExpirationDate(OffsetDateTime.now().plusYears(4L));
        return this.plasticCardService.save(plasticCard);
    }

    @Test
    void contextLoads() {

    }

    @Test
    void getPlasticCard() throws Exception {
        String uri = "/plastic-card/";
        PlasticCard plasticCard = createPlasticCard();

        this.mockMvc.perform(get(uri + (plasticCard.getCardNo() + "1")))
                .andExpect(status().isBadRequest());

        ResultActions resultActions = this.mockMvc.perform(get(uri + plasticCard.getCardNo()))
                .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        PlasticCard obtained = this.objectMapper.readValue(response, PlasticCard.class);

        assertEquals(plasticCard.getCardNo(), obtained.getCardNo());
        assertEquals(plasticCard.getPin(), obtained.getPin());
        assertEquals(plasticCard.getUserId(), obtained.getUserId());
        assertEquals(plasticCard.getColor(), obtained.getColor());
        assertEquals(plasticCard.getImageURL(), obtained.getImageURL());

        this.plasticCardService.deleteByCardNo(plasticCard.getCardNo());
    }

    @Test
    void changePlasticCardColor() throws Exception {
        String uri1 = "/plastic-card/";
        String uri2 = "/change-color/";
        PlasticCard plasticCard = createPlasticCard();

        this.mockMvc.perform(patch(uri1 + (plasticCard.getCardNo() + "1") + uri2 + "yellow"))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(patch(uri1 + plasticCard.getCardNo() + uri2 + "yellow"))
                .andExpect(status().isOk());

        PlasticCard modified = this.plasticCardService.findByCardNo(plasticCard.getCardNo());
        assertEquals("yellow", modified.getColor());

        this.plasticCardService.deleteByCardNo(modified.getCardNo());
    }

    @Test
    void changePlasticCardImage() throws Exception {
        String uri1 = "/plastic-card/";
        String uri2 = "/change-image/";
        PlasticCard plasticCard = createPlasticCard();

        this.mockMvc.perform(patch(uri1 + (plasticCard.getCardNo() + "1") + uri2 + "new URL"))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(patch(uri1 + plasticCard.getCardNo() + uri2 + "new URL"))
                .andExpect(status().isOk());

        PlasticCard modified = this.plasticCardService.findByCardNo(plasticCard.getCardNo());
        assertEquals("new URL", modified.getImageURL());

        this.plasticCardService.deleteByCardNo(modified.getCardNo());
    }

    @Test
    void deletePlasticCard() throws Exception {
        String uri = "/plastic-card/delete/";
        PlasticCard plasticCard = createPlasticCard();

        this.mockMvc.perform(delete(uri + (plasticCard.getCardNo() + "1")))
                .andExpect(status().isBadRequest());

        this.mockMvc.perform(delete(uri + plasticCard.getCardNo()))
                .andExpect(status().isOk());

        assertNull(this.plasticCardService.findByCardNo(plasticCard.getCardNo()));
    }

    @Test
    void createPlasticCardAfterMessageFromUsersModule() {
        PlasticCardPOJO plasticCardPOJO = new PlasticCardPOJO();
        plasticCardPOJO.setCardNo("1");
        plasticCardPOJO.setPin(1234);
        plasticCardPOJO.setUserId("user");
        plasticCardPOJO.setColor("color");
        plasticCardPOJO.setImageURL("URL");
        plasticCardPOJO.setExpirationDate(OffsetDateTime.now().plusYears(4L));

        this.sink.input().send(MessageBuilder.withPayload(plasticCardPOJO)
            .setHeader("type", "create")
            .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE).build());

        assertNotNull(this.plasticCardService.findByCardNo(plasticCardPOJO.getCardNo()));
        this.plasticCardService.deleteByCardNo(plasticCardPOJO.getCardNo());
    }

}
