package com.yc.ui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class HelpAboutUs extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text text;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public HelpAboutUs(Shell parent, int style) {
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
		shell = new Shell(getParent(), SWT.SHELL_TRIM | SWT.BORDER);
		shell.setImage(SWTResourceManager.getImage(HelpAboutUs.class, "/com/yc/ui/1.jpg"));
		shell.setSize(561, 303);
		shell.setText("帮助");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text = new Text(shell, SWT.READ_ONLY | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		
		InputStream is=HelpAboutUs.class.getClassLoader().getResourceAsStream("help");
		Scanner sc=null;
		sc = new Scanner(is,"UTF-8");
		
		while(sc.hasNextLine()){
	     text.setText(text.getText()+"\r\n"+sc.nextLine());//TODO：转行
		}
		
		
		text.setEditable(false);

	}

}
