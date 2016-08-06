package com.yc.ui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.yc.bean.Admin;
import com.yc.dao.DBHelper;
import com.yc.utils.Common;

public class UserInfo extends Dialog {

	protected Object result;
	protected Shell shell;


	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public UserInfo(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM);
		shell.setSize(367, 281);
		shell.setText("用户信息");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		
		SashForm sashForm = new SashForm(shell, SWT.VERTICAL);
		
		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Group group_1 = new Group(composite_1, SWT.BORDER);
		group_1.setText("用户");
		
		Label lblNewLabel = new Label(group_1, SWT.NONE);
		lblNewLabel.setBounds(55, 37, 61, 17);
		lblNewLabel.setText("用户名");
		
		
		Label lblNewLabel_1 = new Label(group_1, SWT.NONE);
		lblNewLabel_1.setBounds(55, 82, 61, 17);
		lblNewLabel_1.setText("积分");
		
		Label lblNewLabel_2 = new Label(group_1, SWT.NONE);
		lblNewLabel_2.setBounds(55, 133, 61, 17);
		lblNewLabel_2.setText("级别");
		
		Label lblNewLabel_3 = new Label(group_1, SWT.NONE);
		lblNewLabel_3.setBounds(55, 183, 61, 17);
		lblNewLabel_3.setText("注册时间");
		
		Label lblNewLabel_4 = new Label(group_1, SWT.NONE);
		lblNewLabel_4.setBounds(153, 82, 188, 17);
		
		Label lblNewLabel_5 = new Label(group_1, SWT.NONE);
		lblNewLabel_5.setBounds(153, 133, 188, 17);
		
		Label lblNewLabel_6 = new Label(group_1, SWT.NONE);
		lblNewLabel_6.setBounds(153, 183, 188, 17);
		
		
		Label lblNewLabel_7 = new Label(group_1, SWT.NONE);
		lblNewLabel_7.setBounds(153, 37, 188, 17);
		sashForm.setWeights(new int[] {249});
		
		
	
		String username=Common.admin.getUserName();
		if(username.equals("")||username==null){
			MessageDialog.openError(getParent(), "提示", "用户无登录");
		}
		DBHelper db=new DBHelper();
		String sql="select userRank from MeituUser where userName=?";
		List<String> up=new ArrayList<String>();
		up.add(username);
		String i=null;
		try {
			ResultSet r=db.doSelect(sql, up);
			
			if(r.next()){
				i=r.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int level=Integer.parseInt(i);
		level=level/200;
		sql="select userName,userRank,userTegral,userRegisterTime from MeituUser where userName=?";
		List<String>params=new ArrayList<String>();
		params.add(username);
		List<Map<String,String>> list=db.find(sql, params);
		Map map=new HashMap();
		for(int index=0;index<list.size();index++){
			map=list.get(index);
			Set<String >keys=map.keySet();
			Iterator<String> its=keys.iterator();
			
			while(its.hasNext()){
				String key=its.next();
				String value=(String) map.get(key);
				if(key.equals("USERNAME")){
					lblNewLabel_7 .setText(value);
				}else if(key.equals("USERRANK")){
					lblNewLabel_4.setText(value);
				}else if(key.equals("USERTEGRAL")){
					lblNewLabel_5.setText(level+"");
				}else if(key.equals("USERREGISTERTIME")){
					value=value.substring(0, 19);
					lblNewLabel_6.setText(value);
				}
			}
		}

	}
}
