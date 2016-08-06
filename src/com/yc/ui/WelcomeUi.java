package com.yc.ui;

import java.io.InputStream;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.wb.swt.SWTResourceManager;

import com.yc.utils.Common;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;


public class WelcomeUi extends Composite {
	private EditorUi eu;
	private StackLayout sl;
	private RecordUi ru;
	private Composite composite;
	private ImageData imageData,imageData1,imageData2,imageData3;
	private Image image,image1,image2,image3;
	private Rectangle rectangle;
	private Button button_2;
	private Button button;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public WelcomeUi(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		composite = new Composite(this, SWT.NONE);
		
		button = new Button(composite, SWT.NONE |SWT.DOUBLE_BUFFERED);
		button.setText("新的旅程");
		button.setBounds(170, 290, 250, 150);

		Button button_1 = new Button(composite, SWT.NONE |SWT.DOUBLE_BUFFERED);
		button_1.setText("历史足迹");
		button_1.setBounds(920, 290, 250, 150);
		
		button_2 = new Button(composite, SWT.NONE |SWT.DOUBLE_BUFFERED);
		button_2.setBounds(540, 290, 250, 150);
		button_2.setText("图库");
		
		//背景图
		InputStream is=WelcomeUi.class.getClassLoader().getResourceAsStream("meitu_background.jpg");
		imageData=new ImageData(is);
		image=new Image(null,imageData);
		composite.setBackgroundImage(image);
		
		//美化图片 背景图
		InputStream is1=WelcomeUi.class.getClassLoader().getResourceAsStream("button_background2.jpg");
		imageData2=new ImageData(is1);
		image2=new Image(null,imageData2);
		button.redraw();
		
		//重新将图片画到button
		button.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent arg0) {
				rectangle=image2.getBounds();
	            int picwidth=rectangle.width;        //图片宽
	            int picheight=rectangle.height;      //图片高
	            int buttonheight=button.getBounds().height;                 //button的高(可以为固定)
	            int buttonwidth=button.getBounds().width;                  //button的宽（乐意为固定值）
	            arg0.gc.drawImage(image2,0,0,picwidth,picheight,0,0,buttonwidth,buttonheight);
	       }
		});
		
		//历史足迹 背景图
				InputStream is2=WelcomeUi.class.getClassLoader().getResourceAsStream("button_background1.jpg");
				imageData1=new ImageData(is2);
				image1=new Image(null,imageData1);
				button_1.redraw();
				
		//重新将图片画到button
		button_1.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent arg0) {
				rectangle=image1.getBounds();
	            int picwidth=rectangle.width;        //图片宽
	            int picheight=rectangle.height;      //图片高
	            int button_1height=button.getBounds().height;                 //button的高(可以为固定)
	            int button_1width=button.getBounds().width;                  //button的宽（乐意为固定值）
	            arg0.gc.drawImage(image1,0,0,picwidth,picheight,0,0,button_1width,button_1height);
	       }
		});

				
		//图库 背景图
		button_2.setBounds(540, 290, 250, 150);
		button_2.setText("图库");
		InputStream is3=WelcomeUi.class.getClassLoader().getResourceAsStream("button_background3.jpg");
		imageData3=new ImageData(is3);
		image3=new Image(null,imageData3);
		button_2.redraw();
				
		//重新将图片画到button
		button_2.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent arg0) {
				rectangle=image3.getBounds();
	            int picwidth=rectangle.width;        //图片宽
	            int picheight=rectangle.height;      //图片高
	            int button_2height=button.getBounds().height;                 //button的高(可以为固定)
	            int button_2width=button.getBounds().width;                  //button的宽（乐意为固定值）
	            arg0.gc.drawImage(image3,0,0,picwidth,picheight,0,0,button_2width,button_2height);
	       }
		});
		
		//设置堆栈式布局
		sl=new StackLayout();
		ru=new RecordUi(composite,SWT.None);
		eu=new EditorUi(composite,SWT.None);
		Common.plu=new PicLibraryUi(composite,SWT.None);
		
		//切换到  美化图片
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
	
				composite.setLayout(sl);
				sl.topControl=eu;
				composite.layout();
				
			}
		});
		
		//切换到 图库
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				composite.setLayout(sl);
				sl.topControl=Common.plu;
				composite.layout();
			}
		});
		
		
		//切换到 历史足迹
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				composite.setLayout(sl);
				sl.topControl=ru;
				composite.layout();
			}
		});
		
	}

	@Override
	protected void checkSubclass() {}
}
