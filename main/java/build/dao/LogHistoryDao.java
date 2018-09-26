package build.dao;
import build.model.LogHistory;

import java.util.List;

/**
 * Created by apple on 29/08/2017.
 */
public interface LogHistoryDao {

    public String addLogHistory(LogHistory logHistory);

    public String logUser(LogHistory log);

    //ADMIN REPORTS
    public List<LogHistory> getAllSystemLogs();

    //ADMIN DASHBOARD TIMELINES FOR ADMIN
    public List<LogHistory> getMostRecentLoggedInUserTimeline(long userId,int limit);

    //ADMIN DASHBOARD TIMELINES FOR ALL SYSTEM USERS
    public List<LogHistory> getMostRecentAllSystemUserTimeline(long userId,int limit);

    //ADMIN DASHBOARD TIMELINES FOR TEACHERS
    public List<LogHistory> getMostRecentSystemUserTeacherTimeline(long userId,int limit);

    //ADMIN DASHBOARD TIMELINES FOR STUDENTS
    public List<LogHistory> getMostRecentSystemUserStudentTimeline(long userId,int limit);


}
