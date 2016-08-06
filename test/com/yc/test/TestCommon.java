package com.yc.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.yc.utils.Common;

public class TestCommon {
	
	@Test
	public void testLog4J(){
		Common.log.fatal("这是一个致命错误的信息..");
		Common.log.error("这是一个错误信息");
		Common.log.info("这是一个普通信息");
		Common.log.debug("调试信息");
	}
	
	
	
	
	

}
