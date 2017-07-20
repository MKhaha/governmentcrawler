package com.crawler.dao;

import com.crawler.pojo.ZjjxwDocumnet;

public interface ZjjxwDocumentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ZjjxwDocumnet record);

    int insertSelective(ZjjxwDocumnet record);

    ZjjxwDocumnet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ZjjxwDocumnet record);

    int updateByPrimaryKeyWithBLOBs(ZjjxwDocumnet record);

    int updateByPrimaryKey(ZjjxwDocumnet record);
}