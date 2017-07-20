package com.crawler.service;

import com.crawler.dao.ZjjxwDocumentMapper;
import org.apache.ibatis.session.SqlSession;
import com.crawler.pojo.ZjjxwDocumnet;
import com.crawler.tools.DBTools;

import java.util.Date;

public class UserService {

    public static void main(String[] args) {
        insertUser();
    }


    /**
     * 新增用户
     */
    private static void insertUser() {
        SqlSession session = DBTools.getSession();
        ZjjxwDocumentMapper zjjxwDocumentMapper = session.getMapper(ZjjxwDocumentMapper.class);

        ZjjxwDocumnet zjjxwDocumnet = new ZjjxwDocumnet(1,
                "test",
                "http://test.html",
                new Date(),
                "http://test.html",
                null,
                null,
                null);
        try {
            zjjxwDocumentMapper.insertSelective(zjjxwDocumnet);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }

}
