package com.parser.data.bean;

import com.parser.data.connutil.ConnUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by root on 2017/5/10.
 */
public class ComplaintCaseDataDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public void caseDataSave(ArrayList<ComplaintCaseBean> list){

        String sql = "insert into complaintcase(casecode,year,month,province,enterprise,question,handle,comment) values(?,?,?,?,?,?,?,?);";
        ConnUtil connUtil = new ConnUtil();

        try {
            conn = connUtil.getConnection();

            for (ComplaintCaseBean complaintCaseBean:list){
                ps = conn.prepareStatement(sql);
                ps.setString(1,complaintCaseBean.getCaseCode());
                ps.setString(2,complaintCaseBean.getYear());
                ps.setString(3,complaintCaseBean.getMonth());
                ps.setString(4,complaintCaseBean.getProvince());
                ps.setString(5,complaintCaseBean.getEnterprise());
                ps.setString(6,complaintCaseBean.getQuestion());
                ps.setString(7,complaintCaseBean.getHandle());
                ps.setString(8,complaintCaseBean.getComment());

                int i = ps.executeUpdate();
                if(i > 0){

                    System.out.println("案件编号："+complaintCaseBean.getCaseCode()+"\n年份："+complaintCaseBean.getYear()+"\n月份："
                            +complaintCaseBean.getMonth()+"\n省份："+complaintCaseBean.getProvince()+"\n涉及企业："+complaintCaseBean.getEnterprise()
                            +"\n存在问题："+complaintCaseBean.getQuestion()+"\n处理情况："+complaintCaseBean.getHandle()+"\n备注："+complaintCaseBean.getComment());
                    System.out.println("--------------------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                connUtil.close(conn,ps,rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
