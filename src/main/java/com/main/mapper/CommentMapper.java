package com.main.mapper;

import com.main.dto.comment.CommentDto;
import com.main.entity.CommentEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panagiotis on 28/9/2017.
 */
@Component
public class CommentMapper {

    public List<CommentDto> toCommentDto(List<CommentEntity> comment) {
        List<CommentDto> commentDtos = new ArrayList<>();
        for(CommentEntity c : comment){
            CommentDto commentDto = new CommentDto();
            commentDto.setUsername(c.getUser().getUsername());
            commentDto.setComment(c.getComment());
            commentDto.setGrade(c.getGrade());
            commentDtos.add(commentDto);
        }
        return commentDtos;
    }

}
