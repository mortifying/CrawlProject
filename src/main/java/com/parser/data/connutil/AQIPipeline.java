package com.parser.data.connutil;

import com.parser.data.bean.CityAQIDayData;
import com.parser.data.bean.CityAQIDayDataDAO;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;

/**
 * Created by root on 2017/5/9.
 */
public class AQIPipeline implements Pipeline {

    public void process(ResultItems resultItems, Task task) {


        ArrayList<CityAQIDayData> list = resultItems.get("list");
        CityAQIDayDataDAO cityAQIDayDataDAO = new CityAQIDayDataDAO();
        cityAQIDayDataDAO.aqiDataSave(list);

    }
}
