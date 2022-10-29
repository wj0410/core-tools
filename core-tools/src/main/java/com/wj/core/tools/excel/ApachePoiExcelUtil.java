package com.wj.core.tools.excel;

import com.wj.core.tools.restful.exception.ServiceException;
import com.wj.core.tools.util.CommonUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

/**
 * @author wangjie
 * @version 1.0
 * @date 2021年08月05日15时41分
 */
public final class ApachePoiExcelUtil {

    public static HSSFWorkbook export(HSSFWorkbook workbook, List list, String[] title, String sheetName) {
        //1.创建Excel工作簿
        if (workbook == null) {
            workbook = new HSSFWorkbook();
        }
        //2.创建一个工作表
        HSSFSheet sheet = workbook.createSheet(sheetName);
        //3.创建第一行
        HSSFRow row = sheet.createRow(0);
        //4.插入第一行表头数据
        HSSFCell cell = null;
        for (int i = 0; i < title.length; i++) {
            try {
                //创建单元格
                cell = row.createCell(i);
                //设置值
                String[] split = title[i].split(":");
                cell.setCellValue(split[0]);
                //设置列的宽度
                sheet.setColumnWidth(i, Integer.parseInt(split[2]) * 100);
                //设置默认单元格样式
                setDefaultCellStyle(workbook, cell);
//            setCellStyle(cell).setWrapText(true);
//            setCellStyle(cell).setAlignment(HorizontalAlignment.CENTER);
            } catch (Exception e) {
                throw new ServiceException("title数组配置有误，请检查！格式为[excel表头名称:导出实体对应的属性名称:宽度]");
            }
        }
        //5.插入数据
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            for (int j = 0; j < title.length; j++) {
                String[] split = title[j].split(":");
                try {
                    String methodName = "get" + CommonUtil.captureName(split[1]);
                    Method method = list.get(i).getClass().getMethod(methodName);
                    Object value = method.invoke(list.get(i));
                    //创建单元格
                    cell = row.createCell(j);
                    //设置值
                    cell.setCellValue(value == null ? "" : value.toString());
                    //设置默认单元格样式
                    setDefaultCellStyle(workbook, cell);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ServiceException("插入数据出错！error:" + e.getMessage());
                }
            }
        }
        return workbook;
    }

    /**
     * 给单元格设置默认样式
     *
     * @param workbook
     * @param cell
     */
    public static void setDefaultCellStyle(HSSFWorkbook workbook, HSSFCell cell) {
        //创建样式
        CellStyle style = workbook.createCellStyle();
        //自动换行
        style.setWrapText(true);
        //水平对齐方式（居中）
        style.setAlignment(HorizontalAlignment.CENTER);
        //垂直对齐方式（居中）
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        //创建字体设置
        Font baseFont = workbook.createFont();
        //字体
        baseFont.setFontName("宋体");
        //大小
        baseFont.setFontHeightInPoints((short) 12);
        style.setFont(baseFont);
        //行列的边框
        style.setBorderBottom(BorderStyle.THIN); //下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderRight(BorderStyle.THIN);//右边框

        cell.setCellStyle(style);
    }

    /**
     * 给单元格设置样式
     *
     * @param cell
     * @return
     */
    public static CellStyle setCellStyle(HSSFCell cell) {
        //创建样式
        CellStyle style = cell.getCellStyle();
        return style;
    }

    /**
     * 导出
     *
     * @param fileName
     * @param response
     * @param workbook
     */
    public static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            if (fileName.toLowerCase().indexOf(".xls") == -1 || fileName.toLowerCase().indexOf(".xlsx") == -1) {
                fileName += ".xlsx";
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
            workbook.write(response.getOutputStream());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}