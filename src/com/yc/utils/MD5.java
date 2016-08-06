package com.yc.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	/*
	 *MD5加密类
	 *  在现阶段 ,我们一般认为存在两种加密方式，单向加密和双向加密。双向加密是加密算法中最常用的
	 *  它将我们可以直接理解的明文数据加密为我们不可直接理解的密文数据
	 *  ，然后，在需要的时候，可以使用一定的算法将这些加密以后的密文解密为原来可以理解的明文。
	 *  双向加密适合于隐秘通讯，比如，我们在网上购物的时候，需要向网站提交信用卡密码，
	 *  我们当然不希望我们的数据直接在网上明文传送，因为这样很可能被别的用户“偷听”，
	 *  我们希望我们的信用卡密码是通过加密以后，再在网络传送，这样，网站接受到我们的数据以后，通过解密算法就可以得到准确的信用卡账号。
	 *  单向加密刚好相反，只能对数据进行加密，也就是说，没有办法对加密以后的数据进行解密。可能我们立即就会想，这样的加密有什么用处？
	 *  不能解密的加密算法有什么作用呢？
	 *  在实际的一个应用就是数据库中的用户信息加密，当用户创建一个新的账户或密码，他的信息不是直接保存到数据库
	 *  而是经过一次加密后再保存，这样，即使这些信息被泄露，也不能立即理解这些信息的真正含义
	 *  MD5就是采用单向加密的加密算法，对于MD5而言，
	 *  有两个特性是很重要的，第一是任意两段明文数据，加密以后的密文不能是相同的；
	 *  第二是任意一段明文数据，经过加密以后，其结果必须永远是不变的。前者的意思是不可能有任意两段明文加密以后得到相同的密文，
	 *  后者的意思是如果我们加密特定的数据，得到的密文一定是相同的。
	 *
	 */
	private final static String[] hexDigits={"0","1","2","3","4","5","6",
			"7","8","9","a","b","c","d","e","f"};
	
	/***
	 * 将一个字节数组中的每一个字节值转换为两个十六进制的字符，并拼接成一个字符串
	 * @param b
	 * @return
	 */
	private static String byteArrayToHexString(byte[] b){
		StringBuffer resultSb=new StringBuffer();
		for(int i=0;i<b.length;i++){
			resultSb.append(byteToHexString(b[i] ));
		}
		return resultSb.toString();
	}
	
	/**
	 * 将一个字节数字转换为两位的十六进制值
	 */
	private static String byteToHexString(byte b){
		int n=b;
		if(n<0)
			n=256+n;
		int d1=n/16;
		int d2=n%16;
		return hexDigits[d1]+ hexDigits[d2];
	}
	
	public static String MD5Encode(String origin) throws Exception{
		String resultString = null;
		
		try {
			resultString=new String(origin);
			/**
			 * MessageDigest 类为应用程序提供信息摘要算法功能，如MD5或SHA
			 * 算法。但是摘要是安全的单项哈希函数，他接收任意大小的数据，并输出固定长度的哈希值
			 */
			MessageDigest md=MessageDigest.getInstance("MD5");
			resultString =byteArrayToHexString(md.digest(resultString.getBytes() ));
		} catch (Exception ex) {
			throw ex;
		}
		return resultString;
	}
	public static void main(String[] args) throws Exception{
		System.out.println(MD5.MD5Encode("abc中文123! @#" ));
		System.out.println("a");
		System.out.println(MD5.MD5Encode("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
	}
}
