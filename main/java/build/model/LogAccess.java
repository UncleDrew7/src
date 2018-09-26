package build.model;

/**
 * Created by apple on 15/08/2017.
 */
public class LogAccess {
    private int id;
    private long user_id;
    private String loginDatetime ;
    private String logoutDatetime;

    public LogAccess() {
        super();
    }

    public LogAccess(long user_id, String loginDatetime, String logoutDatetime) {
        this.user_id = user_id;
        this.loginDatetime = loginDatetime;
        this.logoutDatetime = logoutDatetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getLoginDatetime() {
        return loginDatetime;
    }

    public void setLoginDatetime(String loginDatetime) {
        this.loginDatetime = loginDatetime;
    }

    public String getLogoutDatetime() {
        return logoutDatetime;
    }

    public void setLogoutDatetime(String logoutDatetime) {
        this.logoutDatetime = logoutDatetime;
    }
}
