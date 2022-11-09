package io.github.wj0410.core.tools.util.test;

import io.github.wj0410.core.tools.excel.ApachePoiExcelUtil;
import lombok.Data;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PoiExcelTest {

    public static void main(String[] args) throws SQLException {
        List<Student> list = new ArrayList<>();
        Student s1 = new Student();
        s1.setName("wangjie");
        s1.setCourse("math");
        s1.setScore(100);
        Student s2 = new Student();
        s2.setName("wangjun");
        s2.setCourse("math");
        s2.setScore(90);
        list.add(s1);
        list.add(s2);
        final String title[] = {"名字:name:50", "课程:course:50", "分数:score:50"};
        final String sheetName = "sheet1";
        HSSFWorkbook workbook = ApachePoiExcelUtil.export(null,list, title, sheetName);
    }
    @Data
    static class Student {
        private String name;
        private String course;
        private int score;
    }
}
