package com.cdai.util;

import build.dao.*;
import build.model.StudentClass;
import build.model.User;
import build.model.UserProfile;
import com.alibaba.fastjson.JSONObject;
import com.cdai.security.HashCredentials;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class UserUtil {
    private static  ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");

//    public static String processStudentUploadChamber(int majorId,int classId , List<UserProfile> tempListProcessedFinal){
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
//        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");
//        UserProfileDao daoAccessProfile = (UserProfileDao) applicationContext.getBean("UserProfileDao");
//        RoleDao daoAccessRole = (RoleDao) applicationContext.getBean("roleDao");
//        StudentMajorDao daoAccessMajor = (StudentMajorDao) applicationContext.getBean("StudentMajorDao");
//        StudentClassDao daoAccessClass = (StudentClassDao) applicationContext.getBean("StudentClassDao");
//
//
//        System.out.println("@::-------------SUCCESS ADDING USERS TO SYSTEM----------------");
//        for(UserProfile entity:tempListProcessedFinal){
//            System.out.println("User Id:"+entity.getUserId()+"   User Name:"+entity.getFirstName());
//            String confirmation = daoAccessProfile.createNewUserProfile(entity);
//            String roleConfirmation  = daoAccessRole.addUserRole(entity.getUserId(),3);
//            daoAccessMajor.addStudentToMajor(majorId,entity.getUserId());
//            User newUser = new User();
//            newUser.setUserId(entity.getUserId());
//            newUser.setRoleId(3);
//            newUser.setUserName(entity.getUserName());
//            newUser.setEmail(entity.getEmail());
//            newUser.setPassword(new HashCredentials().securePass("cdai",""+entity.getUserId()));
//            newUser.setSalt("1234");
//            String returnedConfermation = daoAccessUser.createNewUser(newUser);
//            if(classId != 0){
//                daoAccessClass.createStudentClass(new StudentClass(entity.getUserId(),classId));
//            }
//
//            System.out.println(" saved data  :>> " + returnedConfermation);
//            System.out.println("::>> " + confirmation );
//
//        }
//       return "200";
//
//    }


    public static String processStudentUploadChamber( int classId , List<User> tempListProcessedFinal){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");
        UserProfileDao daoAccessProfile = (UserProfileDao) applicationContext.getBean("UserProfileDao");
        RoleDao daoAccessRole = (RoleDao) applicationContext.getBean("roleDao");
        StudentMajorDao daoAccessMajor = (StudentMajorDao) applicationContext.getBean("StudentMajorDao");
        StudentClassDao daoAccessClass = (StudentClassDao) applicationContext.getBean("StudentClassDao");


        System.out.println("@::-------------SUCCESS ADDING USERS TO SYSTEM----------------");
        for(User entity:tempListProcessedFinal){
            System.out.println("User Id:"+entity.getUserId()+"   User Name:"+entity.getUserName());
         //  String confirmation = daoAccessProfile.createNewUserProfile(entity);
            String roleConfirmation  = daoAccessRole.addUserRole(entity.getUserId(),3);
         //   daoAccessMajor.addStudentToMajor(majorId,entity.getUserId());
            User newUser = new User();
            newUser.setUserId(entity.getUserId());
            newUser.setRoleId(3);
            newUser.setUserName(entity.getUserName());
            newUser.setEmail(entity.getEmail());
            newUser.setPassword(new HashCredentials().securePass("cdai",""+entity.getUserId()));
            newUser.setSalt("1234");
            newUser.setClassId(classId);
            String returnedConfermation = daoAccessUser.createNewUser(newUser);

            UserProfile userProfile = new UserProfile();
            userProfile.setUserId(entity.getUserId());
            userProfile.setProfileImageUrl("avatar.png");
            userProfile.setChineseFullName(entity.getUserName());
            userProfile.setFirstName(entity.getUserName());
            userProfile.setLastName(entity.getLastName());
            userProfile.setOtherName("");
            userProfile.setGender(entity.getGender());
            userProfile.setDateOfBirth("");
            userProfile.setMobilePhone("");
            userProfile.setWeChatId("");
            userProfile.setQqId("");
            userProfile.setHomeAddress("");
            userProfile.setCity("");
            userProfile.setCountry("");
            userProfile.setInterests("");
            userProfile.setSelfDescription("");
            userProfile.setRole("Student");

            daoAccessProfile.createNewStudentProfile(userProfile);
//            if(classId != 0){
//                daoAccessClass.createStudentClass(new StudentClass(entity.getUserId(),classId));
//            }

            System.out.println(" saved data  :>> " + returnedConfermation);
//            System.out.println("::>> " + confirmation );

        }
        return "200";

    }


//    public static JSONObject studentUploadEntranceChamber(String intake , HSSFWorkbook workbook) {
//
//
//        //DAOS
//        //create parent course
//        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");
//        UserProfileDao daoAccessProfile = (UserProfileDao) applicationContext.getBean("UserProfileDao");
//        RoleDao daoAccessRole = (RoleDao) applicationContext.getBean("roleDao");
//        StudentMajorDao daoAccessMajor = (StudentMajorDao) applicationContext.getBean("StudentMajorDao");
//        StudentClassDao daoAccessClass = (StudentClassDao) applicationContext.getBean("StudentClassDao");
//        int majorId = 3;
//        int classId = 21;
//        JSONObject rtn = new JSONObject();
//
//        List<UserProfile> tempListError = new ArrayList<>();
//        List<UserProfile> tempListErrorDuplicates = new ArrayList<>();
//        List<UserProfile> tempListProcessed = new ArrayList<>();
//        List<UserProfile> tempListProcessedFinal = new ArrayList<>();
//        HashSet<Long> set = new HashSet<>();
//        JSONObject result = new JSONObject();
//
//
//        HSSFRow row;
//
//        try {
//
//            int i = 0;
//
//
//            HSSFSheet worksheet = workbook.getSheetAt(0);
//            Iterator<Row> rowIterator = worksheet.iterator();
//
//
//            while (rowIterator.hasNext()) {
//                row = (HSSFRow) rowIterator.next();
//                if (row.getRowNum() == 0) {
//                    continue; //just skip the rows if row number is 0
//                }
//                Iterator<Cell> cellIterator = row.cellIterator();
//                while (cellIterator.hasNext()) {
//                    Cell cell = cellIterator.next();
////                    System.out.println(row.getCell(0).getStringCellValue());
////                    System.out.println(row.getCell(1).getStringCellValue());
////                    System.out.println(row.getCell(2).getStringCellValue());
////                    System.out.println(row.getCell(3).getStringCellValue());
////                    System.out.println(row.getCell(4).getStringCellValue());
////                    System.out.println(row.getCell(5).getStringCellValue());
////                    System.out.println(row.getCell(6).getStringCellValue());
////                    System.out.println(row.getCell(7).getStringCellValue());
//                    // CREATING USER
//
//
//                    UserProfile userProfile = new UserProfile();
//                    long userId = 0;
//                    String firstName = "";
//                    String lastName = "";
//                    String degreeType = "";
//                    String gender = "";
//                    String email = "";
//                    String city = "";
//                    String country = "";
//                    if (row.getCell(0) != null) {
//                        if (row.getCell(0).getCellType() == 1) {
//                            userId = Long.parseLong(row.getCell(0).getStringCellValue());
//                        } else {
//                            userId = (long) row.getCell(0).getNumericCellValue();
//                        }
//                    }
//
//                    if (row.getCell(1) != null) {
//                        firstName = row.getCell(1).getStringCellValue();
//                    }
//                    if (row.getCell(2) != null) {
//                        lastName = row.getCell(2).getStringCellValue();
//                    }
//                    if (row.getCell(3) != null) {
//                        degreeType = row.getCell(3).getStringCellValue();
//                    }
//                    if (row.getCell(4) != null) {
//                        gender = row.getCell(4).getStringCellValue();
//                    }
//                    if (row.getCell(5) != null) {
//                        email = row.getCell(5).getStringCellValue();
//                    }
//                    if (row.getCell(6) != null) {
//                        city = row.getCell(6).getStringCellValue();
//                    }
//                    if (row.getCell(7) != null) {
//                        country = row.getCell(7).getStringCellValue();
//                    }
//
//
//                    userProfile.setUserId(userId);
//                    userProfile.setUserName(firstName + " " + lastName);
//                    userProfile.setEmail(email);
//                    userProfile.setProfileImageUrl("avatar.png");
//                    userProfile.setChineseFullName("");
//                    userProfile.setFirstName(firstName);
//                    userProfile.setLastName(lastName);
//                    userProfile.setOtherName("");
//                    userProfile.setDegreeType(degreeType);
//                    userProfile.setGender(gender);
//                    userProfile.setDateOfBirth("");
//                    userProfile.setIntake(intake);
//                    userProfile.setMobilePhone("");
//                    userProfile.setWeChatId("");
//                    userProfile.setQqId("");
//                    userProfile.setHomeAddress("");
//                    userProfile.setCity(city);
//                    userProfile.setCountry(country);
//                    userProfile.setInterests("");
//                    userProfile.setSelfDescription("");
//                    userProfile.setRole("Student");
//
//
//                    //check if Course id is already in system
//
//                    if (daoAccessUser.checkIfUserIdInSystem(userId)) {
////                        System.out.println(" Error User Id @::"+userId+"::Already In System");
//                        tempListError.add(userProfile);
//                    } else {
//                        tempListProcessed.add(userProfile);
//                    }
//
//                    break;
//                }
//            }
//            System.out.println("@::EXCEL PROCESSING COMPLETE!!");
//
//            if (tempListError.isEmpty()) {
//                System.out.println("@::CHECKING FOR DUPLICATES");
//                for (UserProfile entity : tempListProcessed) {
//
//                    // If String is not in set, add it to the list and the set.
//                    if (!set.contains(entity.getUserId())) {
//                        tempListProcessedFinal.add(entity);
//                        set.add(entity.getUserId());
//                    } else {
////                        System.out.println("DUPLICATE KEY FOUND @"+entity.getUserId()+" name "+entity.getFirstName());
////                        System.out.println("Adding Duplicate to error list...");
//                        tempListErrorDuplicates.add(entity);
//                    }
//                }
//
//                if (tempListErrorDuplicates.isEmpty()) {
//                    System.out.println("@::-------------GETTING USERS FROM EXCEL----------------");
//                    rtn.put("msg", "200");
//                    rtn.put("data", tempListProcessed);
//
//
//                } else {
//                    System.out.println("@::ERROR THE FOLLOWING USERS USER ID ARE DUPLICATED IN THE EXCEL DOCUMENT ");
//                    for (UserProfile entity : tempListErrorDuplicates) {
//                        System.out.println("User Id:" + entity.getUserId() + "   User Name:" + entity.getFirstName());
//                    }
//                    rtn.put("msg", "405");
//                    rtn.put("data", tempListErrorDuplicates);
//
//                }
//            } else {
//                System.out.println("@::ERROR THE FOLLOWING USERS USER ID ARE ALREADY IN THE SYSTEM ");
//                for (UserProfile entity : tempListError) {
//                    System.out.println("User Id:" + entity.getUserId() + "   User Name:" + entity.getFirstName());
//                }
//                rtn.put("msg", "406");
//                rtn.put("data", tempListError);
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            rtn.put("msg", "400");
//            rtn.put("data", null);
//        }
//        return rtn;
//
//    }

    public static JSONObject studentUploadEntranceChamber( Workbook workbook) {


        //DAOS
        //create parent course
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");
        UserProfileDao daoAccessProfile = (UserProfileDao) applicationContext.getBean("UserProfileDao");
        RoleDao daoAccessRole = (RoleDao) applicationContext.getBean("roleDao");
        StudentMajorDao daoAccessMajor = (StudentMajorDao) applicationContext.getBean("StudentMajorDao");
        StudentClassDao daoAccessClass = (StudentClassDao) applicationContext.getBean("StudentClassDao");
        int majorId = 3;
        int classId = 21;
        JSONObject rtn = new JSONObject();

        List<User> tempListError = new ArrayList<>();
        List<User> tempListErrorDuplicates = new ArrayList<>();
        List<User> tempListProcessed = new ArrayList<>();
        List<User> tempListProcessedFinal = new ArrayList<>();
        HashSet<Long> set = new HashSet<>();
        JSONObject result = new JSONObject();


        Row row;

        try {

            int i = 0;


            Sheet worksheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = worksheet.iterator();


            while (rowIterator.hasNext()) {
                row = (Row) rowIterator.next();
                if (row.getRowNum() == 0) {
                    continue; //just skip the rows if row number is 0
                }
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
//                    System.out.println(row.getCell(0).getStringCellValue());
//                    System.out.println(row.getCell(1).getStringCellValue());
//                    System.out.println(row.getCell(2).getStringCellValue());
//                    System.out.println(row.getCell(3).getStringCellValue());
//                    System.out.println(row.getCell(4).getStringCellValue());
//                    System.out.println(row.getCell(5).getStringCellValue());
//                    System.out.println(row.getCell(6).getStringCellValue());
//                    System.out.println(row.getCell(7).getStringCellValue());
                    // CREATING USER


                    User user = new User();
                    long userId = 0;
                    String userName = "";
                    String psw = "";

                    if (row.getCell(0) != null) {
                        if (row.getCell(0).getCellType() == 1) {
                            userId = Long.parseLong(row.getCell(0).getStringCellValue());
                        } else {
                            userId = (long) row.getCell(0).getNumericCellValue();
                        }
                        user.setUserId(userId);
                    }

                    if (row.getCell(1) != null) {
                        userName = row.getCell(1).getStringCellValue();
                        user.setUserName(userName);
                    }
                    user.setPassword(new HashCredentials().securePass("cdai",Long.toString( userId ) ));

                    if (row.getCell(2) != null) {
                        String pinyingName = row.getCell(2).getStringCellValue();
                        user.setLastName(pinyingName);
                    }

                    if (row.getCell(3) != null) {
                        String sex = row.getCell(3).getStringCellValue();
                        user.setGender(sex);
                    }

//                    if (row.getCell(4) != null) {
//                        String dob = row.getCell(4).getStringCellValue();
//                        user.setBirthday(dob);
//                    }
                    if (row.getCell(4) != null) {
                        String email = row.getCell(4).getStringCellValue();
                        user.setEmail(email);
                    }


                    //check if Course id is already in system

                    if (daoAccessUser.checkIfUserIdInSystem(userId)) {
//                        System.out.println(" Error User Id @::"+userId+"::Already In System");
                        tempListError.add(user);
                    } else {
                        tempListProcessed.add(user);
                    }

                    break;
                }
            }
            System.out.println("@::EXCEL PROCESSING COMPLETE!!");

            if (tempListError.isEmpty()) {
                System.out.println("@::CHECKING FOR DUPLICATES");
                for (User entity : tempListProcessed) {

                    // If String is not in set, add it to the list and the set.
                    if (!set.contains(entity.getUserId())) {
                        tempListProcessedFinal.add(entity);
                        set.add(entity.getUserId());
                    } else {
//                        System.out.println("DUPLICATE KEY FOUND @"+entity.getUserId()+" name "+entity.getFirstName());
//                        System.out.println("Adding Duplicate to error list...");
                        tempListErrorDuplicates.add(entity);
                    }
                }

                if (tempListErrorDuplicates.isEmpty()) {
                    System.out.println("@::-------------GETTING USERS FROM EXCEL----------------");
                    rtn.put("msg", "200");
                    rtn.put("data", tempListProcessed);


                } else {
                    System.out.println("@::ERROR THE FOLLOWING USERS USER ID ARE DUPLICATED IN THE EXCEL DOCUMENT ");
                    for (User entity : tempListErrorDuplicates) {
                        System.out.println("User Id:" + entity.getUserId() + "   User Name:" + entity.getUserName());
                    }
                    rtn.put("msg", "405");
                    rtn.put("data", tempListErrorDuplicates);

                }
            } else {
                System.out.println("@::ERROR THE FOLLOWING USERS USER ID ARE ALREADY IN THE SYSTEM ");
                for (User entity : tempListError) {
                    System.out.println("User Id:" + entity.getUserId() + "   User Name:" + entity.getUserName());
                }
                rtn.put("msg", "406");
                rtn.put("data", tempListError);

            }

        } catch (Exception e) {
            e.printStackTrace();
            rtn.put("msg", "400");
            rtn.put("data", null);
        }
        return rtn;

    }

    public static String processTeacherUploadChamber(List<UserProfile> tempListProcessedFinal){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/spring_DatabaseMappings.xml");
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");
        UserProfileDao daoAccessProfile = (UserProfileDao) applicationContext.getBean("UserProfileDao");
        RoleDao daoAccessRole = (RoleDao) applicationContext.getBean("roleDao");
        StudentMajorDao daoAccessMajor = (StudentMajorDao) applicationContext.getBean("StudentMajorDao");
        StudentClassDao daoAccessClass = (StudentClassDao) applicationContext.getBean("StudentClassDao");


        System.out.println("@::-------------SUCCESS ADDING USERS TO SYSTEM----------------");
        for(UserProfile entity:tempListProcessedFinal){
            System.out.println("User Id:"+entity.getUserId()+"   User Name:"+entity.getFirstName());
            String confirmation = daoAccessProfile.createNewUserProfile(entity);
            String roleConfirmation  = daoAccessRole.addUserRole(entity.getUserId(),2);
            User newUser = new User();
            newUser.setUserId(entity.getUserId());
            newUser.setRoleId(3);
            newUser.setUserName(entity.getUserName());
            newUser.setEmail(entity.getEmail());
            newUser.setPassword(new HashCredentials().securePass("cdai",""+entity.getUserId()));
            newUser.setSalt("1234");
            String returnedConfermation = daoAccessUser.createNewUser(newUser);
            System.out.println(" saved data  :>> " + returnedConfermation);
            System.out.println("::>> " + confirmation );

        }
        return "200";

    }

    public static JSONObject teacherUploadEntranceChamber(HSSFWorkbook workbook) {
        //DAOS
        UserDao daoAccessUser = (UserDao) applicationContext.getBean("userDao");
        UserProfileDao daoAccessProfile = (UserProfileDao) applicationContext.getBean("UserProfileDao");
        RoleDao daoAccessRole = (RoleDao) applicationContext.getBean("roleDao");
        //MODELS

        DataFormatter formatter = new DataFormatter();

        List<UserProfile> tempListError = new ArrayList<>();
        List<UserProfile> tempListProcessed = new ArrayList<>();
        List<UserProfile> tempListErrorDuplicates = new ArrayList<>();
        List<UserProfile> tempListProcessedFinal = new ArrayList<>();
        HashSet<Long> set = new HashSet<>();
        JSONObject rtn = new JSONObject();

        HSSFRow row;

        System.out.println(">> Started importing Teachers Excel file ");
        try{

            int i =0;


            HSSFSheet worksheet = workbook.getSheetAt(0);
            Iterator< Row > rowIterator = worksheet.iterator();

            System.out.println(">> Reading Excel file ");
            int count =0;

            while(rowIterator.hasNext()){
                User newUser = new User();
                UserProfile userProfile = new UserProfile();

                row = (HSSFRow)rowIterator.next();
                if (row.getRowNum() == 0) {
                    continue; //just skip the rows if row number is 0
                }
                Iterator<Cell> cellIterator = row.cellIterator();
                while(cellIterator.hasNext()){
                    Cell cell = cellIterator.next();

                    long userId = 0;
                    String firstName = "";
                    String lastName = "";
                    String gender = "";
                    String email = "";
                    String city = "";
                    String country = "";


                    userId = Long.parseLong(formatter.formatCellValue(row.getCell(0)));
//                    if (row.getCell(0) != null) {
//                        if (row.getCell(0).getCellType() == 1) {
//                            userId = Long.parseLong(row.getCell(0).getStringCellValue());
//                        } else {
//                            userId = (long) row.getCell(0).getNumericCellValue();
//                        }
//                    }

                    if (row.getCell(1) != null) {
                        firstName = formatter.formatCellValue(row.getCell(1));
                        //row.getCell(1).getStringCellValue();
                    }
                    if (row.getCell(2) != null) {
                        lastName = formatter.formatCellValue(row.getCell(2)); //row.getCell(2).getStringCellValue();
                    }
                    if (row.getCell(3) != null) {
                        gender = formatter.formatCellValue(row.getCell(3)); //row.getCell(3).getStringCellValue();
                    }
                    if (row.getCell(4) != null) {
                        email = formatter.formatCellValue(row.getCell(4)); //row.getCell(4).getStringCellValue();
                    }
                    if (row.getCell(5) != null) {
                        city = formatter.formatCellValue(row.getCell(5));//row.getCell(5).getStringCellValue();
                    }
                    if (row.getCell(6) != null) {
                        country = formatter.formatCellValue(row.getCell(6));//row.getCell(6).getStringCellValue();
                    }





                    //ADD TO USER TABLE

                    System.out.println("------userID >>#"+userId+"---Roleid >>>2 #Teacher"+"---UserName >>>#"+firstName+" "+lastName);
                    newUser.setUserId(userId);
                    newUser.setRoleId(2);
                    newUser.setUserName(firstName+" "+lastName);
                    newUser.setEmail(email);
                    newUser.setPassword( new HashCredentials().securePass("cdai",""+userId));
                    newUser.setSalt("1234");

                    //INSERT INTO USER PROFILE

                    userProfile.setUserId(userId);
                    userProfile.setProfileImageUrl("avatar.png");
                    userProfile.setChineseFullName("");
                    userProfile.setFirstName(firstName);
                    userProfile.setLastName(lastName);
                    userProfile.setOtherName("");
                    userProfile.setGender(gender);
                    userProfile.setDateOfBirth("");
                    userProfile.setIntake("");
                    userProfile.setMobilePhone("");
                    userProfile.setWeChatId("");
                    userProfile.setQqId("");
                    userProfile.setHomeAddress("");
                    userProfile.setCity(city);
                    userProfile.setCountry(country);
                    userProfile.setInterests("");
                    userProfile.setSelfDescription("");
                    //userProfile.setRole(row.getCell(1).getStringCellValue());
                    userProfile.setRole("Teacher");


                    //check if user id in system
                    if(daoAccessUser.checkIfUserIdInSystem(userId)){
                        System.out.println(" Error User Id @::"+userId+"::Already In System");
                        tempListError.add(userProfile);
                    }else {
                        System.out.println();
                        tempListProcessed.add(userProfile);
                    }
                    break;

                }
            }

            System.out.println("@::EXCEL PROCESSING COMPLETE!!");

            if (tempListError.isEmpty()) {
                System.out.println("@::CHECKING FOR DUPLICATES");
                for (UserProfile entity : tempListProcessed) {

                    // If String is not in set, add it to the list and the set.
                    if (!set.contains(entity.getUserId())) {
                        tempListProcessedFinal.add(entity);
                        set.add(entity.getUserId());
                    } else {
//                        System.out.println("DUPLICATE KEY FOUND @"+entity.getUserId()+" name "+entity.getFirstName());
//                        System.out.println("Adding Duplicate to error list...");
                        tempListErrorDuplicates.add(entity);
                    }
                }

                if (tempListErrorDuplicates.isEmpty()) {
                    System.out.println("@::-------------GETTING USERS FROM EXCEL----------------");
                    rtn.put("msg", "200");
                    rtn.put("data", tempListProcessed);


                } else {
                    System.out.println("@::ERROR THE FOLLOWING USERS USER ID ARE DUPLICATED IN THE EXCEL DOCUMENT ");
                    for (UserProfile entity : tempListErrorDuplicates) {
                        System.out.println("User Id:" + entity.getUserId() + "   User Name:" + entity.getFirstName());
                    }
                    rtn.put("msg", "405");
                    rtn.put("data", tempListErrorDuplicates);

                }
            } else {
                System.out.println("@::ERROR THE FOLLOWING USERS USER ID ARE ALREADY IN THE SYSTEM ");
                for (UserProfile entity : tempListError) {
                    System.out.println("User Id:" + entity.getUserId() + "   User Name:" + entity.getFirstName());
                }
                rtn.put("msg", "406");
                rtn.put("data", tempListError);

            }



        }catch(Exception e){
            e.printStackTrace();
            rtn.put("msg", "400");
            rtn.put("data", null);
        }
        return rtn;

    }

    public static String deleteStudentFromSystem(long studentId){

        UserDao daoAccess = (UserDao) applicationContext.getBean("userDao");

        System.out.println("------------------------DELETING STUDENT ::"+studentId +" STATING NOW...");

        daoAccess.deleteStudentFromCourseEnrolments(studentId);
        daoAccess.deleteStudentFromCourseStudentRequestEnrolment(studentId);
        daoAccess.deleteStudentFromExamEnrolment(studentId);
        daoAccess.deleteStudentFromExamStudentRequestEnrolment(studentId);
        daoAccess.deleteStudentFromGrade(studentId);
        daoAccess.deleteStudentFromStudentClass(studentId);
        daoAccess.deleteStudentFromUserRole(studentId);
        daoAccess.deleteUserFromUsers(studentId);
        daoAccess.deleteUserFromStudentMajor(studentId);
        daoAccess.deleteUserFromUserProfile(studentId);
        daoAccess.deleteStudentFromClearanceExamEnrolment(studentId);
        daoAccess.deleteStudentFromGradeCustom(studentId);

        System.out.println("@::200---------------STUDENT DELETED SUCCESSFULLY----------------");

        return "200";
    }

}
