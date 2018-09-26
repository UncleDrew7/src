package build.dao.impl;

import build.model.Log ;
import build.dao.LogDao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Created by apple on 29/08/2017.
 */
public class LogDaoImpl extends JdbcDaoSupport implements LogDao{
    @Override
    public String addLog(Log log) {

        String sql = "INSERT INTO  log (name ) VALUES( ? )";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{ log.getName() }
        );
        if(1 == returnValue)
            return "QUERY SUCESS MESSAGE ";
        else
            return "QUERY FAILURE MESSAGE";
    }
}
