package com.cdai.util;

import build.dao.ClassDao;
import build.dao.ClassMajorDao;
import build.model.Class;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class ClassUtils {

    public static String processClassUploadChamber(List<Class> tempListProcessed){

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");

        ClassDao daoAccessClass = (ClassDao) applicationContext.getBean("ClassDao");
        ClassMajorDao daoAccesscClassMajor = (ClassMajorDao) applicationContext.getBean("ClassMajorDao");

        for(Class entity : tempListProcessed){
            daoAccessClass.createNewClass(entity);
            daoAccesscClassMajor.addClassToMajor(entity.getMajorId(),entity.getId());
        }
        return "200";

    }


    public static JSONObject  classUploadEntranceChamber(HSSFWorkbook workbook, int majorId, String intake){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");

        ClassDao daoAccessClass = (ClassDao) applicationContext.getBean("ClassDao");
        ClassMajorDao daoAccesscClassMajor = (ClassMajorDao) applicationContext.getBean("ClassMajorDao");



        int classId= daoAccessClass.getLargestClassId();
        if (classId == -1){
            classId = 0;
        }
        System.out.println("classId = "+classId);


        List<Class> tempListError = new ArrayList<>();
        List<Class> tempListErrorDuplicates = new ArrayList<>();
        List<Class> tempListProcessed = new ArrayList<>();
        List<Class> tempListProcessedFinal = new ArrayList<>();
        HashSet<String> set = new HashSet<>();
        JSONObject rtn = new JSONObject();

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        Calendar startYear = Calendar.getInstance();
        Calendar midYear = Calendar.getInstance();

        startYear.set(year,Calendar.JANUARY,1);
        midYear.set(year,Calendar.JULY,30);

        if(intake.equals("")){
            if(now.after(startYear) && now.before(midYear)){
                intake = year+"-1";
            }
            else{
                intake = year+"-2";
            }
        }

        /**
         * CREATE ROW
         */
        HSSFRow row;


        System.out.println("@ClassController::@addClassByUploadingExcelFile:::#STARTING TO IMPORT CLASS EXCEL FILE#");

        try{

            HSSFSheet worksheet = workbook.getSheetAt(0);
            Iterator< Row > rowIterator = worksheet.iterator();
            DataFormatter formatter = new DataFormatter();

            System.out.println("@ClassController::@addClassByUploadingExcelFile:::#Reading Excel file ");
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

                    System.out.println("@ClassController::@addClassByUploadingExcelFile:::#>>>>>>>>Adding to database>>>>>>>>>");

                    //ADD TO COURSE TABLE
                    Class newClass = new Class();

                    //id = (int)row.getCell(0).getNumericCellValue();
                    //teacherid = (int)row.getCell(1).getNumericCellValue();
                    //categoryid = (int)row.getCell(2).getNumericCellValue();
                    String className = "";
                    if(row.getCell(0)!=null){
                        className = formatter.formatCellValue(row.getCell(0));
                    }

                    newClass.setId(++classId);
                    newClass.setClassName(className);
                    newClass.setIntakePeriod(Integer.parseInt(intake));
                    newClass.setMajor(majorId);
                    newClass.setCreatedBy(911);


                    if(daoAccessClass.checkIfClassNameAlreadyInSystem(className)){
//                        System.out.println("Error class cant add class name that already in the system");
                        tempListError.add(newClass);
                    }else{
                        tempListProcessed.add(newClass);
                    }

                    break;

                }
            }
            System.out.println("@::EXCEL PROCESSING COMPLETE!!");

            if(tempListError.isEmpty()){
                System.out.println("@::CHECKING FOR DUPLICATES");
                for (Class entity: tempListProcessed) {
                    if (!set.contains(entity.getClassName())) {
                        tempListProcessedFinal.add(entity);
                        set.add(entity.getClassName());
                    }else{
                        System.out.println("Adding Duplicate to error list...");
                        tempListErrorDuplicates.add(entity);
                    }
                }

                if(tempListErrorDuplicates.isEmpty()){
                    System.out.println("@::-------------COMPILING FINAL LIST ----------------");
                    rtn.put("msg", "200");
                    rtn.put("data", tempListProcessedFinal);
                    for(Class entity:tempListProcessedFinal){
                        System.out.println("#::Class Name:"+entity.getClassName());
                    }
                }else{
                    System.out.println("@::ERROR CANT ADD CLASSES TO SYSTEM DUPLICATE CLASS NAMES FOUND:");
                    rtn.put("msg", "405");
                    rtn.put("data", tempListErrorDuplicates);
                    for(Class entity:tempListErrorDuplicates){
                        System.out.println("Class Name:"+entity.getClassName());
                    }
                }
            }else{
                System.out.println("@::ERROR CANT ADD CLASS NAME'S ALREADY IN SYSTEM :");
                rtn.put("msg", "406");
                rtn.put("data", tempListError);
                for(Class entity:tempListError){
                    System.out.println("#::Class Name:"+entity.getClassName());
                }
            }

            return rtn;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
