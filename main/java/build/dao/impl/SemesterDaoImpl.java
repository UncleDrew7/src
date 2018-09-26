package build.dao.impl;

import build.model.Semester;
import build.dao.SemesterDao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by apple on 30/08/2017.
 */
public class SemesterDaoImpl  extends JdbcDaoSupport implements SemesterDao{
    @Override
    public String addSemester(Semester semester) {
        String sql = "INSERT INTO semester (semester_code ,start_date,end_date,createdBy) VALUES( ? ,?,?,?)";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{ semester.getSemesterCode(),semester.getStartDate(),semester.getEndDate(),semester.getCreatedBy() }
        );
        try {
            if(1 == returnValue)
                return "@SemesterDaoImpl::@addSemester:: SUCESS MESSAGE ";
            else
                return "@SemesterDaoImpl::@addSemester:: FAILURE MESSAGE";

        }catch (Exception e){
            System.out.println("@SemesterDaoImpl::@addSemester::ERROR");
            e.printStackTrace();
            return "@SemesterDaoImpl::@addSemester:: FAILURE MESSAGE";
        }


    }

    @Override
    public String updateSemester(Semester semester) {
        String sql = "UPDATE semester " +
                "SET semester_code = ?, start_date = ?, end_date = ? " +
                "WHERE semester_id = ?;";

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ semester.getSemesterCode(),semester.getStartDate(),semester.getEndDate(),semester.getSemesterId()}
            );
            if(1 == returnValue)
                return "@SemesterDaImpl::@updateSemester::SUCCESS";
            else
                return "@SemesterDaImpl::@updateSemester::FAILURE";

        }catch (Exception e){
            System.out.println("@SemesterDaImpl::@updateSemester::ERROR");
            e.printStackTrace();
            return "@SemesterDaImpl::@updateSemester:: FAILURE MESSAGE";
        }

    }

    @Override
    public Semester getSingleSemesterBySemesterId(int semesterId) {
        String sql = "SELECT  " +
                "s.semester_id, " +
                "s.semester_code, " +
                "s.start_date, " +
                "s.end_date " +
                "FROM semester s " +
                "WHERE s.semester_id = ?";

        try{

            Semester semesterObject = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{semesterId},
                    new RowMapper<Semester>() {
                        @Override
                        public Semester mapRow(ResultSet resultSet, int i) throws SQLException {
                            Semester semester = new Semester();
                            semester.setSemesterId(resultSet.getInt("semester_id"));
                            semester.setSemesterCode(resultSet.getString("semester_code"));
                            semester.setStartDate(resultSet.getString("start_date"));
                            semester.setEndDate(resultSet.getString("end_date"));
                            return semester;
                        }
                    }
            );
            return semesterObject;


        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@SemesterDaoImpl::@getSingleSemesterBySemesterId::EMPTY");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@SemesterDaoImpl::@getSingleSemesterBySemesterId::ERROR");
            e.printStackTrace();
            return  null;
        }

    }

    @Override
    public int getNumberOfCoursesForSemester(int semesterId) {
        String sql = "SELECT COUNT(*) " +
                "FROM child_courses ccs " +
                "WHERE ccs.semester_id =  ? ";

        try{
            int totalNumberOfCourses =  getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{semesterId},
                    Integer.class
            );
            return totalNumberOfCourses;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Semester> getAllSemesterTableDisplayData() {
        String sql = "SELECT " +
                "s.semester_id, " +
                "s.semester_code, " +
                "DATE_FORMAT(s.start_date, '%D %M %Y')startDate, " +
                "DATE_FORMAT(s.end_date, '%D %M %Y')endDate, " +
                "(SELECT  " +
                "COUNT(*) " +
                "FROM child_courses cc " +
                "WHERE cc.semester_id = s.semester_id " +
                ") numberOfCourses  " +
                "FROM semester s  " +
                "ORDER BY s.start_date DESC";

        try{
            List<Semester> allSemestersDataList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Semester>() {
                        @Override
                        public Semester mapRow(ResultSet resultSet, int i) throws SQLException {
                            Semester semester = new Semester();

                            semester.setSemesterId(resultSet.getInt("semester_id"));
                            semester.setSemesterCode(resultSet.getString("semester_code"));
                            semester.setStartDate(resultSet.getString("startDate"));
                            semester.setEndDate(resultSet.getString("endDate"));
                            semester.setTotalNumberOfCourses(resultSet.getInt("numberOfCourses"));

                            return  semester;
                        }
                    }
            );
            return allSemestersDataList;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public int getTotalSemesterCount() {
        String sql = "SELECT COUNT(*) FROM semester";

        try{
            int totalNumberOfSemesters = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return totalNumberOfSemesters;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Semester> getLastAddedSemestersList() {
        String sql = "SELECT " +
                "s.semester_id, " +
                "s.semester_code, " +
                "DATE_FORMAT(s.start_date, '%D %M %Y')start_date, " +
                "DATE_FORMAT(s.updated_at, '%D %M %Y')updated_at " +
                "FROM semester s " +
                "ORDER BY s.created_at  DESC   " +
                "LIMIT 5";

        try{

            List<Semester> lastEditedList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Semester>() {
                        @Override
                        public Semester mapRow(ResultSet resultSet, int i) throws SQLException {
                            Semester semester = new Semester();
                            semester.setSemesterId(resultSet.getInt("semester_id"));
                            semester.setSemesterCode(resultSet.getString("semester_code"));
                            semester.setStartDate(resultSet.getString("start_date"));
                            semester.setUpdatedAt(resultSet.getString("updated_at"));
                            return semester;
                        }
                    }

            );
            return lastEditedList;


        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@SemesterDaoImpl::@getLastAddedSemestersList::EMPTY");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@SemesterDaoImpl::@getLastAddedSemestersList::ERROR");
            e.printStackTrace();
            return  null;
        }


    }

    @Override
    public List<Semester> getLastEditedSemesterList() {
        String sql = "SELECT " +
                "s.semester_id, " +
                "s.semester_code, " +
                "DATE_FORMAT(s.start_date, '%D %M %Y')start_date," +
                "DATE_FORMAT(s.updated_at, '%D %M %Y')updated_at " +
                "FROM semester s " +
                "ORDER BY s.updated_at DESC  " +
                "LIMIT 5";

        try{

            List<Semester> lastUpdatedList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Semester>() {
                        @Override
                        public Semester mapRow(ResultSet resultSet, int i) throws SQLException {
                            Semester semester = new Semester();
                            semester.setSemesterId(resultSet.getInt("semester_id"));
                            semester.setSemesterCode(resultSet.getString("semester_code"));
                            semester.setStartDate(resultSet.getString("start_date"));
                            semester.setUpdatedAt(resultSet.getString("updated_at"));
                            return semester;
                        }
                    }

            );
            return lastUpdatedList;


        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@SemesterDaoImpl::@getLastEditedSemesterList::EMPTY");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@SemesterDaoImpl::@getLastEditedSemesterList::ERROR");
            e.printStackTrace();
            return  null;
        }

    }

    @Override
    public List<Semester> getSystemSemesterList() {
        String  sql = "SELECT  " +
                "s.semester_id, " +
                "s.semester_code " +
                "FROM semester s " +
                "ORDER BY s.semester_code DESC";

        try{

            List<Semester> semesterList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Semester>() {
                        @Override
                        public Semester mapRow(ResultSet resultSet, int i) throws SQLException {
                            Semester semester = new Semester();
                            semester.setSemesterId(resultSet.getInt("semester_id"));
                            semester.setSemesterCode(resultSet.getString("semester_code"));
                            return semester;
                        }
                    }
            );
            return semesterList;

        }
        catch(EmptyResultDataAccessException e){
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }
}
