package com.toolshare.toolshare.models;

public class ToolReview {
    private int Id;
    private int ToolId;
    private Tool Tool;
    private int Rating;
    private String Review;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setToolId(int toolId) {
        ToolId = toolId;
    }

    public int getToolId() {
        return ToolId;
    }

    public com.toolshare.toolshare.models.Tool getTool() {
        return Tool;
    }

    public void setTool(com.toolshare.toolshare.models.Tool tool) {
        Tool = tool;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }

    public String getReview() {
        return Review;
    }

    public void setReview(String review) {
        Review = review;
    }

    /*****************************************************************************
     * DB Functions
     *
     */

    // TOOL REVIEW TABLE
    public static final String TABLE_TOOL_REVIEW = "tool_reviews";
    public static final String TOOL_REVIEW_COLUMN_ID = "id";
    public static final String TOOL_REVIEW_COLUMN_TOOL_ID = "tool_id";
    public static final String TOOL_REVIEW_COLUMN_RATING = "rating";
    public static final String TOOL_REVIEW_COLUMN_REVIEW = "review";
}
