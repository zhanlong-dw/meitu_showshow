package com.yc.biz;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;

import com.yc.bean.PicInfo;
import com.yc.bean.SharePicInfo;
import com.yc.dao.DBHelper;
import com.yc.ui.PicLibraryUi;
import com.yc.utils.Common;

public class MeituBiz {
	
	//从数据库中读图片
	public int readPic(String id,String filePath){
		DBHelper db=new DBHelper();
		Connection con=db.getCon();
		PreparedStatement pstmt=null;
		ResultSet r=null;
		
		
		String sql="select * from MeituPicture where picId=?";
		
			try {
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1,id);
				
				r=pstmt.executeQuery();
				if(r.next()){
					oracle.sql.BLOB b=(oracle.sql.BLOB) r.getBlob(4);
					InputStream iis=b.getBinaryStream();
					OutputStream oos=new BufferedOutputStream(new FileOutputStream(filePath));
					int length=-1;
					byte[] bs=new byte[1024];
					int i=1;
					while( ( length=iis.read(bs,0,bs.length)  )!=-1){
						oos.write(bs,0,length);
						
						Common.plu.progressBar.setVisible(true);
						Common.plu.progressBar.setMinimum(0); // 最小值
						Common.plu.progressBar.setMaximum(200);// 最大值
						
                        Common.plu.progressBar.setSelection(200);
                        Common.plu.progressBar.setVisible(false);
                       
						oos.flush();
					}
				}
				return 1;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			} finally{
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				db.closeAll(r,pstmt, con);
			}
	}
	//图片大小转换
	private static String getSize(long size){
		if(size/1024/1024/1024/1024>0){
			return size/1024/1024/1024/1024+"T";
		}else if(size/1024/1024/1024>0){
			return size/1024/1024/1024+"G";
		}else if(size/1024/1024>0){
			return size/1024/1024+"M";
		}else if(size/1024>0){
			return size/1024+"KB";
		}else{
		return size+"b";
		}
	}
	//图片查询
	public ImageData[] showPic(int persize,int page){
		DBHelper db=new DBHelper();
		Connection con=db.getCon();
		PreparedStatement pstmt=null;
		ResultSet r=null;
		ImageData imageData=null;
		ImageData[] is=new ImageData[4];
		int i=0;
		String sql="select * from (select A.*,rownum rn from (select * from MeituPicture where userId=?) A where rownum<?) where rn>?";
		try {
			String userId=Common.admin.getUserId();
			int max= (persize*page+1);
			int min=(page-1)*persize;
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1,userId);
			pstmt.setInt(2,max);
			pstmt.setInt(3,min);
			
			List list=new ArrayList(4);
			r=pstmt.executeQuery();
			while(r.next()){
				oracle.sql.BLOB b=(oracle.sql.BLOB) r.getBlob(4);
				InputStream iis=b.getBinaryStream();
				imageData=new ImageData(iis);
				
				List<String> params=new ArrayList<String>();
				String sql1="select userName from MeituUser where userId=?";
				params.add(r.getString(1));
				String name=db.comFind(sql1, params);
				String[] s=new String[]{r.getString(3),r.getString(8),r.getString(7),r.getString(6),r.getString(10),name,r.getString(2)};
				
				if(i<is.length){
					is[i]=imageData;
					list.add(i,s);
					i++;
				}
			}
			Common.picInfo=new PicInfo(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			db.closeAll(r,pstmt, con);
		}
		return is;
	}
	
	//共享图片查询
	public ImageData[] showSharePic(int persize,int page){
		DBHelper db=new DBHelper();
		Connection con=db.getCon();
		PreparedStatement pstmt=null;
		ResultSet r=null;
		ImageData imageData=null;
		ImageData[] is=new ImageData[4];
		String sql="select * from (select A.*,rownum rn from (select * from MeituPicture where picState='是') A where rownum<?) where rn>?";
		try {
			
			int max= (persize*page+1);
			int min=(page-1)*persize;
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,max);
			pstmt.setInt(2,min);
			int i=0;
			
			List list=new ArrayList();
			r=pstmt.executeQuery();
			while(r.next()){
				oracle.sql.BLOB b=(oracle.sql.BLOB) r.getBlob(4);
				InputStream iis=b.getBinaryStream();
				imageData=new ImageData(iis);
				List<String> params=new ArrayList<String>();
				String sql1="select userName from MeituUser where userId=?";
				params.add(r.getString(1));
				String name=db.comFind(sql1, params);
				String[] s=new String[]{r.getString(3),r.getString(8),r.getString(7),r.getString(6),r.getString(10),name,r.getString(2)};
				
				if(i<is.length){
					is[i]=imageData;
					list.add(i,s);
					i++;
				}	
			}
			Common.sharePicInfo=new SharePicInfo(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			db.closeAll(r,pstmt, con);
		}
		return is;
	}
	/**
	 * 添加图片到数据库
	 * @param userId
	 * @param type
	 * @param picName
	 * @param size
	 * @param picResolution
	 * @param filePath
	 * @return
	 */
	public int addPic(String userId,String type,String picName,String size,String picResolution,String filePath){
			
		DBHelper db=new DBHelper();
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet r=null;
		try {
			con=db.getCon();
			pstmt = null;
			r = null;
			
			con.setAutoCommit(false);//关闭隐式事务
				
				
			//插入基本信息  但不包括图片
			String sql="insert into MeituPicture values(?,seq_MeituPicture_picId.nextval,?,empty_blob(),'暂无备注',sysdate,?,?,?,default)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,userId);
			pstmt.setString(2,picName);
			pstmt.setString(3,picResolution);
			pstmt.setString(4,size);
			pstmt.setString(5,type);
			pstmt.executeUpdate();				
			
			String sql1="select picPicture from MeituPicture where picName=? for update ";// 锁定这一行的数据
			pstmt=con.prepareStatement(sql1);
			pstmt.setString(1, picName);
			r=pstmt.executeQuery();				
			
			if(r!=null&&r.next()){
				InputStream iis=null;
				OutputStream oos=null;
				iis=new BufferedInputStream(new FileInputStream(filePath));
				oracle.sql.BLOB b=(oracle.sql.BLOB) r.getBlob(1);
				oos=b.getBinaryOutputStream();
				//从输入流读取图片
				byte[] bs=new byte[1024];
				int length=-1;
				while( (length=iis.read(bs,0,bs.length))!=-1){
					oos.write(bs,0,length);
					oos.flush();
				}
				con.commit();
				
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e.printStackTrace();
			}
			return 0;
		}finally{
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			db.closeAll(r,pstmt, con);
		}
	}
}
