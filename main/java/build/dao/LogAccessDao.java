package build.dao;

import build.model.LogAccess;

import java.util.List;

/**
 * Created by apple on 15/08/2017.
 */
public interface LogAccessDao {

    public String createNewLogAccess(long userId);

    public String updateLogAccess(LogAccess logAccess);

    public String deleteLogAccess(LogAccess logAccess);

    public List<LogAccess> displayAccessLogs();
}
