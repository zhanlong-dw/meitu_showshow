package com.yc.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

import com.yc.biz.MeituBiz;
import com.yc.dao.DBHelper;
import com.yc.utils.Common;

public class SharedUi extends Group {
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
	private Label label_6;
	public static Label lblNo;
	DBHelper db=new DBHelper();
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SharedUi(Composite parent, int style) {
		super(parent, style);
		setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		setText("共享社区");
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm = new SashForm(this, SWT.VERTICAL);
		
		SashForm sashForm_1 = new SashForm(sashForm, SWT.NONE);
		
		Composite composite = new Composite(sashForm_1, SWT.NONE);
		
		Group group = new Group(sashForm_1, SWT.NONE);
		group.setText("图片信息");
		
		label_2 = new Label(group, SWT.NONE);
		label_2.setBounds(10, 49, 206, 17);
		label_2.setText("图片名：");
		
		label_3 = new Label(group, SWT.NONE);
		label_3.setBounds(10, 90, 206, 17);
		label_3.setText("图片大小：");
		
		label_4 = new Label(group, SWT.NONE);
		label_4.setBounds(10, 130, 233, 17);
		label_4.setText("图片分辨率");
		
		label_5 = new Label(group, SWT.NONE);
		label_5.setBounds(10, 198, 194, 17);
		label_5.setText("上传用户：");
		
		label_6 = new Label(group, SWT.NONE);
		label_6.setBounds(10, 164, 233, 17);
		label_6.setText("上传时间：");
		
		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		Button button = new Button(composite_1, SWT.NONE);

		button.setText("上一页");
		button.setBounds(409, 15, 80, 27);
		
		Button button_1 = new Button(composite_1, SWT.NONE);

		button_1.setText("下一页");
		button_1.setBounds(611, 15, 80, 27);
		
		label = new Label(composite_1, SWT.NONE);
		label.setText("当前页 ");
		label.setBounds(517, 20, 61, 17);
		
		label_1 = new Label(composite_1, SWT.NONE);
		label_1.setText("图片共：");
		label_1.setBounds(762, 20, 99, 17);
		
		
		Button button_2 = new Button(composite_1, SWT.NONE);

		button_2.setBounds(107, 15, 80, 27);
		button_2.setText("刷新页面");
		composite.setLayout(null);
		
		Label lblNewLabel = new Label(composite, SWT.CENTER);
		lblNewLabel.setBounds(104, 10, 322, 200);
		
		
		Label lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setBounds(532, 10, 322, 200);
		
		Label lblNewLabel_2 = new Label(composite, SWT.NONE);
		lblNewLabel_2.setBounds(104, 223, 322, 200);
		
		Label lblNewLabel_3 = new Label(composite, SWT.NONE);
		lblNewLabel_3.setBounds(532, 223, 322,200);
		
		lblNo = new Label(group, SWT.NONE);
		lblNo.setBounds(193, 366, 61, 17);
		
		Label lblNo_1 = new Label(group, SWT.NONE);
		lblNo_1.setBounds(164, 366, 23, 17);
		lblNo_1.setText("NO.");
		//初始化显示
		showPic(lblNewLabel,lblNewLabel_1,lblNewLabel_2,lblNewLabel_3,persize,page);
		sashForm_1.setWeights(new int[] {959, 263});
		sashForm.setWeights(new int[] {393, 76});

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
		//刷新页面
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				showPic(lblNewLabel,lblNewLabel_1,lblNewLabel_2,lblNewLabel_3,persize,page);
			}
		});
		//上一张
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				page--;
				
				if(page<=0){
					MessageDialog.openInformation(getShell(), "提示", "已经是第1张！");
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
				String sql="select count(*) from MeituPicture where picState='是'";
				int r=(int) db.doSelectFunction(sql,null);
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
				
			
			}
		});
	}
	//将图片显示到label
	private void showPic(Label Label1,Label Label2,Label Label3,Label Label4,int persieze,int page) {
		MeituBiz meitu=new MeituBiz();
		ImageData[] is=meitu.showSharePic(persize,page);
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
			
			
			String sql="select count(*) from MeituPicture where  picState='是'";
			int r=(int) db.doSelectFunction(sql,null);
			//picInfo();//显示图片基本信息
			label_1.setText("图片共："+r+"张");
			if(r%persize>0){
				label.setText("当前页"+page+"/"+(r/persize+1));
			}else{
				label.setText("当前页"+page+"/"+r/persize);
			}
	}
	
	
	//查询图片基本信息
	private void picInfo(int i){
		List list=Common.sharePicInfo.getList();
		
		if(i<list.size()){
			String[] s=(String[]) list.get(i);
		
			String pname=s[0];
			label_2.setText("图片名： "+pname);
			
			String size=s[1];
			label_3.setText("图片大小： "+size);
			
			String resolution=s[2];
			label_4.setText("图片分辨率： "+resolution);
			
			String time=s[3];
			label_6.setText("上传时间： "+time);
			
			String uname=s[5];
			label_5.setText("上传用户： "+uname);
			
			String userId=s[6];
			lblNo.setText(userId);
		}	
	}
	//显示图片
	private void layout(Label Label, ImageData[] is,int i) {
		is[i]=is[i].scaledTo(Label.getBounds().width,Label.getBounds().height);
		Image image=new Image(getDisplay(),is[i]);
		Label.setBackgroundImage(image);
	}	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
