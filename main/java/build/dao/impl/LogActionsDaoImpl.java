package build.dao.impl;

import build.model.LogActions;
import build.dao.LogActionsDao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
/**
 * Created by apple on 29/08/2017.
 */
public class LogActionsDaoImpl extends JdbcDaoSupport implements LogActionsDao{
    @Override
    public String addLogActions(LogActions logActions) {

        String sql = "INSERT INTO log_actions ( log_action ) VALUES( ? )";
        int returnValue = getJdbcTemplate().update(
                sql,
                new Object[]{ logActions.getLogAction() }
        );
        if(1 == returnValue)
            return "QUERY SUCESS MESSAGE ";
        else
            return "QUERY FAILURE MESSAGE";
    }
}
