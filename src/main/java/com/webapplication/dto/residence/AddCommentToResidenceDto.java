package com.webapplication.dto.residence;

/**
 * Created by panagiotis on 29/7/2017.
 */
public class AddCommentToResidenceDto {


    public Integer residenceId;

    public String comment;

    public Integer grade;

    public Integer getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(Integer residenceId) {
        this.residenceId = residenceId;
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
}
