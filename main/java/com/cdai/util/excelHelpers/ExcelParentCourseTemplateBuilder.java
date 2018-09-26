package com.cdai.util.excelHelpers;

import com.cdai.models.tempModels.ExcelCourse;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 13/12/2017.
 */

public class ExcelParentCourseTemplateBuilder  extends AbstractExcelView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // get data model which is passed by the Spring container
        List<ExcelCourse> listCourse = (List<ExcelCourse>) model.get("pcrsTemplate");

        // create a new Excel sheet
        HSSFSheet sheet = workbook.createSheet("Add Course Template");
        sheet.setDefaultColumnWidth(30);

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

        header.createCell(0).setCellValue("Parent Course Id");
        header.getCell(0).setCellStyle(style);

        header.createCell(1).setCellValue("Course Type");
        header.getCell(1).setCellStyle(style);

        header.createCell(2).setCellValue("Course Short Name");
        header.getCell(2).setCellStyle(style);

        header.createCell(3).setCellValue("Course Name");
        header.getCell(3).setCellStyle(style);

        header.createCell(4).setCellValue("Credits");
        header.getCell(4).setCellStyle(style);

        header.createCell(5).setCellValue("Course Description");
        header.getCell(5).setCellStyle(style);


        // create data rows
        int rowCount = 1;

        for (ExcelCourse course : listCourse) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(course.getParentCourseId());
            aRow.createCell(1).setCellValue(course.getCourseType());

            aRow.createCell(2).setCellValue(course.getCourseShortName());
            aRow.createCell(3).setCellValue(course.getCourseName());
            aRow.createCell(4).setCellValue(course.getCredits());
            aRow.createCell(5).setCellValue(course.getCourseDescription());
        }
    }

}
