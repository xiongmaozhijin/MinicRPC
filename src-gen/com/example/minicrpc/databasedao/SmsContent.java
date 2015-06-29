package com.example.minicrpc.databasedao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table SMS_CONTENT.
 */
public class SmsContent {

    private Long id;
    private Long categoryId;
    private Long sonCategoryId;
    private String content;
    private Integer hots;

    public SmsContent() {
    }

    public SmsContent(Long id) {
        this.id = id;
    }

    public SmsContent(Long id, Long categoryId, Long sonCategoryId, String content, Integer hots) {
        this.id = id;
        this.categoryId = categoryId;
        this.sonCategoryId = sonCategoryId;
        this.content = content;
        this.hots = hots;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getSonCategoryId() {
        return sonCategoryId;
    }

    public void setSonCategoryId(Long sonCategoryId) {
        this.sonCategoryId = sonCategoryId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getHots() {
        return hots;
    }

    public void setHots(Integer hots) {
        this.hots = hots;
    }

}
