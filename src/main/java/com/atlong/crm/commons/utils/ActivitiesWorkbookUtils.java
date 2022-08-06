package com.atlong.crm.commons.utils;

import com.atlong.crm.settings.domain.User;
import com.atlong.crm.workbench.domian.Activity;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;

/**
 * @Author: YunLong
 * @Date: 2022/8/4 23:07
 */
public class ActivitiesWorkbookUtils {
    private static HSSFWorkbook wb = null;

    //获取模板市场活动表
    public static HSSFWorkbook getMouldWorkbook(List<User> users) {
        wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("市场活动模板列表");
        HSSFRow row = sheet.createRow(0);
        /*cell.setCellValue("ID");
        cell = row.createCell(1);*/
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("所有者(若有name相同的所有者,请在备注列填写所对应的login_act)");
        cell = row.createCell(1);
        cell.setCellValue("市场名称");
        cell = row.createCell(2);
        cell.setCellValue("开始日期(yyyy-mm-dd)");
        cell = row.createCell(3);
        cell.setCellValue("结束日期(yyyy-mm-dd)");
        cell = row.createCell(4);
        cell.setCellValue("成本(非负整数)");
        cell = row.createCell(5);
        cell.setCellValue("描述");
        cell = row.createCell(6);
        cell.setCellValue("备注(所有者name相同在此填写login_act)");
        sheet = wb.createSheet("用户姓名账号关系列表");
        row = sheet.createRow(0);
        cell=row.createCell(0);
        cell.setCellValue("name");
        cell=row.createCell(1);
        cell.setCellValue("login_act");
        if (users != null && users.size() > 0) {
            for (int i = 0; i < users.size(); i++) {
                row = sheet.createRow(i + 1);
                //每一行创建2列，每一列的数据从users中获取
                cell = row.createCell(0);
                cell.setCellValue(users.get(i).getName());
                cell = row.createCell(1);
                cell.setCellValue(users.get(i).getLoginAct());
            }
        }
        return wb;
    }

    /**
     * 获取市场活动表
     */
    public static HSSFWorkbook getWorkbook(List<Activity> activities) {
        //查询所有的市场活动
        wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("市场活动列表");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell = row.createCell(1);
        cell.setCellValue("所有者");
        cell = row.createCell(2);
        cell.setCellValue("名称");
        cell = row.createCell(3);
        cell.setCellValue("开始日期");
        cell = row.createCell(4);
        cell.setCellValue("结束日期");
        cell = row.createCell(5);
        cell.setCellValue("成本");
        cell = row.createCell(6);
        cell.setCellValue("描述");
        cell = row.createCell(7);
        cell.setCellValue("创建时间");
        cell = row.createCell(8);
        cell.setCellValue("创建者");
        cell = row.createCell(9);
        cell.setCellValue("修改时间");
        cell = row.createCell(10);
        cell.setCellValue("修改者");
        //遍历activityList，创建HSSFRow对象，生成所有的数据行
        if (activities != null && activities.size() > 0) {
            Activity activity = null;
            for (int i = 0; i < activities.size(); i++) {
                activity = activities.get(i);
                //每遍历出一个activity，生成一行
                row = sheet.createRow(i + 1);
                //每一行创建11列，每一列的数据从activity中获取
                cell = row.createCell(0);
                cell.setCellValue(activity.getId());
                cell = row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell = row.createCell(2);
                cell.setCellValue(activity.getName());
                cell = row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell = row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell = row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell = row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell = row.createCell(7);
                cell.setCellValue(activity.getCreateTime());
                cell = row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell = row.createCell(9);
                cell.setCellValue(activity.getEditTime());
                cell = row.createCell(10);
                cell.setCellValue(activity.getEditBy());
            }
        }
        return wb;
    }
}
