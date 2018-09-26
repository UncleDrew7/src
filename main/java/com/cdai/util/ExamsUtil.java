package com.cdai.util;

import build.dao.*;
import build.model.*;
import com.alibaba.fastjson.JSONObject;
import com.cdai.models.tempModels.ExamReporting;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by apple on 07/01/2018.
 */
public class ExamsUtil {
    public static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
    public static GradeDao daoAccessGrade = (GradeDao) applicationContext.getBean("GradeDao");
    public static ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");
    public static GradeItemsDao daoAccessGradeItem = (GradeItemsDao) applicationContext.getBean("GradeItemsDao");
    public static UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");


    public static List<User> getClearanceExamEligableStudentsList(int parentExam , int clearanceExam){
        return daoAccessUser.getExamEnrollmentListByExamId(parentExam);
    }

    //NOT FINISHED YET
    public static List<ExamReporting> getAvailableRecommendedExamsListForStudent(long parentCourse , long studentId){

        List<ExamReporting> tempExamList = new ArrayList<>();

        for(GradeItems entity : daoAccessGradeItem.getRecommendedAvailableExamsForStudent(parentCourse,studentId)){

//            for(ClearanceExam clearanceEntity : daoAccessExam.getIfAnyStudentClearanceExamList(studentId, parentCourseId)){
//                ExamReporting childExamReporting = new ExamReporting();
//
//                if(clearanceEntity.getParentExamId() == entity.getGradeItemId()){
//
//                    childExamReporting.setExamType("childExam");
//                    childExamReporting.setParentCourseId(clearanceEntity.getParentCourseId());
//                    childExamReporting.setChildCourseId(clearanceEntity.getChildCourseId());
//                    childExamReporting.setParentExamId(clearanceEntity.getParentExamId());
//                    childExamReporting.setChildExamId(clearanceEntity.getClearanceExamId());
//                    childExamReporting.setExamName(clearanceEntity.getExamName());
//                    childExamReporting.setExamDate(clearanceEntity.getExamDate());
//                    childExamReporting.setExamEnrollmentDeadline(clearanceEntity.getEnrolmentEndDate());
//                    childExamReporting.setGrade(clearanceEntity.getGrade());
//
//                    tempExamList.add(childExamReporting);
//
//                }
//
//            }

            ExamReporting examReporting = new ExamReporting();
            examReporting.setExamType("parentExam");
            examReporting.setParentCourseId(entity.getParentCourseId());
            examReporting.setChildCourseId(entity.getCourseId());
            examReporting.setChildCourseName(entity.getCourseShortName());
            examReporting.setParentCourseName(entity.getCourseName());
            examReporting.setParentExamId(entity.getGradeItemId());
            examReporting.setExamName(entity.getGradeItemName());
            examReporting.setExamDate(entity.getDateOfExam());
            examReporting.setExamEnrollmentDeadline(entity.getEnrolmentCloseDate());
            examReporting.setGrade("");

            tempExamList.add(examReporting);


        }
        return tempExamList;
    }


    public static List<User> getEligibleStudentsForClearanceExam(int clearanceExam){

        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");
        ExamEnrolmentDao daoAccessExEnroll = (ExamEnrolmentDao) applicationContext.getBean("ExamEnrolmentDao");
        //get clearance exam parent exam

        List<User> tempList = new ArrayList<>();
     //   long parentCourseId = daoAccessExam.getClearanceExamParentCourse(clearanceExam);
//        int childCourseId = daoAccessExam.getClearanceExamChildCourse(clearanceExam);
        int parentExam = daoAccessExam.getClearanceExamParentExam(clearanceExam);
        int pCourseId = daoAccessExam.getParentCourseId(parentExam);




        for(ExamEnrolment entity :daoAccessExEnroll.getExamStudentGrades(parentExam)) {

            long stuId = entity.getStudentId();
            int enrolls = daoAccessExEnroll.getCountOfEnrolledExams(pCourseId, stuId);
            if ( enrolls == 1 )
                continue;

            if ( checkIfStudentHasInClearenceExam( stuId, clearanceExam) )
                continue;

            double currentCourseFinalGrade = Double.parseDouble( entity.getExamScore() );

            if ( currentCourseFinalGrade < 60){
                if(currentCourseFinalGrade >=48 ){
                    if( checkIfStudentHasPassedClearenceExamBefore( stuId, pCourseId) ){
                        User stuUser = daoAccessUser.getUserByUserId( stuId );
                        tempList.add(stuUser);
                    }else {
                        // already cleared course
                        System.out.println("--------Student already cleared Course .. close");
                    }
                }else{
                    // student not eligible sign out
                    System.out.println("------------Student not eligible for exam ...close");
                }

            }else{
                //student has passed sign off
                System.out.println("Student already passed course first time ..close");
            }
        }





//        System.out.println(checkIfStudentHasPassedClearenceExamBefore(800,1052));
        return tempList;


        }

