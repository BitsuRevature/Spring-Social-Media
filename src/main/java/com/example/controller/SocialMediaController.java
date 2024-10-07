package com.example.controller;

import java.util.Optional;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController()
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account){
        if(account.getUsername().isBlank() || account.getUsername().isEmpty()){
            return ResponseEntity.status(400).body(null);
        }

        if(account.getPassword().length() < 4){
            return ResponseEntity.status(400).body(null);
        }

        Optional<Account> createdAcc = accountService.register(account);

        if(createdAcc.isEmpty()){
            return ResponseEntity.status(409).body(null);
        }

        return ResponseEntity.status(200).body(createdAcc.get());
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account){

        Optional<Account> existingAccount = accountService.login(account);
        if(existingAccount.isEmpty()){
            return ResponseEntity.status(401).body(null);
        }

        return ResponseEntity.status(200).body(existingAccount.get());

    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        if(message.getMessageText().isBlank() || message.getMessageText().isEmpty() || message.getMessageText().length() > 255){
            return ResponseEntity.status(400).body(null);
        }

        Optional<Message> createdMessage = messageService.createMessage(message);
        if(createdMessage.isEmpty()){
            return ResponseEntity.status(400).body(null);
        }

        return ResponseEntity.status(200).body(createdMessage.get());
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer id){
        Message message= messageService.getMessageById(id).orElse(null);
        return ResponseEntity.status(200).body(message);
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer id){
        return ResponseEntity.status(200).body(messageService.deleteMessage(id));
    }

    @PatchMapping("/messages/{id}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer id, @RequestBody Message message){

        String messageText = message.getMessageText();

        System.out.println(messageText);

        if(messageText.isEmpty() || messageText.isBlank() || messageText.length() > 255){
            return ResponseEntity.status(400).body(null);
        }

        Integer rowsAffected = messageService.updateMessage(id, messageText);
        if(rowsAffected == null){
            return ResponseEntity.status(400).body(null);
        }

        return ResponseEntity.status(200).body(rowsAffected);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUser(@PathVariable Integer accountId){
        return ResponseEntity.status(200).body(messageService.getAllMessagesByUser(accountId));
    }


}
