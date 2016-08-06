package com.yc.bean;

import java.util.ArrayList;
import java.util.List;

public class PicInfo {
	private List list=new ArrayList();	

	/**
	 * @param list
	 */
	public PicInfo(List list) {
		super();
		this.list = list;
	}


	

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
}
