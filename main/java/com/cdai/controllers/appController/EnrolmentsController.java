package com.cdai.controllers.appController;
import build.dao.*;
import build.model.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cdai.models.tempModels.ExcelCourse;
import com.cdai.models.tempModels.ExcelStudent;
import com.cdai.models.tempModels.ExcelTeacher;
import com.cdai.security.UserUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.cdai.models.testModels.Book;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.apache.log4j.Logger;
import java.util.List;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by apple on 25/09/2017.
 */
@Controller
public class EnrolmentsController {
    private static final Logger logger = Logger.getLogger(EnrolmentsController.class);
    private static final  ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");


    /**
     * COURSE ENROLLMENT VAI EXCEL IMPORT
     */
    @RequestMapping(value = "admin/manage-courses/enroll/students-excel-import/{courseId}",method = RequestMethod.POST)
    public String enrollStudentsInCourseVaiImportedExcelFile(Model model,@PathVariable int courseId, @RequestParam("unEnrolledSystemStudents")MultipartFile unEnrolledSystemStudents){
        HSSFRow row;

        CourseEnrolmentDao daoAccess = (CourseEnrolmentDao) applicationContext.getBean("CourseEnrolmentDao");

        System.out.println("@............... Started importing Student Excel file ...............");

        try{

            int i = 0;
            long studentId = 0;
            String confirm = "";

            HSSFWorkbook workbook = new HSSFWorkbook (unEnrolledSystemStudents.getInputStream());

            HSSFSheet worksheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = worksheet.iterator();

            System.out.println("@............... Reading Excel file ...............");

            int count =0;

            while(rowIterator.hasNext()){
                row = (HSSFRow)rowIterator.next();
                if(row.getRowNum()==0){
                    continue; //just skip the rows if row number is 0
                }
                Iterator<Cell> cellIterator = row.cellIterator();

                while(cellIterator.hasNext()){
                    Cell cell = cellIterator.next();

                    System.out.println("@............... Adding to database ...............");

                    studentId = (long)row.getCell(0).getNumericCellValue();

                    CourseEnrolment courseEnrolment = new CourseEnrolment(courseId,studentId);

                    if(daoAccess.checkIfStudentWasPreviouslyEnrolled(courseEnrolment)){
                        logger.info("@enrollStudentsInCourseVaiImportedExcelFile::##ALREADY ENROLLED =>::"+row.getCell(1).getStringCellValue());
                        confirm = daoAccess.enrollPreviouslyEnrolledStudent(courseEnrolment);
                    }else{
                        confirm = daoAccess.enrollStudentInCourse(courseEnrolment);
                        logger.info("@enrollStudentsInCourseVaiImportedExcelFile::Enrolled=>::"+row.getCell(1).getStringCellValue());
                    }

                    System.out.println("@enrollStudentsInCourseVaiImportedExcelFile::"+confirm);

                    break;


                }
            }

        }catch (Exception e){
            System.out.println("@enrollStudentsInCourseVaiImportedExcelFile::ERROR");
            e.printStackTrace();
        }

        return "redirect:/admin/manage-courses/enroll-student/"+courseId+"/1";


    }


