package com.test.springboot.domain.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author guojun
 * 该类增加每个方法，需要在 BaseController 数据绑定中加上不进行自动数据注入绑定以保证安全
 */
public class VO {

    @JSONField(serialize = false, deserialize = false)
    private List<ExpressionChain> expressionChainList;
    @JSONField(serialize = false, deserialize = false)
    private Integer pageBeginIndex;
    @JSONField(serialize = false, deserialize = false)
    private Integer pageNumber;//当前第几页
    @JSONField(serialize = false, deserialize = false)
    private Integer pageSize;//每页显示的条数
    @JSONField(serialize = false, deserialize = false)
    private String orderBy;//排序
    @JSONField(serialize = false, deserialize = false)
    private Map<String, Object> extConditions;//扩展条件
    @JSONField(serialize = false, deserialize = false)
    private List<String> nullColums;// UPDATE 时需要更新为 NULL 的字段


    public VO() {
        expressionChainList = new ArrayList<>();
        extConditions = new HashMap<>();
        nullColums = new ArrayList<>();
    }

    public void or(ExpressionChain expressionChain) {
        expressionChainList.add(expressionChain);
    }

    public void or(Expression expression) {
        expressionChainList.add(new ExpressionChain().and(expression));
    }

    public void and(Expression expression) {
        if (expressionChainList.isEmpty()) {
            expressionChainList.add(new ExpressionChain());
        }
        expressionChainList.get(0).and(expression);
    }

    public List<ExpressionChain> getExpressionChainList() {
        return expressionChainList;
    }

    public void setExpressionChainList(List<ExpressionChain> expressionChainList) {
        this.expressionChainList = expressionChainList;
    }

    public Integer getPageBeginIndex() {
        if (pageBeginIndex == null && getPageNumber() != null && pageSize != null) {
            pageBeginIndex = (getPageNumber() - 1) * pageSize;
        }
        return pageBeginIndex;
    }

    public void setPageBeginIndex(Integer pageBeginIndex) {
        this.pageBeginIndex = pageBeginIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        DAOUtils.checkOrderBy(orderBy);
        this.orderBy = orderBy;
    }

    public Map<String, Object> getExtConditions() {
        return extConditions;
    }

    public void setExtConditions(Map<String, Object> extConditions) {
        this.extConditions = extConditions;
    }
    /**
     * @Title: addExtConditions
     * @Description: 添加扩展条件
     * @author 龚国君 javazj@qq.com
     * @date 2016年5月3日 上午11:35:54
     * @version V1.0
     * @param @param key
     * @param @param value    设定文件
     * @return void    返回类型
     * @throws
     */
    public void addExtConditions(String key, Object value) {
        extConditions.put(key, value);
    }

    public List<String> getNullColums() {
        return nullColums;
    }

    public void setNullColums(List<String> nullColums) {
        this.nullColums = nullColums;
    }

    public void addNullColumFORUPDATE (String column) {
        DAOUtils.checkColumn(column);
        nullColums.add(column);
    }
}