package com.yc.utils;


import org.apache.log4j.Logger;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Event;

import com.yc.bean.Admin;
import com.yc.bean.PicInfo;
import com.yc.bean.SharePicInfo;
import com.yc.ui.AlterPwdUi;
import com.yc.ui.DownloadRecordUi;
import com.yc.ui.EditorUi;
import com.yc.ui.LoginUi;
import com.yc.ui.MainAlterPwdUi;
import com.yc.ui.MainUi;
import com.yc.ui.MyPicUi;
import com.yc.ui.PicLibraryUi;
import com.yc.ui.PicWatermarkDialog;
import com.yc.ui.RecordUi;
import com.yc.ui.RegisterUi;
import com.yc.ui.SharedUi;
import com.yc.ui.UplaodRecordUi;
import com.yc.ui.WelcomeUi;
import com.yc.ui.WordWatermarkDialog;

//常用对象及方法类
public class Common {
	
	//获取一个log4j的日志记录器
	public static Logger log=Logger.getLogger(Common.class);
	
	public static Admin admin;
	public static LoginUi lu;
	public static EditorUi eu;
	public static WelcomeUi wu;
	public static MainUi mu;
	public static RecordUi ru;
	public static UplaodRecordUi uu;
	public static RegisterUi rtu;
	public static DownloadRecordUi du;
	public static AlterPwdUi au;
	public static MainAlterPwdUi mau;
	public static PicLibraryUi plu;
	public static SharedUi su;
	public static MyPicUi mpu;
	public static Event event;
	public static PicWatermarkDialog pwk;
	public static WordWatermarkDialog wwk;
	public static PicInfo picInfo;  //图片信息
	public static Image image;      //（图片水印、马赛克）处理后的图片
	public static SharePicInfo sharePicInfo;   //分享图片信息
	public static ImageData srcImage;
}
