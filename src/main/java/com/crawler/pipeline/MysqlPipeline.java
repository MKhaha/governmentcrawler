package com.crawler.pipeline;

import com.crawler.dao.ZjjxwDocumentMapper;
import com.crawler.pojo.ZjjxwDocumnet;
import com.crawler.tools.DBTools;
import org.apache.ibatis.session.SqlSession;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/19.
 */
public class MysqlPipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {

        SqlSession session = DBTools.getSession();
        ZjjxwDocumentMapper zjjxwDocumentMapper = session.getMapper(ZjjxwDocumentMapper.class);

//        System.out.println(resultItems);
//        System.out.println(resultItems.getRequest().getUrl());
//        System.out.println((String)resultItems.get("title"));
//        System.out.println((String)resultItems.get("content"));
//        System.out.println((Date)resultItems.get("date"));
//        System.out.println((String)resultItems.get("attachmentUrl"));


        System.out.println("get page: " + resultItems.getRequest().getUrl());
        ZjjxwDocumnet zjjxwDocumnet = new ZjjxwDocumnet();

        /* 将下列数据项导入结果中：
        title,url,attachmentUrl,date,content
        */
        zjjxwDocumnet.setUrl(resultItems.getRequest().getUrl());
        zjjxwDocumnet.setTitle((String) resultItems.get("title"));
        zjjxwDocumnet.setContent((String) resultItems.get("content"));
        zjjxwDocumnet.setDate((Date) resultItems.get("date"));
        zjjxwDocumnet.setUrlAttachment((String) resultItems.get("attachmentUrl"));

        try {
            zjjxwDocumentMapper.insertSelective(zjjxwDocumnet);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        } finally {
            session.close();
        }
    }
}
