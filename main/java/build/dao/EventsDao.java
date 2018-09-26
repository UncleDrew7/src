package build.dao;

import build.model.Events;
import java.util.List;

/**
 * Created by apple on 23/09/2017.
 */
public interface EventsDao {

    //ADMIN EVENTS LIST
    public List<Events> getAllSystemEvents();


    //ADMIN DASHBOARD
    public String addEvent(Events event);

    //ADMIN
    public String updateEvent(Events event);

    //ADMIN
    public String deleteEvent(int eventId);


    //ADMIN
    public List<Events> displayAdminUpcomingEvent(long userId);


    //ADMIN
    public List<Events> displayAllAdminUpcomingEvents(long userId);

    //ADMIN
    public List<Events> displayAdminCalenderEvent(long userId);

    //STUDENT CALENDER EVENTS
    public List<Events> getStudentCalenderEvents(long userId);

    //ADMIN
    public List<Events> getLastAddedEvents();

    //STUDENT
    public List<Events> displayStudentUpcomingEvent();

    //STUDENT
    public List<Events> displayAllStudentUpcomingEvents();

    //STUDENT
    public List<Events> displayStudentCalenderEvent();

    // ADMIN ADD COURSE EVENT
    public String addCourseEvent(Events events);

    // ADMIN UPDATE COURSE EVENT
    public String editCourseEvent(Events events);

    //ADMIN GET SINGLE EVENT DATA BY EVENT ID
    public Events getEventDetailsByEventId(int eventId);

    //ADMIN GET COURSE EVENT LIST
    public List<Events> getCourseEventListByCourseId(int courseId);

    //STUDENT GET MOST UPCOMING EVENTS LIMIT 2
    public List<Events> getMostUpcomingUserEvents(long userId);

    //STUDENT GET COUNT FOR UPCOMING EVENTS FOR USER
    public int getCountOfMostUpcomingUserEvents(long userId);

    //STUDENT GET COURSE EVENT LIT FOR USER
    public List<Events> getAllCoursesEventsListForUser(long userId);

    //STUDENT GET SYSTEM EVENTS LIST FOR USER
    public List<Events> getAllSystemEventsListForAll();


    //GET ALL UPCOMING EVENTS AND HISTORY FOR USER
    public List<Events> getAllEventsAndHistoryForUser(long userId);



}
