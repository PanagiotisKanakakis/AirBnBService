package com.main.rest.residence;


import com.main.dto.comment.CommentDto;
import com.main.dto.residence.*;
import com.main.dto.user.UserUtilsDto;
import com.main.entity.ResidenceEntity;
import com.main.exception.RestException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public interface ResidenceApi {

    @RequestMapping(path = "/residence", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    ResidenceEntity addResidence(AddResidenceRequestDto addResidenceRequestDto) throws RestException;

    @RequestMapping(path = "/searchResidence" , method = RequestMethod.POST ,consumes = "application/json", produces = "application/json")
    List<ResidenceEntity> searchResidence(SearchResidenceDto searchResidenceDto) throws RestException, IOException;

    @RequestMapping(path = "/searchResidenceById" , method = RequestMethod.POST ,consumes = "application/json", produces = "application/json")
    AddResidenceResponseDto searchResidenceById(SearchResidenceByIdDto searchResidenceByIdDto) throws  RestException;

    @RequestMapping(path = "/getAllResidences" , method = RequestMethod.GET ,produces = "application/json")
    List<ResidenceEntity> getAllResidence() throws  RestException;

    @RequestMapping(path = "/addComment" , method = RequestMethod.POST ,consumes = "application/json",produces = "application/json")
    void addComment(AddCommentToResidenceDto addCommentToResidenceDto) throws RestException;

    @RequestMapping(path = "/updateResidence" , method = RequestMethod.POST ,consumes = "application/json",produces = "application/json")
    ResidenceEntity updateResidence(ResidenceEntity residenceEntity) throws RestException;

    @RequestMapping(path = "/deleteResidence" , method = RequestMethod.POST ,consumes = "application/json",produces = "application/json")
    void deleteResidence(ResidenceEntity residenceEntity) throws RestException;

    @RequestMapping(path = "/getResidencesBasedOnUserSearchedLocations" , method = RequestMethod.POST ,produces = "application/json")
    List<ResidenceEntity> getResidencesBasedOnUserSearchedLocations(UserUtilsDto userUtilsDto) throws  RestException;

    @RequestMapping(path = "/reserveResidence" , method = RequestMethod.POST ,consumes = "application/json")
    ResidenceEntity reserveResidence(ReservationDto reservationDto) throws  RestException;

    @RequestMapping(path = "/getCommentsForResidence" , method = RequestMethod.POST,consumes = "application/json",
            produces = "application/json")
    List<CommentDto> getCommentsForResidence(SearchResidenceByIdDto searchResidenceByIdDto) throws  RestException;

}
