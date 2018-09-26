package com.cdai.models.calender;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by apple on 23/09/2017.
 */
public class CalenderEvents {

    private String title;
    private String description;
    private String start;

    public CalenderEvents() {
        super();
    }

    public CalenderEvents(String title, String description, String start) {
        this.title = title;
        this.description = description;
        this.start = start;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }
}
