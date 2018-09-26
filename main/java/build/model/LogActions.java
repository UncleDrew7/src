package build.model;

import java.util.Date;

/**
 * Created by apple on 29/08/2017.
 * WINNI
 */
public class LogActions {

    private int id;
    private String logAction;

    public LogActions(){
        super();
    }

    public LogActions(String logAction) {
        this.logAction = logAction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogAction() {
        return logAction;
    }

    public void setLogAction(String logAction) {
        this.logAction = logAction;
    }
}
