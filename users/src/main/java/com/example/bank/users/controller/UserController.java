package com.example.bank.users.controller;

import com.example.bank.users.model.User;
import com.example.bank.users.pojo.CreditCardPOJO;
import com.example.bank.users.pojo.PlasticCardPOJO;
import com.example.bank.users.pojo.UserPOJO;
import com.example.bank.users.producer.OperationsNotifier;
import com.example.bank.users.producer.PlasticCardNotifier;
import com.example.bank.users.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    public static final String USER_NOT_FOUND = "Usuario no encontrado";

    private static final String USER_CREATED = "Usuario creado";

    private static final String USER_DELETED = "Usuario eliminado";

    private static final String INVALID_DATA_FORMAT = "El formato de los datos es inválido";

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final OperationsNotifier operationsNotifier;

    private final PlasticCardNotifier plasticCardNotifier;

    public UserController(UserService userService, OperationsNotifier operationsNotifier, PlasticCardNotifier plasticCardNotifier) {
        this.userService = userService;
        this.operationsNotifier = operationsNotifier;
        this.plasticCardNotifier = plasticCardNotifier;
    }

    @GetMapping(path = "/user/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        User user = this.userService.findByUsername(username);
        if (user == null) {
            LOGGER.info(USER_NOT_FOUND);
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping(path = "/user/create")
    public ResponseEntity<String> createUser(@RequestBody UserPOJO userPOJO) {
        if (!UserPOJO.isRequestEntityRight(userPOJO)) {
            LOGGER.info(INVALID_DATA_FORMAT);
            return ResponseEntity.badRequest().body(INVALID_DATA_FORMAT);
        }
        User user = UserPOJO.mapRequestEntity(userPOJO);
        this.userService.save(user);
        return ResponseEntity.ok(USER_CREATED);
    }

    @PostMapping(path = "/plastic-card/create")
    public ResponseEntity<String> createPlasticCard(@RequestBody PlasticCardPOJO plasticCardPOJO) {
        if (this.userService.findByUsername(plasticCardPOJO.getUserId()) == null) {
            LOGGER.info(USER_NOT_FOUND);
            return ResponseEntity.badRequest().body(USER_NOT_FOUND);
        }
        this.plasticCardNotifier.getSource().output().send(MessageBuilder.withPayload(plasticCardPOJO)
                .setHeader("type", "create")
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE).build());
        return ResponseEntity.ok("Enviado");
    }

    @PostMapping(path = "/credit-card/create")
    public ResponseEntity<String> createCreditCard(@RequestBody CreditCardPOJO creditCardPOJO) {
        User user = this.userService.findByUsername(creditCardPOJO.getUserId());
        if (user == null) {
            LOGGER.info(USER_NOT_FOUND);
            return ResponseEntity.badRequest().body(USER_NOT_FOUND);
        }
        user.setCreditCard(creditCardPOJO.getCardNo());
        this.userService.save(user);
        this.operationsNotifier.send(creditCardPOJO);
        return ResponseEntity.ok("Enviado");
    }

    @DeleteMapping(path = "/user/{username}/delete")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        if (this.userService.findByUsername(username) == null) {
            LOGGER.info(USER_NOT_FOUND);
            return ResponseEntity.badRequest().body(USER_NOT_FOUND);
        }
        this.userService.deleteByUsername(username);
        return ResponseEntity.ok(USER_DELETED);
    }

}
