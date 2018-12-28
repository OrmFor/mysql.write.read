package com.test.springboot.dao.base;

import com.test.springboot.page.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDao<T, PK extends Serializable> {
    List<T> getList(Map<String, ?> var1);

    int getCount(Map<String, ?> var1);

    Page<T> getPage(int var1, int var2, String var3, Map<String, Object> var4);

    int deleteByPrimaryKey(PK var1);

    int delete(Map<String, ?> var1);

    int insert(T var1);

    int insertSelective(T var1);

    T selectByPrimaryKey(PK var1);

    T getByCondition(Map<String, ?> var1);

    int updateByPrimaryKeySelective(T var1);

    int updateByPrimaryKey(T var1);

    int updateByCondition(Map<String, ?> var1);

    int updateIncreateNumbers(Map<String, ?> var1);

    Map<String, ?> aggregate(Map<String, ?> var1);
}

