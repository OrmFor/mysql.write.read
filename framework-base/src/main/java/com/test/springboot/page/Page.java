package com.test.springboot.page;

import java.util.List;

public class Page<T> {
    public boolean hidePageBarIFOnlyOnePage = true;
    private boolean hasPrevPage;
    private boolean hasNextPage;
    private int pageSize;
    private int totalPage;
    private int totalRow;
    private int curPage;
    private int beginIndex;
    private String url;
    private String param;
    private String pageToolBar;
    private Condition condition;
    private List<T> result;

    public Page() {
    }

    public Page(int pageSize, String url, String param) {
        this.pageSize = pageSize;
        this.url = null == url ? "" : url;
        this.param = null == param ? "" : param;
    }

    public Page(boolean hasPrevPage, boolean hasNextPage, int pageSize, int totalPage, int totalRow, int curPage, int beginIndex, Condition condition, String url) {
        this.hasPrevPage = hasPrevPage;
        this.hasNextPage = hasNextPage;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.totalRow = totalRow;
        this.curPage = curPage;
        this.beginIndex = beginIndex;
        this.condition = condition;
        this.url = url;
    }

    public List<T> getResult() {
        return this.result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public int getBeginIndex() {
        return this.beginIndex;
    }

    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    public int getCurPage() {
        return this.curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public boolean isHasNextPage() {
        return this.hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPrevPage() {
        return this.hasPrevPage;
    }

    public void setHasPrevPage(boolean hasPrevPage) {
        this.hasPrevPage = hasPrevPage;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalRow() {
        return this.totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageToolBar() {
        StringBuffer strBuf = new StringBuffer();
        boolean isHaveParam = false;
        if (null != this.param && !"".equals(this.param)) {
            isHaveParam = true;
        }

        strBuf.append("<span class=\"pagebarcount\">");
        strBuf.append("<b class='red'>" + this.totalRow + "</b> 条记录，共  <b class='red'>" + this.totalPage + "</b> 页，每页 <b class='red'>" + this.pageSize + "</b> 条记录");
        strBuf.append("</span>");
        strBuf.append("<span class=\"pagebarFirst\">&nbsp;&nbsp;&nbsp;");
        strBuf.append("<a href=\"" + this.url + "?page=1" + (isHaveParam ? "&amp;" + this.param : "") + "\">第一页</a>");
        strBuf.append("</span>");
        strBuf.append("<span class=\"pagebarFirst\">&nbsp;&nbsp;&nbsp;");
        strBuf.append("<a href=\"" + this.url + "?page=" + this.totalPage + (isHaveParam ? "&amp;" + this.param : "") + "\">最后一页</a>");
        strBuf.append("</span>");
        if (this.hidePageBarIFOnlyOnePage && this.totalPage < 2) {
            this.pageToolBar = strBuf.toString();
            return this.pageToolBar;
        } else {
            if (this.url == null) {
                this.url = "";
            }

            strBuf.append("<ul class=\"pagination pull-right no-margin\">");
            if (this.hasPrevPage) {
                if (this.url != null && this.url.indexOf("{page}") != -1) {
                    strBuf.append("<li class=\"prev\"><a href=\"" + this.url.replace("{page}", String.valueOf(this.curPage - 1)) + (this.curPage - 1) + (isHaveParam ? "&amp;" + this.param : "") + "\"><i class=\"icon-double-angle-left\"></i></a></li>");
                } else {
                    strBuf.append("<li class=\"prev\"><a href=\"" + this.url + "?page=" + (this.curPage - 1) + (isHaveParam ? "&amp;" + this.param : "") + "\"><i class=\"icon-double-angle-left\"></i></a></li>");
                }
            } else {
                strBuf.append("<li class=\"prev disabled\"><a href=\"javascript:void(0)\"><i class=\"icon-double-angle-left\"></i></a></li>");
            }

            int showPages = 10;
            int showPages_now = 1;
            int showPages_min = 1;
            int showPages_max = this.totalPage;
            StringBuffer pagesBuffer = new StringBuffer();
            if (this.url != null && this.url.indexOf("{page}") != -1) {
                pagesBuffer.append("<li class=\"active\"><a href=\"" + this.url.replace("{page}", String.valueOf(this.curPage)) + (isHaveParam ? "&amp;" + this.param : "") + "\">" + this.curPage + "</a></li>");
            } else {
                pagesBuffer.append("<li class=\"active\"><a href=\"" + this.url + "?page=" + this.curPage + (isHaveParam ? "&amp;" + this.param : "") + "\">" + this.curPage + "</a></li>");
            }

            int i;
            int curPage_tmp;
            for(i = 1; i <= showPages / 2; ++i) {
                curPage_tmp = this.curPage - i;
                if (curPage_tmp < 1) {
                    break;
                }

                if (this.url != null && this.url.indexOf("{page}") != -1) {
                    pagesBuffer.insert(0, "<li><a href=\"" + this.url.replace("{page}", String.valueOf(curPage_tmp)) + (isHaveParam ? "&amp;" + this.param : "") + "\">" + curPage_tmp + "</a></li>");
                } else {
                    pagesBuffer.insert(0, "<li><a href=\"" + this.url + "?page=" + curPage_tmp + (isHaveParam ? "&amp;" + this.param : "") + "\">" + curPage_tmp + "</a></li>");
                }

                ++showPages_now;
                showPages_min = curPage_tmp;
            }

            for(i = 1; i <= showPages / 2; ++i) {
                curPage_tmp = this.curPage + i;
                if (curPage_tmp > this.totalPage) {
                    break;
                }

                if (this.url != null && this.url.indexOf("{page}") != -1) {
                    pagesBuffer.append("<li><a href=\"" + this.url.replace("{page}", String.valueOf(curPage_tmp)) + (isHaveParam ? "&amp;" + this.param : "") + "\">" + curPage_tmp + "</a></li>");
                } else {
                    pagesBuffer.append("<li><a href=\"" + this.url + "?page=" + curPage_tmp + (isHaveParam ? "&amp;" + this.param : "") + "\">" + curPage_tmp + "</a></li>");
                }

                ++showPages_now;
                showPages_max = curPage_tmp;
            }

            for(; showPages - showPages_now > 0; ++showPages_now) {
                ++showPages_max;
                if (showPages_max > this.totalPage) {
                    break;
                }

                if (this.url != null && this.url.indexOf("{page}") != -1) {
                    pagesBuffer.append("<li><a href=\"" + this.url.replace("{page}", String.valueOf(showPages_max)) + (isHaveParam ? "&amp;" + this.param : "") + "\">" + showPages_max + "</a></li>");
                } else {
                    pagesBuffer.append("<li><a href=\"" + this.url + "?page=" + showPages_max + (isHaveParam ? "&amp;" + this.param : "") + "\">" + showPages_max + "</a></li>");
                }
            }

            for(; showPages - showPages_now > 0; ++showPages_now) {
                --showPages_min;
                if (showPages_min < 1) {
                    break;
                }

                if (this.url != null && this.url.indexOf("{page}") != -1) {
                    pagesBuffer.insert(0, "<li><a href=\"" + this.url.replace("{page}", String.valueOf(showPages_min)) + (isHaveParam ? "&amp;" + this.param : "") + "\">" + showPages_min + "</a></li>");
                } else {
                    pagesBuffer.insert(0, "<li><a href=\"" + this.url + "?page=" + showPages_min + (isHaveParam ? "&amp;" + this.param : "") + "\">" + showPages_min + "</a></li>");
                }
            }

            strBuf.append(pagesBuffer);
            if (this.hasNextPage) {
                if (this.url != null && this.url.indexOf("{page}") != -1) {
                    strBuf.append("<li class=\"next\"><a href=\"" + this.url.replace("{page}", String.valueOf(this.curPage + 1)) + (isHaveParam ? "&amp;" + this.param : "") + "\"><i class=\"icon-double-angle-right\"></i></a></li>");
                } else {
                    strBuf.append("<li class=\"next\"><a href=\"" + this.url + "?page=" + (this.curPage + 1) + (isHaveParam ? "&amp;" + this.param : "") + "\"><i class=\"icon-double-angle-right\"></i></a></li>");
                }
            } else {
                strBuf.append("<li class=\"next disabled\"><a href=\"javascript:void(0)\"><i class=\"icon-double-angle-right\"></i></a></li>");
            }

            strBuf.append("</ul>");
            this.pageToolBar = strBuf.toString();
            return this.pageToolBar;
        }
    }

    public void setPageToolBar(String pageToolBar) {
        this.pageToolBar = pageToolBar;
    }

    public String getParam() {
        return this.param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Condition getCondition() {
        return this.condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}

