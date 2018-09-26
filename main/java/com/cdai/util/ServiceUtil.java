package com.cdai.util;

import build.model.GradeItems;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by apple on 04/01/2018.
 */
public class ServiceUtil {
    public ServiceUtil() {
        super();
    }

    public static List<String> buildIntakeList(){

        List<String> intakeList = new ArrayList<>();
        String intake = "";

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        Calendar startYear = Calendar.getInstance();
        Calendar midYear = Calendar.getInstance();

        startYear.set(year,Calendar.JANUARY,1);
        midYear.set(year,Calendar.JULY,30);


        /*for(int i=0;i<=5;i++){
            intakeList.add(""+ (year+i)+"-1");
            intakeList.add(""+ (year+i)+"-2");
        }*/

        for(int i=0;i<=50;i++){
            intakeList.add(""+ (2013+i));
          //  intakeList.add(""+ (year+i)+"-2");
        }
        return intakeList;

    }

    public static boolean checkIfInputIsAnInteger(String temp){
        try{
            double d= Double.valueOf(temp);
            if (d==(int)d){
                System.out.println("integer"+(int)d);
                Integer.parseInt(temp);
                return true;

            }else{
                System.out.println("double"+d);
                Double.parseDouble(temp);
                return true;
            }
        }catch(Exception e){
            System.out.println("not number");
            return false;
        }

    }

    public static boolean isStringInt(String s) {
        try
        {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex)
        {
            return false;
        }
    }

    public static List<String> getStudentIntakeList(){
        List<String> futureIntakeList = new ArrayList<>();
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR) ;
        Calendar startYear = Calendar.getInstance();
        Calendar midYear = Calendar.getInstance();

        startYear.set(year,Calendar.JANUARY,1);
        midYear.set(year,Calendar.JULY,30);

        year = year - 4;

        for (int i=1;i<=50;i++){
            int tempYear = 2013 + i;
            futureIntakeList.add(""+tempYear);
           // futureIntakeList.add(""+tempYear+"-2");
        }

        return futureIntakeList;
    }

    public static boolean checkIfDateIsStillActive(String dateOfExam){

        String currentDate = new java.sql.Date(System.currentTimeMillis()).toString();
        boolean status = false;

        try{

//            System.out.println(dateOfExam.compareTo(currentDate));
//            System.out.println(currentDate);
            if(dateOfExam.compareTo(currentDate) >= 0 ){
                System.out.println("@::DATE ITEM IS CURRENTLY STILL ACTIVE");
                status = true;
            }else{
                System.out.println("----::DATE ITEM  IS NO LONGER ACTIVE ....!");
                status = false;
            }

        }catch (Exception e){
            System.out.println("----::DATE ITEM  CHECK ERROR REQUEST FAILED ....!");
            status = false;
        }

        return status;

    }


}
