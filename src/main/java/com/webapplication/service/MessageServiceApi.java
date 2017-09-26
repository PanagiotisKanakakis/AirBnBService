package com.webapplication.service;

import com.webapplication.dto.message.NewMessageDto;
import com.webapplication.dto.user.UserUtilsDto;
import com.webapplication.entity.MessageEntity;
import com.webapplication.exception.RestException;

import java.util.List;

/**
 * Created by panagiotis on 7/8/2017.
 */
public interface MessageServiceApi {

    public void sendMessage(NewMessageDto newMessageDto) throws RestException;

    public List<MessageEntity> getInboxMessages(UserUtilsDto userUtilsDto);

    public List<MessageEntity> getOutboxMessages(UserUtilsDto userUtilsDto);

}
