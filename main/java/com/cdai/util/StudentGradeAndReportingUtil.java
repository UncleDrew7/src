package com.cdai.util;

import build.dao.ExamDao;
import build.dao.GradeDao;
import build.dao.GradeItemsDao;
import build.model.ClearanceExam;
import build.model.Exam;
import build.model.GradeCustom;
import build.model.GradeItems;
import com.cdai.models.tempModels.ExamReporting;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static com.cdai.util.ExamsUtil.checkIfExamIsStillActive;

/**
 * Created by apple on 07/01/2018.
 */
public class StudentGradeAndReportingUtil {

    public static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
    public static GradeDao daoAccessGrade = (GradeDao) applicationContext.getBean("GradeDao");
    public static ExamDao daoAccessExam = (ExamDao) applicationContext.getBean("ExamDao");
    public static GradeItemsDao daoAccessGradeItem = (GradeItemsDao) applicationContext.getBean("GradeItemsDao");


    //this api chnages the grade if student has passed clearance exam
    public static List<GradeItems> getCustomStudentGradeReport(long parentCourseId,int childCourseId, long userId){


        List<GradeItems> tempGradeList = new ArrayList<>();
        for (GradeItems entity: daoAccessGradeItem.getStudentCourseExamsReportList(parentCourseId,userId)){
            if(daoAccessExam.getIfAnyClearanceExamResultsList(userId, parentCourseId).isEmpty()){
                tempGradeList.add(entity);
            }else{
                for (GradeCustom gradeEntity: daoAccessExam.getIfAnyClearanceExamResultsList(userId, parentCourseId)){
                    if(gradeEntity.getParentExamId() == entity.getGradeItemId() && gradeEntity.getGrade().equals("pass")){
                        entity.setGrade(60);
                        entity.setPercentage(60.0);
                        entity.setLetter("D");
                        tempGradeList.add(entity);
                    }
                    else{
                        tempGradeList.add(entity);
                    }
                }
            }
        }
        for (GradeItems entity: daoAccessGradeItem.getStudentCourseGradeItemReportList(childCourseId, userId)){
            if(entity != null){
                tempGradeList.add(entity);
            }
        }
        return tempGradeList;
    }

    public static List<ExamReporting> getForChildCourseExamList(int childCourseId){
        List<ExamReporting> tempExamList = new ArrayList<>();

        for(GradeItems entity:  daoAccessGradeItem.getChildCourseExams(childCourseId)){

            for(ClearanceExam clearanceEntity : daoAccessExam.getIfAnyChildCourseClearnceExamList(childCourseId)){
                ExamReporting childExamReporting = new ExamReporting();

                if(clearanceEntity.getParentExamId() == entity.getGradeItemId()){

                    childExamReporting.setExamType("childExam");
                    childExamReporting.setParentCourseId(clearanceEntity.getParentCourseId());
                    childExamReporting.setChildCourseId(clearanceEntity.getChildCourseId());
                    childExamReporting.setParentExamId(clearanceEntity.getParentExamId());
                    childExamReporting.setChildExamId(clearanceEntity.getClearanceExamId());
                    childExamReporting.setExamName(clearanceEntity.getExamName());
                    childExamReporting.setExamDate(clearanceEntity.getExamDate());
                    childExamReporting.setExamEnrollmentDeadline(clearanceEntity.getEnrolmentEndDate());
                    childExamReporting.setGrade(clearanceEntity.getGrade());
                    childExamReporting.setWeight(0);
                    childExamReporting.setMinGrade(0);
                    childExamReporting.setMaxGrade(100);

                    tempExamList.add(childExamReporting);
                }
            }


            ExamReporting examReporting = new ExamReporting();
            if(entity.getGradeItemType().equals("Exam")){
                examReporting.setExamType("parentExam");
            }else{
                examReporting.setExamType(entity.getGradeItemType());
            }
            examReporting.setParentCourseId(entity.getParentCourseId());
            examReporting.setChildCourseId(entity.getCourseId());
            examReporting.setParentExamId(entity.getGradeItemId());
            examReporting.setChildCourseName(entity.getCourseShortName());
            examReporting.setParentCourseName(entity.getCourseName());
            examReporting.setExamName(entity.getGradeItemName());
            examReporting.setExamDate(entity.getDateOfExam());
            examReporting.setExamEnrollmentDeadline(entity.getEnrolmentCloseDate());
            examReporting.setGrade("");
            examReporting.setWeight(entity.getWeight());
            examReporting.setMinGrade(entity.getMinGrade());
            examReporting.setMaxGrade(entity.getMaxGrade());
            examReporting.setIsExamActive(checkIfExamIsStillActive(entity.getExamEndDate()));
            examReporting.setTotalEnrolledStudents(entity.getTotalEnrollments());

            tempExamList.add(examReporting);

        }
        return tempExamList;
    }


    public static List<ExamReporting> getForStudentEnrolledExamsList( long parentCourseId,long studentId){

        List<ExamReporting> tempExamList = new ArrayList<>();

        for(GradeItems entity:  daoAccessGradeItem.getCourseExams(parentCourseId,studentId)){

            for(ClearanceExam clearanceEntity : daoAccessExam.getIfAnyStudentClearanceExamList(studentId, parentCourseId)){
                ExamReporting childExamReporting = new ExamReporting();

                if(clearanceEntity.getParentExamId() == entity.getGradeItemId()){

                    childExamReporting.setExamType("childExam");
                    childExamReporting.setParentCourseId(clearanceEntity.getParentCourseId());
                    childExamReporting.setChildCourseId(clearanceEntity.getChildCourseId());
                    childExamReporting.setParentExamId(clearanceEntity.getParentExamId());
                    childExamReporting.setChildExamId(clearanceEntity.getClearanceExamId());
                    childExamReporting.setExamName(clearanceEntity.getExamName());
                    childExamReporting.setExamDate(clearanceEntity.getExamDate());
                    childExamReporting.setExamEnrollmentDeadline(clearanceEntity.getEnrolmentEndDate());
                    childExamReporting.setGrade(clearanceEntity.getGrade());

                    tempExamList.add(childExamReporting);

                }

            }

            ExamReporting examReporting = new ExamReporting();
            examReporting.setExamType("parentExam");
            examReporting.setParentCourseId(entity.getParentCourseId());
            examReporting.setChildCourseId(entity.getCourseId());
            examReporting.setParentExamId(entity.getGradeItemId());
            examReporting.setExamName(entity.getGradeItemName());
            examReporting.setExamDate(entity.getDateOfExam());
            examReporting.setExamEnrollmentDeadline(entity.getEnrolmentCloseDate());
            examReporting.setGrade("");

            tempExamList.add(examReporting);


        }
        return tempExamList;
    }


}


