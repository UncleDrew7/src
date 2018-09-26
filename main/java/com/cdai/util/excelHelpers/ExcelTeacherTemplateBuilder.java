package com.cdai.util.excelHelpers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * Created by apple on 17/08/2017.
 * configure location in view.xml so that spring can acess file
 */
public class ExcelTeacherTemplateBuilder extends AbstractExcelView{
    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // get data model which is passed by the Spring container
        List<ExcelTeacher> listTeachers = (List<ExcelTeacher>) model.get("template");

        // create a new Excel sheet
        HSSFSheet sheet = workbook.createSheet("Add Teacher Template");
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

        header.createCell(0).setCellValue("User Id");
        header.getCell(0).setCellStyle(style);

        header.createCell(1).setCellValue("User Role");
        header.getCell(1).setCellStyle(style);

        header.createCell(2).setCellValue("First Name");
        header.getCell(2).setCellStyle(style);

        header.createCell(3).setCellValue("Last Name");
        header.getCell(3).setCellStyle(style);

        header.createCell(4).setCellValue("Gender");
        header.getCell(4).setCellStyle(style);

        header.createCell(5).setCellValue("Email");
        header.getCell(5).setCellStyle(style);

        header.createCell(6).setCellValue("City/Town");
        header.getCell(6).setCellStyle(style);

        header.createCell(7).setCellValue("Country");
        header.getCell(7).setCellStyle(style);

        // create data rows
        int rowCount = 1;

        for (ExcelTeacher teacher : listTeachers) {
            HSSFRow aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(teacher.getUserId());
            aRow.createCell(1).setCellValue(teacher.getRole());
            aRow.createCell(2).setCellValue(teacher.getFirstName());
            aRow.createCell(3).setCellValue(teacher.getLastName());
            aRow.createCell(4).setCellValue(teacher.getGender());
            aRow.createCell(5).setCellValue(teacher.getEmail());
            aRow.createCell(6).setCellValue(teacher.getCity());
            aRow.createCell(7).setCellValue(teacher.getCountry());
        }
    }

}