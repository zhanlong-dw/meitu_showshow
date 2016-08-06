package com.yc.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

import com.yc.bean.Admin;
import com.yc.biz.MeituBiz;
import com.yc.dao.DBHelper;
import com.yc.utils.Common;
import com.yc.utils.UiUtils;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class MyPicUi extends Group {
	private int persize=4;
	private int page=1;
	private Label label;
	private Label label_1;
	private Label lblNewLabel;
	private Label lblNewLabel_1;
	private Label lblNewLabel_2;
	private Label lblNewLabel_3;
	private Label label_2;
	private Label label_3;
	private Label label_4;
	private Label label_5;
	private Label Label_6;
	public static Label lblNo;
	DBHelper db=new DBHelper();

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public MyPicUi(Composite parent, int style) {
		super(parent, style);
		setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		setText("我的图片");
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm = new SashForm(this, SWT.VERTICAL);
		
		SashForm sashForm_1 = new SashForm(sashForm, SWT.NONE);
		
		Composite composite = new Composite(sashForm_1, SWT.NONE);

		
		Group group = new Group(sashForm_1, SWT.NONE);
		group.setText("图片属性");
		
		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		Button button = new Button(composite_1, SWT.NONE);


		button.setText("上一页");
		button.setBounds(409, 15, 80, 27);
		
		Button button_1 = new Button(composite_1, SWT.NONE);

		button_1.setText("下一页");
		button_1.setBounds(611, 15, 80, 27);
		
		label = new Label(composite_1, SWT.NONE);
		label.setText("当前页");
		label.setBounds(517, 20, 61, 17);
		
		label_1 = new Label(composite_1, SWT.NONE);
		label_1.setText("图片共:");
		label_1.setBounds(762, 20, 99, 17);
		
		label_2 = new Label(group, SWT.NONE);
		label_2.setBounds(10, 49, 204, 17);
		label_2.setText("图片名：");
		
		label_3 = new Label(group, SWT.NONE);
		label_3.setBounds(10, 90, 228, 17);
		label_3.setText("图片大小：");
		
		label_4 = new Label(group, SWT.NONE);
		label_4.setBounds(10, 136, 204, 17);
		label_4.setText("图片分辨率：");
		
		label_5 = new Label(group, SWT.NONE);
		label_5.setBounds(10, 179, 204, 17);
		label_5.setText("上传时间：");
		
		Button button_2 = new Button(group, SWT.NONE);

		button_2.setBounds(121, 306, 80, 27);
		button_2.setText("分享图片");
		
		Button btnNewButton = new Button(group, SWT.NONE);

		btnNewButton.setBounds(22, 306, 80, 27);
		btnNewButton.setText("取消分享");
		
		Label_6 = new Label(group, SWT.NONE);
		Label_6.setBounds(10, 227, 191, 17);
		Label_6.setText("是否已分享： ");
		
		lblNo = new Label(group, SWT.NONE);
		lblNo.setBounds(153, 364, 61, 17);
		
		Label lblNo_1 = new Label(group, SWT.NONE);
		lblNo_1.setBounds(121, 364, 26, 17);
		lblNo_1.setText("NO.");
		
		Button button_4 = new Button(composite_1, SWT.NONE);

		button_4.setBounds(171, 15, 106, 27);
		button_4.setText("上传图片");
		composite.setLayout(null);
		
		Label lblNewLabel = new Label(composite, SWT.CENTER);
		lblNewLabel.setBounds(104, 10, 322, 200);
		
		
		Label lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setBounds(532, 10, 322, 200);
		
		Label lblNewLabel_2 = new Label(composite, SWT.NONE);
		lblNewLabel_2.setBounds(104, 223, 322, 200);
		
		Label lblNewLabel_3 = new Label(composite, SWT.NONE);
		lblNewLabel_3.setBounds(532, 223, 322,200);
		
		sashForm_1.setWeights(new int[] {872, 223});
		sashForm.setWeights(new int[] {392, 71});
		
		showPic(lblNewLabel,lblNewLabel_1,lblNewLabel_2,lblNewLabel_3,persize,page);
		
		//显示图片 信息
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				picInfo(0);
			}
		});
		
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				picInfo(1);
			}
		});
		
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				picInfo(2);
				
			}
		});
		
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				picInfo(3);
			}
		});
		//取消分享
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String id=lblNo.getText();
				//查询该图片是否已被分享
				List<String> list =new ArrayList<String>();
				String sql="select count(*) from MeituPicture where picState='是' and picId=?";
				list.add(id);
				double rs=db.doSelectFunction(sql, list);
				
				if(rs<=0){
					MessageDialog.openInformation(getShell(), "提示", "该图片并未分享....");
				}else{
					//分享图片
					List<String> params=new ArrayList<String>();
					sql="update MeituPicture set picState='否' where picId=?";
					params.add(id);
					int r=db.doUpdate(sql, params);
					
					if(r>0){
						MessageDialog.openInformation(getShell(), "提示", "修改成功！");
						Label_6.setText("是否已分享： 否");
					}else{
						MessageDialog.openInformation(getShell(), "提示", "修改失败！");
					}
				}
			}
		});
		//分享图片
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String id=lblNo.getText();
				//查询该图片是否已被分享
				List<String> list =new ArrayList<String>();
				String sql="select count(*) from MeituPicture where picState='是' and picId=?";
				list.add(id);
				double rs=db.doSelectFunction(sql, list);
				
				if(rs>0){
					MessageDialog.openInformation(getShell(), "提示", "该图片已经分享过了，不能再次分享....");
				}else{
					//分享图片
					List<String> params=new ArrayList<String>();
					sql="update MeituPicture set picState='是' where picId=?";
					params.add(id);
					int r=db.doUpdate(sql, params);
					
					if(r>0){
						String sql2="update  meituuser set userrank=userrank+100 where userid=?";
						List<String> up=new ArrayList<String>();
						up.add(Common.admin.getUserId());
						db.doUpdate(sql2, up);
						MessageDialog.openInformation(getShell(), "提示", "分享成功！        积分+100");
						Label_6.setText("是否已分享：是");
					}else{
						MessageDialog.openInformation(getShell(), "提示", "分享失败！");
					}
				}	
			}
		});
		//上一张
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				page--;
				
				if(page<=0){
					MessageDialog.openInformation(getShell(), "提示", "已经是第1页！");
					page=1;
					return;
				}
				showPic(lblNewLabel,lblNewLabel_1,lblNewLabel_2,lblNewLabel_3,persize,page);
				
			}
		});
		
		//下一张
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				page++;
				String sql="select count(*) from MeituPicture where userId=?";
				List<String> li=new ArrayList<String>();
				li.add(Common.admin.getUserId());
				int r=(int) db.doSelectFunction(sql,li);
				int y=0;
				if(r%persize>0){
					y=r/persize+1;
				}else{
					y=r/persize;
				}	
				if(page>y){
					MessageDialog.openInformation(getShell(), "提示", "已经是最后一页了！");
					--page;
					return;
				}
				showPic(lblNewLabel,lblNewLabel_1,lblNewLabel_2,lblNewLabel_3,persize,page);
				//composite.getBackgroundImage().dispose();
				//button_2.notifyListeners(SWT.Selection, Common.event);
			}
		});
		//上传
		button_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
		        //打开对话框
				FileDialog fd = new FileDialog(getShell(), SWT.OPEN);
		        fd.setText("打开图片");
		        fd.setFilterPath(System.getProperty("user.home"));
		        fd.setFilterExtensions(new String[] { "*.gif; *.jpg; *.png; *.ico; *.bmp" });
		        fd.setFilterNames(new String[] { "图片(gif, jpeg, png, ico, bmp)" });
		        String filePath=fd.open();
		        
		        if(filePath==null || "".equals(filePath)){
		        	return;
		        }
		        
		        //获取基本信息
		        String userId=Common.admin.getUserId();
		        
		        int index=filePath.lastIndexOf(".");
		        String type=filePath.substring(index+1);  //图片类型
		        
		        int star=filePath.lastIndexOf("\\");
		        String picName=filePath.substring(star+1,index);//图片名
		        String s="select count(*) from MeituPicture where picName=?";
		        List<String> li=new ArrayList<String>();
		        li.add(picName);
		        double rr=db.doSelectFunction(s,li);
		        if(rr>0){
		        	MessageDialog.openInformation(getShell(), "提示", "图片名重复！");
		        	return;
		        }
		        File file=new File (filePath);
		        long size=file.length()/1024;
		        String sizes=size+" KB";//图片大小
		        
		       
		        BufferedImage bufferedImage=null;
				try {
					bufferedImage = ImageIO.read(file);
				} catch (IOException e1) {
					e1.printStackTrace();
				}   
		        int width = bufferedImage.getWidth();   
		        int height = bufferedImage.getHeight();//图片分辨率
		        String picResolution=width+"x"+height;
		        //存入数据库
		       MeituBiz meitu=new MeituBiz();
		       int r=meitu.addPic(userId,type,picName,sizes,picResolution,filePath);
		        if(r==1){
					String sql2="update  meituuser set userrank=userrank+200 where userid=?";
					List<String> up=new ArrayList<String>();
					up.add(0,Common.admin.getUserId());
					db.doUpdate(sql2, up);
		        	MessageDialog.openInformation(getShell(), "提示", "上传成功！        积分+200");
		        	showPic(lblNewLabel,lblNewLabel_1,lblNewLabel_2,lblNewLabel_3,persize,page);
		        	
		        }else{
		        	
		        	MessageDialog.openInformation(getShell(), "提示", "上传失败！");
		        }
			}
		});

	}


	//将图片显示到label
	
	private void showPic(Label Label1,Label Label2,Label Label3,Label Label4, int persize,int page) {
		MeituBiz meitu=new MeituBiz();
		ImageData[] is=meitu.showPic(persize,page);
		if(is[0]==null){
			Label1.setBackgroundImage(null);
			Label2.setBackgroundImage(null);
			Label3.setBackgroundImage(null);
			Label4.setBackgroundImage(null);
		}else if(is[1]==null){
			layout(Label1, is,0);
			Label2.setBackgroundImage(null);
			Label3.setBackgroundImage(null);
			Label4.setBackgroundImage(null);
		}else if(is[2]==null){
			layout(Label1, is,0);
			layout(Label2, is,1);
			Label3.setBackgroundImage(null);
			Label4.setBackgroundImage(null);
		}else if(is[3]==null){
			layout(Label1, is,0);
			layout(Label2, is,1);
			layout(Label3, is,2);
			Label4.setBackgroundImage(null);
		}else{
			layout(Label1, is,0);
			layout(Label2, is,1);
			layout(Label3, is,2);
			layout(Label4, is,3);
		}
			
			
			String sql="select count(*) from MeituPicture where userId=?";
			String id=Common.admin.getUserId();
			List<String> params=new ArrayList<String>();
			params.add(id);
			int r=(int) db.doSelectFunction(sql,params);
			//picInfo();//显示图片基本信息
			label_1.setText("图片共："+r+"张");
			if(r%persize>0){
				label.setText("当前页"+page+"/"+(r/persize+1));
			}else{
				label.setText("当前页"+page+"/"+r/persize);
			}
			
		
	}

	//显示图片
	private void layout(Label Label, ImageData[] is,int i) {
		is[i]=is[i].scaledTo(Label.getBounds().width,Label.getBounds().height);
		Image image=new Image(getDisplay(),is[i]);
		Label.setBackgroundImage(image);
	}
	//查询图片基本信息
	private void picInfo(int i){
		List list=Common.picInfo.getList();
		
		if(i<list.size()){
			String[] s=(String[]) list.get(i);
		
			String name=s[0];
			label_2.setText("图片名： "+name);
			
			String size=s[1];
			label_3.setText("图片大小： "+size);
			
			String resolution=s[2];
			label_4.setText("图片分辨率： "+resolution);
			
			String time=s[3];
			label_5.setText("上传时间： "+time);
			
			String state=s[4];
			Label_6.setText("是否已分享： "+state);
			
			String id=s[6];
			lblNo.setText(id);
		}	
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
