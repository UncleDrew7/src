package build.dao.impl;

import build.model.Events;
import build.dao.EventsDao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;



/**
 * Created by apple on 23/09/2017.
 */
public class EventsDaoImpl extends JdbcDaoSupport implements EventsDao{
    @Override
    public List<Events> getAllSystemEvents() {
        String sql = "SELECT " +
                "e.event_id, " +
                "e.title, " +
                "e.event_type, " +
                "e.description, " +
                "DATE_FORMAT(e.event_date_time, '%D %M %Y')event_date_time," +
                "e.created_by, " +
                "usr.user_name " +
                "FROM events e " +
                "JOIN user usr ON usr.user_id = e.created_by " +
                "ORDER BY e.created_at DESC";

        try{

            List<Events> eventsList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Events>() {
                        @Override
                        public Events mapRow(ResultSet resultSet, int i) throws SQLException {
                            Events event = new Events();

                            event.setEvent_id(resultSet.getInt("event_id"));
                            event.setTitle(resultSet.getString("title"));
                            event.setEventType(resultSet.getString("event_type"));
                            event.setDescription(resultSet.getString("description"));
                            event.setEventDateTime(resultSet.getString("event_date_time"));
                            event.setCreated_by(resultSet.getLong("created_by"));
                            event.setCreatedByUserName(resultSet.getString("user_name"));
                            return event;
                        }
                    }

            );
            return eventsList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String addEvent(Events event) {
        String sql = "INSERT INTO events (title,event_type, description, event_date_time,created_by) VALUES (?,?,?,?,?)";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{event.getTitle(),event.getEventType(),event.getDescription(),event.getEventDateTime(),event.getCreated_by()}
        );
        if(1 == returnValue)
            return "@EventsDaoImpl::@addEvent:::#>>>"+event.getTitle()+" CREATED  SUCCESSFULLY";
        else
            return "@EventsDaoImpl::@addEvent:::FAILED TO CREATE EVENT ";
    }


