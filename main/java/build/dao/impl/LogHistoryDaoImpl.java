package build.dao.impl;

import build.model.LogHistory;
import build.dao.LogHistoryDao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by apple on 29/08/2017.
 */
public class LogHistoryDaoImpl extends JdbcDaoSupport implements LogHistoryDao{
    @Override
    public String addLogHistory(LogHistory logHistory) {

        String sql = "INSERT INTO  log_history (location ,user_id ,ip_address,action,information) VALUES( ?,?,?,?,? )";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{logHistory.getLocation() ,logHistory.getUserId(),logHistory.getIpAddress(),logHistory.getAction(),logHistory.getInformation() }
        );
        if(1 == returnValue)
            return "QUERY SUCESS MESSAGE ";
        else
            return "QUERY FAILURE MESSAGE";
    }

    @Override
    public String logUser(LogHistory log) {
        String sql = "INSERT INTO log_history (app,location_name,location_uri,user_id,user_role,ip_address,action ) VALUES(?,?,?,?,?,?,? );";

        try {

            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{log.getApp(),log.getLocationName(),log.getLocationUrl(),log.getUserId(),log.getUserRole(),log.getIpAddress(),log.getAction()}
            );
            if(1 == returnValue){
                System.out.println("@LogHistoryDaoImpl::@logUser::::::LOGGING USER ACTIVITY ...");
                return "LOGGING ACTIVITY ...";
            }
            else
                return "LOGGING FAILURE MESSAGE";



        }catch (Exception e){
            System.out.println("@LogHistoryDaoImpl::@logUser::ERROR!!!");
            e.printStackTrace();
            return "@LogHistoryDaoImpl::@logUser::ERROR!!!";
        }
    }

    @Override
    public List<LogHistory> getAllSystemLogs() {
        String sql = " SELECT  " +
                "  lh.id," +
                " lh.app ," +
                "  lh.location_name,  " +
                "  lh.location_uri,  " +
                "  lh.user_id,  " +
                " u.user_name, " +
                " lh.user_role,  " +
                " lh.ip_address,  " +
                " DATE_FORMAT(lh.time, '%d %M %Y %r') TIME,  " +
                " lh.action,  " +
                " lh.action_desc,  " +
                " lh.full_information  " +
                " FROM log_history lh  " +
                " LEFT JOIN user u ON u.user_id = lh.user_id " +
                " ORDER BY lh.time DESC  " +
                " LIMIT 50";

        try{
            List<LogHistory> systemLogsList =  getJdbcTemplate().query(
                    sql,
                    new RowMapper<LogHistory>() {
                        @Override
                        public LogHistory mapRow(ResultSet resultSet, int i) throws SQLException {

                            LogHistory log = new LogHistory();

                            log.setId(resultSet.getInt("id"));
                            log.setLocationName(resultSet.getString("location_name"));
                            log.setApp(resultSet.getString("app"));
                            log.setLocationUrl(resultSet.getString("location_uri"));
                            log.setUserId(resultSet.getLong("user_id"));
                            log.setUserName(resultSet.getString("user_name"));
                            log.setUserRole(resultSet.getString("user_role"));
                            log.setIpAddress(resultSet.getString("ip_address"));
                            log.setTime(resultSet.getString("time"));
                            log.setAction(resultSet.getString("action"));
                            log.setActionDesc(resultSet.getString("action_desc"));
                            log.setFullInformation(resultSet.getString("full_information"));

                            return log;
                        }
                    }

            );
            return systemLogsList;
        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<LogHistory> getMostRecentLoggedInUserTimeline(long userId,int limit) {
        String sql = "SELECT  " +
                "  lh.id , " +
                "  lh.app, " +
                " lh.user_id, " +
                " lh.ip_address," +
                " lh.user_role, " +
                " lh.action, " +
                " DATE_FORMAT(lh.time, '%D %M %Y')DATE," +
                " DATE_FORMAT(lh.time, '%r')timeAt ," +
                " lh.location_name," +
                " lh.location_uri," +
                " lh.action_desc," +
                " lh.full_information " +
                "  FROM log_history lh " +
                "   WHERE lh.user_id = ?" +
                "   ORDER BY lh.time DESC " +
                "   LIMIT ?";

        try{
            List<LogHistory> loggedInUserTimelineList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId, limit},
                    new RowMapper<LogHistory>() {
                        @Override
                        public LogHistory mapRow(ResultSet resultSet, int i) throws SQLException {

                            LogHistory log = new LogHistory();
                            log.setApp(resultSet.getString("app"));
                            log.setId(resultSet.getInt("id"));
                            log.setUserId(resultSet.getLong("user_id"));
                            log.setIpAddress(resultSet.getString("ip_address"));
                            log.setUserRole(resultSet.getString("user_role"));
                            log.setAction(resultSet.getString("action"));
                            log.setDate(resultSet.getString("DATE"));
                            log.setTimeAt(resultSet.getString("timeAt"));
                            log.setLocationName(resultSet.getString("location_name"));
                            log.setLocationUrl(resultSet.getString("location_uri"));
                            log.setActionDesc(resultSet.getString("action_desc"));
                            log.setFullInformation(resultSet.getString("full_information"));

                            return log;

                        }
                    }
            );
            return loggedInUserTimelineList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public List<LogHistory> getMostRecentAllSystemUserTimeline(long userId,int limit) {
        String sql = "    SELECT " +
                "    lh.user_id," +
                "    lh.ip_address," +
                "    lh.user_role," +
                "    lh.action," +
                "    DATE_FORMAT(lh.time, '%D %M %Y')DATE," +
                "    DATE_FORMAT(lh.time, '%r')timeAt ," +
                "    lh.location_name," +
                "    lh.location_uri," +
                "    lh.action_desc," +
                "    lh.full_information " +
                "    FROM log_history lh " +
                "    WHERE lh.user_id != ? " +
                "    ORDER BY lh.time DESC " +
                "    LIMIT ?";

        try{
            List<LogHistory> mostRecentSystemUsersTimelineList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId, limit},
                    new RowMapper<LogHistory>() {
                        @Override
                        public LogHistory mapRow(ResultSet resultSet, int i) throws SQLException {
                            LogHistory log = new LogHistory();
                            log.setUserId(resultSet.getLong("user_id"));
                            log.setIpAddress(resultSet.getString("ip_address"));
                            log.setUserRole(resultSet.getString("user_role"));
                            log.setAction(resultSet.getString("action"));
                            log.setDate(resultSet.getString("DATE"));
                            log.setTimeAt(resultSet.getString("timeAt"));
                            log.setLocationName(resultSet.getString("location_name"));
                            log.setLocationUrl(resultSet.getString("location_uri"));
                            log.setActionDesc(resultSet.getString("action_desc"));
                            log.setFullInformation(resultSet.getString("full_information"));

                            return log;
                        }
                    }
            );
            return mostRecentSystemUsersTimelineList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<LogHistory> getMostRecentSystemUserTeacherTimeline(long userId, int limit) {
        String sql = " SELECT " +
                " lh.id," +
                " lh.user_id, " +
                " lh.ip_address, " +
                " lh.user_role, " +
                " lh.action, " +
                " DATE_FORMAT(lh.time, '%D %M %Y')DATE, " +
                " DATE_FORMAT(lh.time, '%r')timeAt , " +
                " lh.location_name, " +
                " lh.location_uri, " +
                " lh.action_desc, " +
                " lh.full_information " +
                "    FROM log_history lh " +
                "    WHERE  " +
                "    lh.user_role = 'teacher'  " +
                "    AND lh.user_id != ? " +
                "    ORDER BY lh.time DESC " +
                "    LIMIT ?";

        try{

            List<LogHistory> mostRecentSystemUserTeacherTimelineList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId, limit},
                    new RowMapper<LogHistory>() {
                        @Override
                        public LogHistory mapRow(ResultSet resultSet, int i) throws SQLException {
                            LogHistory log = new LogHistory();
                            log.setId(resultSet.getInt("id"));
                            log.setUserId(resultSet.getLong("user_id"));
                            log.setIpAddress(resultSet.getString("ip_address"));
                            log.setUserRole(resultSet.getString("user_role"));
                            log.setAction(resultSet.getString("action"));
                            log.setDate(resultSet.getString("DATE"));
                            log.setTimeAt(resultSet.getString("timeAt"));
                            log.setLocationName(resultSet.getString("location_name"));
                            log.setLocationUrl(resultSet.getString("location_uri"));
                            log.setActionDesc(resultSet.getString("action_desc"));
                            log.setFullInformation(resultSet.getString("full_information"));

                            return log;
                        }
                    }
            );
            return mostRecentSystemUserTeacherTimelineList;


        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<LogHistory> getMostRecentSystemUserStudentTimeline(long userId, int limit) {
        String sql = "SELECT  " +
                "   lh.id, " +
                "   lh.user_id, " +
                "   lh.ip_address, " +
                "   lh.user_role, " +
                "   lh.action, " +
                "   DATE_FORMAT(lh.time, '%D %M %Y')DATE, " +
                " DATE_FORMAT(lh.time, '%r')timeAt , " +
                "   lh.location_name, " +
                "   lh.location_uri, " +
                "   lh.action_desc, " +
                "   lh.full_information " +
                "    FROM log_history lh " +
                "    WHERE  " +
                "    lh.user_role = 'student'  " +
                "    AND lh.user_id != ? " +
                "    ORDER BY lh.time DESC  " +
                "    LIMIT ? ";

        try{
            List<LogHistory>  mostRecentSystemUserStudentTimelineList = getJdbcTemplate().query(
                    sql,
                    new Object[]{userId, limit},
                    new RowMapper<LogHistory>() {
                        @Override
                        public LogHistory mapRow(ResultSet resultSet, int i) throws SQLException {
                            LogHistory log = new LogHistory();
                            log.setId(resultSet.getInt("id"));
                            log.setUserId(resultSet.getLong("user_id"));
                            log.setIpAddress(resultSet.getString("ip_address"));
                            log.setUserRole(resultSet.getString("user_role"));
                            log.setAction(resultSet.getString("action"));
                            log.setDate(resultSet.getString("DATE"));
                            log.setTimeAt(resultSet.getString("timeAt"));
                            log.setLocationName(resultSet.getString("location_name"));
                            log.setLocationUrl(resultSet.getString("location_uri"));
                            log.setActionDesc(resultSet.getString("action_desc"));
                            log.setFullInformation(resultSet.getString("full_information"));

                            return log;
                        }
                    }
            );
            return  mostRecentSystemUserStudentTimelineList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }

    }
}
