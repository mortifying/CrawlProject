package com.parser.data.connutil;

import com.parser.data.bean.ComplaintCaseBean;
import com.parser.data.bean.ComplaintCaseDataDAO;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;

/**
 * Created by root on 2017/5/10.
 */
public class ComplaintCasePipeline implements Pipeline {

    public void process(ResultItems resultItems, Task task) {

        ArrayList<ComplaintCaseBean> list = resultItems.get("list");
        ComplaintCaseDataDAO complaintCaseDataDAO = new ComplaintCaseDataDAO();
        complaintCaseDataDAO.caseDataSave(list);
    }
}
