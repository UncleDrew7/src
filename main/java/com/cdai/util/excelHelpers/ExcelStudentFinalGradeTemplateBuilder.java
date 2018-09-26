package com.cdai.util.excelHelpers;

import build.model.CourseEnrolment;
import build.model.Courses;
import build.model.User;
import com.cdai.models.tempModels.ExcelStudent;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 05/11/2017.
 */
public class ExcelStudentFinalGradeTemplateBuilder extends AbstractExcelView{

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // get data model which is passed by the Spring container
        //List<ExcelStudent> listStudent = (List<ExcelStudent>) model.get("students");

//        int semId = (int) model.get("semester");
        String semester = (String) model.get("semester");
        List<User> studentList = (List<User>)model.get("students");
        List<Courses> courseList = (List<Courses>)model.get("courses");
        List<CourseEnrolment> resultList = (List<CourseEnrolment>)model.get("results");

//        if ( studentList == null || courseList == null || resultList == null )
//            return;

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
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.BLACK.index);
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

        int coursePos = 3;
        if ( courseList != null )
            for (Courses course1 : courseList) {
                header.createCell(coursePos).setCellValue(course1.getCourseName());
                header.getCell(coursePos).setCellStyle(style);
                sheet.setColumnWidth(coursePos++, 3500);
            }

        // create Title row
        HSSFRow title = sheet.createRow(0);

        String x = semester;
        x = "20" + x.substring(0, 3) +"20"+ x.substring(3, 5) +"学年第"+ x.substring(6, x.length());

        sheet.addMergedRegion(new CellRangeAddress(0,0,0,(coursePos-1)));
        title.createCell(0).setCellValue(x + "学期班级成绩汇总表");
        title.getCell(0).setCellStyle(titlestyle);


// create data rows
        int rowCount = 2;

        if ( studentList != null )
            for (User student : studentList) {
                HSSFRow aRow = sheet.createRow(rowCount++);
                aRow.createCell(0).setCellValue(rowCount-2);
                aRow.createCell(1).setCellValue(student.getUserId());
                aRow.createCell(2).setCellValue(student.getUserName());

                if ( courseList != null )
                    for ( Courses course : courseList ) {
                        int col = 3;
                        if ( resultList != null )
                            for ( CourseEnrolment result : resultList ) {
                                if (course.getCourseName().equals(result.getCourseCode()) && student.getUserId() == result.getStudentId() ) {
                                    aRow.createCell(col++).setCellValue(result.getFinalAll());
                                    break;
                                }
                            }
                    }
            }
    }
 }
