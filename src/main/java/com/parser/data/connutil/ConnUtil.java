package com.parser.data.connutil;


import java.sql.*;

/**
 * Created by root on 2017/5/5.
 */
public class ConnUtil {

    private String url = "jdbc:mysql://192.168.3.138/crawldata?characterEncoding=utf-8";
    private String user = "root";
    private String password = "edps";

    public Connection getConnection() throws Exception{

        Connection conn;

        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection(url,user,password);
        return conn;
    }

    public void close(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException{
        if(rs != null){
            rs.close();
        }
        if(pstmt != null){
            pstmt.close();
        }
        if(conn != null){
            conn.close();
        }
    }
}
