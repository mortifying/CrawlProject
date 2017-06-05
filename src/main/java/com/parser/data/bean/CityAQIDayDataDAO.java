package com.parser.data.bean;

import com.parser.data.connutil.ConnUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by root on 2017/5/9.
 */
public class CityAQIDayDataDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public void aqiDataSave(ArrayList<CityAQIDayData> list){

        String sql = "insert into cityaqidaydata(region,date,aqilevel,aqi,aqiorder,pm25,pm10,so2,no2,co,o3) values(?,?,?,?,?,?,?,?,?,?,?);";
        ConnUtil connUtil = new ConnUtil();
        try {
            conn = connUtil.getConnection();
            for(CityAQIDayData cityAQIDayData:list){
                ps = conn.prepareStatement(sql);
                ps.setString(1,cityAQIDayData.getRegion());
                ps.setString(2,cityAQIDayData.getDate());
                ps.setString(3,cityAQIDayData.getAqiLevel());
                ps.setString(4,cityAQIDayData.getAqi());
                ps.setString(5,cityAQIDayData.getAqiOrder());
                ps.setString(6,cityAQIDayData.getPm25());
                ps.setString(7,cityAQIDayData.getPm10());
                ps.setString(8,cityAQIDayData.getSo2());
                ps.setString(9,cityAQIDayData.getNo2());
                ps.setString(10,cityAQIDayData.getCo());
                ps.setString(11,cityAQIDayData.getO3());

                int i = ps.executeUpdate();
                if(i > 0){
                    System.out.println("城市："+cityAQIDayData.getRegion()+"\n"+"日期："+cityAQIDayData.getDate()+"\n"+"空气质量等级："+cityAQIDayData.getAqiLevel()+"\n"
                            +"AQI："+cityAQIDayData.getAqi()+"\n" +"当天AQI排名："+cityAQIDayData.getAqiOrder()+"\n"+"PM2.5："+cityAQIDayData.getPm25()+"\n"+"PM10："+cityAQIDayData.getPm10()+"\n"
                            +"SO2："+cityAQIDayData.getSo2()+"\n"+"NO2："+cityAQIDayData.getNo2() +"\n"+"CO："+cityAQIDayData.getCo()+"\n"+"O3："+cityAQIDayData.getO3());
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
        //list.clear();
    }

}
