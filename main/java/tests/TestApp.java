//package tests;
//
//import java.util.List;
//
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//// you need the run engine and the model you need to use and its respective dao run engine
//
////logging
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * Created by apple on 05/08/2017.
// */
//public class TestApp {
//
//    private static final transient Logger log = LoggerFactory.getLogger(TestApp.class);
//
//    public static void main(String [] args){
//
//        log.info("*****SHE HAS BEGAN*****");
//        ClassPathXmlApplicationContext context = new  ClassPathXmlApplicationContext("/config/spring_indexConfig.xml");
//
//        //now you have acess to the dao access and the database is now responsiive to call
//        // link has been set and turned on now u can use the database
//        UserDao userDao = context.getBean(UserDao.class);
//
//        //doa keep the pool of queries
//        // the model feries those queries to and from
//
//        UserModel user = new UserModel();
//        user.setName("Lani");
//        user.setPassword("newPassword");
//        user.setEmail("lani@nowatermountains.com");
//
//        // using the model feriy to set the quiery pool in the dao acess
//        //use the dao acess to save this pool back to the database
//        // the pool is the vertual databse when draied it goes back inthe hard database
//        // so to draine the pool just save the fery model carig the data to confim its reached the destination being the databes
//        userDao.save(user);
//        List<UserModel> userList = userDao.list();
//        log.info("User Data ===>>>"+ userList);
//
//        //then finally close the context usin the sprin.xml is the live connection which must be closed
//        context.close();
//        System.exit(0);
//
//
//
//    }
//}
