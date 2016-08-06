package com.yc.test;

import java.sql.Connection;

import org.junit.Assert;
import org.junit.Test;

import com.yc.biz.MeituBiz;
import com.yc.dao.DBHelper;

public class TestDBHelper {

	@Test
	public void testGetCon() {
			DBHelper db=new DBHelper();
			Connection con=db.getCon();
			Assert.assertNotNull(con);   //断言 我 断言  con不为空   如果为空  则测试通不过
		
	}

	public void testDoDDL(){
		DBHelper db=new DBHelper();
		String sql="create table qqqq(id int)";
		db.doDDL(sql, null);
	}
	
}
