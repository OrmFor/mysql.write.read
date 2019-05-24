package com.test.springboot.service.base;

import com.test.springboot.annotation.ReadDataSource;
import com.test.springboot.annotation.WriteDataSource;
import com.test.springboot.dao.base.BaseDao;
import com.test.springboot.domain.entity.vo.DAOUtils;
import com.test.springboot.domain.entity.vo.VO;
import com.test.springboot.page.Page;
import com.test.springboot.page.PageUtil;
import com.test.springboot.util.BeanConverter;
import com.test.springboot.util.Method;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class BaseServiceImpl<T, DAO extends BaseDao<T, PK>, PK extends Serializable> {
    public BaseServiceImpl() {
    }

    protected abstract DAO getDao();

    public int deleteByPrimaryKey(PK id) {
        return this.getDao().deleteByPrimaryKey(id);
    }

    /** @deprecated */
    @Deprecated
    @WriteDataSource
    public int delete(T condition, Map<String, Object> map) {
        if (map == null) {
            map =new HashMap<>();
        }

        if (condition != null) {
            ((Map)map).putAll(BeanConverter.toMap(condition, false));
        }

        return this.getDao().delete((Map)map);
    }

    @WriteDataSource
    public int delete(T condition) {
        Map<String, Object> map =new HashMap<>();
        if (condition != null) {
            map.putAll(BeanConverter.toMap(condition, false));
        }

        return this.getDao().delete(map);
    }

    @WriteDataSource
    public int insert(T record) {
        return this.getDao().insert(record);
    }

    @WriteDataSource
    public int insertSelective(T record) {
        return this.getDao().insertSelective(record);
    }

    @ReadDataSource
    public T selectByPrimaryKey(PK id) {
/*        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return this.getDao().selectByPrimaryKey(id);
    }

    @ReadDataSource
    public T getByCondition(T record) {
        Map<String, Object> params =new HashMap<>();
        if (record != null) {
            params.putAll(BeanConverter.toMap(record, false));
        }

        return this.getDao().getByCondition(params);
    }

    /** @deprecated */
    @Deprecated
    @ReadDataSource
    public T getByCondition(T record, VO vo) {
        Map<String, Object> params = new HashMap();
        params.put("condition", record);
        params.put("vo", vo);
        return this.getDao().getByCondition(params);
    }

    /** @deprecated */
    @Deprecated
    @ReadDataSource
    public T getByCondition(T record, VO vo, String orderBy) {
        Map<String, Object> params = new HashMap();
        params.put("condition", record);
        params.put("vo", vo);
        params.put("orderBy", orderBy);
        return this.getDao().getByCondition(params);
    }

    @WriteDataSource
    public int updateByPrimaryKey(T record) {
        return this.getDao().updateByPrimaryKey(record);
    }

    @WriteDataSource
    public int updateByPrimaryKeySelective(T record) {
        return this.getDao().updateByPrimaryKeySelective(record);
    }

    @WriteDataSource
   // @Transactional(propagation = Propagation.REQUIRED , rollbackFor = Exception.class)
    public int updateByCondition(T record, T condition) {
        Map<String, Object> params =new HashMap<>();
        params.put("entity", record);
        if (condition != null) {
            Map<String, Object> paramsCondition = BeanConverter.toMap(condition, false);
            if (paramsCondition.containsKey("entity")) {
                throw new RuntimeException("the update entity bean has the same colum entity");
            }

            params.putAll(paramsCondition);
        }

        params.put("condition", condition);
        return this.getDao().updateByCondition(params);
    }

    /** @deprecated */
    @Deprecated
    @WriteDataSource
    public int updateByCondition(T record, T condition, VO vo) {
        Map<String, Object> params = new HashMap();
        params.put("entity", record);
        params.put("condition", condition);
        params.put("vo", vo);
        return this.getDao().updateByCondition(params);
    }

    @WriteDataSource
    public int updateIncreateNumbers(T condition, String[] colums, Object[] numbers) {
        Map<String, Object> map =new HashMap<>();
        if (condition != null) {
            map.putAll(BeanConverter.toMap(condition, false));
        }

        if (colums != null && colums.length != 0 && numbers != null && numbers.length != 0) {
            if (colums.length != numbers.length) {
                throw new RuntimeException("colums and numbers do not have the same size");
            } else {
                List<Map<String, Object>> increateNumbers = new ArrayList<>();

                for(int i = 0; i < colums.length; ++i) {
                    Map<String, Object> columnNumber =new HashMap<>();
                    DAOUtils.checkColumn(colums[i]);
                    columnNumber.put("column", colums[i]);
                    columnNumber.put("number", numbers[i]);
                    increateNumbers.add(columnNumber);
                }

                map.put("increateNumbers", increateNumbers);
                return this.getDao().updateIncreateNumbers(map);
            }
        } else {
            throw new RuntimeException("colums or numbers can not be empty!");
        }
    }

    /** @deprecated */
    @Deprecated
    @ReadDataSource
    public List<T> getList(T condition, Map<String, Object> map) {
        if (map == null) {
            map =new HashMap<>();
        }

        if (condition != null) {
            ((Map)map).putAll(BeanConverter.toMap(condition, false));
        }

        return this.getDao().getList((Map)map);
    }

    /** @deprecated */
    @Deprecated
    @ReadDataSource
    public List<T> getList(Map<String, Object> map) {
        return this.getDao().getList(map);
    }

    @ReadDataSource
    public List<T> getList() {
        Map<String, Object> map =new HashMap<>();
        return this.getDao().getList(map);
    }

    @ReadDataSource
    public List<T> getList(T condition) {
        Map<String, Object> map =new HashMap<>();
        if (condition != null) {
            map.putAll(BeanConverter.toMap(condition, false));
        }

        return this.getDao().getList(map);
    }

    @ReadDataSource
    public int getCount() {
        Map<String, Object> map =new HashMap<>();
        return this.getDao().getCount(map);
    }

    @ReadDataSource
    public int getCount(T condition) {
        Map<String, Object> map =new HashMap<>();
        if (condition != null) {
            map.putAll(BeanConverter.toMap(condition, false));
        }

        return this.getDao().getCount(map);
    }

    /** @deprecated */
    @Deprecated
    @ReadDataSource
    public Page<T> getPage(int pageNumber, int maxRows, String url, Map<String, Object> map) {
        String params = Method.getQuery(map);
        int totalRow = this.getDao().getCount(map);
        Page<T> page = new Page(maxRows, url, params);
        page.setCurPage(pageNumber);
        page = PageUtil.createPage(page, totalRow);
        map.put("pageBeginIndex", page.getBeginIndex());
        map.put("pageSize", page.getPageSize());
        page.setResult(this.getDao().getList(map));
        return page;
    }

    @ReadDataSource
    public Page<T> getPage(T condition, Map<String, ?> paramsMap) {
        return this.getPage((String)null, condition, paramsMap);
    }

    @ReadDataSource
    public Page<T> getPage(String url, T condition, Map<String, ?> paramsMap) {
        Map<String, Object> map =new HashMap<>();
        map.putAll(BeanConverter.toMap(condition, false));
        Integer pageNumber = (Integer)map.get("pageNumber");
        if (pageNumber == null) {
            throw new RuntimeException("Please Set the Condition's pageNumber Property");
        } else {
            Integer pageSize = (Integer)map.get("pageSize");
            if (pageSize == null) {
                throw new RuntimeException("Please Set the Condition's pageSize Property");
            } else {
                String params = Method.getQuery(paramsMap);
                int totalRow = this.getDao().getCount(map);
                Page<T> page = new Page(pageSize, url, params);
                page.setCurPage(pageNumber);
                page = PageUtil.createPage(page, totalRow);
                page.setResult(this.getList(condition));
                return page;
            }
        }
    }

    @ReadDataSource
    public Map<String, ?> aggregate(T condition, String[] functions, String[] columns) {
        Map<String, Object> map =new HashMap<>();
        if (condition != null) {
            map.putAll(BeanConverter.toMap(condition, false));
        }

        if (functions != null && functions.length != 0 && columns != null && columns.length != 0) {
            if (functions.length != columns.length) {
                throw new RuntimeException("functions and columns do not have the same size");
            } else {
                map.put("aggregate_sql", DAOUtils.buildAggregateSql(functions, columns));
                return this.getDao().aggregate(map);
            }
        } else {
            throw new RuntimeException("functions or columns can not be empty!");
        }
    }
}