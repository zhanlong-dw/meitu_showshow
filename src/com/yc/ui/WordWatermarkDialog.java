package com.yc.ui;

import java.awt.Color;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
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

public class WordWatermarkDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Display display;
	private Text text;
	private InputStream is=null;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public WordWatermarkDialog(Shell parent, int style) {
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

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.MIN |SWT.APPLICATION_MODAL);
		shell.setImage(SWTResourceManager.getImage(WordWatermarkDialog.class, "/com/yc/ui/1.jpg"));
		shell.setSize(436, 321);
		shell.setText("设置文字水印");
		
		Label label_font = new Label(shell, SWT.NONE);
		label_font.setBounds(38, 80, 61, 17);
		label_font.setText("字体名称：");
		
		Label label_style = new Label(shell, SWT.NONE);
		label_style.setBounds(232, 77, 61, 17);
		label_style.setText("字体样式：");
		
		Label label_size = new Label(shell, SWT.NONE);
		label_size.setBounds(38, 120, 68, 17);
		label_size.setText("字体大小：");
		
		Label label_color = new Label(shell, SWT.NONE);
		label_color.setBounds(232, 120, 68, 17);
		label_color.setText("字体颜色：");
		
		Label label_word = new Label(shell, SWT.NONE);
		label_word.setBounds(38, 38, 61, 17);
		label_word.setText("水印文字：");
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(115, 35, 278, 23);
		
		Button button_confirm = new Button(shell, SWT.NONE);
		
		button_confirm.setBounds(313, 256, 80, 27);
		button_confirm.setText("确定");
		
		Combo combo = new Combo(shell, SWT.NONE);
		combo.setItems(new String[] {"宋体", "黑体", "楷体", "微软雅黑", "仿宋"});
		combo.setBounds(115, 77, 93, 25);
		combo.setText("黑体");
		
		Combo combo_1 = new Combo(shell, SWT.NONE);
		combo_1.setItems(new String[] {"粗体", "斜体"});
		combo_1.setBounds(300, 74, 93, 25);
		combo_1.setText("粗体");
		
		Combo combo_2 = new Combo(shell, SWT.NONE);
		combo_2.setItems(new String[] {"1", "3", "5", "8", "10", "12", "16", "18", "20", "24", "30", "36", "48", "56", "66", "72"});
		combo_2.setBounds(115, 117, 93, 25);
		combo_2.setText("24");
		
		Combo combo_3 = new Combo(shell, SWT.NONE);
		combo_3.setItems(new String[] {"红色", "绿色", "蓝色"});
		combo_3.setBounds(300, 117, 93, 25);
		combo_3.setText("红色");
		
		Button button_cancle = new Button(shell, SWT.NONE);
		
		button_cancle.setBounds(182, 256, 80, 27);
		button_cancle.setText("取消");
		
		Label label_X = new Label(shell, SWT.NONE);
		label_X.setBounds(31, 161, 68, 17);
		label_X.setText("X轴偏移值：");
		
		Combo combo_4 = new Combo(shell, SWT.NONE);
		combo_4.setItems(new String[] {"10", "20", "30", "40", "50", "80", "100", "160", "320", "640"});
		combo_4.setBounds(115, 158, 93, 25);
		combo_4.setText("50");
		
		Label label_Y = new Label(shell, SWT.NONE);
		label_Y.setText("Y轴偏移值：");
		label_Y.setBounds(225, 161, 68, 17);
		
		Combo combo_5 = new Combo(shell, SWT.NONE);
		combo_5.setItems(new String[] {"10", "20", "30", "40", "50", "80", "100", "160", "320", "640"});
		combo_5.setBounds(300, 158, 93, 25);
		combo_5.setText("50");
		
		Label label_alpha = new Label(shell, SWT.NONE);
		label_alpha.setBounds(46, 204, 53, 17);
		label_alpha.setText("透明度：");
		
		Combo combo_6 = new Combo(shell, SWT.NONE);
		combo_6.setItems(new String[] {"0", "0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1.0"});
		combo_6.setBounds(115, 201, 93, 25);
		combo_6.setText("0.8");
		
		//取消按钮
		button_cancle.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				WordWatermarkDialog.this.shell.dispose();
			}
		});
		
		//确认按钮
		button_confirm.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(text.getText().trim()==null || "".equals(text.getText().trim())
						||combo.getText().trim()==null || "".equals(combo.getText().trim()) 
							||combo_1.getText().trim()==null || "".equals(combo_1.getText().trim())
								|| combo_2.getText().trim()==null || "".equals(combo_2.getText().trim())
									|| combo_3.getText().trim()==null || "".equals(combo_3.getText().trim())
										||combo_4.getText().trim()==null || "".equals(combo_4.getText().trim())
											||combo_5.getText().trim()==null || "".equals(combo_5.getText().trim())
												||combo_6.getText().trim()==null || "".equals(combo_6.getText().trim())){
					MessageDialog.openError(shell, "错误", "输入框不能为空或输入空值，请确认后重新输入！");
					return;
				}
				
				String word=text.getText().trim();
				String wordName=getFontName(combo.getText().trim());
				int wordStyle=getFonStyle(combo_1.getText().trim());
				int wordSize=Integer.parseInt(combo_2.getText().trim());
				Color wordColor=getFontColor(combo_3.getText().trim());
				int word_X=Integer.parseInt(combo_4.getText().trim());
				int word_Y=Integer.parseInt(combo_5.getText().trim());
				float word_Alpha=Float.parseFloat(combo_6.getText().trim());
				
				is=MeituUtils.waterMarkWord(EditorUi.filePath,word, wordName, wordStyle, wordSize, wordColor, word_X, word_Y,word_Alpha);
				Common.image=new Image(display,is);
				WordWatermarkDialog.this.shell.dispose();
			}
		});
		
		
		combo.addVerifyListener(new VerifyListener() {   
			 public void verifyText(VerifyEvent e) {   
			      Pattern pattern = Pattern.compile("[^0-9]*");   
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
			      Pattern pattern = Pattern.compile("[^0-9]*");   
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
		
		combo_3.addVerifyListener(new VerifyListener() {   
			 public void verifyText(VerifyEvent e) {   
			      Pattern pattern = Pattern.compile("[^0-9]*");   
			      Matcher matcher = pattern.matcher(e.text);   
			      if (matcher.matches()) // 处理数字   
			    	  e.doit = true;   
			      else if (e.text.length() > 0) // 有字符情况,包含中文、空格   
			    	  e.doit = false;   
			      else  
			    	  e.doit = false;   
			  }   
		  });
		
		
		
		combo_4.addVerifyListener(new VerifyListener() {   
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
		
		combo_5.addVerifyListener(new VerifyListener() {   
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
		
		combo_6.addVerifyListener(new VerifyListener() {   
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

	}
	
	//获取字体颜色
	private Color getFontColor(String wordColor){
		Color color=null;
		if(wordColor.equals("红色")){
			color= new Color(255,0,0,255);
		}else if(wordColor.equals("绿色")){
			color=new Color(0,255,0,255);
		}else if(wordColor.equals("蓝色")){
			color=new Color(0,0,255,255);
		}else{
			MessageDialog.openError(shell, "错误", "字体颜色错误,请输入正确的值【红色、绿色、蓝色】，请确认后重新输入！");
		}
		return color;
	}
	
	//获取字体样式
	private int getFonStyle(String wordStyle){
		int fontStyle=1;
		if(wordStyle.equals("粗体")){
			fontStyle=1;
		}else if(wordStyle.equals("斜体")){
			fontStyle=2;
		}else{
			MessageDialog.openError(shell, "错误", "字体样式错误,请输入正确的值【粗体、斜体】，请确认后重新输入！");
		}
		return fontStyle;
	}
	
	//获取字体名称
	private String getFontName(String wordName){
		String fontName=null;
		if(wordName.equals("宋体")){
			fontName=wordName;
		}else if(wordName.equals("黑体")){
			fontName=wordName;
		}else if(wordName.equals("楷体")){
			fontName=wordName;
		}else if(wordName.equals("微软雅黑")){
			fontName=wordName;
		}else if(wordName.equals("仿宋")){
			fontName=wordName;
		}else{
			MessageDialog.openError(shell, "错误", "字体名称错误,请输入正确的值【宋体、黑体、楷体、仿宋、微软雅黑】，请确认后重新输入！");
		}
		return fontName;
	}
}
