package build.dao.impl;


import build.model.LogAccess;
import build.dao.LogAccessDao;
import build.row_mapper.LogAccessRowMapper;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
/**
 * Created by apple on 15/08/2017.
 */
public class LogAccessDaoImpl extends JdbcDaoSupport implements LogAccessDao{
    @Override
    public String createNewLogAccess(long userId) {
        String sql = "INSERT INTO log_access (user_id)VALUES(?) ";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{userId}
        );

        if(1 == returnValue)
            return "# User Logged In Logging Access ";
        else
            return "# LOG CREATION FAILURE";
    }

    @Override
    public String updateLogAccess(LogAccess logAccess) {
        return null;
    }

    @Override
    public String deleteLogAccess(LogAccess logAccess) {
        return null;
    }

    @Override
    public List<LogAccess> displayAccessLogs() {
        return null;
    }
}

