package com.webapplication.service;

import com.webapplication.dao.UserRepository;
import com.webapplication.dto.message.NewMessageDto;
import com.webapplication.dto.user.UserUtilsDto;
import com.webapplication.entity.MessageEntity;
import com.webapplication.entity.UserEntity;
import com.webapplication.error.UserError;
import com.webapplication.exception.AuthenticationException;
import com.webapplication.exception.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by panagiotis on 7/8/2017.
 */
@Transactional
@Service
public class MessageServiceApiImpl implements MessageServiceApi {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void sendMessage(NewMessageDto newMessageDto) throws RestException {
        String fromuser = newMessageDto.getFromUser();
        String touser = newMessageDto.getToUser();
        String text = newMessageDto.getMessageText();
        Date dateCreated = new Date();

        UserEntity fromUser = userRepository.findUserEntityByUsername(fromuser);
        UserEntity toUser = userRepository.findUserEntityByUsername(touser);

        if(fromUser == null || toUser == null)
            throw new AuthenticationException(UserError.USER_NOT_EXISTS);

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setDateCreated(dateCreated);
        messageEntity.setIsRead(0);
        messageEntity.setMessageText(text);
        messageEntity.setFromUser(fromUser);
        messageEntity.setToUser(toUser);
        messageEntity.setInbox(toUser.getMailbox());
        messageEntity.setOutbox(fromUser.getMailbox());

        fromUser.getMailbox().getOutbox().add(messageEntity);
        toUser.getMailbox().getInbox().add(messageEntity);

    }

    @Override
    public List<MessageEntity> getInboxMessages(UserUtilsDto userUtilsDto) throws RestException{
        UserEntity userEntity = userRepository.findUserEntityByUsername(userUtilsDto.getUsername());
        if(userEntity == null)
            throw new AuthenticationException(UserError.USER_NOT_EXISTS);

        return userEntity.getMailbox().getInbox();
    }

    @Override
    public List<MessageEntity> getOutboxMessages(UserUtilsDto userUtilsDto)  throws RestException{
        UserEntity userEntity = userRepository.findUserEntityByUsername(userUtilsDto.getUsername());
        if(userEntity == null)
            throw new AuthenticationException(UserError.USER_NOT_EXISTS);

        return userEntity.getMailbox().getOutbox();
    }
}
