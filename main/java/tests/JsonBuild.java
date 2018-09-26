package tests;

import build.dao.EventsDao;
import build.model.Events;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSON;
 import com.cdai.models.calender.CalenderEvents;


/**
 * Created by apple on 22/09/2017.
 */
public class JsonBuild {

    private static final transient Logger logger = LoggerFactory.getLogger(JsonBuild.class);

    public static void main(String[] args){
        run();
    }

    public static void run(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
        EventsDao daoAccess = (EventsDao) applicationContext.getBean("EventsDao");

        List<Events> eventsList = daoAccess.displayAdminCalenderEvent(904);



        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();


        String jsonOutput = JSON.toJSONString(eventsList);
        logger.info(jsonOutput);


    }
}
