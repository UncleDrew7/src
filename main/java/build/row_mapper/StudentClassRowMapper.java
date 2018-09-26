package build.row_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import build.model.StudentClass;
/**
 * Created by apple on 15/08/2017.
 */
public class StudentClassRowMapper implements RowMapper<StudentClass> {

    public StudentClass mapRow(ResultSet resultSet, int rowNumber)throws SQLException{

        StudentClass studentClass = new StudentClass();

        studentClass.setId(resultSet.getInt("id"));
        studentClass.setStudentId(resultSet.getInt("student_id"));
        studentClass.setClassId(resultSet.getInt("class_id"));
        studentClass.setCreatedAt(resultSet.getString("created_at"));

        return studentClass;
    }

}
