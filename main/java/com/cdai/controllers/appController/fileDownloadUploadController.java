package com.cdai.controllers.appController;

import com.cdai.models.tempModels.FileModel;
import com.cdai.models.tempModels.FileUploadForm;
import com.cdai.util.fileDownloadUpload.MultiFileUpload;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by apple on 03/09/2017.
 */
@Controller
public class fileDownloadUploadController {

    private static final Logger logger = Logger.getLogger(fileDownloadUploadController.class);

    @Autowired
    ServletContext context;


    /**
     * FILE UPLOAD PAGE
     */
    @RequestMapping(value ="/fileUploadPage",method = RequestMethod.GET)
    public ModelAndView fileUploadPage(){
        FileModel file = new FileModel();
        ModelAndView modelAndView = new ModelAndView("test/fileUpload/fileUpload","command",file);
        return modelAndView;
    }


    /**
     * FILE UPLOAD PAGE HANDLE POST
     * https://www.tutorialspoint.com/springmvc/springmvc_upload.htm
     */
    @RequestMapping(value ="/fileUploadPage",method = RequestMethod.POST)
    public String fileUplaod(@Validated FileModel file , BindingResult result, ModelMap model)throws IOException{
        if(result.hasErrors()){
            logger.error("Validation Error");
            return "test/fileUpload/fileUpload";
        }else{
            logger.debug("Fetching File");
            MultipartFile multipartFile = file.getFile();
            String uploadPath = context.getRealPath("")+ File.separator + "temp"+ File.separator;
            //now do something with file
            FileCopyUtils.copy(file.getFile().getBytes(),new File(uploadPath+file.getFile().getOriginalFilename()));
            String fileName = multipartFile.getOriginalFilename();
            model.addAttribute("fileName",fileName);
            return"test/fileUpload/success";
        }
    }


    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String displayForm() {
        return "test/fileUpload/file_upload_form";
    }



    /**
     * SAVE UPLOADED FILE
     * http://viralpatel.net/blogs/spring-mvc-multiple-file-upload-example/
     */
    @RequestMapping(value ="/save",method = RequestMethod.POST)
    public String save(
            @ModelAttribute("uploadForm") FileUploadForm uploadForm, BindingResult result,
            ModelMap map){

        if (result.hasErrors()) {
            return "error";
        }

        List<MultipartFile> files = uploadForm.getFiles();
        List<String> fileNames = new ArrayList<String>();

        if(null != files && files.size() > 0) {
            for (MultipartFile multipartFile : files) {

                String fileName = multipartFile.getOriginalFilename();
                fileNames.add(fileName);
                //Handle file content - multipartFile.getInputStream()

            }
        }

        map.addAttribute("files", fileNames);
        return "test/fileUpload/file_upload_success";

    }


    /**
     * Upload single file using Spring Controller
     * http://www.journaldev.com/2573/spring-mvc-file-upload-example-single-multiple-files
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public @ResponseBody
    String uploadFileHandler(@RequestParam("name") String name,
                             @RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "uploads"+ File.separator +"appContent");
                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                logger.info("Server File Location="
                        + serverFile.getAbsolutePath());

                return "You successfully uploaded file=" + name;
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name
                    + " because the file was empty.";
        }
    }

    /**
     * Upload single file using Spring Controller
     * http://javahonk.com/spring-mvc-upload-multiple-file/
     */

    @RequestMapping(value = "/sendfile",
            method = RequestMethod.GET)
    public String printWelcome(ModelMap model, HttpServletRequest
            request, HttpServletResponse response) {

        model.addAttribute("message", "Spring MVC Multi File upload");
        return "test/fileUpload/dynamicUpload";

    }

    @RequestMapping(value = "/multiFileUpload.web",
            method = RequestMethod.POST)
    public String save(@ModelAttribute("multiFileUpload")
                               MultiFileUpload multiFileUpload, BindingResult bindingResult, Model model) throws IOException {

        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "uploads"+ File.separator +"appContent");
        if (!dir.exists())
            dir.mkdirs();

        List<MultipartFile> files = multiFileUpload
                .getMultiUploadedFileList();
        StringBuffer uploadedfile =
                new StringBuffer("Below files uploaded successfully");

        if (files.isEmpty() ) {
            bindingResult.reject("noFile", "Please select file to upload");
            return "test/fileUpload/dynamicUpload";
        }

