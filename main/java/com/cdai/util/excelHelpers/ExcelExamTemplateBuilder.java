package com.cdai.util.excelHelpers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 import com.cdai.models.tempModels.ExcelExam;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;


/**
 * Created by apple on 20/09/2017.
 */
public class ExcelExamTemplateBuilder  extends AbstractExcelView{

    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {


        /**
         * GET DATA MODEL WHICH IS PASSSED BY THE SPRING CONTAINER
         */
        List<ExcelExam> excelExamList = (List<ExcelExam>) map.get("examTemplate");

        /**
         * CREATE A NEW EXCEL SHEET
         */
        HSSFSheet sheet = workbook.createSheet("Add Exam Template");
//        sheet.setDefaultColumnWidth(30);

        /**
         * CREATE STYLE FOR HEADER CELLS
         */
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);

        /**
         * CREATE HEADER ROW
         */
        HSSFRow header = sheet.createRow(0);

        header.createCell(0).setCellValue("Major");
        header.getCell(0).setCellStyle(style);

        header.createCell(1).setCellValue("Semester");
        header.getCell(1).setCellStyle(style);

        header.createCell(2).setCellValue("Parent Course Id");
        header.getCell(2).setCellStyle(style);

        header.createCell(3).setCellValue("Child Course Id");
        header.getCell(3).setCellStyle(style);

        header.createCell(4).setCellValue("Parent Course Name");
        header.getCell(4).setCellStyle(style);

        header.createCell(5).setCellValue("Course Short Name");
        header.getCell(5).setCellStyle(style);

        header.createCell(6).setCellValue("Exam Name");
        header.getCell(6).setCellStyle(style);

        header.createCell(7).setCellValue("Exam Date");
        header.getCell(7).setCellStyle(style);

        header.createCell(8).setCellValue("Exam Enrollment Start Date");
        header.getCell(8).setCellStyle(style);

        header.createCell(9).setCellValue("Exam Enrollment End Date");
        header.getCell(9).setCellStyle(style);



        /**
         * CREATE DATA ROWS
         */
        int rowCount = 1;

        for (ExcelExam entityExam : excelExamList ){
            HSSFRow aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(entityExam.getMajorName());
            aRow.createCell(1).setCellValue(entityExam.getSemesterCode());
            aRow.createCell(2).setCellValue(entityExam.getParentCourseId());
            aRow.createCell(3).setCellValue(entityExam.getChildCourseId());
            aRow.createCell(4).setCellValue(entityExam.getParentCourseName());
            aRow.createCell(5).setCellValue(entityExam.getChildCourseName());
            aRow.createCell(6).setCellValue(entityExam.getExamCode());
            aRow.createCell(7).setCellValue(entityExam.getExamDate());
            aRow.createCell(8).setCellValue(entityExam.getEnrolmentStartDate());
            aRow.createCell(9).setCellValue(entityExam.getEnrolmentEndDate());
        }


    }
}

