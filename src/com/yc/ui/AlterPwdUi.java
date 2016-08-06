package com.yc.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.yc.dao.DBHelper;
import com.yc.utils.MD5;
import com.yc.utils.UiUtils;

public class AlterPwdUi {

	protected Shell shell;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Display display;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AlterPwdUi window = new AlterPwdUi();
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
		shell.setImage(SWTResourceManager.getImage(AlterPwdUi.class, "/com/yc/ui/1.jpg"));
		shell.setSize(416, 332);
		shell.setText("   \u4FEE\u6539\u5BC6\u7801");
		
		UiUtils.showInCenter(shell, display);    //使窗口居中显示
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setFont(SWTResourceManager.getFont("楷体", 25, SWT.BOLD));
		lblNewLabel.setBounds(92, 31, 215, 44);
		lblNewLabel.setText("密码修改");
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setAlignment(SWT.CENTER);
		lblNewLabel_1.setBounds(67, 100, 61, 17);
		lblNewLabel_1.setText("\u7528\u6237\u540D \uFF1A");
		
		Label label = new Label(shell, SWT.NONE);
		label.setAlignment(SWT.CENTER);
		label.setBounds(67, 147, 61, 17);
		label.setText("\u539F\u5BC6\u7801\uFF1A");
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setAlignment(SWT.CENTER);
		label_1.setBounds(67, 193, 61, 17);
		label_1.setText("\u65B0\u5BC6\u7801\uFF1A");
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(155, 97, 192, 23);
		
		text_1 = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		text_1.setBounds(155, 144, 192, 23);
		
		text_2 = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		text_2.setBounds(155, 190, 192, 23);
		
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.setBounds(165, 244, 80, 27);
		btnNewButton.setText("\u786E   \u8BA4");
		
		Button btnNewButton_1 = new Button(shell, SWT.NONE);
		btnNewButton_1.setBounds(267, 244, 80, 27);
		btnNewButton_1.setText("取消");
		
		Label label_2 = new Label(shell, SWT.CENTER);
		label_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label_2.setBounds(155, 126, 192, 17);
		
		Label label_3 = new Label(shell, SWT.CENTER);
		label_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label_3.setBounds(155, 173, 192, 17);
		
		Label label_4 = new Label(shell, SWT.CENTER);
		label_4.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label_4.setBounds(155, 219, 192, 17);
		
		//修改密码
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//1.取出用户名  密码  是否记住
				String userName=text.getText().trim();
				String userPwd= text_1.getText().trim();
				String newPwd=text_2.getText().trim();
				
				if(userName==null||"".equals(userName)){
					label_2.setText("用户名不能为空！");
					return;
				}
				label_2.setText("");
				if(userPwd==null||"".equals(userPwd)){
					label_3.setText("原密码不能为空！");
					return;
				}
				label_3.setText("");
				if(newPwd==null||"".equals(newPwd)){
					label_4.setText("请设置你的新密码！");
					return;
				}
				label_4.setText("");
				
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
				if(r>0){
					String sql_1="update MeituUser set userPwd=? where userName=?";
					List<String> list=new ArrayList<String>();
					try {
						list.add(MD5.MD5Encode(newPwd));
					} catch (Exception e1) {
						
						MessageDialog.openError(shell,"加密错误","请联系管理员。。");
						return;
					}
					list.add(userName);
					
					int r1=db.doUpdate(sql_1, list);
					if(r1>0){
						MessageDialog.openInformation(shell,"提示","已成功修改密码！请返回重新登录");
					}else{
						MessageDialog.openError(shell, "错误", "改密失败，请联系管理员！");
						return;
					}
				}else{
					label_4.setText("输入有误，请重新输入！");
					return;
				}
				
				
				AlterPwdUi.this.shell.dispose();
				LoginUi window = new LoginUi();
				window.open();
			}
		});
		//取消
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//跳转页面
				AlterPwdUi.this.shell.dispose();
				LoginUi window = new LoginUi();
				window.open();
				
			}
		});
		

	}
}
