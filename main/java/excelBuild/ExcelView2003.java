package excelBuild;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import excelBuild.Room;
import excelBuild.AbstractExcelPOIView;


/**
 * Created by apple on 16/08/2017.
 */
public class ExcelView2003 extends AbstractExcelPOIView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        List<Room> listRooms = (List<Room>) model.get("listRooms");

        Sheet sheet = workbook.createSheet("2003");
        Row row = null;
        Cell cell = null;
        int r = 0;
        int c = 0;

        // Style for header cell
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(CellStyle.ALIGN_CENTER);

        // Create header cells
        row = sheet.createRow(r++);

        cell = row.createCell(c++);
        cell.setCellStyle(style);
        cell.setCellValue("id");

        cell = row.createCell(c++);
        cell.setCellStyle(style);
        cell.setCellValue("nameRoom");

        cell = row.createCell(c++);
        cell.setCellStyle(style);
        cell.setCellValue("countRoom");

        // Create data cell
        for (Room room : listRooms) {
            row = sheet.createRow(r++);
            c = 0;
            row.createCell(c++).setCellValue(room.getId());
            row.createCell(c++).setCellValue(room.getNameRoom());
            row.createCell(c++).setCellValue(room.getCountRoom());

        }
        for (int i = 0; i < 3; i++)
            sheet.autoSizeColumn(i, true);

    }

    @Override
    protected Workbook createWorkbook() {
        // TODO Auto-generated method stub
        // 2003
        return new HSSFWorkbook();
    }
}
