package build.model;

import java.util.Date;

/**
 * Created by apple on 29/08/2017.
 * WINNI
 */
public class LogHistory {

    private int id;
    private String app;
    private long userId;
    private String userName;
    private String ipAddress;
    private String location;
    private String userRole;
    private String action ;
    private String date;
    private String timeAt;
    private String locationName;
    private String locationUrl;
    private String actionDesc;
    private String fullInformation;
    private String time;//for inserting to be changed
    private String information;//for inserting to be changed

    public LogHistory(){
        super();
    }

    public LogHistory(String location, long userId, String ipAddress, String action, String information) {
        this.location = location;
        this.userId = userId;
        this.ipAddress = ipAddress;
        this.action = action;
        this.information = information;
    }

    //LOG HISTORY


    public LogHistory(String app,String locationName,String locationUrl, long userId, String userRole, String ipAddress, String action ) {
        this.app = app;
        this.userId = userId;
        this.ipAddress = ipAddress;
        this.userRole = userRole;
        this.action = action;
        this.locationName = locationName;
        this.locationUrl = locationUrl;
    }

    public int getId() {
        return id;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeAt() {
        return timeAt;
    }

    public void setTimeAt(String timeAt) {
        this.timeAt = timeAt;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationUrl() {
        return locationUrl;
    }

    public void setLocationUrl(String locationUrl) {
        this.locationUrl = locationUrl;
    }

    public String getActionDesc() {
        return actionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
    }

    public String getFullInformation() {
        return fullInformation;
    }

    public void setFullInformation(String fullInformation) {
        this.fullInformation = fullInformation;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
