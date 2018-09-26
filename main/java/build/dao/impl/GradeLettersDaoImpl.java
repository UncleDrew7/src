package build.dao.impl;

import build.model.GradeLetters;
import build.dao.GradeLettersDao ;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Created by apple on 29/08/2017.
 */
public class GradeLettersDaoImpl extends JdbcDaoSupport implements GradeLettersDao{
    @Override
    public String addGradeLetters(GradeLetters gradeLetters) {
        String sql = "INSERT INTO grade_letters ( letter,highest ,lowest) VALUES( ?,?,? )";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{ gradeLetters.getLetter(),gradeLetters.getHighest(),gradeLetters.getLowest() }
        );
        if(1 == returnValue)
            return "QUERY SUCESS MESSAGE ";
        else
            return "QUERY FAILURE MESSAGE";
    }
}
