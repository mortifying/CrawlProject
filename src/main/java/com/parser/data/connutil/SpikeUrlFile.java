package com.parser.data.connutil;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 2017/6/2.
 */
public class SpikeUrlFile {

    private File file = null;   //new File("E:\\IDEA_workspace\\CrawlProject\\新建文件夹\\www.tianqihoubao.com.urls.txt");
    private File fileCursor = null;  //new File("E:\\IDEA_workspace\\CrawlProject\\新建文件夹\\www.tianqihoubao.com.cursor.txt");
    private Set<String> list = new HashSet<String>();
    private int linenum = 0;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public SpikeUrlFile(String urls, String cursor){
        file = new File(urls);
        fileCursor = new File(cursor);
    }

    public void readFile(){

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String s = null;
            while ((s = reader.readLine()) != null){

                linenum++;
                if(s.contains(time)){
                    //System.out.println("匹配的url"+s);
                    list.add(s);
                }else if(s.contains(getPrevMonth())){
                    String ss = s.replace(getPrevMonth(),time);
                    list.add(ss);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        System.out.println(list.size());
        for(String s : list){
            //System.out.println(getPrevMonth());
            //System.out.println(time);
            System.out.println("匹配到的:"+s);
        }
        System.out.println(linenum);
    }

    public void writerFile(){

        BufferedWriter writer = null;
        OutputStreamWriter write = null;
        try {
            write = new OutputStreamWriter(new FileOutputStream(file,true));
            writer = new BufferedWriter(write);
            for(String s : list){
                writer.write(s);
                writer.newLine();
                writer.flush();
            }
            write.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(write != null){
                try {
                    write.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void writerCursorFile(){

        BufferedWriter writer = null;
        OutputStreamWriter write = null;
        try {
            write = new OutputStreamWriter(new FileOutputStream(fileCursor));
            writer = new BufferedWriter(write);
            writer.write(String.valueOf(linenum));
            writer.flush();
            write.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(write != null){
                try {
                    write.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Boolean isMonthFirstDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date("2017-07-01"));
        if(calendar.get(Calendar.DAY_OF_MONTH) == 1){
            return true;
        }else{
            return false;
        }
    }

    public String getPrevMonth(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH,-1);
        return sdf.format(calendar.getTime());
    }


    public static void main(String[] args) {
        SpikeUrlFile spikeUrlFile = new SpikeUrlFile("E:\\IDEA_workspace\\CrawlProject\\新建文件夹\\www.tianqihoubao.com.urls.txt","E:\\IDEA_workspace\\CrawlProject\\新建文件夹\\www.tianqihoubao.com.cursor.txt");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        spikeUrlFile.setTime(sdf.format(new Date()));
        //spikeUrlFile.setTime("201707");
        System.out.println(spikeUrlFile.getTime());
        //Calendar calendar = Calendar.getInstance();
        //calendar.setTime(new Date());
        //calendar.add(calendar.DATE,-1);
        //System.out.println(sdf.format(calendar.getTime()));
        //System.out.println("2017-06-02".compareTo(sdf.format(calendar.getTime())));
        spikeUrlFile.readFile();
        //spikeUrlFile.writerFile();
        //spikeUrlFile.writerCursorFile();



    }

}
