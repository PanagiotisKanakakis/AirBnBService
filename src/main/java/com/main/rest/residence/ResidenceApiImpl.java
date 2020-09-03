package com.main.rest.residence;


import com.main.dto.comment.CommentDto;
import com.main.dto.residence.*;
import com.main.dto.user.UserUtilsDto;
import com.main.entity.ResidenceEntity;
import com.main.exception.RestException;
import com.main.service.ResidenceServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

@Component
public class ResidenceApiImpl implements ResidenceApi {

    @Autowired
    private ResidenceServiceApi residenceServiceApi;

    @Override
    public ResidenceEntity addResidence(@RequestBody AddResidenceRequestDto addResidenceRequestDto) throws RestException {
        return residenceServiceApi.addResidence(addResidenceRequestDto);
    }

    @Override
    public List<ResidenceEntity> searchResidence(@RequestBody SearchResidenceDto searchResidenceDto) throws RestException, IOException {
        return residenceServiceApi.searchResidence(searchResidenceDto);
    }

    @Override
    public AddResidenceResponseDto searchResidenceById(@RequestBody SearchResidenceByIdDto searchResidenceByIdDto) throws RestException {
        return residenceServiceApi.searchResidenceById(searchResidenceByIdDto);
    }

    @Override
    public List<ResidenceEntity> getAllResidence() throws RestException {
        return residenceServiceApi.getAllResidences();
    }

    @Override
    public void addComment(@RequestBody AddCommentToResidenceDto addCommentToResidenceDto) throws RestException {
        residenceServiceApi.addComment(addCommentToResidenceDto);
    }

    @Override
    public ResidenceEntity updateResidence(@RequestBody ResidenceEntity residenceEntity) throws RestException{
        return residenceServiceApi.updateResidence(residenceEntity);
    }

    @Override
    public void deleteResidence(@RequestBody  ResidenceEntity residenceEntity) throws RestException {
        residenceServiceApi.deleteResidence(residenceEntity);
    }

    @Override
    public List<ResidenceEntity> getResidencesBasedOnUserSearchedLocations(@RequestBody UserUtilsDto userUtilsDto) throws RestException {
        return residenceServiceApi.getResidencesBasedOnUserSearchedLocations(userUtilsDto);
    }

    @Override
    public ResidenceEntity reserveResidence(@RequestBody ReservationDto reservationDto) throws RestException {
        return residenceServiceApi.reserveResidence(reservationDto);
    }

    @Override
    public List<CommentDto> getCommentsForResidence(@RequestBody SearchResidenceByIdDto searchResidenceByIdDto) throws RestException {
        return residenceServiceApi.getCommentOfResidence(searchResidenceByIdDto);
    }


}
