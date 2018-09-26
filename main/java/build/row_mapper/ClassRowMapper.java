package build.row_mapper;

import java.sql.ResultSet;
import java.sql.SQLException;



import build.model.Class;
import org.springframework.jdbc.core.RowMapper;

/**
 * Created by apple on 15/08/2017.
 */
public class ClassRowMapper implements RowMapper<Class> {

    public Class mapRow(ResultSet resultSet, int rowNumber)throws SQLException{

        Class newClass = new Class();

        newClass.setId(resultSet.getInt("id"));
        newClass.setClassName(resultSet.getString("class_name"));
        newClass.setIntakePeriod(resultSet.getInt("intake_period"));
        newClass.setStatus(resultSet.getString("status"));
        newClass.setCreatedAt(resultSet.getString("created_at"));
        newClass.setDeletedAt(resultSet.getString("deleted_at"));

        return newClass;
    }

}
