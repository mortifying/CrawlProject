package com.parser.data;


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
public class GDPDataParser {



    public static void main(String[] args) {

        Connection conn = null;
        PreparedStatement pstmt =null;
        ResultSet rs =null;

        GDPSumValueBean sumGDP;
        SAXReader reader = new SAXReader();
        ConnUtil connUtil = new ConnUtil();
        try {
            Document document = reader.read(new File("C:\\Users\\Administrator\\Downloads\\分省季度数据 (7).xml"));
            Element root = document.getRootElement();
            Iterator it = root.elementIterator();

            conn = connUtil.getConnection();
            while (it.hasNext()){
                //System.out.println("开始遍历某一数据");
                Element data = (Element) it.next();
                //System.out.println("属性名："+ data.getName()+"\n属性值："+data.getStringValue());
                /*List<Attribute> dataAttr = data.attributes();
                for(Attribute attr : dataAttr){
                    System.out.println("属性名："+ attr.getName()+"--属性值："+attr.getStringValue());
                }*/

               Iterator itt = data.elementIterator();
                while (itt.hasNext()){
                    Element record = (Element) itt.next();
                    //System.out.println("属性名："+ dataChild.getName()+"--属性值："+dataChild.getStringValue());
                    Iterator ittt = record.elementIterator();
                    sumGDP = new GDPSumValueBean();
                    while (ittt.hasNext()){
                        Element field = (Element)ittt.next();
                        List<Attribute> dataGChildAttr = field.attributes();
                        for(Attribute attr : dataGChildAttr) {
                            //System.out.println("属性名：" + attr.getName() + "--属性值：" + attr.getValue());
                            //System.out.println("标签名："+ field.getName()+"--标签值："+field.getStringValue());
                            if(attr.getValue().equals("指标")){
                                sumGDP.setGDPSumValue(field.getStringValue());
                            }else if(attr.getValue().equals("地区")){
                                sumGDP.setRegion(field.getStringValue());
                            }else if(attr.getValue().equals("时间")){
                                sumGDP.setYear(field.getStringValue().substring(0,4));
                                sumGDP.setQuarter(field.getStringValue().substring(5));
                            }else {
                                if(!field.getStringValue().equals("")){
                                    sumGDP.setGDPValue(Double.valueOf(field.getStringValue()));
                                }else {
                                    sumGDP.setGDPValue(0);
                                }

                            }
                        }
                    }
                    System.out.println(sumGDP.getGDPSumValue()+"\n"+sumGDP.getRegion()+"\n"+sumGDP.getYear()+"\n"+sumGDP.getQuarter()+"\n"+sumGDP.getGDPValue());

                    String sql = "insert into quarterdata(zb,region,year,quarter,datavalue) values(?,?,?,?,?)";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1,sumGDP.getGDPSumValue());
                    pstmt.setString(2,sumGDP.getRegion());
                    pstmt.setString(3,sumGDP.getYear());
                    pstmt.setString(4,sumGDP.getQuarter());
                    pstmt.setDouble(5,sumGDP.getGDPValue());
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