        if (null != files && files.size() > 0) {


            for (MultipartFile multipartFile : files) {

                ByteArrayOutputStream baos =
                        new ByteArrayOutputStream();
                FileOutputStream output = null;
                File outputfile = null;
                String fileName = multipartFile.getOriginalFilename();
                uploadedfile.append("<h4>"+fileName+"</h4>");
                String extensionOfFileName = fileName.
                        substring(fileName.indexOf(".")+1,
                                fileName.length());
                outputfile = new File(dir.getAbsolutePath()+ File.separator +fileName);
                InputStream inputStream = multipartFile
                        .getInputStream();

                if (null!=extensionOfFileName &&
                        extensionOfFileName.equalsIgnoreCase("pdf")) {
                    if (!outputfile.isDirectory()) {
                        try {
                            output = new FileOutputStream(outputfile);
                            IOUtils.copy(inputStream, output);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            baos.close();
                            output.close();

                        }
                    }
                } else if (null != extensionOfFileName
                        && extensionOfFileName
                        .equalsIgnoreCase("png")) {
                    BufferedImage imBuff = ImageIO.read(multipartFile
                            .getInputStream());
                    ImageIO.write(imBuff, extensionOfFileName,
                            outputfile);
                } else if (null != extensionOfFileName
                        && extensionOfFileName
                        .equalsIgnoreCase("jpg")) {
                    BufferedImage imBuff = ImageIO
                            .read(multipartFile.getInputStream());
                    ImageIO.write(imBuff, extensionOfFileName,
                            outputfile);
                }else {
                    System.out.println("Unknown file extension"
                            +extensionOfFileName);
                }
                logger.info("Server File Location="
                        + outputfile.getAbsolutePath());

            }
        }

        model.addAttribute("message", uploadedfile);
        return "test/fileUpload/Upload";
    }

    /**
     * RETURN DOWNLOAD VIEW PAGE
     * http://localhost:8080/fileUploadPage
     * http://www.technicalkeeda.com/spring-tutorials/how-to-download-file-using-spring-mvc
     */

    @RequestMapping(value = "/download/pdf/{fileName:.+}", method = RequestMethod.GET)
    public void download(HttpServletRequest request,HttpServletResponse response,
                         @PathVariable("fileName") String fileName){

        logger.debug("Downloding File:- "+ fileName);

        String downloadFolder = context.getRealPath("/WEB-INF/downloads/");
        Path file = Paths.get(downloadFolder,fileName);
        //check if file exists
        if(Files.exists(file)){
            //set content type
            response.setContentType("application/pdf");
            //add response header
            response.addHeader("Content-Disposition","attachment; filename=" + fileName);
            try{
                //copies all bytes from a file to an output stream
                Files.copy(file,response.getOutputStream());
                //flushes output stream
                response.getOutputStream().flush();
            }catch (IOException e){
                logger.error("Error! :- "+ e.getMessage());
            }
        }else{
            logger.error("Sorry File not Found!");
        }

    }

    /**
     * DOWNLOAD PDF FILE
     * https://howtodoinjava.com/spring/spring-mvc/spring-mvc-download-file-controller-example/
     */

    @RequestMapping(value = "/pdf/{fileName:.+}")
    public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response,
                                    @PathVariable("fileName") String fileName,
                                    @RequestHeader String referer){
        //Check the renderer
        if(referer != null && !referer.isEmpty()){
            //do nothing
            //or send error
        }

        // if  user is not authorized -  he should br thrown out from here itself
        //Authorized user will download the file
        String dataDirectory = context.getRealPath("/WEB-INF/downloads/pdf/");
        Path file =  Paths.get(dataDirectory,fileName);
        if(Files.exists(file)){
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename="+fileName);

            try
            {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * DOWNLOAD JAR FILE
     * http://javahonk.com/spring-mvc-download-files/
     */

    @RequestMapping(value = "/downloadFiles.web",method = RequestMethod.GET)
    public @ResponseBody void downloadFiles(HttpServletRequest request, HttpServletResponse response){

        String downloadPath = context.getRealPath("/WEB-INF/downloads/");
        File downloadFile = new File(downloadPath);
        FileInputStream inputStream = null;
        OutputStream outStream = null;

        try{
            inputStream = new FileInputStream(downloadFile);

            // response header
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"",downloadFile.getName());
            response.setHeader(headerKey, headerValue);

            // Write response
            outStream = response.getOutputStream();
            IOUtils.copy(inputStream, outStream);

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (null != inputStream)
                    inputStream.close();
                if (null != inputStream)
                    outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    }
//	<a href="downloadFiles.web">Click and download file here</a>
//</body>
//>>http://www.codejava.net/frameworks/spring/spring-mvc-sample-application-for-downloading-files
//>>http://memorynotfound.com/spring-mvc-download-file-examples/





}
