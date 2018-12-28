package com.test.springboot.page;


public class PageUtil {
    private static final int DEF_PAGE_SIZE = 10;

    public PageUtil() {
    }

    public static <T> Page<T> createPage(Page<T> page, int totalRow) {
        //Lists.newArrayList();
        return createPage(page.getCondition(), page.getUrl(), page.getParam(), page.getPageSize(), page.getCurPage(), totalRow);
    }

    public static <T> Page<T> createPage(Condition condition, String url, String param, int pageSize, int curPage, int totalRow) {
        pageSize = getpageSize(pageSize);
        curPage = getcurPage(curPage);
        int beginIndex = getBeginIndex(pageSize, curPage);
        int totalPage = getTotalPage(pageSize, totalRow);
        boolean hasNextPage = hasNextPage(curPage, totalPage);
        boolean hasPrePage = hasPrePage(curPage);
        Page<T> page = new Page(hasPrePage, hasNextPage, pageSize, totalPage, totalRow, curPage, beginIndex, condition, url);
        page.setParam(param);
        return page;
    }

    private static int getpageSize(int pageSize) {
        return pageSize == 0 ? 10 : pageSize;
    }

    private static int getcurPage(int curPage) {
        return curPage == 0 ? 1 : curPage;
    }

    private static int getBeginIndex(int pageSize, int curPage) {
        return (curPage - 1) * pageSize;
    }

    private static int getTotalPage(int pageSize, int totalRow) {
        //int totalPage = false;
        int totalPage;
        if (totalRow % pageSize == 0) {
            totalPage = totalRow / pageSize;
        } else {
            totalPage = totalRow / pageSize + 1;
        }

        return totalPage;
    }

    private static boolean hasPrePage(int curPage) {
        return curPage != 1;
    }

    private static boolean hasNextPage(int curPage, int totalPage) {
        return curPage != totalPage && totalPage != 0;
    }
}

