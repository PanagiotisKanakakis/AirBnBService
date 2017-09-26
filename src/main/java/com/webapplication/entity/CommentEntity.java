package com.webapplication.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by panagiotis on 29/7/2017.
 */
@Entity(name = "Comments")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COMMENT_ID")
    private Integer commentId;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "GRADE")
    private Integer grade;

    @ManyToOne
    @JoinColumn(name = "RESIDENCE_ID")
    @JsonIgnore
    private ResidenceEntity residenceEntity;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public ResidenceEntity getResidenceEntity() {
        return residenceEntity;
    }

    public void setResidenceEntity(ResidenceEntity residenceEntity) {
        this.residenceEntity = residenceEntity;
    }
}
