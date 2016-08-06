package com.yc.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//单例
public class Env  extends Properties{
	private static Env env;
	
	//在构造方法中读取db.properties的数据
	private Env(){
		//db.properties文件会放到bin目录，bin目录是字节码，类文件目录，可以通过一个类的类加载器来扫描这个目录，并加载这个目录下的文件信息
		InputStream iis=Env.class.getClassLoader(). getResourceAsStream("db.properties");
		try {
			super.load(iis);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Env getenv(){
		if(env==null){
			env=new Env();
		}
		return env;
	}
}
