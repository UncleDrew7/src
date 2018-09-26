package build.dao.impl;

import build.model.Class;
import build.dao.ClassDao;
import build.model.User;
import build.row_mapper.ClassRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Created by apple on 15/08/2017.
 */
public class ClassDaoImpl extends JdbcDaoSupport implements ClassDao{


    @Override
    public int getLargestClassId() {

        String sql = "SELECT  " +
                "MAX(c.class_id) " +
                "FROM class c";
        try{
            int id = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return id;

        } catch (EmptyResultDataAccessException e) {
            return 0;
        }catch (Exception e ){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public String createNewClass(Class newClass) {


        String sql = "INSERT INTO class (class_name,intake_period,major_id, created_by)VALUES(?,?,?,?) ";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{
                        newClass.getClassName(),
                        newClass.getIntakePeriod(),
                        newClass.getMajorId(),
                        newClass.getCreatedBy()
                }
        );
        if(1 == returnValue)
            return "@classDaoImpl::@createNewClass:::# New Class>>> "+newClass.getClassName()+" CREATED SUCCESSFULLY ";
        else
            return "@classDaoImpl::@createNewClass:::# CLASS CREATION FAILURE";

    }

    @Override
    public String deleteClassInClassTableByClassId(int classId) {
        String sql = "DELETE FROM class" +
                    " WHERE class.class_id = ?";

        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{classId}
        );
        if(1 == returnValue)
            return "@classDaoImpl::@createNewClass:::# CLASS DELETED";
        else
            return "@classDaoImpl::@createNewClass:::#ERROR!!! CANT DELETE CLASS";

    }

