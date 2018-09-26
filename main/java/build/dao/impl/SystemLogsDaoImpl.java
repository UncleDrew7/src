package build.dao.impl;

import build.model.SystemLogs;
import build.dao.SystemLogsDao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Created by apple on 30/08/2017.
 */
public class SystemLogsDaoImpl extends JdbcDaoSupport implements SystemLogsDao{
    @Override
    public String addSystemLogs(SystemLogs systemLogs) {
        String sql = "INSERT INTO  system_logs ( user_name,ip_address,page_location,course,action,information,userType ) VALUES( ?,?,?,?,?,?,? )";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{systemLogs.getUsername() ,systemLogs.getIpAddress(),systemLogs.getPageLocation(),systemLogs.getCourse(),systemLogs.getAction(),systemLogs.getInformation(),systemLogs.getUserType() }
        );
        if(1 == returnValue)
            return "QUERY SUCESS MESSAGE ";
        else
            return "QUERY FAILURE MESSAGE";
    }
}
