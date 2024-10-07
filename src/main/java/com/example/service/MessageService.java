package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private final AccountRepository accountRepository;
    private final MessageRepository messageRepository;

    

    public MessageService(AccountRepository accountRepository, MessageRepository messageRepository) {
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }



    public Optional<Message> createMessage(Message message) {

        Optional<Account> postedBy = accountRepository.findById(message.getPostedBy());
        if(postedBy.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(messageRepository.save(message));
        
    }



    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }



    public Optional<Message> getMessageById(Integer id) {
        return messageRepository.findById(id);
    }



    public Integer deleteMessage(Integer id) {
       Optional<Message> existingMessage = messageRepository.findById(id);
       if(existingMessage.isEmpty()){
        return null;
       }

       messageRepository.delete(existingMessage.get());
       return 1;
    }



    public Integer updateMessage(Integer id, String messageText) {
        Optional<Message> existingMessage = messageRepository.findById(id);
        if(existingMessage.isEmpty()){
            return null;
        }

        Message message = existingMessage.get();
        message.setMessageText(messageText);

        messageRepository.save(message);
        
        return 1;
     }



    public List<Message> getAllMessagesByUser(Integer accountId) {
        return messageRepository.findAllByPostedBy(accountId);
    }
}
