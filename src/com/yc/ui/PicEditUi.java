package com.yc.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.List;
import org.eclipse.jface.viewers.ListViewer;

public class PicEditUi extends Composite {
	private Text text;
	private Text text_1;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public PicEditUi(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm = new SashForm(this, SWT.VERTICAL);
		
		Composite composite = new Composite(sashForm, SWT.NONE);
		
		Group group = new Group(sashForm, SWT.NONE);
		group.setFont(SWTResourceManager.getFont("微软雅黑", 11, SWT.NORMAL));
		group.setText("保存与分享");
		
		Label label = new Label(group, SWT.NONE);
		label.setBounds(33, 29, 219, 24);
		label.setText("请输入基本信息，然后点击上传即可：");
		
		Label label_1 = new Label(group, SWT.NONE);
		label_1.setBounds(18, 67, 61, 17);
		label_1.setText("图片名：");
		
		text = new Text(group, SWT.BORDER);
		text.setBounds(18, 90, 125, 23);
		
		Label label_2 = new Label(group, SWT.NONE);
		label_2.setBounds(673, 67, 73, 17);
		label_2.setText("图片分辨率：");
		
		Label lblx = new Label(group, SWT.NONE);
		lblx.setBounds(752, 67, 61, 17);
		lblx.setText("900x800");
		
		Label label_3 = new Label(group, SWT.NONE);
		label_3.setBounds(417, 67, 54, 17);
		label_3.setText("图片大小：");
		
		Label lblm = new Label(group, SWT.NONE);
		lblm.setBounds(484, 67, 61, 17);
		lblm.setText("2.35M");
		
		Label label_4 = new Label(group, SWT.NONE);
		label_4.setBounds(179, 67, 125, 17);
		label_4.setText("备注信息（可不填）：");
		
		text_1 = new Text(group, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		text_1.setBounds(179, 88, 182, 88);
		
		Label label_5 = new Label(group, SWT.NONE);
		label_5.setBounds(551, 67, 61, 17);
		label_5.setText("图片格式：");
		
		Label lblJpg = new Label(group, SWT.NONE);
		lblJpg.setBounds(618, 67, 34, 17);
		lblJpg.setText("JPG");
		
		Button button = new Button(group, SWT.RADIO);
		button.setBounds(417, 142, 42, 17);
		button.setText("是");
		
		Button button_1 = new Button(group, SWT.RADIO);
		button_1.setBounds(474, 142, 42, 17);
		button_1.setText("否");
		
		Label label_6 = new Label(group, SWT.NONE);
		label_6.setBounds(417, 119, 128, 17);
		label_6.setText("请选择是否共享图片");
		
		Button button_2 = new Button(group, SWT.NONE);
		button_2.setBounds(599, 137, 80, 27);
		button_2.setText("上传");
		sashForm.setWeights(new int[] {330, 150});

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
