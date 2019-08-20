package cn.itcast.travel.domain;

import java.util.List;

public class PageBean<T> {
    private int totalCount;         //数据总条目
    private int totalPage;          //总页数
    private int currentPage;        //当前页码
    private int pageRows;           //每页条目

    private List<T> dataList;       //数据列表

    @Override
    public String toString(){
        return "PageBean{" +
                "totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                ", pageRows=" + pageRows +
                ", dataList=" + dataList +
                '}';
    }

    public PageBean(){
    }

    public int getTotalCount(){
        return totalCount;
    }

    public void setTotalCount( int totalCount ){
        this.totalCount = totalCount;
    }

    public int getTotalPage(){
        return totalPage;
    }

    public void setTotalPage( int totalPage ){
        this.totalPage = totalPage;
    }

    public int getCurrentPage(){
        return currentPage;
    }

    public void setCurrentPage( int currentPage ){
        this.currentPage = currentPage;
    }

    public int getPageRows(){
        return pageRows;
    }

    public void setPageRows( int pageRows ){
        this.pageRows = pageRows;
    }

    public List<T> getDataList(){
        return dataList;
    }

    public void setDataList( List<T> dataList ){
        this.dataList = dataList;
    }
}
