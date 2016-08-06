package com.yc.ui;

import org.eclipse.jface.dialogs.MessageDialog;


import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.prefs.BackingStoreException;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.omg.PortableServer.ServantLocatorPackage.CookieHolder;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.yc.bean.Admin;

import com.yc.dao.DBHelper;
import com.yc.utils.Common;

import com.yc.utils.MD5;
import com.yc.utils.RegistrationUtils;
import com.yc.utils.UiUtils;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Combo;

public class LoginUi {

	protected Shell shell;
	private Display display;
	private Text text_1;
	private Combo combo;
	private  Button registerButton;
	

	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LoginUi window = new LoginUi();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.MIN);
		shell.setImage(SWTResourceManager.getImage(LoginUi.class, "/com/yc/ui/1.jpg"));
		shell.setSize(489, 353);
		shell.setText("\u7F8E\u56FE\u79C0\u79C0\u767B\u5F55");
		shell.setLayout(null);
		
		UiUtils.showInCenter(shell, display);    //使窗口居中显示
	
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setFont(SWTResourceManager.getFont("微软雅黑", 22, SWT.BOLD));
		lblNewLabel.setBounds(103, 46, 264, 39);
		lblNewLabel.setText("\u6B22\u8FCE\u4F7F\u7528\u7F8E\u56FE\u79C0\u79C0");
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("微软雅黑", 13, SWT.NORMAL));
		lblNewLabel_1.setAlignment(SWT.CENTER);
		lblNewLabel_1.setBounds(55, 112, 96, 28);
		lblNewLabel_1.setText("\u7528\u6237\u540D\uFF1A");
		
		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("微软雅黑", 13, SWT.NORMAL));
		lblNewLabel_2.setAlignment(SWT.CENTER);
		lblNewLabel_2.setBounds(55, 166, 96, 28);
		lblNewLabel_2.setText("\u7528\u6237\u5BC6\u7801\uFF1A");
		
		combo = new Combo(shell, SWT.NONE);
		
		combo.setBounds(166, 114, 201, 26);
		
		
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setEchoChar('*');//回显字符
		text_1.setBounds(168, 168, 199, 25);
		
	
		
		Link link = new Link(shell, SWT.NONE);
		link.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				//跳转页面注册
				LoginUi.this.shell.dispose();
				RegisterUi window = new RegisterUi();
				window.open();
			}
		});
		
		link.setForeground(SWTResourceManager.getColor(30, 144, 255));
		
		link.setBounds(388, 123, 53, 17);
		link.setText("   \u6CE8  \u518C");
		
		CLabel lblNewLabel_3 = new CLabel(shell, SWT.NONE);
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				//跳转页面修改密码
				LoginUi.this.shell.dispose();
				AlterPwdUi window = new AlterPwdUi();
				window.open();
				
			}
		});
		lblNewLabel_3.setForeground(SWTResourceManager.getColor(30, 144, 255));
		lblNewLabel_3.setBounds(388, 178, 60, 16);
		lblNewLabel_3.setText(" 修改密码");
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		
		btnNewButton.setBounds(150, 275, 80, 27);
		btnNewButton.setText("\u767B  \u5F55");
		
		registerButton = new Button(shell, SWT.CHECK);
	
		registerButton.setBounds(166, 232, 98, 17);
		registerButton.setText("   \u8BB0\u4F4F\u5BC6\u7801");
		
		Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.exit(0);
			}
		});
		button.setBounds(318, 275, 80, 27);
		button.setText("取消");
		
		Label lblNewLabel_4 = new Label(shell, SWT.CENTER);
		lblNewLabel_4.setAlignment(SWT.CENTER);
		lblNewLabel_4.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblNewLabel_4.setBounds(166, 145, 201, 17);
		
		Label lblNewLabel_5 = new Label(shell, SWT.NONE);
		lblNewLabel_5.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblNewLabel_5.setAlignment(SWT.CENTER);
		lblNewLabel_5.setBounds(166, 199, 201, 17);
		
		
		//初始化
		init();
		
		//选择用户
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//从combo中选择一个用户
				String username=combo.getText().trim();
				String userpwd=text_1.getText().trim();
				MD5 md5=new MD5();
				
					try {
						userpwd=md5.MD5Encode(userpwd);
					} catch (Exception e1) {
					}
			
				String sql1="select count(*) from MeituUser where userName=? and userPwd=?";
				List<String> params=new ArrayList<String>();
				params.add(username);
				params.add(userpwd);
				DBHelper db1=new DBHelper();
				double r=db1.doSelectFunction(sql1, params);
				if(r>0){
					
				}else{
					text_1.setText("");
				}
				if(recollectPwd()==null){
					return ;
				}
				if(recollectPwd().equals(username)){
					Map maps=RegistrationUtils.findRegistration( );
					if(maps==null){
						//MessageDialog.openInformation(shell, "提示", "上次登录没有记住密码");
						return ;
					}
					registerButton.setSelection(true);
					Set keys=maps.keySet();//得到所有键的名字
					Iterator its=keys.iterator();//迭代器
					while(its.hasNext()){
						String key=(String) its.next();
						String value=(String) maps.get(key);
						if(key.equals("userName")){
							combo.setText(value);
						}else if(key.equals("userPwd")){
							text_1.setText(value);
						}
					}
				}
				
			}
		});
		
		
		//登录
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {	
				
				//1.取出用户名  密码  是否记住
				String userName=combo.getText().trim();
				String userPwd= text_1.getText().trim();
				boolean flag=registerButton.getSelection();
				if(userName==null || "".equals(userName) ){
				lblNewLabel_4.setText("用户名不能为空！");
					return;
				}
				lblNewLabel_4.setText("");
				if(userPwd==null || "".equals(userPwd) ){
					lblNewLabel_5.setText("用户密码不能为空！");
					return;
				}
				lblNewLabel_5.setText("");
				//boolean flag=registerButton.getSelection();
				//2.查询数据库 看是否有这个用户
				String sql="select count(*) from MeituUser where userName=? and userPwd=?";
				List<String> params=new ArrayList<String>();
				params.add( userName);
				try {
					params.add( MD5.MD5Encode(userPwd));
				} catch (Exception e1) {
					MessageDialog.openError(shell,"加密错误","请联系管理员。。");
					return;
				}
				DBHelper db=new DBHelper();
				double r=db.doSelectFunction(sql, params);
				//3.有   则看是否记录密码
				if(r>0){
					sql="select userId from MeituUser where userName=?";
					List<String> list=new ArrayList<String>();
					list.add( userName);
					String userId=db.comFind(sql, list);
					
					Common.admin=new Admin( userId , userName,userPwd);  //将登录的用户记录下来，存成一个静态变量 这样后面的页面就可以调用
					
					//MessageDialog.openError(shell,"登录成功","欢迎您"+userName);
				
					//记住密码
					Map<String,String> map=new HashMap<String ,String>();
					map.put("userName", userName);
					map.put("userPwd", userPwd);
					try {
						if(flag){
							RegistrationUtils.saveRegistration(map);
						}else{
							RegistrationUtils.delRegistration(map);
						}
					} catch (Exception e1) {
						MessageDialog.openError(shell,"记住密码错误",e1.getMessage());
					}
					
					//跳转页面
					LoginUi.this.shell.dispose();
					MainUi window = new MainUi();
					window.open();
				}else{
					lblNewLabel_5.setText("用户名或密码错误！");
				}
			}
		});
		
		
	}
	
	//做一个初始化
	private void init(){
		//初始化记录历史用户
		String sql="select userName from MeituUser";
		DBHelper db=new DBHelper();
		List<Map<String,String>>listmap=db.find(sql, null);
		if(listmap!=null && listmap.size()>0){
			combo.removeAll();
			for(Map<String,String > list:listmap){
			String value=list.get("USERNAME");
			combo.add(value);
		}
			
		}
		
	
		//初始化记住密码
		Map maps=RegistrationUtils.findRegistration( );
		if(maps==null){
			//MessageDialog.openInformation(shell, "提示", "上次登录没有记住密码");
			return ;
		}
		registerButton.setSelection(true);
		Set keys=maps.keySet();//得到所有键的名字
		Iterator its=keys.iterator();//迭代器
		while(its.hasNext()){
			String key=(String) its.next();
			String value=(String) maps.get(key);
			if(key.equals("userName")){
				combo.setText(value);
			}else if(key.equals("userPwd")){
				text_1.setText(value);
			}
		}
		
		
	}
	
	//记住密码(得到用户名)
	private String recollectPwd(){
		Map maps=RegistrationUtils.findRegistration( );
		if(maps==null){
			//MessageDialog.openInformation(shell, "提示", "上次登录没有记住密码");
			return null;
		}
		registerButton.setSelection(true);
		Set keys=maps.keySet();//得到所有键的名字
		Iterator its=keys.iterator();//迭代器
		String key=null;
		String value=null;
		while(its.hasNext()){
			key=(String) its.next();
			value=(String) maps.get(key);
			if(key.equals("userName")){
				return value;
			}
		}
		return null;
	
	}
	
	
}
