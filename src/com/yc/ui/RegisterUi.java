package com.yc.ui;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.yc.dao.DBHelper;
import com.yc.utils.MD5;
import com.yc.utils.UiUtils;

public class RegisterUi {

	protected Shell shell;
	private Text text;
	private Text text1;
	private Text text2;
	private Display display;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			RegisterUi window = new RegisterUi();
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
		shell.setImage(SWTResourceManager.getImage(RegisterUi.class, "/com/yc/ui/1.jpg"));
		shell.setSize(530, 379);
		shell.setText("用户注册");
		UiUtils.showInCenter(shell, display);
		
		Group group = new Group(shell, SWT.NONE);
		group.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		group.setText("注册");
		group.setBounds(61, 72, 404, 210);
		
		Label label_1 = new Label(group, SWT.NONE);
		label_1.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		label_1.setBounds(37, 41, 78, 23);
		label_1.setText("请输入用户名");
		
		Label label_2 = new Label(group, SWT.NONE);
		label_2.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		label_2.setBounds(37, 94, 78, 23);
		label_2.setText("请输入密码");
		
		Label label_3 = new Label(group, SWT.NONE);
		label_3.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		label_3.setBounds(37, 147, 78, 23);
		label_3.setText("请确认密码");
		
		text = new Text(group, SWT.BORDER);
		text.setBounds(138, 41, 180, 23);
		
		text1 = new Text(group, SWT.BORDER | SWT.PASSWORD);
		text1.setBounds(138, 94, 180, 23);
		
		text2 = new Text(group, SWT.BORDER | SWT.PASSWORD);
		text2.setBounds(138, 146, 180, 23);
		
		Label lblNewLabel = new Label(group, SWT.NONE);
		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setBounds(138, 71, 155, 17);
		
		Label lblNewLabel_1 = new Label(group, SWT.NONE);
		lblNewLabel_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblNewLabel_1.setAlignment(SWT.CENTER);
		lblNewLabel_1.setBounds(138, 123, 155, 17);
		
		Label lblNewLabel_2 = new Label(group, SWT.NONE);
		lblNewLabel_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblNewLabel_2.setBounds(138, 183, 229, 17);
		
		Label lblNewLabel_3 = new Label(group, SWT.WRAP);
		lblNewLabel_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblNewLabel_3.setBounds(324, 82, 80, 58);
		
		Label label = new Label(shell, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("华文行楷", 20, SWT.NORMAL));
		label.setBounds(230, 31, 108, 35);
		label.setText("注册账号");
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			//注册账号
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblNewLabel_3.setText("");
				String name=text.getText();
				String pwd=text1.getText();
				String rpwd=text2.getText();
				if(name==null||name.equals("")){
					lblNewLabel.setText("用户名不能为空！");
					return;
				}
				lblNewLabel.setText("");
				if(pwd==null||pwd.equals("")){
					lblNewLabel_1.setText("密码不能为空！");
					return;
				}
				lblNewLabel_1.setText("");
				if(rpwd==null||rpwd.equals("")){
					lblNewLabel_2.setText("密码的确认不能为空！");
					return;
				}
				lblNewLabel_2.setText("");
			
				if(!pwd.equals(rpwd)){
					lblNewLabel_2.setText("密码与确认密码不一致！");
					return;
					}
					lblNewLabel_2.setText("");
					
					DBHelper db=new DBHelper();
					String sql="select count(*) from MeituUser where userName=?";
					List<String> params=new ArrayList<String>();
					params.add(name);
					double r=db.doSelectFunction(sql, params);
					if(r<=0){
						sql="insert into MeituUser values(seq_MeituUser_userId.nextval,?,?,?,?,sysdate)";
						List<String> list=new ArrayList<String>();
						try {
							list.add(name);
							list.add(MD5.MD5Encode(pwd));
							list.add("0");
							list.add("0");
							
							
						} catch (Exception e1) {
							MessageDialog.openError(shell, "异常错误", e1.getMessage());
						}
						int value=db.doUpdate(sql, list);
						
						if(value>0){
							MessageDialog.openInformation(shell, "注册", "注册成功！");
							RegisterUi.this.shell.dispose();
							LoginUi window = new LoginUi();
							window.open();
						}else{
							MessageDialog.openError(shell, "错误", "输入异常，请重新注册！");
							return;
						}	
					}else{
						lblNewLabel_2.setText("您输入的用户名已被注册！");
					}
				}
			
			
		});
		//检查注册用户名是否重复
				/*button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						DBHelper db=new DBHelper();
						String name=text.getText();
						if(name==null || name.equals("")){
							lblNewLabel_3.setText("未输入用户名无法检测！");
							return ;
						}
						lblNewLabel_3.setText("");
						String sql="select count(*) from MeituUser where userName=?";
						List<String> params=new ArrayList<String>();
						params.add(name);
						double r=db.doSelectFunction(sql, params);
						if(r>0){
							lblNewLabel_3.setText("该用户名已被注册！请重新输入");
							
						}else{
							
							lblNewLabel_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
							lblNewLabel_3.setText("此用户名未被注册，可以使用!");
						}
						
					}
				});*/
		btnNewButton.setBounds(136, 307, 80, 27);
		btnNewButton.setText("提交注册");
		
		Button button_1 = new Button(shell, SWT.NONE);
		//取消按钮
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				RegisterUi.this.shell.dispose();
				LoginUi window = new LoginUi();
				window.open();
			}
		});
		button_1.setBounds(317, 307, 80, 27);
		button_1.setText("取消");
		
	}
}
