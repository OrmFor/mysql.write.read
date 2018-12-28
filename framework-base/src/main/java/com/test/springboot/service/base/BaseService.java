package com.test.springboot.service.base;


import com.test.springboot.domain.entity.vo.VO;
import com.test.springboot.page.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseService<T, PK extends Serializable> {
    /** @deprecated */
    @Deprecated
    List<T> getList(T var1, Map<String, Object> var2);

    /** @deprecated */
    @Deprecated
    List<T> getList(Map<String, Object> var1);

    List<T> getList(T var1);

    List<T> getList();

    int getCount();

    int getCount(T var1);

    /** @deprecated */
    @Deprecated
    Page<T> getPage(int var1, int var2, String var3, Map<String, Object> var4);

    Page<T> getPage(T var1, Map<String, ?> var2);

    Page<T> getPage(String var1, T var2, Map<String, ?> var3);

    int deleteByPrimaryKey(PK var1);

    /** @deprecated */
    @Deprecated
    int delete(T var1, Map<String, Object> var2);

    int delete(T var1);

    int insert(T var1);

    int insertSelective(T var1);

    T selectByPrimaryKey(PK var1);

    T getByCondition(T var1);

    /** @deprecated */
    @Deprecated
    T getByCondition(T var1, VO var2);

    /** @deprecated */
    @Deprecated
    T getByCondition(T var1, VO var2, String var3);

    int updateByPrimaryKeySelective(T var1);

    int updateByPrimaryKey(T var1);

    int updateByCondition(T var1, T var2);

    /** @deprecated */
    @Deprecated
    int updateByCondition(T var1, T var2, VO var3);

    int updateIncreateNumbers(T var1, String[] var2, Object[] var3);

    Map<String, ?> aggregate(T var1, String[] var2, String[] var3);
}

