package com.cdai.util.excelHelpers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cdai.models.tempModels.ExcelClass;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**
 * Created by apple on 18/09/2017.
 */
public class ExcelClassTemplateBuilder extends AbstractExcelView{
    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook workbook, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {


        /**
         * GET DATA MODEL WHICH IS PASSSED BY THE SPRING CONTAINER
         */
        List<ExcelClass> classList = (List<ExcelClass>) map.get("classTemplate");

        /**
         * CREATE A NEW EXCEL SHEET
         */
        HSSFSheet sheet = workbook.createSheet("Add Class Template");
        sheet.setDefaultColumnWidth(30);

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

        header.createCell(0).setCellValue("CLASS NAME");
        header.getCell(0).setCellStyle(style);
        header.createCell(1).setCellValue("IN-TAKE");
        header.getCell(1).setCellStyle(style);

        /**
         * CREATE DATA ROWS
         */
        int rowCount = 1;

        for (ExcelClass entityClass :classList ){
            HSSFRow aRow = sheet.createRow(rowCount++);
            aRow.createCell(0).setCellValue(entityClass.getClassName());
            aRow.createCell(1).setCellValue(entityClass.getIntakeSemester());
        }

    }
}