    @Override
    public String updateEvent(Events event) {
        String sql = "UPDATE events " +
                " SET " +
                " title = ?, " +
                " description= ?, " +
                " event_date_time = ? " +
                " WHERE event_id = ?; ";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{event.getTitle(),event.getDescription(),event.getEventDateTime(),event.getEvent_id()}
        );
        if(1 == returnValue)
            return "@EventsDaoImpl::@updateEvent:::UPDATE SUCESSFULL ";
        else
            return "@EventsDaoImpl::@updateEvent:::QUERY FAILURE MESSAGE";
    }

    @Override
    public String deleteEvent(int eventId) {
        String sql = "DELETE FROM events WHERE event_id = ?;";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{eventId }
        );
        if(1 == returnValue)
            return "@EventsDaoImpl::@deleteEvent:::EVENT DELETED";
        else
            return "@EventsDaoImpl::@deleteEvent:::DELETE FAILED !!!";

    }

    @Override
    public List<Events> displayAdminUpcomingEvent(long userId) {
        String sql = "SELECT " +
                " e.event_id, " +
                " e.title, " +
                " e.event_type, " +
                "e.description, " +
                "e.event_date_time," +
                "DATE_FORMAT(e.event_date_time, '%d')day," +
                "DATE_FORMAT(e.event_date_time, '%M')month," +
                "e.until_date_time, " +
                "e.created_by " +
                "FROM events e " +
                "WHERE  e.event_date_time >= CURDATE() AND e.event_type = 'all' " +
                "ORDER BY e.event_date_time " +
                "LIMIT 5";
        try{

            List<Events> upcomingEventsList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Events>() {
                        @Override
                        public Events mapRow(ResultSet resultSet, int i) throws SQLException {
                            Events event = new Events();

                            event.setEvent_id(resultSet.getInt("event_id"));
                            event.setTitle(resultSet.getString("title"));
                            event.setEventType(resultSet.getString("event_type"));
                            event.setDescription(resultSet.getString("description"));
                            event.setEventDateTime(resultSet.getString("event_date_time"));
                            event.setEventDay(resultSet.getString("day"));
                            event.setEventMonth(resultSet.getString("month"));

//                            event.setUntilDateTime(resultSet.getString("until_date_time"));
                            event.setCreated_by(resultSet.getLong("created_by"));
                            return event;
                        }
                    }

            );
            return upcomingEventsList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public List<Events> displayAllAdminUpcomingEvents(long userId) {
        String  sql = "SELECT  " +
                "e.event_id, " +
                "e.title, " +
                "e.event_type, " +
                "e.description, " +
                "e.event_date_time, " +
                "e.until_date_time, " +
                "e.created_by " +
                "FROM events e " +
                "WHERE  e.event_date_time >= CURDATE() " +
                "ORDER BY e.event_date_time ";

        try{

            List<Events> upcomingEventsList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Events>() {
                        @Override
                        public Events mapRow(ResultSet resultSet, int i) throws SQLException {
                            Events event = new Events();

                            event.setEvent_id(resultSet.getInt("event_id"));
                            event.setTitle(resultSet.getString("title"));
                            event.setEventType(resultSet.getString("event_type"));
                            event.setDescription(resultSet.getString("description"));
                            event.setEventDateTime(resultSet.getString("event_date_time"));
//                            event.setUntilDateTime(resultSet.getString("until_date_time"));
                            event.setCreated_by(resultSet.getLong("created_by"));
                            return event;
                        }
                    }

            );
            return upcomingEventsList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Events> displayAdminCalenderEvent(long userId) {
        String sql = "SELECT  " +
                " e.event_id, " +
                "e.title, " +
                "e.event_type, " +
                "e.description, " +
                "e.event_date_time, " +
                "e.until_date_time, " +
                "e.created_by " +
                "FROM events e " +
                "WHERE e.event_type = 'all'";

        try{

            List<Events> upcomingEventsList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Events>() {
                        @Override
                        public Events mapRow(ResultSet resultSet, int i) throws SQLException {
                            Events event = new Events();

                            event.setEvent_id(resultSet.getInt("event_id"));
                            event.setTitle(resultSet.getString("title"));
                            event.setEventType(resultSet.getString("event_type"));
                            event.setDescription(resultSet.getString("description"));
                            event.setEventDateTime(resultSet.getString("event_date_time"));
//                            event.setUntilDateTime(resultSet.getString("until_date_time"));
                            event.setCreated_by(resultSet.getLong("created_by"));
                            return event;
                        }
                    }

            );
            return upcomingEventsList;

        }
        catch(EmptyResultDataAccessException e){
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }

    }

    @Override
    public List<Events> getStudentCalenderEvents(long userId) {

        String sql  = " SELECT   " +
                " e.event_id,  " +
                " e.event_type, " +
                " e.course_id,  " +
                "(SELECT  " +
                "pc.course_name " +
                "FROM child_courses cc  " +
                "LEFT JOIN parent_course_child_courses pcc ON cc.child_course_id = pcc.child_course_id  " +
                "LEFT JOIN parent_courses pc ON pcc.parent_course_id = pc.parent_course_id   " +
                "WHERE cc.child_course_id = e.course_id) course_name,  " +
                "e.title,  " +
                "e.description,  " +
                "e.event_date_time, " +
                "e.created_by , " +
                "u.user_name  " +
                "FROM EVENTS e  " +
                "LEFT JOIN USER u ON u.user_id = e.created_by  " +
                "WHERE e.event_type = 'all'  " +
                "OR e.course_id IN ((SELECT ce.course_id FROM course_enrolment ce WHERE ce.student_id = ? ))  " +
                "ORDER BY e.event_date_time DESC ";

        try{

            List<Events> upcomingEventsList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId},
                    new RowMapper<Events>() {
                        @Override
                        public Events mapRow(ResultSet resultSet, int i) throws SQLException {
                            Events event = new Events();

                            event.setCourseName(resultSet.getString("course_name"));
                            event.setEvent_id(resultSet.getInt("event_id"));
                            event.setTitle(resultSet.getString("title"));
                            event.setEventType(resultSet.getString("event_type"));
                            event.setDescription(resultSet.getString("description"));
                            event.setEventDateTime(resultSet.getString("event_date_time"));
//                            event.setUntilDateTime(resultSet.getString("until_date_time"));
                            event.setCreated_by(resultSet.getLong("created_by"));
                            return event;
                        }
                    }

            );
            return upcomingEventsList;

        }
        catch(EmptyResultDataAccessException e){
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<Events> getLastAddedEvents() {
        String sql = "SELECT  " +
                "e.event_id, " +
                "e.title, " +
                "e.event_type, " +
                "e.description, " +
                "DATE_FORMAT(e.event_date_time, '%D')day, " +
                "DATE_FORMAT(e.event_date_time, '%M')month, " +
                "e.event_date_time, " +
                "e.until_date_time, " +
                "e.created_by " +
                "FROM events e " +
                "WHERE   " +
                "e.event_date_time >= CURDATE() " +
                "AND e.event_type = 'all' " +
                "ORDER BY e.created_at DESC " +
                "LIMIT 5;";

        try{

            List<Events> eventsList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Events>() {
                        @Override
                        public Events mapRow(ResultSet resultSet, int i) throws SQLException {
                            Events event = new Events();

                            event.setEvent_id(resultSet.getInt("event_id"));
                            event.setTitle(resultSet.getString("title"));
                            event.setEventType(resultSet.getString("event_type"));
                            event.setDescription(resultSet.getString("description"));
                            event.setEventDateTime(resultSet.getString("event_date_time"));
                            event.setEventDay(resultSet.getString("day"));
                            event.setEventMonth(resultSet.getString("month"));

//                            event.setUntilDateTime(resultSet.getString("until_date_time"));
                            event.setCreated_by(resultSet.getLong("created_by"));
                            return event;
                        }
                    }

            );
            return eventsList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<Events> displayStudentUpcomingEvent() {
        String sql = "SELECT  " +
                "e.event_id, " +
                "e.title, " +
                "e.event_type, " +
                "e.description, " +
                "e.event_date_time, " +
                "e.until_date_time, " +
                "e.created_by " +
                "FROM events e " +
                "WHERE  e.event_date_time >= CURDATE()AND e.event_type = 'all' " +
                "ORDER BY e.event_date_time " +
                "LIMIT 5;";
        try{

            List<Events> upcomingEventsList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Events>() {
                        @Override
                        public Events mapRow(ResultSet resultSet, int i) throws SQLException {
                            Events event = new Events();

                            event.setEvent_id(resultSet.getInt("event_id"));
                            event.setTitle(resultSet.getString("title"));
                            event.setEventType(resultSet.getString("event_type"));
                            event.setDescription(resultSet.getString("description"));
                            event.setEventDateTime(resultSet.getString("event_date_time"));
//                            event.setUntilDateTime(resultSet.getString("until_date_time"));
                            event.setCreated_by(resultSet.getLong("created_by"));
                            return event;
                        }
                    }

            );
            return upcomingEventsList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Events> displayAllStudentUpcomingEvents() {
        String sql = "SELECT  " +
                " e.event_id, " +
                "e.title, " +
                "e.event_type, " +
                "e.description, " +
                "e.event_date_time, " +
                "e.until_date_time, " +
                "e.created_by " +
                "FROM evenst e " +
                "WHERE  e.event_date_time >= CURDATE()AND e.event_type = 'all' " +
                "ORDER BY e.event_date_time";

        try{

            List<Events> upcomingEventsList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Events>() {
                        @Override
                        public Events mapRow(ResultSet resultSet, int i) throws SQLException {
                            Events event = new Events();

                            event.setEvent_id(resultSet.getInt("event_id"));
                            event.setTitle(resultSet.getString("title"));
                            event.setEventType(resultSet.getString("event_type"));
                            event.setDescription(resultSet.getString("description"));
                            event.setEventDateTime(resultSet.getString("event_date_time"));
//                            event.setUntilDateTime(resultSet.getString("until_date_time"));
                            event.setCreated_by(resultSet.getLong("created_by"));
                            return event;
                        }
                    }

            );
            return upcomingEventsList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Events> displayStudentCalenderEvent() {
        String sql = " SELECT  " +
                "e.event_id, " +
                "e.title, " +
                "e.event_type, " +
                "e.description, " +
                "e.event_date_time, " +
                "e.until_date_time, " +
                "e.created_by " +
                "FROM events e " +
                "WHERE e.event_type = 'all'";
        try{

            List<Events> upcomingEventsList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Events>() {
                        @Override
                        public Events mapRow(ResultSet resultSet, int i) throws SQLException {
                            Events event = new Events();

                            event.setEvent_id(resultSet.getInt("event_id"));
                            event.setTitle(resultSet.getString("title"));
                            event.setEventType(resultSet.getString("event_type"));
                            event.setDescription(resultSet.getString("description"));
                            event.setEventDateTime(resultSet.getString("event_date_time"));
//                            event.setUntilDateTime(resultSet.getString("until_date_time"));
                            event.setCreated_by(resultSet.getLong("created_by"));
                            return event;
                        }
                    }

            );
            return upcomingEventsList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String addCourseEvent(Events events) {
        String sql = "INSERT INTO events (event_type,course_id,title,description,event_date_time,created_by)VALUES (?,?,?,?,?,?);";

        try {

            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ events.getEventType(),events.getCourseId(),events.getTitle(),events.getDescription(),events.getEventDateTime(),events.getCreated_by() }
            );
            if(1 == returnValue)
                return "@EventsDaoImpl::@addCourseEvent::EVENT CREATED SUCCESSFULLY ";
            else
                return "@EventsDaoImpl::@addCourseEvent:: FAILED TO CREATE EVENT";


        }catch (Exception e){
            System.out.println("@EventsDaoImpl::@addCourseEvent:: FAILED TO CREATE EVENT");
            e.printStackTrace();
            return "@EventsDaoImpl::@addCourseEvent:: FAILED TO CREATE EVENT";
        }

    }

    @Override
    public String editCourseEvent(Events events) {
        String sql = "UPDATE events " +
                "SET " +
                "title = ?," +
                "description = ?, " +
                "event_date_time = ? " +
                "WHERE event_id = ?";

        try {

            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ events.getTitle(),events.getDescription(),events.getEventDateTime(),events.getEvent_id() }
            );
            if(1 == returnValue)
                return "@EventsDaoImpl::@editCourseEvent::EVENT UPDATED SUCCESSFULLY ";
            else
                return "@EventsDaoImpl::@editCourseEvent:: FAILED TO UPDATE EVENT";


        }catch (Exception e){
            System.out.println("@EventsDaoImpl::@editCourseEvent:: FAILED TO UPDATE EVENT");
            e.printStackTrace();
            return "@EventsDaoImpl::@editCourseEvent:: FAILED TO UPDATE EVENT";
        }

    }

    @Override
    public Events getEventDetailsByEventId(int eventId) {
        String sql = "SELECT  " +
                "e.event_id, " +
                "e.course_id, " +
                "e.event_type, " +
                "e.title, " +
                "e.description, " +
                "e.event_date_time " +
                "FROM events e " +
                "WHERE e.event_id = ? ";

        try{
            Events eventDetails = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{eventId},
                    new RowMapper<Events>() {
                        @Override
                        public Events mapRow(ResultSet resultSet, int i) throws SQLException {
                            Events events = new Events();
                            events.setEvent_id(resultSet.getInt("event_id"));
                            events.setCourseId(resultSet.getInt("course_id"));
                            events.setEventType(resultSet.getString("event_type"));
                            events.setTitle(resultSet.getString("title"));
                            events.setDescription(resultSet.getString("description"));
                            events.setEventDateTime(resultSet.getString("event_date_time"));

                            return events;
                        }
                    }

            );
            return eventDetails;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@EventsDaoImpl::@getEventDetailsByEventId::RETURNED EMPTY!!");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@EventsDaoImpl::@getEventDetailsByEventId::ERROR!!");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<Events> getCourseEventListByCourseId(int courseId) {
        String sql = "SELECT  " +
                "e.event_id, " +
                "e.course_id, " +
                "e.event_type, " +
                "e.title, " +
                "e.description, " +
                "DATE_FORMAT(e.event_date_time, '%d')eDay, " +
                "DATE_FORMAT(e.event_date_time, '%M')eMonth, " +
                "DATE_FORMAT(e.event_date_time, '%D %M %Y')event_date_time " +
                "FROM events e " +
                "WHERE e.course_id = ?";

        try{

            List<Events> courseEventList =  getJdbcTemplate().query(
                    sql,
                    new Object[]{courseId},
                    new RowMapper<Events>() {
                        @Override
                        public Events mapRow(ResultSet resultSet, int i) throws SQLException {
                            Events event = new Events();

                            event.setEvent_id(resultSet.getInt("event_id"));
                            event.setCourseId(resultSet.getInt("course_id"));
                            event.setEventType(resultSet.getString("event_type"));
                            event.setTitle(resultSet.getString("title"));
                            event.setDescription(resultSet.getString("description"));
                            event.setEventDay(resultSet.getString("eDay"));
                            event.setEventMonth(resultSet.getString("eMonth"));
                            event.setEventDateTime(resultSet.getString("event_date_time"));

                            return event;
                        }
                    }

            );
            return courseEventList;


        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@EventsDaoImpl::@getCourseEventListByCourseId::RETURNED EMPTY!!");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@EventsDaoImpl::@getCourseEventListByCourseId::ERROR!!");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<Events> getMostUpcomingUserEvents(long userId) {
        String sql =  "SELECT " +
                "e.event_id, " +
                "e.event_type, " +
                "e.course_id, " +
                "(SELECT c.course_name FROM courses c WHERE c.course_id = e.course_id) course_name, " +
                "e.title, " +
                "e.description, " +
                "DATE_FORMAT(e.event_date_time, '%d')eDay, " +
                "DATE_FORMAT(e.event_date_time, '%M')eMonth, " +
                "DATE_FORMAT(e.event_date_time, '%D %M %Y')event_date_time, " +
                "e.created_by , " +
                "u.user_name " +
                "FROM events e  " +
                "LEFT JOIN user u ON u.user_id = e.created_by " +
                "WHERE  e.event_date_time >=  CURDATE() " +
                "AND(e.event_type = 'all' OR e.course_id IN ((SELECT ce.course_id FROM course_enrolment ce WHERE ce.student_id =  ?))) " +
                "ORDER BY e.event_date_time  ";

        try{

            List<Events> eventList =  getJdbcTemplate().query(
                    sql,
                    new Object[]{userId},
                    new RowMapper<Events>() {
                        @Override
                        public Events mapRow(ResultSet resultSet, int i) throws SQLException {
                            Events event = new Events();

                            event.setEvent_id(resultSet.getInt("event_id"));
                            event.setCourseId(resultSet.getInt("course_id"));
                            event.setCourseName(resultSet.getString("course_name"));
                            event.setEventType(resultSet.getString("event_type"));
                            event.setTitle(resultSet.getString("title"));
                            event.setDescription(resultSet.getString("description"));
                            event.setEventDay(resultSet.getString("eDay"));
                            event.setEventMonth(resultSet.getString("eMonth"));
                            event.setEventDateTime(resultSet.getString("event_date_time"));
                            event.setCreated_by(resultSet.getLong("created_by"));
                            event.setCreatedByUserName(resultSet.getString("user_name"));
                            return event;
                        }
                    }

            );
            return eventList;


        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@EventsDaoImpl::@getMostUpcomingUserEvents::RETURNED EMPTY!!");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@EventsDaoImpl::@getMostUpcomingUserEvents::ERROR!!");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public int getCountOfMostUpcomingUserEvents(long userId) {
        String sql = " SELECT  " +
                " COUNT(*) " +
                " FROM EVENTS e  " +
                " LEFT JOIN user u ON u.user_id = e.created_by  " +
                " WHERE  e.event_date_time >=  CURDATE()  " +
                " AND(e.event_type = 'all' OR e.course_id IN ((SELECT ce.course_id FROM course_enrolment ce WHERE ce.student_id =  ?))) " +
                " ORDER BY e.event_date_time ";


        try{
            int count = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId},
                    Integer.class
            );
            return count;


        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@EventsDaoImpl::@getCountOfMostUpcomingUserEvents::RETURNED EMPTY!!");
            return 0;
        }catch (Exception e){
            System.out.println("@EventsDaoImpl::@getCountOfMostUpcomingUserEvents::ERROR!!");
            e.printStackTrace();
            return  0;
        }




    }

    @Override
    public List<Events> getAllCoursesEventsListForUser(long userId) {
        String sql = "SELECT " +
                "e.event_id, " +
                "e.event_type, " +
                "e.course_id, " +
                "(SELECT c.course_name FROM courses c WHERE c.course_id = e.course_id) course_name, " +
                "e.title, " +
                "e.description, " +
                "DATE_FORMAT(e.event_date_time, '%d')eDay, " +
                "DATE_FORMAT(e.event_date_time, '%M')eMonth, " +
                "DATE_FORMAT(e.event_date_time, '%D %M %Y')event_date_time, " +
                "e.created_by , " +
                "u.user_name " +
                "FROM events e  " +
                "LEFT JOIN user u ON u.user_id = e.created_by " +
                "WHERE e.event_type = 'course' " +
                "AND e.course_id IN ((SELECT ce.course_id FROM course_enrolment ce WHERE ce.student_id =  ?)) " +
                "ORDER BY e.event_date_time DESC ";

        try{

            List<Events> eventList =  getJdbcTemplate().query(
                    sql,
                    new Object[]{userId},
                    new RowMapper<Events>() {
                        @Override
                        public Events mapRow(ResultSet resultSet, int i) throws SQLException {
                            Events event = new Events();

                            event.setEvent_id(resultSet.getInt("event_id"));
                            event.setCourseId(resultSet.getInt("course_id"));
                            event.setCourseName(resultSet.getString("course_name"));
                            event.setEventType(resultSet.getString("event_type"));
                            event.setTitle(resultSet.getString("title"));
                            event.setDescription(resultSet.getString("description"));
                            event.setEventDay(resultSet.getString("eDay"));
                            event.setEventMonth(resultSet.getString("eMonth"));
                            event.setEventDateTime(resultSet.getString("event_date_time"));
                            event.setCreated_by(resultSet.getLong("created_by"));
                            event.setCreatedByUserName(resultSet.getString("user_name"));
                            return event;
                        }
                    }

            );
            return eventList;


        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@EventsDaoImpl::@getAllCoursesEventsListForUser::RETURNED EMPTY!!");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@EventsDaoImpl::@getAllCoursesEventsListForUser::ERROR!!");
            e.printStackTrace();
            return  null;
        }

    }

    @Override
    public List<Events> getAllSystemEventsListForAll() {
       String sql = "SELECT  " +
               "e.event_id, " +
               "e.event_type, " +
               "e.course_id, " +
               "(SELECT c.course_name FROM courses c WHERE c.course_id = e.course_id) course_name, " +
               "e.title, " +
               "e.description, " +
               "DATE_FORMAT(e.event_date_time, '%d')eDay, " +
               "DATE_FORMAT(e.event_date_time, '%M')eMonth, " +
               "DATE_FORMAT(e.event_date_time, '%D %M %Y')event_date_time, " +
               "e.created_by , " +
               "u.user_name " +
               "FROM events e  " +
               "LEFT JOIN user u ON u.user_id = e.created_by " +
               "WHERE e.event_type = 'all' " +
               "ORDER BY e.event_date_time DESC ";

        try{

            List<Events> eventList =  getJdbcTemplate().query(
                    sql,
                    new RowMapper<Events>() {
                        @Override
                        public Events mapRow(ResultSet resultSet, int i) throws SQLException {
                            Events event = new Events();

                            event.setEvent_id(resultSet.getInt("event_id"));
                            event.setCourseId(resultSet.getInt("course_id"));
                            event.setCourseName(resultSet.getString("course_name"));
                            event.setEventType(resultSet.getString("event_type"));
                            event.setTitle(resultSet.getString("title"));
                            event.setDescription(resultSet.getString("description"));
                            event.setEventDay(resultSet.getString("eDay"));
                            event.setEventMonth(resultSet.getString("eMonth"));
                            event.setEventDateTime(resultSet.getString("event_date_time"));
                            event.setCreated_by(resultSet.getLong("created_by"));
                            event.setCreatedByUserName(resultSet.getString("user_name"));
                            return event;
                        }
                    }

            );
            return eventList;


        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@EventsDaoImpl::@getAllSystemEventsListForAll::RETURNED EMPTY!!");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@EventsDaoImpl::@getAllSystemEventsListForAll::ERROR!!");
            e.printStackTrace();
            return  null;
        }


    }

    @Override
    public List<Events> getAllEventsAndHistoryForUser(long userId) {
        String sql  = "SELECT  " +
                "e.event_id, " +
                "e.event_type, " +
                "e.course_id, " +
                "(SELECT c.course_name FROM courses c WHERE c.course_id = e.course_id) course_name, " +
                "e.title, " +
                "e.description, " +
                "DATE_FORMAT(e.event_date_time, '%d')eDay, " +
                "DATE_FORMAT(e.event_date_time, '%M')eMonth, " +
                "DATE_FORMAT(e.event_date_time, '%D %M %Y')event_date_time, " +
                "e.created_by , " +
                "u.user_name " +
                "FROM events e  "  +
                "LEFT JOIN user u ON u.user_id = e.created_by " +
                "WHERE e.event_type = 'all' " +
                "OR e.course_id IN ((SELECT ce.course_id FROM course_enrolment ce WHERE ce.student_id = ?)) " +
                "ORDER BY e.event_date_time DESC ";

        try{

            List<Events> eventList =  getJdbcTemplate().query(
                    sql,
                    new Object[]{userId},
                    new RowMapper<Events>() {
                        @Override
                        public Events mapRow(ResultSet resultSet, int i) throws SQLException {
                            Events event = new Events();

                            event.setEvent_id(resultSet.getInt("event_id"));
                            event.setCourseId(resultSet.getInt("course_id"));
                            event.setCourseName(resultSet.getString("course_name"));
                            event.setEventType(resultSet.getString("event_type"));
                            event.setTitle(resultSet.getString("title"));
                            event.setDescription(resultSet.getString("description"));
                            event.setEventDay(resultSet.getString("eDay"));
                            event.setEventMonth(resultSet.getString("eMonth"));
                            event.setEventDateTime(resultSet.getString("event_date_time"));
                            event.setCreated_by(resultSet.getLong("created_by"));
                            event.setCreatedByUserName(resultSet.getString("user_name"));
                            return event;
                        }
                    }

            );
            return eventList;


        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@EventsDaoImpl::@getAllEventsAndHistoryForUser::RETURNED EMPTY!!");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@EventsDaoImpl::@getAllEventsAndHistoryForUser::ERROR!!");
            e.printStackTrace();
            return  null;
        }
    }
}
