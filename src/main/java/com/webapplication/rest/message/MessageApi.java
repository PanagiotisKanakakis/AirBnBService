package com.webapplication.rest.message;

import com.webapplication.dto.message.NewMessageDto;
import com.webapplication.dto.user.UserUtilsDto;
import com.webapplication.entity.MessageEntity;
import com.webapplication.exception.RestException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by panagiotis on 7/8/2017.
 */
@RestController
public interface MessageApi {

    @RequestMapping(path = "/newMessage", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    void sendMessage(NewMessageDto newMessageDto) throws RestException;

    @RequestMapping(path = "/getInbox", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    List<MessageEntity> getInboxMessages(UserUtilsDto userUtilsDto) throws RestException;

    @RequestMapping(path = "/getOutbox", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    List<MessageEntity> getOutboxMessages(UserUtilsDto userUtilsDto) throws RestException;
}
