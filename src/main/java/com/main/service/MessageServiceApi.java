package com.main.service;

import com.main.dto.message.NewMessageDto;
import com.main.dto.user.UserUtilsDto;
import com.main.entity.MessageEntity;
import com.main.exception.RestException;

import java.util.List;

/**
 * Created by panagiotis on 7/8/2017.
 */
public interface MessageServiceApi {

    public void sendMessage(NewMessageDto newMessageDto) throws RestException;

    public List<MessageEntity> getInboxMessages(UserUtilsDto userUtilsDto);

    public List<MessageEntity> getOutboxMessages(UserUtilsDto userUtilsDto);

}
