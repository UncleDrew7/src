package com.cdai.controllers.appController;

import com.cdai.models.tempModels.FileModel;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by apple on 21/11/2017.
 */

@Controller
public class DownloadController {
    private static final Logger logger = Logger.getLogger(DownloadController.class);

    @Autowired
    ServletContext context;

    /**
     * IMAGE DOWNLOAD CONTROLLER
     */


    @RequestMapping("/download/images/{imageName}.{ext}")
    public ResponseEntity<byte[]> downloadImages(@PathVariable String imageName,@PathVariable String ext ) throws IOException {
        String path="C:\\$Winni_Cloud_Local\\Cdai_Uploads\\images";
//        String path = "/fileserver/images";
        File file=new File(path+ File.separator + imageName+"."+ext);
        HttpHeaders headers = new HttpHeaders();
        String fileName=new String("defaultImage.jpg".getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.IMAGE_PNG);
       // headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.OK);
    }


    /**
     * DOWNLOAD SERVER FILES
     */
    @RequestMapping("/download/docs/{docName}.{ext}")
    public ResponseEntity<byte[]> downloadDocs(@PathVariable String docName,@PathVariable String ext ,HttpServletResponse response) throws IOException {
        String path="C:\\$Winni_Cloud_Local\\Cdai_Uploads\\Docs";
//        String path = "/fileserver/docs";
        File file=new File(path+ File.separator + docName+"."+ext);
        HttpHeaders headers = new HttpHeaders();
        String finalName = docName+"."+ext;
        String fileName=new String(finalName.getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.OK);
    }


    /**
     * VIEW SERVER FILES IN BROWSER
     */
    @RequestMapping("/view/docs/{docName}.{ext}")
    void getFile(HttpServletResponse response,@PathVariable String docName,@PathVariable String ext ) throws IOException {
        String path="C:\\$Winni_Cloud_Local\\Cdai_Uploads\\Docs";

        File file=new File(path+ File.separator + docName+"."+ext);
        String fileName =  docName+"."+ext;

        FileInputStream inputStream = new FileInputStream(file);
        response.setContentType("application/pdf");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "inline;filename=\"" + fileName + "\"");

        FileCopyUtils.copy(inputStream, response.getOutputStream());

    }

    /**
     * DOWNLOAD NEWS NOTIFICATION UPLOADS
     */
    @RequestMapping("/download/new-notifications-items/{docName}.{ext}")
    public ResponseEntity<byte[]> downloadNewsNotificationItems(@PathVariable String docName,@PathVariable String ext ,HttpServletResponse response) throws IOException {
        String path="C:\\Java\\apache-tomcat-8.5.16\\uploads\\news_notifications_uploads";
        File file=new File(path+ File.separator + docName+"."+ext);
       HttpHeaders headers = new HttpHeaders();
        String finalName = docName+"."+ext;
        String fileName=new String(finalName.getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.OK);
    }



}