    // returns true if student has passed clearance exam before
    public static boolean checkIfStudentHasInClearenceExam(long studentId, int clexamId){
        boolean status = false;

        int enrolled = daoAccessExam.isStudentInClearanceExam(studentId,clexamId);
        if ( enrolled > 0 )
            status = true;

       /* for (GradeCustom entity: daoAccessExam.getStudentClearanceExamResults(studentId,parentCourseId)){
//            System.out.println(entity.getGrade());
            if (entity.getGrade().equals("pass")){
                status = true;
            }
        }*/
        return status;
    }

    // returns true if student has passed clearance exam before
    public static boolean checkIfStudentHasPassedClearenceExamBefore(long studentId, long parentCourseId){
        boolean status = false;

        int enrolTimes = daoAccessExam.getStudentClearanceExamTimes(studentId,parentCourseId);
        if ( enrolTimes < 2 )
            status = true;

       /* for (GradeCustom entity: daoAccessExam.getStudentClearanceExamResults(studentId,parentCourseId)){
//            System.out.println(entity.getGrade());
            if (entity.getGrade().equals("pass")){
                status = true;
            }
        }*/
        return status;
    }


    public static JSONObject examUploadEntranceChamber(HSSFWorkbook workbook){

        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");
        ChildCoursesDao daoAccessChildCourses = (ChildCoursesDao) applicationContext.getBean("ChildCoursesDao");
        ParentCoursesDao daoAccessParentCourse = (ParentCoursesDao) applicationContext.getBean("ParentCoursesDao");
        GradeItemsDao daoAccessGradeItem = (GradeItemsDao ) applicationContext.getBean("GradeItemsDao");
        ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");



        List<Exam> examBundle = new ArrayList<>();

        HashSet<Long> set = new HashSet<>();
        List<Exam> tempListError = new ArrayList<>();
        List<Exam> tempListErrorDuplicates = new ArrayList<>();
        List<Exam> tempListProcessed = new ArrayList<>();
        HSSFRow row;
        JSONObject rtn = new JSONObject();



        try{

            HSSFSheet worksheet = workbook.getSheetAt(0);
            Iterator< Row > rowIterator = worksheet.iterator();
            DataFormatter formatter = new DataFormatter();

            int count =0;
            System.out.println("@::EXCEL PROCESSING START!!");

            while(rowIterator.hasNext()){
                row = (HSSFRow )rowIterator.next();
                if(row.getRowNum()==0){
                    continue; //just skip the rows if row number is 0
                }
                Iterator<Cell> cellIterator = row.cellIterator();

                while(cellIterator.hasNext()){
                    Cell cell = cellIterator.next();


                    Exam exam = new Exam();

                    long parentCourseId = 0;
                    int childCourseId=0;
                    String parentCourseName = "";
                    String childCourseName = "";
                    String examName = "";
                    int maxGrade = 100;
                    int minGrade = 0;
                    String examDate = "";
                    String examStartDate = "";
                    String enrolmentDeadlineDate = "";


                    System.out.println((long) row.getCell(2).getNumericCellValue());
                    if(!nullChecker((row.getCell(2)))){
                        parentCourseId = Long.parseLong(formatter.formatCellValue(row.getCell(2)));
                    }
                    if(!nullChecker((row.getCell(3)))){
                        childCourseId = Integer.parseInt(formatter.formatCellValue(row.getCell(3)));
                    }
                    if(!nullChecker((row.getCell(4)))){
                        parentCourseName = formatter.formatCellValue(row.getCell(4));
                    }
                    if(!nullChecker((row.getCell(5)))){
                        childCourseName = formatter.formatCellValue(row.getCell(5));
                    }
                    if(!nullChecker((row.getCell(6)))){
                        examName = formatter.formatCellValue(row.getCell(6));
                    }
                    if(!nullChecker((row.getCell(7)))){
                        examDate = formatter.formatCellValue(row.getCell(7));
                    }
                    if(!nullChecker((row.getCell(8)))){
                        examStartDate =formatter.formatCellValue(row.getCell(8));
                    }

                    if(!nullChecker((row.getCell(9)))){
                        enrolmentDeadlineDate =formatter.formatCellValue(row.getCell(9));
                    }



                    exam.setParentCourseId(parentCourseId);
                    exam.setChildCourseId(childCourseId);
                    exam.setChildCourseName(childCourseName);
                    exam.setParentCourseName(parentCourseName);
                    exam.setExamName(examName);
                    exam.setMaxGrade(maxGrade);
                    exam.setMinGrade(minGrade);
                    exam.setDateOfExam(examDate);
                    exam.setEnrolmentStartDate(examStartDate);
                    exam.setEnrolmentCloseDate(enrolmentDeadlineDate);

                    //check if parent course exsists
                    if(daoAccessParentCourse.checkIfParentCourseIdInSystem(parentCourseId)){
                        if (daoAccessChildCourses.checkIfChildCourseIsInSystem(childCourseId,childCourseName)){
                            if (!daoAccessChildCourses.checkIfChildCourseExamIsInSystem(childCourseId,examName)){
                                tempListProcessed.add(exam);
                            }else{
                                tempListError.add(exam);
                                System.out.println("----400 -----Error Exam in System");
                            }
                        }else{
                            tempListError.add(exam);
                            System.out.println("Child course not in system ==== ::"+childCourseName);
                        }
                    }else{
                        tempListError.add(exam);
                        System.out.println("PARENT NOT IN SYSTEM ");
                    }

                    break;

                }
            }
            System.out.println("@::EXCEL PROCESSING COMPLETE!!");

        }catch (Exception e){
            e.printStackTrace();
        }

        if(tempListError.isEmpty()){
            for(Exam entity:tempListProcessed){
                System.out.println("#::-------------"+entity.getChildCourseName()+" :: "+entity.getExamName());
            }
            rtn.put("msg","200");
            rtn.put("data", tempListProcessed);
            return rtn;

        }else{
            System.out.println("@::ERROR STUDENT ID DO NOT EXIST IN SYSTEM:");
            for(Exam entity:tempListError){
                System.out.println("#::-------------"+entity.getChildCourseName()+" :: "+entity.getExamName());
            }
            rtn.put("msg","400");
            rtn.put("data", tempListError);
            return rtn;
        }

    }

    public static boolean nullChecker(Object data){
        if(data == null){
            return true;
        }else{
            return false;
        }
    }

    public static boolean checkIfExamIsStillActive(String dateOfExam){

        String currentDate = new java.sql.Date(System.currentTimeMillis()).toString();
        boolean status = false;

        try{

            System.out.println(dateOfExam.compareTo(currentDate));
            System.out.println(currentDate);
            if(dateOfExam.compareTo(currentDate) >= 0 ){
                System.out.println("@::EXAM IS CURRENTLY STILL ACTIVE");
                status = true;
            }else{
                System.out.println("----::EXAM IS NO LONGER ACTIVE ....!");
                status = false;
            }

        }catch (Exception e){
            System.out.println("----::EXAM DATE CHECK ERROR REQUEST FAILED ....!");
            status = false;
        }

        return status;

    }


}