    /**
     * EXAM ENROLLMENT VAI EXCEL IMPORT
     */
    @RequestMapping(value = "admin/manage-exam/enroll/students-excel-import/{courseId}/{examId}",method = RequestMethod.POST)
    public String enrollStudentsInExamVaiImportedExcelFile(Model model, @PathVariable int courseId,@PathVariable int examId, @RequestParam("unEnrolledSystemStudents")MultipartFile unEnrolledSystemStudents){
        HSSFRow row;

        ExamEnrolmentDao daoAccess = (ExamEnrolmentDao) applicationContext.getBean("ExamEnrolmentDao");

        System.out.println("@............... Started importing Student Excel file ...............");

        try{

            int i = 0;
            long studentId = 0;
            String confirm = "";
            int check = 0;


            HSSFWorkbook workbook = new HSSFWorkbook (unEnrolledSystemStudents.getInputStream());
            HSSFSheet worksheet = workbook.getSheetAt(0);
            Iterator< Row > rowIterator = worksheet.iterator();

            System.out.println("@............... Reading Excel file ...............");

            int count =0;

            while(rowIterator.hasNext()){
                row = (HSSFRow)rowIterator.next();
                if(row.getRowNum()==0){
                    continue; //just skip the rows if row number is 0
                }
                Iterator<Cell> cellIterator = row.cellIterator();

                while(cellIterator.hasNext()){
                    Cell cell = cellIterator.next();

                    System.out.println("@............... Adding to database ...............");

                    studentId = (long)row.getCell(0).getNumericCellValue();
                    check = daoAccess.checkIfStudentIsAlreadyEnrolled(examId,studentId);
//                    if(check == -1){
//                        confirm = daoAccess.enrollStudentInExam(new ExamEnrolment(courseId,examId,studentId));
//                        logger.info("@enrollStudentsInExamVaiImportedExcelFile::Enrolled=>::"+row.getCell(1).getStringCellValue());
//                    }else if(check == 0){
//                        logger.info("@enrollStudentsInExamVaiImportedExcelFile::##ALREADY ENROLLED =>::"+row.getCell(1).getStringCellValue());
//                        confirm = daoAccess.reEnrollPreviouslyUnenrolledStudent(examId,studentId);
//                    }

                    System.out.println("@enrollStudentsInExamVaiImportedExcelFile::"+confirm);

                    break;


                }
            }

        }catch (Exception e){
            System.out.println("@enrollStudentsInExamVaiImportedExcelFile::ERROR");
            e.printStackTrace();
        }

        return "redirect:/admin/manage-exam/enroll/"+courseId+"/"+examId;


    }

    /**
     * CLASS ENROLLMENT VAI EXCEL IMPORT
     */
    @RequestMapping(value = "admin/manage-class/enroll/students-excel-import/{classId}",method = RequestMethod.POST)
    public String enrollStudentsInClassVaiImportedExcelFile(Model model, @PathVariable int classId, @RequestParam("unEnrolledSystemStudents")MultipartFile unEnrolledSystemStudents){
        HSSFRow row;

        ClassDao daoAccess = (ClassDao) applicationContext.getBean("ClassDao");
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");

        System.out.println("@............... Started importing Student Excel file ...............");

        try{

            int i = 0;
            long studentId = 0;
            int check = 0;
            String confirmFinal = "";


            HSSFWorkbook workbook = new HSSFWorkbook(unEnrolledSystemStudents.getInputStream());
            HSSFSheet worksheet = workbook.getSheetAt(0);
            Iterator< Row > rowIterator = worksheet.iterator();

            System.out.println("@............... Reading Excel file ...............");

            int count =0;

            while(rowIterator.hasNext()){
                row = (HSSFRow)rowIterator.next();
                if(row.getRowNum()==0){
                    continue; //just skip the rows if row number is 0
                }
                Iterator<Cell> cellIterator = row.cellIterator();

                while(cellIterator.hasNext()){
                    Cell cell = cellIterator.next();

                    System.out.println("@............... Adding to database ...............");

                    studentId = (long)row.getCell(0).getNumericCellValue();

                    if(daoAccessUser.checkIfUserIdInSystem(studentId)){
                        if(daoAccess.checkIfStudentBelongsToClassInSystem(studentId)){
                            System.out.println("@enrollStudentsInClassVaiImportedExcelFile::....CHECKING WHAT CLASS USER IS IN .....");
                            String confirm = daoAccess.getStudentClassByUserId(studentId);
                            System.out.println("@::enrollStudentsInClassVaiImportedExcelFile CLASS ===>>"+confirm );
                            logger.info("@enrollStudentsInClassVaiImportedExcelFile::##USER ALREADY BELONGS TO A CLASS IN SYSTEM NOT ADDING =>::"+row.getCell(1).getStringCellValue());
                        } else{
                            System.out.println("@eenrollStudentsInClassVaiImportedExcelFile::............ADDING USER TO CLASS............");
                            confirmFinal = daoAccess.enrollStudentInClass(classId,studentId);
                            logger.info("@enrollStudentsInClassVaiImportedExcelFile:::"+confirmFinal);
                        }

                    }else{
                        logger.info("@enrollStudentsInClassVaiImportedExcelFile::##STUDENT NOT IN SYSTEM =>::"+row.getCell(1).getStringCellValue());
                    }


                    System.out.println("@enrollStudentsInClassVaiImportedExcelFile::"+confirmFinal);

                    break;


                }
            }

        }catch (Exception e){
            System.out.println("@enrollStudentsInExamVaiImportedExcelFile::ERROR");
            e.printStackTrace();
        }

        return "redirect:/admin/class/enroll/"+classId;


    }



}
