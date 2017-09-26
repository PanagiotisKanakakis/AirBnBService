package com.webapplication.rest.message;

import com.webapplication.dto.message.NewMessageDto;
import com.webapplication.dto.user.UserUtilsDto;
import com.webapplication.entity.MessageEntity;
import com.webapplication.exception.RestException;
import com.webapplication.service.MessageServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by panagiotis on 7/8/2017.
 */
@Component
public class MessageApiImpl implements MessageApi {

    @Autowired
    private MessageServiceApi messageServiceApi;

    @Override
    public void sendMessage(@RequestBody NewMessageDto newMessageDto) throws RestException {
        messageServiceApi.sendMessage(newMessageDto);
    }

    @Override
    public List<MessageEntity> getInboxMessages(@RequestBody  UserUtilsDto userUtilsDto) throws RestException {
        return messageServiceApi.getInboxMessages(userUtilsDto);
    }

    @Override
    public List<MessageEntity> getOutboxMessages(@RequestBody UserUtilsDto userUtilsDto) throws RestException {
        return messageServiceApi.getOutboxMessages(userUtilsDto);
    }
}
