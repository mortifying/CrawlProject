package com.parser.data;


import com.parser.data.bean.CityByYearDataBean;
import com.parser.data.bean.GDPSumValueBean;
import com.parser.data.connutil.ConnUtil;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by root on 2017/5/5.
 */
public class CityByYearData {



    public static void main(String[] args) {

        Connection conn = null;
        PreparedStatement pstmt =null;
        ResultSet rs =null;

        CityByYearDataBean sumGDP;
        SAXReader reader = new SAXReader();
        ConnUtil connUtil = new ConnUtil();
        try {
            Document document = reader.read(new File("F:\\主要城市年度数据 (2).xml"));
            Element root = document.getRootElement();
            Iterator it = root.elementIterator();

            conn = connUtil.getConnection();
            while (it.hasNext()){

                Element data = (Element) it.next();

               Iterator itt = data.elementIterator();
                while (itt.hasNext()){
                    Element record = (Element) itt.next();
                    //System.out.println("属性名："+ dataChild.getName()+"--属性值："+dataChild.getStringValue());
                    Iterator ittt = record.elementIterator();
                    sumGDP = new CityByYearDataBean();
                    while (ittt.hasNext()){
                        Element field = (Element)ittt.next();
                        List<Attribute> dataGChildAttr = field.attributes();
                        for(Attribute attr : dataGChildAttr) {
                            //System.out.println("属性名：" + attr.getName() + "--属性值：" + attr.getValue());
                            //System.out.println("标签名："+ field.getName()+"--标签值："+field.getStringValue());
                            if(attr.getValue().equals("指标")){
                                sumGDP.setZb(field.getStringValue());
                            }else if(attr.getValue().equals("地区")){
                                sumGDP.setRegion(field.getStringValue());
                            }else if(attr.getValue().equals("时间")){
                                sumGDP.setYear(field.getStringValue().substring(0,4)); //截取年
                                //sumGDP.setYear(field.getStringValue());
                                //sumGDP.setQuarter(field.getStringValue().substring(5));
                            }else {
                                if(!field.getStringValue().equals("")){
                                    sumGDP.setData(Double.valueOf(field.getStringValue()));
                                }else {
                                    sumGDP.setData(0.0);
                                }

                            }
                        }
                    }
                    System.out.println(sumGDP.getZb()+"\n"+sumGDP.getRegion()+"\n"+sumGDP.getYear()+"\n"+sumGDP.getData());

                    String sql = "insert into quarterdata(zb,region,year,quarter,datavalue) values(?,?,?,?,?)";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1,sumGDP.getZb());
                    pstmt.setString(2,sumGDP.getRegion());
                    pstmt.setString(3,sumGDP.getYear());
                    pstmt.setString(4,"");
                    pstmt.setDouble(5,sumGDP.getData());
                    int i = pstmt.executeUpdate();
                    if(i > 0){
                        System.out.println("数据插入成功！");
                    }
                    System.out.println("-------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                connUtil.close(conn,pstmt,rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
}
