package com.yc.ui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.yc.biz.MeituBiz;
import com.yc.dao.DBHelper;
import com.yc.utils.Common;
import org.eclipse.swt.widgets.ProgressBar;

public class PicLibraryUi extends Composite {
	private StackLayout sl;
	public static ProgressBar progressBar;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public PicLibraryUi(Composite parent, int style) {
		super(parent, style);
		setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.NORMAL));
		
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm = new SashForm(this, SWT.VERTICAL);
		
		Composite composite_2 = new Composite(sashForm, SWT.NONE);
		
		Button button_3 = new Button(composite_2, SWT.NONE);

		button_3.setBounds(227, 10, 80, 27);
		button_3.setText("进入共享社区");
		
		Button button_2 = new Button(composite_2, SWT.NONE);

		button_2.setBounds(486, 10, 80, 27);

		button_2.setText("下载");
		
		Button button_4 = new Button(composite_2, SWT.NONE);

		button_4.setBounds(80, 10, 80, 27);
		button_4.setText("我的图片");
		
		progressBar = new ProgressBar(composite_2, SWT.NONE);
		progressBar.setBounds(594, 10, 192, 27);
		progressBar.setVisible(false);
		Composite composite = new Composite(sashForm, SWT.NONE);
		
		//下载
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				progressBar.setSelection(0);
				int r=0;
		        String id=null;
		        MeituBiz meitu=new MeituBiz();
		        if(sl.topControl==Common.su){
			        id=SharedUi.lblNo.getText();
			        if(id==null||"".equals(id)){
			        	MessageDialog.openInformation(getShell(), "提示", "请先选择您要下载的图片......");
			        	return;
			        }else{
				        String filePath = downDialog();
						if(filePath==null || "".equals(filePath)){
							MessageDialog.openInformation(getShell(), "提示", "下载路径不能为空...");
							return;
						}
			        	r=meitu.readPic(id, filePath);
			        }
			    }else{
			        id=MyPicUi.lblNo.getText();
			        if(id==null||"".equals(id)){
			        	MessageDialog.openInformation(getShell(), "提示", "请先选择您要下载的图片......");
			        	return;
			        }else{
				        String filePath = downDialog();
						if(filePath==null || "".equals(filePath)){
							MessageDialog.openInformation(getShell(), "提示", "下载路径不能为空...");
							return;
						}
			        	r=meitu.readPic(id, filePath);
			        }
		        }    
		        if(r==1){
		        	
						try {
							DBHelper db=new DBHelper();
							String sql="select * from MeituPicture where picId=?";
							List<String> list=new ArrayList<String>();
							list.add(id);
							ResultSet rs=db.doSelect(sql, list);
							
								List<String> params=new ArrayList<String>();
								sql="insert into DownloadInfo values(seq_DownloadInfo_downId.nextval,?,?,?,'暂无备注',sysdate,?,?,?)";
								params.add(0,Common.admin.getUserId());
								String a=null;
								String b=null;
								String c=null;
								String d=null;
								
								if(rs.next()){
									a=rs.getString(3);
									b=rs.getString(7);
									c=rs.getString(8);
									d=rs.getString(9);
									
								}
								
								
								params.add(1,id);
								params.add(2,a);
								params.add(3,b);
								params.add(4,c);
								params.add(5,d);
								int i=db.doUpdate(sql, params);
								//update  meituuser set userrank=userrank+100 where userid=1021
								String sql2="update  meituuser set userrank=userrank+100 where userid=?";
								List<String> up=new ArrayList<String>();
								up.add(0,id);
								db.doUpdate(sql2, up);
							MessageDialog.openInformation(getShell(), "提示", "下载成功！        积分+100");
							//insert into DownloadInfo values(seq_DownloadInfo_downId.nextval,userId,picId,picName,picComent,picDowntime,picResolution,picSize,picFormat);

						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					
		        }else{
		        	
		        	MessageDialog.openInformation(getShell(), "提示", "下载失败！");
		        }
				
			}


		});
		
		//设置堆栈式布局
		sl=new StackLayout();
		Common.mpu=new MyPicUi(composite,SWT.None);
		Common.su=new SharedUi(composite,SWT.None);
		composite.setLayout(sl);
		sl.topControl=Common.mpu;
		sashForm.setWeights(new int[] {50, 491});
		composite.layout();
		//切换到 我的图片
		button_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sl.topControl=Common.mpu;
				composite.layout();
				
			}
		});
		//切换到 共享社区
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sl.topControl=Common.su;
				composite.layout();
			}
		});		

	}
	
	//下载对话框
	private String downDialog() {
		//打开对话框
		FileDialog dialog=new FileDialog(getShell(),SWT.SAVE|SWT.CENTER);
		String[] extensions = new String[] { "*.jpg", "*.gif", "*.ico", "*.png" };
		dialog.setFilterExtensions(extensions);
		dialog.setFilterNames(new String[] { "JPEG (*.jpg)", "GIF (*.gif)", "ICO (*.ico)", "PNG (*.png)" });
		String filePath=dialog.open();  //返回的全路径（路径+文件名）
		return filePath;
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
