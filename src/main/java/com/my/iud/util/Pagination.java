package com.my.iud.util;

import java.util.List;

public class Pagination<T> {

	public List<T> list;

	private long curPage;
	/**
	 * 每页显示的记录数
	 */
	private long pageSize;
	/**
	 * 记录行数
	 */
	private long rowsCount;
	/**
	 * 页数
	 */
	private long pageCount;
	

	public Pagination() {
		super();
	}

	public Pagination(long rowsCount) {
		this.curPage = 1;
		this.pageSize = 10;
		this.rowsCount = rowsCount;
		this.pageCount = (long) Math.ceil((double) rowsCount / pageSize);
	}

	public Pagination(long curPage , long rowsCount) {
		this.curPage = curPage;
		this.pageSize = 10;
		this.rowsCount = rowsCount;
		this.pageCount = (int) Math.ceil((double) rowsCount / pageSize);
	}

	public Pagination(long curPage, long pageSize , long rowsCount) {
		this.curPage = curPage;
		this.pageSize = pageSize;
		this.rowsCount = rowsCount;
		this.pageCount = (long) Math.ceil((double) rowsCount / pageSize);
	}


	public long getCurPage(){
		return curPage < 1 ? 1 : curPage;
	}

	public long getPageSize() {
		return pageSize;
	}

	public long getRowsCount() {
		return rowsCount;
	}


	public long getPageCount() {
		return pageCount;
	}


	public long first() {
		return 1;
	}

	public long last() {
		return pageCount;
	}


	public long previous() {
		return (curPage - 1 <= 0) ? 1 : curPage - 1;
	}


	public long next() {
		return (curPage + 1 > pageCount) ? pageCount : curPage + 1;
	}

	
	public boolean isFirst() {
		return (curPage == 1) ? true : false;
	}


	public boolean isLast() {
		return (curPage == pageCount) ? true : false;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
    
	public long getCurStart(){
		if(curPage <= 1){
			return curPage;
		}else if(curPage > pageCount){
			return pageCount;
		}
		return (curPage - 1) * pageSize + 1;
	}
}