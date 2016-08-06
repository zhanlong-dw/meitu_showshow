package com.yc.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class RecordUi extends Composite {
	private StackLayout sl;
	private UplaodRecordUi uu;
	private	DownloadRecordUi du;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public RecordUi(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm = new SashForm(this, SWT.NONE);
		
		Tree tree = new Tree(sashForm, SWT.BORDER);
		
		
		TreeItem trtmNewTreeitem = new TreeItem(tree, SWT.NONE);
		trtmNewTreeitem.setText("上传记录");
		
		TreeItem trtmNewTreeitem_1 = new TreeItem(tree, SWT.NONE);
		trtmNewTreeitem_1.setText("下载记录");
		
		Composite composite = new Composite(sashForm, SWT.NONE);
		sl=new StackLayout();
		uu=new UplaodRecordUi(composite,SWT.None);
		du=new DownloadRecordUi(composite,SWT.None);
		composite.setLayout(sl);
		sl.topControl=uu;
		sashForm.setWeights(new int[] {89, 358});
		composite.layout();
		//切换
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				TreeItem[] tis=tree.getSelection();  //取出树中选定的节点，可以有多个，但我们只要一个
				if( tis==null||tis.length<=0){
					return;
				}
				
				//取出第一个节点
				TreeItem ti=tis[0];
				if("上传记录".equals(ti.getText())){
					sl.topControl=uu;
				}else if("下载记录".equals(ti.getText())){
					sl.topControl=du;
				}
				composite.layout();
			}
			
		});

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
