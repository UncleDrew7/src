package build.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by apple on 23/09/2017.
 */
public class Events {
    private int event_id;
    private String eventType;
    @JSONField(name = "title")
    private String title;
//    @JSONField(name = "description")
    private String description ;
    @JSONField(name = "start")
    private String eventDateTime;
    private int courseId;
    private String courseName;
    private String eventDay;
    private String eventMonth;
    private String untilDateTime;
    private long created_by;
    private String createdByUserName;

    public Events() {
        super();
    }

    public Events(String eventType, String title, String description, String eventDateTime, long created_by) {
        this.eventType = eventType;
        this.title = title;
        this.description = description;
        this.eventDateTime = eventDateTime;
        this.created_by = created_by;
    }

    //update


    public Events(int event_id, String eventType, String title, String description, String eventDateTime) {
        this.event_id = event_id;
        this.eventType = eventType;
        this.title = title;
        this.description = description;
        this.eventDateTime = eventDateTime;
    }

    public Events(String eventType, String title, String description, String eventDateTime, String untilDateTime, long created_by) {
        this.eventType = eventType;
        this.title = title;
        this.description = description;
        this.eventDateTime = eventDateTime;
        this.untilDateTime = untilDateTime;
        this.created_by = created_by;
    }

    //ADD COURSE EVENT
    public Events(String eventType, String title, String description, String eventDateTime, int courseId, long created_by) {
        this.eventType = eventType;
        this.title = title;
        this.description = description;
        this.eventDateTime = eventDateTime;
        this.courseId = courseId;
        this.created_by = created_by;
    }

    //UPDATE COURSE EVENT
    public Events(int event_id, String title, String description, String eventDateTime) {
        this.event_id = event_id;
        this.title = title;
        this.description = description;
        this.eventDateTime = eventDateTime;
    }


    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
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

    public String getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(String eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public String getUntilDateTime() {
        return untilDateTime;
    }

    public void setUntilDateTime(String untilDateTime) {
        this.untilDateTime = untilDateTime;
    }

    public long getCreated_by() {
        return created_by;
    }

    public void setCreated_by(long created_by) {
        this.created_by = created_by;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getEventDay() {
        return eventDay;
    }

    public void setEventDay(String eventDay) {
        this.eventDay = eventDay;
    }

    public String getEventMonth() {
        return eventMonth;
    }

    public void setEventMonth(String eventMonth) {
        this.eventMonth = eventMonth;
    }

    public String getCreatedByUserName() {
        return createdByUserName;
    }

    public void setCreatedByUserName(String createdByUserName) {
        this.createdByUserName = createdByUserName;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
