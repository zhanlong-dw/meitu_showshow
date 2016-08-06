package com.yc.ui;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;




import com.yc.utils.Common;
import com.yc.utils.MeituUtils;

import org.eclipse.wb.swt.SWTResourceManager;

public class PicWatermarkDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Display display;
	private Text text;
	private Text text_1;
	private Text text_2;
	private String waterMarkfilePath="";
	private InputStream is=null;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public PicWatermarkDialog(Shell parent, int style) {
		super(parent, style);
		display=parent.getDisplay();
		shell= parent.getShell();
	}

	
	
	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}
	
	public Object open(String str) {
		createContents();
		shell.open();
		shell.layout();
		display = getParent().getDisplay();
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
		shell = new Shell(getParent(), SWT.MIN |SWT.APPLICATION_MODAL);
		shell.setImage(SWTResourceManager.getImage(PicWatermarkDialog.class, "/com/yc/ui/1.jpg"));
		shell.setSize(466, 269);
		shell.setText("设置水印图片");
		
		Label label_X = new Label(shell, SWT.NONE);
		label_X.setBounds(47, 103, 73, 17);
		label_X.setText("X轴偏移值：");
		
		Label label_Y = new Label(shell, SWT.NONE);
		label_Y.setBounds(259, 103, 73, 17);
		label_Y.setText("Y轴偏移值：");
		
		Label label_alpha = new Label(shell, SWT.NONE);
		label_alpha.setBounds(47, 143, 73, 17);
		label_alpha.setText("图片透明度：");
		
		Button button_confirm = new Button(shell, SWT.NONE);
		
		button_confirm.setBounds(308, 186, 80, 27);
		button_confirm.setText("确认");
		
		Button button_cancel = new Button(shell, SWT.NONE);
		
		button_cancel.setBounds(166, 186, 80, 27);
		button_cancel.setText("取消");
		
		Combo combo = new Combo(shell, SWT.NONE);
		combo.setItems(new String[] {"10", "20", "30", "40", "50", "60", "70", "80", "90", "100", "150", "200", "250", "300", "350", "400"});
		combo.setBounds(137, 100, 88, 25);
		combo.setText("50");
		
		Combo combo_1 = new Combo(shell, SWT.NONE);
		combo_1.setItems(new String[] {"10", "20", "30", "40", "50", "60", "70", "80", "90", "100", "150", "200", "250", "300", "350", "400"});
		combo_1.setBounds(329, 100, 88, 25);
		combo_1.setText("50");
		
		Combo combo_2 = new Combo(shell, SWT.NONE);
		combo_2.setItems(new String[] {"0", "0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1.0"});
		combo_2.setBounds(137, 140, 88, 25);
		combo_2.setText("0.8");
		
		Label label = new Label(shell, SWT.NONE);
		label.setBounds(47, 26, 61, 17);
		label.setText("图片位置：");
		
		text = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		text.setBounds(137, 23, 183, 23);
		
		Button button = new Button(shell, SWT.NONE);
		
		button.setBounds(337, 23, 80, 27);
		button.setText("浏览");
		
		Label label_width = new Label(shell, SWT.NONE);
		label_width.setBounds(47, 62, 61, 17);
		label_width.setText("图片宽度：");
		
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(137, 61, 88, 23);
		
		Label label_height = new Label(shell, SWT.NONE);
		label_height.setBounds(259, 62, 61, 17);
		label_height.setText("图片高度：");
		
		text_2 = new Text(shell, SWT.BORDER);
		text_2.setBounds(329, 59, 88, 23);
		
		//浏览按钮
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onFileOpen();
				text.setText(waterMarkfilePath);
				if(text.getText()==null || "".equals(text.getText())){
					return;
				}
			}
		});
		
		//确定按钮
		button_confirm.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(text.getText().trim()==null || "".equals(text.getText().trim())
						||text_1.getText().trim()==null || "".equals(text_1.getText().trim()) 
							||text_2.getText().trim()==null || "".equals(text_2.getText().trim())
								|| combo.getText().trim()==null || "".equals(combo.getText().trim())
									|| combo_1.getText().trim()==null || "".equals(combo_1.getText().trim())
										||combo_2.getText().trim()==null || "".equals(combo_2.getText().trim())){
					MessageDialog.openError(shell, "错误", "输入框不能为空或输入空值，请确认后重新输入！\n图片宽度   【1-400】\n图片高度   【1-400】\nX轴偏移值 【1-1000】\nY轴偏移值 【1-1000】\n图片透明度【0-1】");
					return;
				}
				
				int Picwidth=Integer.parseInt(text_1.getText().trim());
				int Picheight=Integer.parseInt(text_2.getText().trim());
				int Pic_x=Integer.parseInt(combo.getText().trim());
				int Pic_y=Integer.parseInt(combo_1.getText().trim());
				float Pic_alpha=Float.parseFloat(combo_2.getText().trim());
				
				if(Picwidth>400
						|| Picheight>400
							||Pic_x>1000
								||Pic_y>1000
									||Pic_alpha<0 || Pic_alpha>1){
					MessageDialog.openError(shell, "错误", "您输入的值太大或为负数，请确认后重新输入！\n图片宽度   【1-400】\n图片高度   【1-400】\nX轴偏移值 【1-1000】\nY轴偏移值 【1-1000】\n图片透明度【0-1】");
					return;
				} 
				
				is=MeituUtils.waterMarkImage(EditorUi.filePath,waterMarkfilePath,Picwidth,Picheight,Pic_x,Pic_y,Pic_alpha);
				Common.image=new Image(display,is);
				PicWatermarkDialog.this.shell.dispose();
			}
		});

	  text_1.addVerifyListener(new VerifyListener() {   
		 public void verifyText(VerifyEvent e) {   
		      Pattern pattern = Pattern.compile("[0-9]\\d*");   
		      Matcher matcher = pattern.matcher(e.text);   
		      if (matcher.matches()) // 处理数字   
		    	  e.doit = true;   
		      else if (e.text.length() > 0) // 有字符情况,包含中文、空格   
		    	  e.doit = false;   
		      else  
		    	  e.doit = false;   
		  }   
	  });
	  
	  text_2.addVerifyListener(new VerifyListener() {   
			 public void verifyText(VerifyEvent e) {   
			      Pattern pattern = Pattern.compile("[0-9]\\d*");   
			      Matcher matcher = pattern.matcher(e.text);   
			      if (matcher.matches()) // 处理数字   
			    	  e.doit = true;   
			      else if (e.text.length() > 0) // 有字符情况,包含中文、空格   
			    	  e.doit = false;   
			      else  
			    	  e.doit = false;   
			  }   
		  });
	  
	  combo.addVerifyListener(new VerifyListener() {   
			 public void verifyText(VerifyEvent e) {   
			      Pattern pattern = Pattern.compile("[0-9]\\d*");   
			      Matcher matcher = pattern.matcher(e.text);   
			      if (matcher.matches()) // 处理数字   
			    	  e.doit = true;   
			      else if (e.text.length() > 0) // 有字符情况,包含中文、空格   
			    	  e.doit = false;   
			      else  
			    	  e.doit = false;   
			  }   
		  });
	  
	  combo_1.addVerifyListener(new VerifyListener() {   
			 public void verifyText(VerifyEvent e) {   
			      Pattern pattern = Pattern.compile("[0-9]\\d*");   
			      Matcher matcher = pattern.matcher(e.text);   
			      if (matcher.matches()) // 处理数字   
			    	  e.doit = true;   
			      else if (e.text.length() > 0) // 有字符情况,包含中文、空格   
			    	  e.doit = false;   
			      else  
			    	  e.doit = false;   
			  }   
		  });
	  
	  combo_2.addVerifyListener(new VerifyListener() {   
			 public void verifyText(VerifyEvent e) {   
			      Pattern pattern = Pattern.compile(".[0-9]*");   
			      Matcher matcher = pattern.matcher(e.text);   
			      if (matcher.matches()) // 处理数字   
			    	  e.doit = true;   
			      else if (e.text.length() > 0) // 有字符情况,包含中文、空格   
			    	  e.doit = false;   
			      else  
			    	  e.doit = false;   
			  }   
		  });
	  
		//退出
		button_cancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PicWatermarkDialog.this.shell.dispose();
			}
		});
	}
	
	/**
     * 打开图片选择对话框
     */
    private void onFileOpen() {
        FileDialog fd = new FileDialog(shell, SWT.OPEN);
        fd.setText("选择水印图片");
        fd.setFilterPath("");
        fd.setFilterExtensions(new String[] {"*.png","*.jpg", "*.gif", "*.ico" });
        fd.setFilterNames(new String[] { "png","*.jpg", "*.gif", "*.ico"});
        waterMarkfilePath = fd.open();
        if(waterMarkfilePath==null || "".equals(waterMarkfilePath)){
        	return;
        }
        int index=waterMarkfilePath.lastIndexOf(".");
        String fileType=waterMarkfilePath.substring(index,index+4);
    }
}
