package com.cdai.util.excelHelpers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cdai.models.tempModels.ExcelStudent;
import com.cdai.models.tempModels.ExcelTeacher;
import com.cdai.models.testModels.Book;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;



/**
 * Created by apple on 05/11/2017.
 */
public class ExcelStudentEnrolmentTemplateBuilder extends AbstractExcelView{

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // get data model which is passed by the Spring container
        List<ExcelStudent> listStudent = (List<ExcelStudent>) model.get("student");

        // create a new Excel sheet
        HSSFSheet sheet = workbook.createSheet("Student List");
        sheet.setDefaultColumnWidth(30);

        // create style for header cells
//        CellStyle style = workbook.createCellStyle();
//        Font font = workbook.createFont();
//        font.setFontName("Arial");
//        style.setFillForegroundColor(HSSFColor.BLUE.index);
//        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//        font.setColor(HSSFColor.WHITE.index);
//        style.setFont(font);

        // create header row
        HSSFRow header = sheet.createRow(0);

        header.createCell(0).setCellValue("User Id");
//        header.getCell(0).setCellStyle(style);

        header.createCell(1).setCellValue("Full Name");
//        header.getCell(1).setCellStyle(style);



        // create data rows
        int rowCount = 1;

        for (ExcelStudent student : listStudent) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(student.getUserId());
            aRow.createCell(1).setCellValue(student.getFullName());

        }
    }

}
