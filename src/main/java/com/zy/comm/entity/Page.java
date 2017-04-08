package com.zy.comm.entity;

import java.util.List;

/**
 * Created by zy on 2017/3/31.
 */
public class Page<T> {

    private int pageNo;
    private int pageSize;
    private int totalPage;
    private int totalRecord;
    private List<T> record;

    public void initTotal(){
        if(pageSize!=0) {
            totalPage = (int)Math.ceil((double) totalRecord/pageSize);
        }
    }


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if(pageSize<=0){
            return ;
        }
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        if(pageNo<=0){
            return ;
        }
        this.pageNo = pageNo;
    }


    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public List<T> getRecord() {
        return record;
    }

    public void setRecord(List<T> record) {
        if(record == null){
            return;
        }
        this.record = record;
    }
}
