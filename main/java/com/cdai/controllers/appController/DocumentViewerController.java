package com.cdai.controllers.appController;

import com.cdai.models.testModels.Employee;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class DocumentViewerController {

    private static final Logger logger = Logger.getLogger(DocumentViewerController.class);
    private static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");


    /**
     * PDF VIEWER CONTROLLER
     */
    @RequestMapping(value = "/generate/pdf.htm",method = RequestMethod.GET)
    ModelAndView generatePdf(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        System.out.println("Calling generatePdf()...");

        Employee employee = new Employee();
        employee.setFirstName("Yashwant");
        employee.setLastName("Chavan");

        ModelAndView modelAndView = new ModelAndView("pdfView", "command",employee);

        return modelAndView;
    }

    @RequestMapping("/report")
    void getFile(HttpServletResponse response) throws IOException {

        String fileName = "0c69990aeaefcdd4.pdf";
//        String path = "/path/to/" + fileName;
        String path="C:\\$Winni_Cloud_Local\\Cdai_Uploads\\Docs\\" + fileName;

        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);

        response.setContentType("application/pdf");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "inline;filename=\"" + fileName + "\"");

        FileCopyUtils.copy(inputStream, response.getOutputStream());

    }

}
