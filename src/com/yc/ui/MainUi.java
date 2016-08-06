package com.yc.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;

import com.yc.utils.Common;
import com.yc.utils.UiUtils;

import org.eclipse.wb.swt.SWTResourceManager;

public class MainUi {
	private StackLayout sl;
	protected Shell shell;
	private Display display;
	private WelcomeUi wu;
	private RecordUi ru;
	private DownloadRecordUi du;
	private UplaodRecordUi uu;
	private MainAlterPwdUi mp;
	private EditorUi eu;

	
	/**
	 * Launch the application.
	 * @param args
	 */
	
	public static void main(String[] args) {
		try {
			MainUi window = new MainUi();
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
		shell = new Shell();
		shell.setImage(SWTResourceManager.getImage(MainUi.class, "/com/yc/ui/1.jpg"));
		shell.setSize(727, 491);
		shell.setText("美图秀秀");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		UiUtils.showFullScreen(shell, display);
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem menuItem_4 = new MenuItem(menu, SWT.NONE);
		menuItem_4.setImage(SWTResourceManager.getImage(MainUi.class, "/com/sun/javafx/scene/control/skin/caspian/fxvk-backspace-button.png"));
		
		MenuItem item_User = new MenuItem(menu, SWT.CASCADE);
		item_User.setText("\u7528\u6237");
		
		Menu menu_1 = new Menu(item_User);
		item_User.setMenu(menu_1);
		
		MenuItem mntmNewItem = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				UserInfo window=new UserInfo(shell, SWT.ALL);
				window.open();
			}
		});
		mntmNewItem.setText("个人信息");
		
		MenuItem item_AlterPwd = new MenuItem(menu_1, SWT.NONE);
		
		item_AlterPwd.setText("\u4FEE\u6539\u5BC6\u7801");
		
		new MenuItem(menu_1, SWT.SEPARATOR);
		
		MenuItem item_Exit = new MenuItem(menu_1, SWT.NONE);
		item_Exit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.exit(0);
			}
		});
		item_Exit.setText("\u9000\u51FA");
		
		MenuItem item_Help = new MenuItem(menu, SWT.CASCADE);
		item_Help.setText("\u5E2E\u52A9");
		
		Menu menu_2 = new Menu(item_Help);
		item_Help.setMenu(menu_2);
		
		MenuItem mntmNewItem_3 = new MenuItem(menu_2, SWT.NONE);
		mntmNewItem_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				HelpAboutUs window=new HelpAboutUs(shell,SWT.NONE);
				window.open();
			}
		});
		mntmNewItem_3.setText("\u5173\u4E8E\u6211\u4EEC...");
		
		SashForm sashForm = new SashForm(shell, SWT.NONE);
		
		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm_1 = new SashForm(composite_1, SWT.VERTICAL);
		
		Composite composite_2 = new Composite(sashForm_1, SWT.NONE);
		composite_2.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm_2 = new SashForm(composite_2, SWT.VERTICAL);
		
		Composite composite_4 = new Composite(sashForm_2, SWT.NONE);
		
		//设置堆栈式布局
		sl=new StackLayout();
		wu=new WelcomeUi(composite_4,SWT.None);
		composite_4.setLayout(sl);
		sl.topControl=wu;
		composite_4.layout();
		sashForm_2.setWeights(new int[] {344});
		sashForm_1.setWeights(new int[] {402});
		sashForm.setWeights(new int[] {561});
		
		//返回
				menuItem_4.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						wu.dispose();
						wu=new WelcomeUi(composite_4,SWT.None);
						sl.topControl=wu;
						composite_4.layout();
					}
				});
				
		//修改密码
		item_AlterPwd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				mp=new MainAlterPwdUi();
				mp.open();
			}
		});
	}
}
