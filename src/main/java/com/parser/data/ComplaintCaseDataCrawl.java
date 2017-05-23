package com.parser.data;


import com.parser.data.bean.ComplaintCaseBean;
import com.parser.data.connutil.ComplaintCasePipeline;
import org.apache.commons.collections.functors.StringValueTransformer;
import org.apache.commons.collections.map.HashedMap;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 2017/5/10.
 */
public class ComplaintCaseDataCrawl implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
    ComplaintCaseBean comp;

    public void process(Page page) {
        ArrayList<ComplaintCaseBean> list = new ArrayList<ComplaintCaseBean>();

        for(int i=2;i<=21;i++){
            if(page.getHtml().xpath("//*[@id=\"GridView1\"]/tbody/tr["+i+"]/td[1]/text()").toString() == null){
                continue;
            }
            comp = new ComplaintCaseBean();
            comp.setCaseCode(page.getHtml().xpath("//*[@id=\"GridView1\"]/tbody/tr["+i+"]/td[1]/text()").toString());
            comp.setYear(page.getHtml().xpath("//*[@id=\"GridView1\"]/tbody/tr["+i+"]/td[2]/text()").toString());
            comp.setMonth(page.getHtml().xpath("//*[@id=\"GridView1\"]/tbody/tr["+i+"]/td[3]/text()").toString());
            comp.setProvince(page.getHtml().xpath("//*[@id=\"GridView1\"]/tbody/tr["+i+"]/td[4]/text()").toString());
            comp.setEnterprise(page.getHtml().xpath("//*[@id=\"GridView1\"]/tbody/tr["+i+"]/td[5]/text()").toString());
            comp.setQuestion(page.getHtml().xpath("//*[@id=\"GridView1\"]/tbody/tr["+i+"]/td[6]/text()").toString());
            comp.setHandle(page.getHtml().xpath("//*[@id=\"GridView1\"]/tbody/tr["+i+"]/td[7]/text()").toString());
            comp.setComment(page.getHtml().xpath("//*[@id=\"GridView1\"]/tbody/tr["+i+"]/td[8]/text()").toString());

            list.add(comp);
        }

        if(list.size() == 0){

            System.out.println(list.size());
            page.setSkip(true);
        }else{
            //System.out.println(list.size());
            page.putField("list",list);
        }
        //page.putField("序号：",page.getHtml().xpath("//*[@id=\"GridView1\"]/tbody/tr[2]/td[1]/text()").toString());

    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        //post方式的请求
        Spider spider = Spider.create(new ComplaintCaseDataCrawl());

        /*Request request = new Request("http://datacenter.mep.gov.cn:8099/ths-report/report!list.action");
        request.setMethod(HttpConstant.Method.POST);
        Map<String,Object> map = new HashedMap();
        NameValuePair[] values = new NameValuePair[2];
        values[0] = new BasicNameValuePair("xmlname","1462866483032");
        values[1] = new BasicNameValuePair("page.pageNo","2");
        map.put("nameValuePair",values);
        request.setExtras(map);*/

        //List<Request> list = new ArrayList<Request>();
        /*Request request = new Request("http://datacenter.mep.gov.cn:8099/ths-report/report!list.action");
        request.setMethod(HttpConstant.Method.POST);*/
        for(int i =1;i< 260;i++){

            String url = null;
            url = "http://datacenter.mep.gov.cn:8099/ths-report/report!list.action?xmlname=1462866483032&page.pageNo="+i;
            /*Map<String,Object> params = new HashedMap();
            NameValuePair[] values = new NameValuePair[2];
            values[0] = new BasicNameValuePair("xmlname","1462866483032");
            values[1] = new BasicNameValuePair("page.pageNo",String.valueOf(i));
            params.put("nameValuePair",values);
            request.setExtras(params);*/
            //System.out.println(request.toString());
            //list.add(request);
            //spider.addRequest(request).addPipeline(new ComplaintCasePipeline()).thread(3).run();
            Request request = new Request(url);
            //request.setMethod(HttpConstant.Method.POST);
            spider.addRequest(request);
        }

        spider.addPipeline(new ComplaintCasePipeline()).run();


        /*Spider.create(new ComplaintCaseDataCrawl())
                .addRequest(request).run();*/

        /*Spider.create(new ComplaintCaseDataCrawl())
                .addUrl("http://datacenter.mep.gov.cn:8099/ths-report/report!list.action?xmlname=1462866483032&page.pageNo=1")
                .thread(3).run();*/


    }

}
