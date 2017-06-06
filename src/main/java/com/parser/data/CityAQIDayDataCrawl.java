package com.parser.data;

import com.parser.data.bean.CityAQIDayData;
import com.parser.data.connutil.AQIPipeline;
import com.parser.data.connutil.SpikeFileCacheQueueScheduler;
import com.parser.data.connutil.SpikeUrlFile;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.component.HashSetDuplicateRemover;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by root on 2017/5/8.
 */
public class CityAQIDayDataCrawl implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(10000);
    CityAQIDayData cityAQIDayData;
    //ArrayList<CityAQIDayData> list = new ArrayList<CityAQIDayData>();
    //List list = Collections.synchronizedList(new ArrayList<CityAQIDayData>());

    public void process(Page page) {

        ArrayList<CityAQIDayData> listData = new ArrayList<CityAQIDayData>();

        if(!page.getUrl().regex("http://www\\.tianqihoubao\\.com/aqi/[a-z]+-[0-9]{6}\\.html").match()){

            List<String> list1 = page.getHtml().xpath("//*[@id=\"content\"]/div[2]").links().regex("http://www\\.tianqihoubao\\.com/aqi/[a-z]+\\.html").all();
            List<String> list = page.getHtml().xpath("//*[@id=\"bd\"]/div[1]/div[3]/ul").links().regex("http://www\\.tianqihoubao\\.com/aqi/[a-z]+-[0-9]{6}\\.html").all();
            //List<String> list = page.().regex("http://www\\.tianqihoubao\\.com/aqi/[a-z]+-[0-9]{6}\\.html").links().all();

            //HashSet h = new HashSet(list1);
            list.addAll(list1);
            page.addTargetRequests(list);
        }else {



            //CityAQIDayData aqiData = new CityAQIDayData();
            String str = page.getHtml().xpath("//*[@id=\"content\"]/h1/text()").get();
            page.putField("城市：",str.substring(str.indexOf("月")+1,str.indexOf("空气质量")));
           for(int i=2;i<=33;i++){
               /*page.putField("日期：",page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[1]/text()").toString());
               page.putField("质量等级：",page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[2]/text()").toString());
               page.putField("当天AQI排名：",page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[3]/text()").get());
               page.putField("AQI：",page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[4]/text()").get());
               page.putField("PM2.5：",page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[5]/text()").get());
               page.putField("PM10：",page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[6]/text()").get());
               page.putField("SO2：",page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[7]/text()").get());
               page.putField("NO2：",page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[8]/text()").get());
               page.putField("CO：",page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[9]/text()").get());
               page.putField("O3：",page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[10]/text()").get());*/
               if((page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[1]/text()").toString() == null)
                       || ((page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[1]/text()").toString().trim().compareTo(getYestoday()) < 0)
                       //&(page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[1]/text()").toString().trim().compareTo(getYestoday()) > -2)
                       )){
                   //System.out.println(getYestoday());
                   //System.out.println(page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[1]/text()").toString());
                   continue;
               }
               cityAQIDayData = new CityAQIDayData();
               cityAQIDayData.setRegion(str.substring(str.indexOf("月")+1,str.indexOf("空气质量")));
               cityAQIDayData.setDate(page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[1]/text()").toString());
               cityAQIDayData.setAqiLevel(page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[2]/text()").toString());
               cityAQIDayData.setAqiOrder(page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[4]/text()").toString());
               cityAQIDayData.setAqi(page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[3]/text()").toString());
               cityAQIDayData.setPm25(page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[5]/text()").toString());
               cityAQIDayData.setPm10(page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[6]/text()").toString());
               cityAQIDayData.setSo2(page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[8]/text()").toString());
               cityAQIDayData.setNo2(page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[7]/text()").toString());
               cityAQIDayData.setCo(page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[9]/text()").toString());
               cityAQIDayData.setO3(page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[10]/text()").toString());

               listData.add(cityAQIDayData);

               //System.out.println("日期："+page.getHtml().xpath("/*//*[@id=\"content\"]/div[3]/table/tbody/tr["+i+"]/td[1]/text()").toString());
           }

        }

        if(listData.size() == 0){
            System.out.println(listData.size());
            page.setSkip(true);
        }else {
            System.out.println(listData.size());
            page.putField("list",listData);
        }
    }

    public Site getSite() {
        return site;
    }

    public String getYestoday(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(calendar.DATE,-1);
        return sdf.format(calendar.getTime());
    }

    public static void main(String[] args) {

        //SpikeFileCacheQueueScheduler file = new SpikeFileCacheQueueScheduler("E:\\IDEA_workspace\\CrawlProject\\新建文件夹");
        //file.setRegx("http://www\\.tianqihoubao\\.com/aqi/[a-z]+-201706\\.html");
        Spider.create(new CityAQIDayDataCrawl())
                .addUrl("http://www.tianqihoubao.com/aqi/")
                .setScheduler(new FileCacheQueueScheduler("E:\\IDEA_workspace\\CrawlProject\\新建文件夹"))
                .addPipeline(new AQIPipeline())
                .run();


        SpikeUrlFile spikeUrlFile = new SpikeUrlFile("E:\\IDEA_workspace\\CrawlProject\\新建文件夹\\www.tianqihoubao.com.urls.txt","E:\\IDEA_workspace\\CrawlProject\\新建文件夹\\www.tianqihoubao.com.cursor.txt");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        spikeUrlFile.setTime(sdf.format(new Date()));
        System.out.println(spikeUrlFile.getTime());
        spikeUrlFile.readFile();
        spikeUrlFile.writerFile();
        spikeUrlFile.writerCursorFile();

    }
}
