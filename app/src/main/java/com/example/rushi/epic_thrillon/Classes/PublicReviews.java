package com.example.rushi.epic_thrillon.Classes;

/**
 * Created by dhaval on 22/12/2017.
 */

import java.util.HashMap;
import java.util.Map;

public class PublicReviews {

    private String comment;
    private String commentTitle;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public PublicReviews() {
    }

    public PublicReviews(String comment, String commentTitle) {
        this.comment = comment;
        this.commentTitle = commentTitle;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentTitle() {
        return commentTitle;
    }

    public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}