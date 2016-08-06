package com.yc.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * 注册表操作帮助类
 */
public class RegistrationUtils {
	//添加        name  a
	//       pwd   a
	public static void saveRegistration(   Map<String,String>   map  ) throws BackingStoreException{
		//                          systemNodeForPackage();     //保存在[HKEY_LOCAL_MACHINE\SOFTWARE\JAVASOFT\Prefs]
		//                          userNodeForPackage() ;      //保存在 [HKEY_CURRENT_USER\SOFTWARE\JAVASOFT\PREFS]          推荐 将信息存在用户节点下
		Preferences pre=Preferences.userNodeForPackage(      RegistrationUtils.class    );   //   RegistrationUtils.class     节点信息的包结构. 
		if(  map!=null){
			Set<String> keys=map.keySet();      //取出map中所有的键
			Iterator<String> its=keys.iterator();    //键是一个set,   只能取出  Iterator选代器
			while(  its.hasNext()  ){    //循环选代器
				String key=its.next();     //取出一个键
				String value=map.get(key);    //根据键从map中取出值
				//向注册表写入
				pre.put(key, value);     //          
			}
			pre.flush();     //    流操作，  flush 
		}
	}
	//删除
	public static void delRegistration(  Map<String,String> map){
		Preferences pre=Preferences.userNodeForPackage( RegistrationUtils.class   );
		if(  map!=null){
			Set<String> keys=map.keySet();
			Iterator<String> its=keys.iterator();
			while(  its.hasNext()  ){
				String key=its.next();
				pre.remove( key    );             //   remove() 根据键来删除
			}
		}
	}
	//查询，返回null表示没有这个注册表项                             返回map表示有这个注册表项，并已经在map填充好了值
	//    形参数map中存了要查找的注册表的键
	public static Map findRegistration(     ){
		Map<String,String> map=null;
		Preferences pre=Preferences.userNodeForPackage( RegistrationUtils.class   );
		String name=pre.get("userName", "");         //     get("键", "mo认值");    如果这个键对应的值存在，则返回这个值，如果不存在，则返回一个空字符串
		if(     !   name.equals("")){
			map=new HashMap<String,String>();
			map.put("userName", pre.get("userName", ""));         //pre.get("name", "")  从注册表取   取出来存在   map 中
			map.put("userPwd", pre.get("userPwd", ""));
		}
		return map;
	}
	
	
	
	
	
	
}
