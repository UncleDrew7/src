package com.cdai.controllers.appController;

import build.dao.LessonUploadsDao;
import build.dao.NewsNotificationsUploadsDao;
import build.dao.UserProfileDao;
import build.model.LessonUploads;
import build.model.NewsNotificationsUploads;
import com.cdai.util.RandomGenerator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by apple on 24/11/2017.
 */
@Controller
public class UploadController {

    private static final Logger logger = Logger.getLogger(UploadController.class);
    private static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");


    @Autowired
    ServletContext context;

    /**
     * PDF FILE UPLOADER
     * max size error vists https://www.mkyong.com/spring/spring-file-upload-and-connection-reset-issue/
     */
    @RequestMapping(value = "/upload/docs/{courseId}/{lessonId}", method = RequestMethod.POST)
    public String uploadFileHandler(@RequestParam("name") String name,
                             @RequestParam("file") MultipartFile file,@PathVariable int courseId,@PathVariable int lessonId) {

        LessonUploadsDao daoAccess = (LessonUploadsDao) applicationContext.getBean("LessonUploadsDao");
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        RandomGenerator randomGenerator = new RandomGenerator();
        LessonUploads lessonUploads = null;

        String sysName = randomGenerator.getRandomString()+"."+extension;

        if(name.equals("") || name == null){
            lessonUploads = new LessonUploads(lessonId, courseId, file.getOriginalFilename(),sysName,extension, 1) ;
        }else{
            lessonUploads = new LessonUploads(lessonId, courseId, name,sysName,extension, 1) ;
        }


        System.out.println("@ContentType::"+file.getContentType());
      System.out.println("@OriginalName::"+file.getOriginalFilename());

        System.out.println(extension);


        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String path="C:\\$Winni_Cloud_Local\\Cdai_Uploads\\Docs";
                File dir = new File(path);
                if (!dir.exists())
                    dir.mkdirs();
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + sysName);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                String confirm = daoAccess.addLessonUploads(lessonUploads);

                logger.info(confirm);
                logger.info("Server File Location="
                        + serverFile.getAbsolutePath());

                logger.info("You successfully uploaded file=" + sysName);
                return "redirect:/admin/course/content/edit/"+courseId+"/"+lessonId+"?msg=200";
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("You failed to upload " + name + " => " + e.getMessage());
                return "redirect:/admin/course/content/edit/"+courseId+"/"+lessonId+"?msg=500";

            }
        } else {
            logger.info("You failed to upload " + name + " because the file was empty.");
            return "redirect:/admin/course/content/edit/"+courseId+"/"+lessonId+"?msg=510";
        }
    }

    /**
     * RESTORE DEFAULT IMAGE
     */
    @ResponseBody
    @RequestMapping(value = "/profile/image/restore_default", method = RequestMethod.POST)
    public String restoreDefaultImage() {
        UserProfileDao daoAccess = (UserProfileDao) applicationContext.getBean("UserProfileDao");
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");

        return daoAccess.changeUserProfilePic(currentUserId,"avatar.png");

    }

    /**
     * IMAGE UPLOADER
     */
    @RequestMapping(value = "/upload/image", method = RequestMethod.POST)
    public String uploadImageHandler(@RequestParam("file") MultipartFile file) {

        UserProfileDao daoAccess = (UserProfileDao) applicationContext.getBean("UserProfileDao");
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        long currentUserId = (Long) session.getAttribute("userId");
        String currentUserRole = (String) session.getAttribute("roleName");


        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        RandomGenerator randomGenerator = new RandomGenerator();

        String sysName = randomGenerator.getRandomString()+"."+extension;

        System.out.println("@ContentType::"+file.getContentType());
        System.out.println("@OriginalName::"+file.getOriginalFilename());

        System.out.println(extension);

        long userId= currentUserId;
        String oldImgUrl = daoAccess.getCurrentProfilePicUrl(userId);
        String newImgUrl = sysName;
        String path="C:\\$Winni_Cloud_Local\\Cdai_Uploads\\images";



        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                File dir = new File(path);
                if (!dir.exists())
                    dir.mkdirs();
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + sysName);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                String confirm = daoAccess.changeUserProfilePic(userId,newImgUrl);

                System.out.println(confirm);
                logger.info("Server File Location="
                        + serverFile.getAbsolutePath());

                logger.info("You successfully uploaded file=" + sysName);
                if(currentUserRole.equals("admin") || currentUserRole.equals("teacher")){
                    return "redirect:/admin/view/user-profile?msg=200";
                }else{
                    return "redirect:/student/profile?msg=200";
                }

            } catch (Exception e) {
                e.printStackTrace();
//                logger.info("You failed to upload " + name + " => " + e.getMessage());
                if(currentUserRole.equals("admin") || currentUserRole.equals("teacher")){
                    return "redirect:/admin/view/user-profile?msg=500";
                }else{
                    return "redirect:/student/profile?msg=500";
                }


            }
        } else {
//            logger.info("You failed to upload " + name + " because the file was empty.");
            if(currentUserRole.equals("admin") || currentUserRole.equals("teacher")){
                return "redirect:/admin/view/user-profile?msg=510";
            }else{
                return "redirect:/student/profile?msg=510";
            }
        }
    }

    /**
     * NEWS NOTIFICATION FILE UPLOADER
     */
    @RequestMapping(value = "/upload/news-notification-items/{newsNotificationId}", method = RequestMethod.POST)
    public String uploadNewsNotificationFileHandler(@RequestParam("name") String name,
                                    @RequestParam("file") MultipartFile file,@PathVariable int newsNotificationId) {

        NewsNotificationsUploadsDao daoAccess = (NewsNotificationsUploadsDao) applicationContext.getBean("NewsNotificationsUploadsDao");
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        RandomGenerator randomGenerator = new RandomGenerator();

        String sysName = randomGenerator.getRandomString()+"."+extension;
        String confirm = "";

        if(name.equals("") || name == null){
            confirm = daoAccess.addNewUploadInfo(new NewsNotificationsUploads(newsNotificationId,file.getOriginalFilename(),sysName,sysName,extension));
        }else{
            confirm = daoAccess.addNewUploadInfo(new NewsNotificationsUploads(newsNotificationId, name,sysName,sysName,extension));
        }

        System.out.println("@ContentType::"+file.getContentType());
        System.out.println("@OriginalName::"+file.getOriginalFilename());
        System.out.println(extension);


        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String path="C:\\Java\\apache-tomcat-8.5.16\\uploads\\news_notifications_uploads";
                File dir = new File(path);
                if (!dir.exists())
                    dir.mkdirs();
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + sysName);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                logger.info(confirm);
                logger.info("Server File Location="
                        + serverFile.getAbsolutePath());

                logger.info("You successfully uploaded file=" + sysName);
                return "redirect:/admin/publish?msg=200";
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("You failed to upload " + name + " => " + e.getMessage());
                return "redirect:/admin/publish?msg=500";

            }
        } else {
            logger.info("You failed to upload " + name + " because the file was empty.");
            return "redirect:/admin/publish?msg=510";
        }
    }

}
