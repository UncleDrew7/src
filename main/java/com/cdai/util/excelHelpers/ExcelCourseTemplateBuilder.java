package com.cdai.util.excelHelpers;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.cdai.models.tempModels.ExcelCourse;

/**
 * Created by apple on 18/08/2017.
 * configure location in view.xml so that spring can acess file
 */
public class ExcelCourseTemplateBuilder extends AbstractExcelView{
    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // get data model which is passed by the Spring container
        List<ExcelCourse> listCourse = (List<ExcelCourse>) model.get("crsTemplate");

        // create a new Excel sheet
        HSSFSheet sheet = workbook.createSheet("Add Course Template");
//        sheet.setDefaultColumnWidth(30);

        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);

        // create header row
        HSSFRow header = sheet.createRow(0);

        header.createCell(0).setCellValue("Major");
        header.getCell(0).setCellStyle(style);

        header.createCell(1).setCellValue("Course Id");
        header.getCell(1).setCellStyle(style);

        header.createCell(2).setCellValue("Course Type");
        header.getCell(2).setCellStyle(style);

        header.createCell(3).setCellValue("Credits");
        header.getCell(3).setCellStyle(style);

        header.createCell(4).setCellValue("Course Name");
        header.getCell(4).setCellStyle(style);

        header.createCell(5).setCellValue("Course Short Name");
        header.getCell(5).setCellStyle(style);

        header.createCell(6).setCellValue("Enrolment Start Date");
        header.getCell(6).setCellStyle(style);

        header.createCell(7).setCellValue("Enrolment Deadline");
        header.getCell(7).setCellStyle(style);

        header.createCell(8).setCellValue("Teacher Id");
        header.getCell(8).setCellStyle(style);

        header.createCell(9).setCellValue("Teacher Name");
        header.getCell(9).setCellStyle(style);


        // create data rows
        int rowCount = 1;

        for (ExcelCourse course : listCourse) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(course.getMajor());
            aRow.createCell(1).setCellValue(course.getParentCourseId());
            aRow.createCell(2).setCellValue(course.getCourseType());
            aRow.createCell(3).setCellValue(course.getCredits());
            aRow.createCell(4).setCellValue(course.getCourseName());
            aRow.createCell(5).setCellValue("");
            aRow.createCell(6).setCellValue("");
            aRow.createCell(7).setCellValue("");
            aRow.createCell(8).setCellValue("");
            aRow.createCell(9).setCellValue("");

        }
    }

}