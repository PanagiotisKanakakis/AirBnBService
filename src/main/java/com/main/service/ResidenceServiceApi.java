package com.main.service;


import com.main.dto.comment.CommentDto;
import com.main.dto.residence.*;
import com.main.dto.user.UserUtilsDto;
import com.main.entity.ResidenceEntity;
import com.main.exception.RestException;

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

    ResidenceEntity reserveResidence(ReservationDto reservationDto) throws RestException;

    List<CommentDto> getCommentOfResidence(SearchResidenceByIdDto searchResidenceByIdDto) throws RestException;

    List<ResidenceEntity> getRecommentedResidences(UserUtilsDto userUtilsDto);

}
