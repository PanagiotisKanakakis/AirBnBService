package com.webapplication.dto.comment;

/**
 * Created by panagiotis on 3/9/2017.
 */
public class CommentDto {

        private Integer commentId;
        private String comment;
        private Integer grade;

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

}
