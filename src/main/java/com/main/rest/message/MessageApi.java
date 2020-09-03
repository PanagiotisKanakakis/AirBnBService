package com.main.rest.message;

import com.main.dto.message.NewMessageDto;
import com.main.dto.user.UserUtilsDto;
import com.main.entity.MessageEntity;
import com.main.exception.RestException;
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