    @Override
    public String deleteClassInClassTableByClassName(String className) {
        String sql = "DELETE FROM class " +
                    " WHERE class.class_name = ? ";

        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{className}
            );
            if(1 == returnValue)
                return "@classDaoImpl::@createNewClass:::# CLASS DELETED";
            else
                return "@classDaoImpl::@createNewClass:::#ERROR!!! CANT DELETE CLASS|| NO CLASS BY THAT NAME";
        }catch (Exception e){
            e.printStackTrace();
            return "ADD ERROR!!";
        }
    }


    @Override
    public String updateExistingClass(Class existingClass) {
        String sql = "UPDATE class SET  " +
                "class_name = ? , " +
                "intake_period =  ?, " +
                "status =  ? " +
                "WHERE class_id =  ?";

        try{
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{existingClass.getClassName(),existingClass.getIntakePeriod(),existingClass.getStatus(),existingClass.getId()}
            );

            return "CLASS EDITED SUCCESSFULLY ";

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("ERROR!!");
            return "UPDATE ERROR!!";
        }
    }

    @Override
    public String deleteExistingClass(int classId) {

        String sql = "DELETE FROM class  WHERE class_id =  ?";
        try{

            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{classId}
            );
            if(1 == returnValue)
            {
                System.out.println("@::Class Successfully deleted From :: Class Table :: 1");
                return "200";
            }
            else
            {
                System.out.println("@::More Than One Instance Of This Class Id Was Found And deleted From :: Class :: 1");
                return "200";
            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("@:: System Error While trying to delete Class From :: Class :: 1");
            return "400";
        }
    }

    @Override
    public String deleteExistingClassStudents(int classId) {
        String sql = "DELETE FROM student_class WHERE class_id =  ?";

        try{

            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{classId}
            );
            if(1 == returnValue)
            {
                System.out.println("@::Class Students Successfully deleted From ::Student_Class :: 2");
                return "200";
            }
            else
            {
                System.out.println("@::More Than One Instance Of This Class Id Was Found And deleted From ::Student_Class :: 2");
                return "200";
            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("@:: System Error While trying to delete Class From ::Student_Class :: 2");
            return "400";
        }
    }

    @Override
    public String deleteClassMajor(int classId) {

        String sql = "DELETE FROM class_major WHERE class_id =  ?";

        try{

            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{classId}
            );
            if(1 == returnValue)
            {
                System.out.println("@::Class Successfully deleted From ::Class_Major :: 3");
                return "200";
            }
            else
            {
                System.out.println("@::More Than One Instance Of This Class Id Was Found And deleted From ::Class_Major :: 3");
                return "200";
            }

        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("@:: System Error While trying to delete Class From ::Class_Major :: 3");
            return "400";
        }
    }

    @Override
    public List<User> getClassStudentList() {
        String sql = "SELECT  " +
                "u.user_id, " +
                "u.user_name, " +
                "up.degree_type, " +
                "c.class_id, " +
                "c.class_name " +
                "FROM USER u " +
                "LEFT JOIN user_profile up ON up.user_id = u.user_id " +
                "LEFT JOIN student_class sc ON sc.student_id = u.user_id " +
                "LEFT JOIN class c ON c.class_id = sc.class_id " +
                "WHERE c.class_id = ? " +
                "ORDER BY u.user_id";
        try{
            List<User> list = getJdbcTemplate().query(
                    sql,
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {
                            User student = new User();
                            student.setUserId(resultSet.getLong("user_id"));
                            student.setUserName(resultSet.getString("user_name"));
                            student.setDegreeType(resultSet.getString("degree_type"));
                            student.setClassId(resultSet.getInt("class_id"));
                            student.setClassName(resultSet.getString("class_name"));

                            return student;
                        }
                    }

            );
            return list;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("EMPTY QUERY");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ERROR!!");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public List<Class> displayExistingClasses() {
        return null;
    }

    @Override
    public List<Class> getAllSystemClasses() {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_short_code, " +
                "m.major_name, " +
                "cl.class_id, " +
                "cl.class_name, " +
                "cl.intake_period, " +
                "cl.status, " +
                "cl.created_by, " +
                "(SELECT COUNT(*) FROM USER scl WHERE scl.class_id = cl.class_id)noOfStudents " +
                "FROM class cl " +
            //    "JOIN class_major cm ON cm.class_id = cl.class_id " +
                "JOIN major m ON m.major_id = cl.major_id " +
                "ORDER BY cl.created_at DESC , cl.intake_period DESC , cl.class_name";

        try{

            List<Class> allClassesList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Class>() {
                        @Override
                        public Class mapRow(ResultSet resultSet, int i) throws SQLException {

                            Class newClass  = new Class();
                            newClass.setMajorId(resultSet.getInt("major_id"));
                            newClass.setMajorName(resultSet.getString("major_name"));
                            newClass.setMajorShortName(resultSet.getString("major_short_code"));
                            newClass.setId(resultSet.getInt("class_id"));
                            newClass.setClassName(resultSet.getString("class_name"));
                            newClass.setIntakePeriod(resultSet.getInt("intake_period"));
                            newClass.setStatus(resultSet.getString("status"));
                            newClass.setCreatedBy(resultSet.getLong("created_by"));
                            newClass.setNumberOfStudents(resultSet.getInt("noOfStudents"));

                            return newClass;
                        }
                    }
            );
            return allClassesList;


        }
        catch(EmptyResultDataAccessException e){
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Class> getAllSystemClassesFilteredByIntake(String intake) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_short_code, " +
                "m.major_name, " +
                "cl.class_id, " +
                "cl.class_name, " +
                "cl.intake_period, " +
                "cl.status, " +
                "cl.created_by, " +
                "(SELECT COUNT(*) FROM student_class scl WHERE cl.class_id = scl.class_id)noOfStudents " +
                "FROM class cl " +
             //   "JOIN class_major cm ON cm.class_id = cl.class_id " +
                "JOIN major m ON m.major_id = cl.major_id  " +
                "WHERE cl.intake_period = ?  " +
                "ORDER BY cl.created_at DESC , cl.intake_period DESC , cl.class_name";

        try{

            List<Class> allClassesList = getJdbcTemplate().query(
                    sql,
                    new Object[]{intake},
                    new RowMapper<Class>() {
                        @Override
                        public Class mapRow(ResultSet resultSet, int i) throws SQLException {

                            Class newClass  = new Class();
                            newClass.setMajorId(resultSet.getInt("major_id"));
                            newClass.setMajorName(resultSet.getString("major_name"));
                            newClass.setMajorShortName(resultSet.getString("major_short_code"));
                            newClass.setId(resultSet.getInt("class_id"));
                            newClass.setClassName(resultSet.getString("class_name"));
                            newClass.setIntakePeriod(resultSet.getInt("intake_period"));
                            newClass.setStatus(resultSet.getString("status"));
                            newClass.setCreatedBy(resultSet.getLong("created_by"));
                            newClass.setNumberOfStudents(resultSet.getInt("noOfStudents"));

                            return newClass;
                        }
                    }
            );
            return allClassesList;


        }
        catch(EmptyResultDataAccessException e){
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Class> getAllSystemClassesFilteredByMajor(int majorId) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_short_code, " +
                "m.major_name, " +
                "cl.class_id, " +
                "cl.class_name, " +
                "cl.intake_period, " +
                "cl.status, " +
                "cl.created_by, " +
                "(SELECT COUNT(*) FROM student_class scl WHERE cl.class_id = scl.class_id)noOfStudents " +
                "FROM class cl " +
             //   "JOIN class_major cm ON cm.class_id = cl.class_id " +
                "JOIN major m ON m.major_id = cl.major_id  " +
                "WHERE m.major_id = ?  " +
                "ORDER BY cl.created_at DESC , cl.intake_period DESC , cl.class_name";

        try{

            List<Class> allClassesList = getJdbcTemplate().query(
                    sql,
                    new Object[]{majorId},
                    new RowMapper<Class>() {
                        @Override
                        public Class mapRow(ResultSet resultSet, int i) throws SQLException {

                            Class newClass  = new Class();
                            newClass.setMajorId(resultSet.getInt("major_id"));
                            newClass.setMajorName(resultSet.getString("major_name"));
                            newClass.setMajorShortName(resultSet.getString("major_short_code"));
                            newClass.setId(resultSet.getInt("class_id"));
                            newClass.setClassName(resultSet.getString("class_name"));
                            newClass.setIntakePeriod(resultSet.getInt("intake_period"));
                            newClass.setStatus(resultSet.getString("status"));
                            newClass.setCreatedBy(resultSet.getLong("created_by"));
                            newClass.setNumberOfStudents(resultSet.getInt("noOfStudents"));

                            return newClass;
                        }
                    }
            );
            return allClassesList;


        }
        catch(EmptyResultDataAccessException e){
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Class> getAllSystemClassesFilteredByMajorAndIntake(int majorId, String intake) {
        String sql = "SELECT  " +
                "m.major_id, " +
                "m.major_short_code, " +
                "m.major_name, " +
                "cl.class_id, " +
                "cl.class_name, " +
                "cl.intake_period, " +
                "cl.status, " +
                "cl.created_by, " +
                "(SELECT COUNT(*) FROM student_class scl WHERE cl.class_id = scl.class_id)noOfStudents " +
                "FROM class cl " +
              //  "JOIN class_major cm ON cm.class_id = cl.class_id " +
                "JOIN major m ON m.major_id = cl.major_id  " +
                "WHERE m.major_id = ?  " +
                "AND cl.intake_period = ?  " +
                "ORDER BY cl.created_at DESC , cl.intake_period DESC , cl.class_name";

        try{

            List<Class> allClassesList = getJdbcTemplate().query(
                    sql,
                    new Object[]{majorId, intake},
                    new RowMapper<Class>() {
                        @Override
                        public Class mapRow(ResultSet resultSet, int i) throws SQLException {

                            Class newClass  = new Class();
                            newClass.setMajorId(resultSet.getInt("major_id"));
                            newClass.setMajorName(resultSet.getString("major_name"));
                            newClass.setMajorShortName(resultSet.getString("major_short_code"));
                            newClass.setId(resultSet.getInt("class_id"));
                            newClass.setClassName(resultSet.getString("class_name"));
                            newClass.setIntakePeriod(resultSet.getInt("intake_period"));
                            newClass.setStatus(resultSet.getString("status"));
                            newClass.setCreatedBy(resultSet.getLong("created_by"));
                            newClass.setNumberOfStudents(resultSet.getInt("noOfStudents"));

                            return newClass;
                        }
                    }
            );
            return allClassesList;


        }
        catch(EmptyResultDataAccessException e){
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getActiveSystemClassesCount() {
        String sql = "SELECT COUNT(*)" +
                "    FROM class cl where cl.status = 'active'";

        try{

            int activeSystemClassesCount = getJdbcTemplate().queryForObject(
                    sql,
                    Integer.class
            );
            return activeSystemClassesCount;

        }
        catch(EmptyResultDataAccessException e){
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public List<User> getClassListByClassId(int classId) {
        String sql = "SELECT  " +
                "u.user_id, " +
                "u.user_name, " +
                "DATE_FORMAT(u.created_at, '%D %M %Y') enrolledDate " +
                "FROM user u  " +
                "WHERE u.status = 'active' " +
                "AND u.class_id= ?";

        try{

            List<User> list = getJdbcTemplate().query(
                    sql,
                    new Object[]{classId},
                    new RowMapper<User>() {
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {
                            User user = new User();
                            user.setUserId(resultSet.getLong("user_id"));
                            user.setUserName(resultSet.getString("user_name"));
                            user.setEnrolmentDate(resultSet.getString("enrolledDate"));
                            return user;
                        }
                    }

            );
            return list;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ClassDaoImpl::@getClassListByClassId::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ClassDaoImpl::@getClassListByClassId::ERROR !");
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public Class getSingleClassDetailsByClassId(int classId) {
        String sql = "SELECT   " +
                "m.major_id, " +
                "m.major_short_code, " +
                "m.major_name, " +
                "c.class_id, " +
                "c.class_name,  " +
                "c.intake_period, " +
                "c.status, " +
                "DATE_FORMAT(c.created_at, '%D %M %Y') createdDate  " +
                "FROM class c  " +
           //     "LEFT JOIN class_major cm ON cm.class_id = c.class_id " +
                "LEFT JOIN major m ON m.major_id = c.major_id " +
                "WHERE c.class_id =   ? ";

        try{

            Class object = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{classId},
                    new RowMapper<Class>() {
                        @Override
                        public Class mapRow(ResultSet resultSet, int i) throws SQLException {
                            Class selectedClass = new Class();

                            selectedClass.setMajorId(resultSet.getInt("major_id"));
                            selectedClass.setMajorShortName(resultSet.getString("major_short_code"));
                            selectedClass.setMajorName(resultSet.getString("major_name"));
                            selectedClass.setId(resultSet.getInt("class_id"));
                            selectedClass.setClassName(resultSet.getString("class_name"));
                            selectedClass.setIntakePeriod(resultSet.getInt("intake_period"));
                            selectedClass.setStatus(resultSet.getString("status"));
                            selectedClass.setCreatedAt(resultSet.getString("createdDate"));

                            return  selectedClass;
                        }
                    }
            );
            return object;



        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ClassDaoImpl::@getSingleClassDetailsByClassId::EMPTY !");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ClassDaoImpl::@getSingleClassDetailsByClassId::ERROR !");
            e.printStackTrace();
            return  null;
        }


    }

    @Override
    public String enrollStudentInClass(int classId, long userId) {
     //   String sql = "INSERT INTO student_class(class_id,student_id)VALUES(?,?) ";
        String sql = "UPDATE USER SET class_id = ? WHERE user_id = ? ";

        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ classId,userId}
            );
            if(1 == returnValue){
                System.out.println("@ClassDaoImpl::@enrollStudentInClass:: SUCCEFULLY ENROLLED ==>"+userId);
                return "ENROLLMENT SUCESS MESSAGE ";
            }
            else
                return "ENROLLMENT FAILURE MESSAGE";

        }catch (Exception e){
            System.out.println("@ClassDaoImpl::@enrollStudentInClass::ERROR!!!");
            e.printStackTrace();
            return "ENROLLMENT FAILURE MESSAGE";
        }

    }

    @Override
    public String deleteStudentFromClass(int classId, long userId) {
      //  String sql = "DELETE FROM student_class WHERE class_id = ? AND student_id = ?";
        String sql = "UPDATE USER SET  class_id = NULL WHERE user_id = ?";


        try {
            int returnValue = getJdbcTemplate().update(
                    sql,
                    new Object[]{ userId }
            );
            if(1 == returnValue){
                System.out.println("@ClassDaoImpl::@enrollStudentInClass:: SUCCEFULLY DELETED ==>"+userId);
                return "DELETE SUCESS MESSAGE ";
            }
            else
                return "DELETE FAILURE MESSAGE";

        }catch (Exception e){
            System.out.println("@ClassDaoImpl::@enrollStudentInClass::ERROR!!!");
            e.printStackTrace();
            return "DELETE FAILURE MESSAGE";
        }
    }

    @Override
    public boolean checkIfStudentBelongsToClassInSystem(long userId) {
        String sql = "SELECT sc.id " +
                "FROM student_class sc " +
                "WHERE sc.student_id =  ?";

        try{
            int val = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId},
                    Integer.class
            );
            return true;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ClassDaoImpl::@checkIfStudentBelongsToClassInSystem::::EMPTY !");
            return false;
        }catch (Exception e){
            System.out.println("@ClassDaoImpl::@checkIfStudentBelongsToClassInSystem::ERROR !");
            e.printStackTrace();
            return  false;
        }

    }

    @Override
    public boolean checkIfClassNameAlreadyInSystem(String className) {
        String sql = "SELECT  " +
                "c.class_id " +
                "FROM class c " +
                "WHERE c.class_name = ?";

        try{
            getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{className},
                    Integer.class
            );
            return true;

        } catch (EmptyResultDataAccessException e) {
            return false;
        }catch (Exception e ){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getStudentClassByUserId(long userId) {
        String sql  = "SELECT  " +
                "c.class_name " +
                "FROM student_class sc " +
                "LEFT JOIN class c ON c.class_id = sc.class_id " +
                "WHERE sc.student_id =  ?";

        try{
            String  val = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{userId},
                    String.class
            );
            return val;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ClassDaoImpl::@getStudentClassByUserId::::EMPTY !");
            return "";
        }catch (Exception e){
            System.out.println("@ClassDaoImpl::@getStudentClassByUserId::ERROR !");
            e.printStackTrace();
            return  "";
        }


    }

    @Override
    public int getCountOfTotalEnrolledStudentsInClass(int classId) {
        String sql = "SELECT COUNT(*) " +
                "FROM user sc " +
                "WHERE sc.class_id =  ?";

        try{
            int val = getJdbcTemplate().queryForObject(
                    sql,
                    new Object[]{classId},
                    Integer.class
            );
            return val;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("@ClassDaoImpl::@getCountOfTotalEnrolledStudentsInClass::::EMPTY !");
            return 0;
        }catch (Exception e){
            System.out.println("@ClassDaoImpl::@getCountOfTotalEnrolledStudentsInClass::ERROR !");
            e.printStackTrace();
            return  0;
        }



    }

    @Override
    public List<Class> getLastAddedClasses() {
        String sql  = "SELECT  " +
                "c.class_id, " +
                "c.class_name, " +
                "c.intake_period " +
                "FROM class c " +
                "ORDER BY c.created_at DESC  " +
                "LIMIT 5";

        try{

            List<Class> classList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Class>() {
                        @Override
                        public Class mapRow(ResultSet resultSet, int i) throws SQLException {
                            Class classList = new Class();
                            classList.setId(resultSet.getInt("class_id"));
                            classList.setClassName(resultSet.getString("class_name"));
                            classList.setIntakePeriod(resultSet.getInt("intake_period"));
                            return classList;
                        }
                    }

            );
            return classList;

        }
        catch(EmptyResultDataAccessException e){
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }

    }

    @Override
    public List<Class> getAllIntakeNamesSelectList() {
        String sql = "SELECT  " +
                "distinct c.intake_period " +
                "FROM class c " +
                "ORDER BY c.intake_period DESC ";
        try{

            List<Class> classList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Class>() {
                        @Override
                        public Class mapRow(ResultSet resultSet, int i) throws SQLException {
                            Class classList = new Class();
//                            classList.setId(resultSet.getInt("class_id"));
//                            classList.setClassName(resultSet.getString("class_name"));
                            classList.setIntakePeriod(resultSet.getInt("intake_period"));
                            return classList;
                        }
                    }

            );
            return classList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("EMPTY QUERY");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ERROR!!");
            e.printStackTrace();
            return  null;
        }
    }



    @Override
    public List<Class> getAllClassesNamesSelectList() {
        String sql = "SELECT  " +
                "c.class_id, " +
                "c.class_name, " +
                "c.intake_period," +
                "m.major_id," +
                "m.major_name," +
                "m.major_short_code " +
                "FROM class c, major m WHERE c.major_id = m.major_id " +
                "ORDER BY c.intake_period DESC ";
        try{

            List<Class> classList = getJdbcTemplate().query(
                    sql,
                    new RowMapper<Class>() {
                        @Override
                        public Class mapRow(ResultSet resultSet, int i) throws SQLException {
                            Class classList = new Class();
                            classList.setId(resultSet.getInt("class_id"));
                            classList.setClassName(resultSet.getString("class_name"));
                            classList.setIntakePeriod(resultSet.getInt("intake_period"));
                            classList.setMajorId(resultSet.getInt("major_id"));
                            classList.setMajorName(resultSet.getString("major_name"));
                            classList.setMajorShortName(resultSet.getString("major_short_code"));

                            return classList;
                        }
                    }

            );
            return classList;

        }
        catch(EmptyResultDataAccessException e){
            System.out.println("EMPTY QUERY");
            e.printStackTrace();
            return null;
        }catch (Exception e){
            System.out.println("@ERROR!!");
            e.printStackTrace();
            return  null;
        }
    }
}
