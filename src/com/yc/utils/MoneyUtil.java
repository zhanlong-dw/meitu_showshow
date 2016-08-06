package com.yc.utils;

public class MoneyUtil{

	private static final String[]         NUMBERS={"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};

	private static final String []
 	IUNIT={"元","拾","佰","仟","万","拾","佰","仟","亿","拾","佰","仟","万","拾","佰","仟"};


	private static final String[] DUNIT={"角","分","厘"};


	public static String toChinese(String str){

		str=str.replaceAll(",","");
		String integerStr;
		String decimalStr;

		if(str.indexOf(".")>0){
			integerStr=str.substring(0,str.indexOf("."));
			decimalStr=str.substring(str.indexOf("."));
		}else if(str.indexOf(".")==0){

			integerStr="";
			decimalStr=str.substring(1);
		}else{
			integerStr=str;
			decimalStr="";
		}

		if(!integerStr.equals("")){
			integerStr=Long.toString(Long.parseLong(integerStr));
			if(integerStr.equals("0")){
				integerStr="";
			}
		}

		if(integerStr.length()>IUNIT.length){

			System.out.println(str+":超出处理能力");
			return str;
		}
		int[] integers=toArray(integerStr);
		boolean isMust5=isMust5(integerStr);
		int[] decimals=toArray(decimalStr);
		return getChineseInteger(integers,isMust5)+getChineseDecimal(decimals);
	}

	private static int[] toArray(String number){

		int[] array=new int[number.length()];
		for(int i=0;i<number.length(); 	i++){
			array[i]=Integer.parseInt(number.substring(i,i+1));
		}
		return array;
	}

	
	/**
	 * 得到中文金额的整数部分
	 */
	private static String getChineseInteger(int[] integers,boolean isMust5){
		StringBuffer chineseInteger =new StringBuffer("");
		int length = integers.length;
		for(int i=0;i<length; i++){
			//0出现杂关键位置：1234(万) 5678（亿）9012（万）3456（元）
			//特殊情况：10（拾元。壹拾元。壹拾万元。拾万元）
			String key="";
			if(integers[i] ==0){
				if((length-i)==13) //万（亿（必读）
					key = IUNIT[4];
				else if((length-i)==9) //亿（必读）
					key =IUNIT[0];
				else if((length-i)==5 && isMust5)  //万（不必填）
					key=IUNIT[4];
				else if((length=i) ==1) //元（必填）
					key=IUNIT[0];
				//0遇非0时补零，不包含最后一位
				if(( length-i)> 1  &&  integers[i+1] !=0)
					key+=NUMBERS[0];
			}
			chineseInteger.append(integers[i]== 0 ? key
					: (NUMBERS[integers[i]] + IUNIT[length-i-1]));
		}
		return chineseInteger.toString();
	}
	
	/**
	 * 得到中文金额的小数部分
	 */
	private static String getChineseDecimal(int [] decimals){
		StringBuffer chineseDecimal = new StringBuffer("");
		for(int i=0;i<decimals.length;i++){
			//舍去3位小数之后的
			if(i==3)
				break;
				chineseDecimal.append(decimals[i]==0?""
						:(NUMBERS[decimals[i]]+ DUNIT[i]));
		}
			return chineseDecimal.toString();
	}
	
	
	/**
	 * 判断第5位数字的单位"万"是否应加。
	 * @param args
	 */
	private static boolean isMust5(String integerStr){
		int length =integerStr.length();
		if(length>4){
			String subInteger="";
			if(length>8){
				//取得从低位数，第5到第8位的字串
				subInteger = integerStr.substring(length -8,length -4);
			}else{
				subInteger = integerStr.substring(0,length -4);
			}
			return Integer.parseInt(subInteger)>0;
		}else{
			return false;
		}
	}
	
	public static void main(String[] args){
		String number = "1.23";
		System.out.println(number+ " "+MoneyUtil.toChinese(number));
		number ="1234567890123456.123";
		System.out.println(number+ " "+MoneyUtil.toChinese(number));
		number ="0.0798";
		System.out.println(number+ " "+MoneyUtil.toChinese(number));
		number ="10,001,000.09";
		System.out.println(number+ " "+MoneyUtil.toChinese(number));
		number ="1.00";
		System.out.println(number+ " "+MoneyUtil.toChinese(number));
		number = "10000000100.oo";
		System.out.println(number+ " "+MoneyUtil.toChinese(number));
		
	}
}
