package com.vanda.vRbac.utils;

import java.util.List;
import java.util.Map;
/**
 * 1.不管有多少页面，最终显示的就是10页数据
 * 2.1-6页，开始位置都是1开始。从第7页开始，每增加1也，开始位置就加1
 * 
 * 思路：我们需要计算的就是开始位置以及结束位置和当前索引的关系
 */
public class Page {
	
	//1.当前分页索引,分页索引=数据库索引+1
	private int index=1;
	//2.总记录数
	private int count;
	//3.每页记录数
	private int size;
	//4.总数页数
	private int totalPage;
	//5.数据
	private List<Map<String, Object>> data;
	//6.是否有上一页
	private boolean previous=false;
	//7.是否有下一页
	private boolean next=false;
	
	//8.开始位置
	private int start;
	//9.结束位置
	private int end;
	
	public int getStart() {
		
		if (this.index<7) {
			//1.开始位置。如果当前页面小于7不变
			this.start= 1;
		}else  {
			//规律：当结束页值-当前页的值<4,就返回结束页号-9
			if (this.getEnd()-this.index<4) {
				this.start= this.getEnd()-9;
			}else {
				//2.开始位置=当前索引-5
				this.start= this.index-5;
			}
		}
		
		
		return start;
	}

	public int getEnd() {
		
		if (this.getTotalPage()<10) {
			//假如总页面小于10呢？
			this.end=this.getTotalPage();
		}else if (this.index<7) {
			this.end=10;
		}else {
			//规律：如果this.index+4大于总页数了，返回最后的页数
			if (this.index+4>this.getTotalPage()) {
				this.end=this.getTotalPage();
			}else {
				this.end=this.index+4;	
			}
		}
		
		return end;
	}

	public boolean isPrevious() {
		//如果当前页面大于1，才有上一页。
		if (this.index>1) {
			this.previous=true;
		}else {
			this.previous=false;
		}
		return previous;
	}

	public boolean isNext() {
		//如果当前页小于总页数，才有下一页
		if (this.index<this.totalPage) {
			this.next=true;
		}else {
			this.next=false;
		}
		return next;
	}

	public Page(int index, int count, int size, List<Map<String, Object>> data) {
		super();
		this.index = index;
		this.count = count;
		this.size = size;
		this.data = data;
		//计算总页数,如果可以被整除，等于=总记录数/每页记录数
		//如果不能被整除，结果=总记录数/每页记录数+1
		if (count%size==0) {
			this.totalPage=count/size;
		}else {
			this.totalPage=count/size+1;
		}
		
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getTotalPage() {
		return totalPage;
	}
	
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<Map<String, Object>> getData() {
		return data;
	}
	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
}
