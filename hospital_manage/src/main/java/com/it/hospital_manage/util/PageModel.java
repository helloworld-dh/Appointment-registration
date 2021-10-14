package com.it.hospital_manage.util;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;
import java.util.Arrays;

public class PageModel<T> implements Serializable {
    //总条数
    private long total;

    //每页大小
    private int pageSize;

    //总页数
    private int totalPage;

    //第几页
    private int pageNum;

    private int[] navigatePageNums;

    private JSONArray list;

    public PageModel() {
    }

    public PageModel(JSONArray list, int pageNum, long total, int pageSize){
        this.list = list;
        this.pageNum = pageNum;
        this.total = total;
        this.pageSize = pageSize;

        this.init();
    }

    public void init(){
        //pageSize 默认为5
        if (pageSize<=0){
            pageSize = 5;
        }

        totalPage = (int)(total/pageSize);

        if (total/pageSize !=0){
            totalPage +=1;
        }

        if (pageNum > totalPage){
            pageNum = totalPage;
        }

        if (pageNum <1 ){
            pageNum =1;
        }
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public JSONArray getList() {
        return list;
    }

    public void setList(JSONArray list) {
        this.list = list;
    }

    public int[] getNavigatePageNums(){
        int beginPageIndex;  // 页码列表的开始索引
        int endPageIndex;   // 页码列表的结束索引
        if (totalPage<=10){
            beginPageIndex=1;
            endPageIndex=totalPage;
        }else {
            //总页码多于10页，则显示当前页附近的共10个页码(前4个+当前页+后5个)
            beginPageIndex = pageNum -4;
            endPageIndex = pageNum +5;

            //当前面的页码不足4个时，则显示前10个页码
            if (beginPageIndex < 1){
                beginPageIndex =1;
                endPageIndex = 10;
            }
            //当后面的页码不足5个时，则显示后10个页码
            if (endPageIndex > totalPage){
                endPageIndex = totalPage;
                beginPageIndex = totalPage - 10 +1;
            }
        }
        navigatePageNums = new int[endPageIndex-beginPageIndex+1];
        int j = 0;
        for (int i = beginPageIndex;i<endPageIndex;i++){
            navigatePageNums[j] = i;
            j++;
        }
        return navigatePageNums;
    }

    public void setNavigatePageNums(int[] navigatePageNums){
        this.navigatePageNums = navigatePageNums;
    }

    @Override
    public String toString() {
        return "PageModel{" +
                "total=" + total +
                ", pageSize=" + pageSize +
                ", totalPage=" + totalPage +
                ", pageNum=" + pageNum +
                ", navigatePageNums=" + Arrays.toString(navigatePageNums) +
                ", list=" + list +
                '}';
    }
}