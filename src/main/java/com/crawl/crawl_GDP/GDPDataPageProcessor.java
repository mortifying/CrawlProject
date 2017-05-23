package com.crawl.crawl_GDP;


import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;


/**
 * Created by root on 2017/5/3.
 */
public class GDPDataPageProcessor implements PageProcessor{

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    public void process(Page page) {


        /*if(page != null){
            System.out.println(page.toString());
        }

        if(page.getJson() != null){
            System.out.println("Json");
        }*/

        System.out.println("chenggong");
        //page.putField("title", "afds");

        //page.putField("状态",page.getJson().jsonPath("$.status").get());
        //page.putField("地址:",page.getJson().jsonPath("$.result.formatted_address").get());
        //page.putField("城市:",page.getJson().jsonPath("$.result.addressComponent.city").get());
        //page.putField("diqu",page.getJson().jsonPath("$.status").get());
        //page.putField("diqu",page.getJson().jsonPath("$.status").get());
        page.putField("ack:",page.getJson().jsonPath("$.data[1].content").get());

    }

    public Site getSite() {
        return site;
    }


    public static void main(String[] args) {
        //System.out.println("url地址：\\\"safd");

        //String url = "http://data.stats.gov.cn/easyquery.htm?m=QueryData&dbcode=fsjd&rowcode=reg&colcode=sj&wds={\"wdcode\":\"zb\",\"valuecode\":\"A010101\"}&dfwds={\"wdcode\":\"sj\",\"valuecode\":\"LAST1\"}";
        //String url1 = "http://data.stats.gov.cn/easyquery.htm?m=QueryData&dbcode=hgnd&rowcode=zb&colcode=sj&wds=%5B%5D&dfwds=%5B%5D";
        //String url2 = "http://data.stats.gov.cn/easyquery.htm?m=QueryData&dbcode=fsjd&rowcode=reg&colcode=sj&wds=%5B%7B%22wdcode%22%3A%22zb%22%2C%22valuecode%22%3A%22A010101%22%7D%5D&dfwds=%5B%7B%22wdcode%22%3A%22sj%22%2C%22valuecode%22%3A%22LAST2%22%7D%5D";
        //System.out.println(url2.length());

        Spider.create(new GDPDataPageProcessor())
                .addUrl("http://angularjs.cn/api/article/latest?p=1&s=20")
                .run();

        /*Spider.create(new GDPDataPageProcessor())
                .addUrl("http://data.stats.gov.cn/easyquery.htm?cn=E0102")
                .run();

        Spider.create(new GDPDataPageProcessor())
                .addUrl("http://data.stats.gov.cn/easyquery.htm?id=1")
                .run();*/

        //Request request = new Request(url);
        /*Spider.create(new GDPDataPageProcessor())
                .addUrl("http://api.map.baidu.com/geocoder?location=39.889442%2C116.54209&output=json&key=iiwu3dZS05pcTYtzhVd0gtiYLC8U4rW5")
                .run();*/

       /* Spider.create(new GDPDataPageProcessor())
                .addUrl("http://www.pm25.in/api/querys/aqi_ranking.json")
                .run();*/


        /*try {
            Spider.create(new GDPDataPageProcessor())
                    .addUrl(URLEncoder.encode(url,"utf-8"))
                    .run();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/


    }


}
