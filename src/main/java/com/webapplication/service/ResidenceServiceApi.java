package com.webapplication.service;


import com.webapplication.dto.comment.CommentDto;
import com.webapplication.dto.residence.*;
import com.webapplication.dto.user.UserUtilsDto;
import com.webapplication.entity.ResidenceEntity;
import com.webapplication.exception.RestException;

import java.io.IOException;
import java.util.List;

public interface ResidenceServiceApi {

    ResidenceEntity addResidence(AddResidenceRequestDto addResidenceRequestDto) throws RestException;

    List<ResidenceEntity> searchResidence(SearchResidenceDto searchResidenceDto) throws RestException, IOException;

    AddResidenceResponseDto searchResidenceById(SearchResidenceByIdDto searchResidenceByIdDto) throws RestException;

    List<ResidenceEntity> getAllResidences();

    void addComment(AddCommentToResidenceDto addCommentToResidenceDto);

    ResidenceEntity updateResidence(ResidenceEntity residenceEntity) throws RestException;

    void deleteResidence(ResidenceEntity residenceEntity);

    List<ResidenceEntity> getResidencesBasedOnUserSearchedLocations(UserUtilsDto userUtilsDto);

    void reserveResidence(ReservationDto reservationDto) throws RestException;

    List<CommentDto> getCommentOfResidence(SearchResidenceByIdDto searchResidenceByIdDto) throws RestException;
}
