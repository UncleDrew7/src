package com.cdai.util.excelHelpers;

import com.cdai.models.tempModels.ExcelStudent;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 05/11/2017.
 */
public class ExcelStudentRetakeGradeTemplateBuilder extends AbstractExcelView{

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // get data model which is passed by the Spring container
        List<ExcelStudent> Student = (List<ExcelStudent>) model.get("student");

        // create a new Excel sheet
        HSSFSheet sheet = workbook.createSheet("Student List");
        sheet.setDefaultColumnWidth(30);

        // create style for Title cells
        CellStyle titlestyle = workbook.createCellStyle();
        titlestyle.setAlignment(CellStyle.ALIGN_CENTER);

        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);
        style.setWrapText(true);

        // create header row
        HSSFRow header = sheet.createRow(1);

        header.createCell(0).setCellValue("序号");
        header.getCell(0).setCellStyle(style);
        sheet.setColumnWidth(0, 3500);

        header.createCell(1).setCellValue("学号");
        header.getCell(1).setCellStyle(style);
        sheet.setColumnWidth(1, 3500);

        header.createCell(2).setCellValue("姓名");
        header.getCell(2).setCellStyle(style);
        sheet.setColumnWidth(2, 3500);

        header.createCell(3).setCellValue("Semester");
        header.getCell(3).setCellStyle(style);
        sheet.setColumnWidth(3, 3500);

        header.createCell(4).setCellValue("Course Id");
        header.getCell(4).setCellStyle(style);
        sheet.setColumnWidth(4, 3500);

        header.createCell(5).setCellValue("Course Name");
        header.getCell(5).setCellStyle(style);
        sheet.setColumnWidth(5, 3500);

        header.createCell(6).setCellValue("Course Name(CN)");
        header.getCell(6).setCellStyle(style);
        sheet.setColumnWidth(6, 3500);

        header.createCell(7).setCellValue("Final Grade");
        header.getCell(7).setCellStyle(style);
        sheet.setColumnWidth(7, 3500);

        // create Title row
        HSSFRow title = sheet.createRow(0);

        sheet.addMergedRegion(new CellRangeAddress(0,0,0,7));
        title.createCell(0).setCellValue("班级成绩汇总表");
        title.getCell(0).setCellStyle(titlestyle);

// create data rows
        int rowCount = 2;

        for (ExcelStudent student : Student) {

            HSSFRow aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(rowCount-2);
            aRow.createCell(1).setCellValue(student.getUserId());
            aRow.createCell(2).setCellValue(student.getFullName());
            aRow.createCell(3).setCellValue(student.getSemester());
            aRow.createCell(4).setCellValue(student.getCourseCode());
            aRow.createCell(5).setCellValue(student.getCourseName());
            aRow.createCell(6).setCellValue(student.getCourseNameCN());
            aRow.createCell(7).setCellValue(student.getFinalAll());
        }
    }

}
