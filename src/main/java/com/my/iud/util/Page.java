package com.my.iud.util;

import java.util.List;

public class Page<T> {
	
	public List<T> list;
    
	/** 当前页数   **/
	private int curPage;
    
	/** 每页记录数  **/
	private int pageSize = 15;
    
	/** 总的记录数  **/
	private int rowsCount;
	
        /** 总的页数   **/
	private int pageCount;

    
	public Page(int curPage, int pageSize, int rowsCount) {
		super();
		this.curPage = curPage;
		this.pageSize = pageSize;
		this.rowsCount = rowsCount;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getCurPage() {
		if(curPage < 1){
			curPage = 1;
		}else if(curPage > pageCount){
			curPage = pageCount;
		}
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRowsCount() {
		return rowsCount;
	}

	public void setRowsCount(int rowsCount) {
		this.rowsCount = rowsCount;
	}

	public int getPageCount() {
		pageCount = rowsCount % pageSize == 0 ? rowsCount/pageSize : rowsCount/pageSize + 1 ;
		if(pageCount == 0){
			return 1;
		}
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	
	public int first() {
		return 1;
	}

	public int last() {
		return pageCount;
	}


	public long previous() {
		return (curPage - 1 < 1) ? 1 : curPage - 1;
	}


	public long next() {
		return (curPage + 1 > pageCount) ? pageCount : curPage + 1;
	}

	
}
