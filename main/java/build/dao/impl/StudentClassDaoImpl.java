package build.dao.impl;


import build.model.StudentClass;
import build.dao.StudentClassDao;
import build.row_mapper.StudentClassRowMapper;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
/**
 * Created by apple on 15/08/2017.
 */
public class StudentClassDaoImpl extends JdbcDaoSupport implements StudentClassDao{
    @Override
    public String createStudentClass(StudentClass studentClass) {
        String sql = "INSERT INTO student_class (student_id,class_id)VALUES(?,?) ";

        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{
                        studentClass.getStudentId(),
                        studentClass.getClassId()

                }
        );

        if(1 == returnValue)
        {
            System.out.println("# New StudentClass>>> "+studentClass.getStudentId()+"--"+studentClass.getClassId()+" CREATED SUCCESSFULLY ");
            return "200";
        }
        else
        {
            System.out.println("# CREATION FAILURE");
            return "400";
        }
    }

    @Override
    public String updateStudentClass(StudentClass studentClass) {
        return null;
    }

    @Override
    public String deleteStudentClass(StudentClass studentClass) {
        return null;
    }

    @Override
    public List<StudentClass> displayAllStudentClass() {
        return null;
    }
}

