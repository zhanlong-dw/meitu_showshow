package com.yc.ui;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseEvent;

import com.yc.utils.Common;
import com.yc.utils.MeituUtils;

import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.events.MouseWheelListener;

public class EditorUi extends Composite {
	private Display display;
	protected Shell shell;
	
	private Event event;
	
	/**
     * 剪切图片的宽度和高度
     */
    public static final int CROP_WIDTH = 165;
    public static final int CROP_HEIGHT = 170;

    public static Canvas cropCanvas;
    private Canvas viewCanvas;

    // 按比例
    private boolean ratio=true;  //是否按比例来截图
    private boolean movable;    //鼠标是up还是down

    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public static Image sourceImage;
    private Image viewImage;
    private Image alphaImage;
    private Image saveImage;
    private Image srcImage; //特效处理之前的图片
    
    private Group group_7;
    public static Composite srcComposite;
	private Composite viewComposite;
    private Button button;
    private Label lblJpg;
    private Label lblm;
    private Label lblx;
    private Label label_7;
    private ImageData imageDataSrc=null;
    
    private double rate = 1.0;
    private Rectangle cropRect = new Rectangle(0, 0, CROP_WIDTH, CROP_HEIGHT);
    
    private String currentDir = "";  //设置默认文件路径为空
    public static String filePath;   //文件目录
    private InputStream is=null;     //马赛克后图片的流
    
	private boolean r=false;   //判断是否按了“截图”按钮
	private boolean t=false;   //判断是否按了“打开文件”按钮
	
    private int choice;
    private int alpha;
    
    private PaintEvent paintEvent;
    
    
	 /* Create the composite.
	 * @param parent
	 * @param style
	 */
	public  EditorUi(Composite parent, int style) {
		super(parent, style);
		display=parent.getDisplay();
		shell= parent.getShell();
		setBackground(SWTResourceManager.getColor(233, 150, 122));
		setLayout(new FillLayout(SWT.HORIZONTAL));
		SashForm sashForm = new SashForm(this, SWT.NONE);
		
		Composite composite = new Composite(sashForm, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm_2 = new SashForm(composite, SWT.VERTICAL);
		
		Group group_5 = new Group(sashForm_2, SWT.NONE);
		group_5.setText("新建与保存");
		
		Button fileOpenButton = new Button(group_5, SWT.NONE);
		fileOpenButton.setBounds(49, 42, 80, 27);
		fileOpenButton.setText("打开图片");
		
		Button saveButton = new Button(group_5, SWT.NONE);
		saveButton.setBounds(163, 42, 80, 27);
		saveButton.setText("保存到本地");
		
		Group group = new Group(sashForm_2, SWT.NONE);
		group.setText("旋转");
		
		Button button_Left = new Button(group, SWT.NONE);
		
		button_Left.setImage(SWTResourceManager.getImage(EditorUi.class, "/com/sun/javafx/scene/web/skin/Undo_16x16_JFX.png"));
		button_Left.setBounds(51, 35, 80, 27);
		button_Left.setText("向左旋转");
		
		Button button_Right = new Button(group, SWT.NONE);
		
		button_Right.setImage(SWTResourceManager.getImage(EditorUi.class, "/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png"));
		button_Right.setBounds(164, 35, 80, 27);
		button_Right.setText("向右旋转");
		
		Group group_1 = new Group(sashForm_2, SWT.NONE);
		group_1.setText("截图");
		
		button = new Button(group_1, SWT.NONE);		
		
		button.setImage(SWTResourceManager.getImage(EditorUi.class, "/com/sun/javafx/scene/web/skin/Cut_16x16_JFX.png"));
		button.setBounds(49, 40, 80, 27);
		button.setText("截图");
		
		Button button_3 = new Button(group_1, SWT.NONE);
		
		button_3.setImage(SWTResourceManager.getImage(EditorUi.class, "/com/sun/javafx/scene/web/skin/DecreaseIndent_16x16_JFX.png"));
		button_3.setBounds(164, 40, 80, 27);
		button_3.setText("保存截图");
		
		Group group_2 = new Group(sashForm_2, SWT.NONE);
		group_2.setText("特效");
		
		Scale scale = new Scale(group_2, SWT.NONE);
		scale.setMaximum(255);
		scale.setSelection(255);
		scale.setBounds(78, 54, 140, 41);
		
		Label label = new Label(group_2, SWT.NONE);
		label.setBounds(23, 67, 61, 17);
		label.setText("透明度：");
		
		Button button_4 = new Button(group_2, SWT.NONE);
		button_4.setImage(SWTResourceManager.getImage(EditorUi.class, "/com/sun/javafx/scene/control/skin/caspian/pattern-transparent.png"));
		
		button_4.setBounds(46, 21, 81, 27);
		button_4.setText("马赛克");
		
		Button button_5 = new Button(group_2, SWT.NONE);
		
		button_5.setBounds(165, 21, 80, 27);
		button_5.setText("特效恢复");
		
		Group group_3 = new Group(sashForm_2, SWT.NONE);
		group_3.setText("水印");
		
		Button btnNewButton_4 = new Button(group_3, SWT.NONE);
		
		btnNewButton_4.setImage(SWTResourceManager.getImage(EditorUi.class, "/org/eclipse/jface/fieldassist/images/contassist_ovr.gif"));
		btnNewButton_4.setBounds(45, 35, 80, 27);
		btnNewButton_4.setText("文字水印");
		
		Button btnNewButton_5 = new Button(group_3, SWT.NONE);
		
		btnNewButton_5.setImage(SWTResourceManager.getImage(EditorUi.class, "/icons/progress/ani/8.png"));
		btnNewButton_5.setBounds(163, 35, 80, 27);
		btnNewButton_5.setText("图片水印");
		
		Group group_4 = new Group(sashForm_2, SWT.NONE);
		group_4.setText("大小");
		
		Button button_1 = new Button(group_4, SWT.NONE);
		
		button_1.setImage(SWTResourceManager.getImage(EditorUi.class, "/org/eclipse/jface/text/source/projection/images/collapsed.gif"));
		button_1.setBounds(45, 23, 80, 27);
		button_1.setText("放大");
		
		Button button_2 = new Button(group_4, SWT.NONE);
		
		button_2.setImage(SWTResourceManager.getImage(EditorUi.class, "/org/eclipse/jface/text/source/projection/images/expanded.png"));
		button_2.setBounds(164, 23, 80, 27);
		button_2.setText("缩小");
		
		Button button_6 = new Button(group_4, SWT.NONE);
		button_6.setImage(SWTResourceManager.getImage(EditorUi.class, "/com/sun/javafx/webkit/prism/resources/mediaTimeThumb.png"));
		button_6.setBounds(102, 66, 80, 27);
		button_6.setText("恢复");
		sashForm_2.setWeights(new int[] {88, 89, 89, 106, 78, 111});
		
		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm_1 = new SashForm(composite_1, SWT.VERTICAL);
		
		Composite composite_2 = new Composite(sashForm_1, SWT.NONE);
		sashForm_1.setWeights(new int[] {396});
		composite_2.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm_3 = new SashForm(composite_2, SWT.VERTICAL);
		
		Composite composite_3 = new Composite(sashForm_3, SWT.NONE);
		composite_3.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		group_7 = new Group(composite_3, SWT.BORDER);
		group_7.setText("图片显示");
		group_7.setLayout(new GridLayout(2, false));
		
		srcComposite = new Composite(group_7, SWT.BORDER);
		srcComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		GridData gd_srcComposite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_srcComposite.heightHint = 479;
		gd_srcComposite.widthHint = 884;
		srcComposite.setLayoutData(gd_srcComposite);
		srcComposite.setBounds(0, 0, 64, 64);
		
		cropCanvas=(new Canvas(srcComposite, SWT.CENTER |SWT.DOUBLE_BUFFERED));
		cropCanvas.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		
		Composite upViewComposite = new Composite(group_7, SWT.BORDER);
		upViewComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		GridData gd_upViewComposite = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_upViewComposite.heightHint = 170;
		gd_upViewComposite.widthHint = 170;
		upViewComposite.setLayoutData(gd_upViewComposite);
		
		SashForm sashForm_4 = new SashForm(upViewComposite, SWT.VERTICAL);
		
		viewComposite = new Composite(sashForm_4, SWT.BORDER);
		viewComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		viewCanvas = new Canvas(viewComposite, SWT.NONE |SWT.DOUBLE_BUFFERED);
		viewCanvas.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite_4 = new Composite(sashForm_4, SWT.NONE);
		composite_4.setLayout(new FillLayout(SWT.HORIZONTAL));
		sashForm_4.setWeights(new int[] {175, 301});
		
		Group group_6 = new Group(sashForm_3, SWT.NONE);
		group_6.setText("图片基本信息");
		group_6.setLayout(null);
		
		Label label_1 = new Label(group_6, SWT.NONE);
		label_1.setBounds(230, 67, 54, 17);
		label_1.setText("图片名：");
		
		Label label_2 = new Label(group_6, SWT.NONE);
		label_2.setBounds(673, 67, 73, 17);
		label_2.setText("图片分辨率：");
		
		lblx = new Label(group_6, SWT.NONE);
		lblx.setBounds(752, 67, 61, 17);
		
		Label label_3 = new Label(group_6, SWT.NONE);
		label_3.setBounds(417, 67, 54, 17);
		label_3.setText("图片大小：");
		
		lblm = new Label(group_6, SWT.NONE);
		lblm.setBounds(484, 67, 61, 17);
		
		Label label_5 = new Label(group_6, SWT.NONE);
		label_5.setBounds(551, 67, 61, 17);
		label_5.setText("图片格式：");
		
		lblJpg = new Label(group_6, SWT.NONE);
		lblJpg.setBounds(618, 67, 34, 17);
		
		label_7 = new Label(group_6, SWT.NONE);
		label_7.setBounds(290, 67, 104, 17);
		
		//特效恢复
		button_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(Common.srcImage==null){
					return;
				}
				choice=9;
			}
		});
		
		//马赛克
		button_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				choice=6;
				try {
					is=MeituUtils.mosaic(filePath, 6);
					Common.image=new Image(display,is);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		//图片透明度
		scale.addMouseWheelListener(new MouseWheelListener() {
			public void mouseScrolled(MouseEvent e) {
				cropCanvas.notifyListeners(SWT.Paint,  event);
			}
		});
		
		//面板事件
		cropCanvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				alpha=scale.getSelection();
				if( choice==4){   //打开图片
					alphaImage=MeituUtils.TouMing(imageDataSrc, 0,0, imageDataSrc.width, imageDataSrc.height, alpha);
					e.gc.drawImage(alphaImage,0,0, imageDataSrc.width,imageDataSrc.height,0,0,imageDataSrc.width,imageDataSrc.height);
					cropCanvas.redraw();
					paintEvent=e;
					Common.image=alphaImage;
					e.gc.dispose();
				}else if(choice==1){   //旋转
					if(alphaImage==null){
						return;
					}
					int picwidth=alphaImage.getBounds().width;        //图片宽
					int picheight=alphaImage.getBounds().height;      //图片高
					int cropCanvasheight=srcComposite.getBounds().height;                 //cropCanva的高(可以为固定)
					int cropCanvaswidth=srcComposite.getBounds().width;                  //cropCanva的宽（可以为固定值）
					alphaImage=MeituUtils.TouMing(alphaImage, 0,0, alphaImage.getBounds().width, alphaImage.getBounds().height, alpha);
					e.gc.drawImage(alphaImage,0,0,picwidth,picheight,0,0,cropCanvaswidth,cropCanvasheight);
					cropCanvas.redraw();
					cropCanvas.setData(alphaImage);
					Common.image=alphaImage;
					e.gc.dispose();
				}else if(  choice==2){   //截图
					onCroppingPaint(e.gc, alpha);
					Common.image=alphaImage;
				}else if( choice==3){  //图片透明度
					GC newGC =new GC(alphaImage,SWT.CENTER);
					newGC.drawImage(alphaImage,0,0, alphaImage.getBounds().width,alphaImage.getBounds().height,0,0,alphaImage.getBounds().width,alphaImage.getBounds().height);
					saveImage=alphaImage;
					cropCanvas.setData(alphaImage);
					newGC.dispose();
					Common.image=alphaImage;
				}else if(choice==5){    //放大缩小
					onCroppingPaint_1(e.gc, alpha);
					alphaImage=MeituUtils.TouMing(alphaImage, 0,0, alphaImage.getBounds().width, alphaImage.getBounds().height, alpha);
					cropCanvas.redraw();
				}else if(choice==7){  //图片水印
					alphaImage=Common.image;
					e.gc.drawImage(Common.image,0,0,Common.image.getBounds().width,Common.image.getBounds().height,0,0,srcComposite.getBounds().width,srcComposite.getBounds().height);
					cropCanvas.redraw();
					cropCanvas.setData(alphaImage);
					e.gc.dispose();
				}else if(choice==8){  //文字水印
					alphaImage=Common.image;
					e.gc.drawImage(Common.image,0,0,Common.image.getBounds().width,Common.image.getBounds().height,0,0,srcComposite.getBounds().width,srcComposite.getBounds().height);
					cropCanvas.redraw();
					cropCanvas.setData(alphaImage);
					e.gc.dispose();
				}else if(choice==6){  //马赛克 
					alphaImage=Common.image;  
					e.gc.setAlpha(alpha);
					e.gc.drawImage(Common.image,0,0,Common.image.getBounds().width,Common.image.getBounds().height,0,0,srcComposite.getBounds().width,srcComposite.getBounds().height);
					cropCanvas.redraw();
					cropCanvas.setData(alphaImage);
					e.gc.dispose();
				}else if(choice==9){
					Image srcImage=new Image(display,Common.srcImage);
					e.gc.drawImage(srcImage,0,0,Common.srcImage.width,Common.srcImage.height,0,0,srcComposite.getBounds().width,srcComposite.getBounds().height);
					cropCanvas.redraw();
					alphaImage=srcImage;
					MeituUtils.getsourceImage(filePath, srcImage);  //覆盖原图
					cropCanvas.setData(alphaImage);
					e.gc.dispose();
				}
			}
		});
		
		//文字水印
		btnNewButton_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(t){   //判断是否按了“打开图片”按钮
					WordWatermarkDialog wwk=new WordWatermarkDialog(shell,SWT.MIN  | SWT.APPLICATION_MODAL);
					wwk.open();
					if(Common.image!=null){
						choice=8;
					}
				}
				
			}
		});
		
		//图片水印
		btnNewButton_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(t){   //判断是否按了“打开图片”按钮
					PicWatermarkDialog pwk=new PicWatermarkDialog(shell,SWT.MIN  | SWT.APPLICATION_MODAL);
					pwk.open();
					if(Common.image!=null){
						choice=7;
					}
				}
			}
		});
		
		//保存图片对话框事件
		saveButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(e.doit){
					saveImage=(Image)cropCanvas.getData();
					onFileSave(saveImage);
				}
			}
		});
				
				
		//打开选择图片对话框事件
		fileOpenButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				 t=e.doit;
				 onFileOpen();
				 choice=4;
			}
		});
				
		//图片向右旋转事件
		button_Right.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(alphaImage==null){
					return;
				}
				imageDataSrc=MeituUtils.rotateRight(alphaImage.getImageData());
				alphaImage= new Image(display,imageDataSrc);
				cropCanvas.redraw();
				choice=1;
			}
		});
		
		//图片向左旋转事件
		button_Left.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(alphaImage==null){
					return;
				}
				imageDataSrc=MeituUtils.rotateLeft(alphaImage.getImageData());
				alphaImage= new Image(display,imageDataSrc);
				cropCanvas.redraw();
				choice=1;
			}
			
		});
		
		sashForm_3.setWeights(new int[] {361, 131});
		sashForm.setWeights(new int[] {208, 867});
		
		cropCanvas.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				if(r || choice==6){
					onMotion(e);
				}else{
					return;
				}
			}
		});
		
		cropCanvas.addMouseListener(new CropMouseListener());
		
        viewCanvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				onViewPaint(e.gc);
			}
		});
        
        //截图方法
        button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(e.doit){
					r=e.doit;
					choice=2;
				}
			}
		});
        
        //保存截图
        button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(e.doit && r){
					onFileSave(viewImage);
				}else{
					MessageDialog.openConfirm(shell, "提示", "\n请先点击截图按钮进行截图之后再保存！");
				}
			}
		});
        
        //放大
        button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onZoom(1);
				choice=5;
			}
		});
        
        //缩小
        button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				 onZoom(-1);
				 choice=5;
			}
		});
        
        //恢复原始大小
        button_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onZoom(0);
				choice=5;
			}
		});
        
        event=new Event();
        alphaImage = new Image(display, 100, 100);
        GC newGC =new GC(alphaImage,SWT.CENTER);
        event.gc=newGC;
		event.widget=cropCanvas;
        
	}

	@Override
	protected void checkSubclass() {}
	
	private void onZoom(int zoom) {
        if (zoom == 0) {
            showOriginal();
        } else if (zoom > 0) {
            zoomOut();
        } else if (zoom < 0) {
            zoomIn();
        }
    }

    private void showOriginal() {
        if (sourceImage == null) {
            return;
        }
        rate = 1.0;
        zoomRate(rate);
    }

    private void zoomIn() {
        if (sourceImage == null) {
            return;
        }
        // 销毁缩放图片，重新计算缩放图片
        rate = rate - 0.1;
        if (rate < 0.1) {
            rate = 0.1;
            MessageDialog.openConfirm(shell,"提示", "图片已经放到最小！");
        }
        zoomRate(rate);
    }

    private void zoomOut() {
        if (sourceImage == null) {
            return;
        }

        // 销毁缩放图片，重新计算缩放图片
        rate = rate + 0.1;
        if (rate > 5) {
            rate = 5;
            MessageDialog.openConfirm(shell,"提示", "图片已经放到最大！");
        }
        zoomRate(rate);
    }

    private void zoomRate(double rate) {
        Rectangle sret = sourceImage.getBounds();
        int dw = (int) (sret.width * rate);
        int dh = (int) (sret.height * rate);
        
        if (alphaImage != null) {
        	alphaImage.dispose();
        }
        alphaImage = new Image(display, dw, dh);
        GC newGC =new GC(alphaImage,SWT.CENTER);
        newGC.setAlpha(alpha);
        newGC.drawImage(sourceImage, 0, 0, sret.width, 
        		sret.height, 0,0, dw, dh);  //drawImage只是复制图片，此时还没有粘贴到目标区域，需要用调用redraw()粘贴
        freshCanvas();	
        newGC.dispose();
    }
    
    //取出那个矩形中交集
    private Rectangle getValidBound(Rectangle r1, Rectangle r2) {
        return r1.intersection(r2);
    }

    /**
     * 计算矩形裁剪框
     * 
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @param scale
     * @return null 没有矩形
     */
    private Rectangle calculateRectangle(int startX, int startY, int endX,int endY, boolean scale1) {
        int w = (endX - startX);
        int h = (endY - startY);
        
        //这种方法可任意截取，但是在预览框，图片会失真。
        //w=Math.abs(w);
        //h=Math.abs(h);
        
        if (w == 0 || h == 0) {
            return null;
        }

        // 初始剪切坐标P(x,y)和宽、高
        int x = startX;
        int y = startY;
        int cw = w;
        int ch = h;

        //这种方法是等比例的，图片会按比例显示，不会失真
          if (scale1) {
        	int len = Math.min(Math.abs(w), Math.abs(h));
            cw =len;
            ch =len;

            // 计算剪切坐标、宽、高
            if (w < 0) {
                x = startX - len;
            }

            if (h < 0) {
                y = startY - len;
            }
        } else {
            // 计算剪切坐标、宽、高
            if (w < 0) {
                x = startX + w;
                cw = -w;
            }
            if (h < 0) {
                y = startY + h;
                ch = -h;
            }
        }
        return new Rectangle(x, y, cw, ch);
    }
    
    /*
     * 保存截图的方法
     */
    private void onFileSave(Image image) {
        if (image == null) {
            return;
        }

        FileDialog fd = new FileDialog(shell, SWT.SAVE);
        fd.setText("图片另存为");
        String[] extensions = new String[] { "*.jpg", "*.gif", "*.ico", "*.png" };
        fd.setFilterExtensions(extensions);
        fd.setFilterNames(new String[] { "JPEG (*.jpg)", "GIF (*.gif)", "ICO (*.ico)", "PNG (*.png)" });
        String fp = fd.open();
        if (fp != null) {
            String extension = extensions[fd.getFilterIndex()];
            int fileType = getImageFileType(fp);
            if (SWT.IMAGE_UNDEFINED == fileType) {
                fp = fp + extension;
                fileType = getImageFileType(fp);
            }

            if (new File(fp).exists()) {
                MessageBox box = new MessageBox(shell, SWT.ICON_QUESTION | SWT.OK | SWT.CANCEL);
                box.setMessage("文件已存在，是否覆盖原文件？\n " + fp);
                if (box.open() == SWT.CANCEL)
                    return;
            }

            try {
                ImageLoader loader = new ImageLoader();
                ImageData imageData = image.getImageData();

                loader.data = new ImageData[] { imageData };
                loader.save(fp, fileType);
            } catch (SWTException e) {
                showErrorDialog("保存图片文件", fp, e);
            } catch (SWTError e) {
                showErrorDialog("保存图片文件", fp, e);
            }
        }
    }
 
    /**
     * 错误信息框
     */
    private void showErrorDialog(String msg, String filename, Throwable e) {
        MessageBox box = new MessageBox(shell, SWT.ICON_ERROR);
        String message = msg + "\n " + filename + "\n 出现异常:" + e.getMessage();
        box.setText("错误信息");
        box.setMessage(message);
        box.open();
    }
    
    /**
     * 判断图片类型
     */
    private int getImageFileType(String filename) {
        String ext = filename.substring(filename.lastIndexOf('.') + 1);
        if (ext.equalsIgnoreCase("gif"))
            return SWT.IMAGE_GIF;
        if (ext.equalsIgnoreCase("ico"))
            return SWT.IMAGE_ICO;
        if (ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg"))
            return SWT.IMAGE_JPEG;
        if (ext.equalsIgnoreCase("png"))
            return SWT.IMAGE_PNG;
        return SWT.IMAGE_UNDEFINED;
    }

    /**
     * 打开图片选择对话框
     */
    private void onFileOpen() {
        FileDialog fd = new FileDialog(shell, SWT.OPEN);
        fd.setText("打开图片");
        fd.setFilterPath(currentDir);
        fd.setFilterExtensions(new String[] { "*.gif; *.jpg; *.png; *.ico; *.bmp" });
        fd.setFilterNames(new String[] { "图片(gif, jpeg, png, ico, bmp,jpg)" });
        filePath = fd.open();
        if (filePath != null) {
            try {
                loadImage(filePath);
            } catch (SWTException e) {
                showErrorDialog("打开图片文件", filePath, e);
            } catch (SWTError e) {
                showErrorDialog("打开图片文件", filePath, e);
            }
            currentDir = fd.getFilterPath();
        }
        
        if(filePath==null || "".equals(filePath)){
        	return;
        }
        
        //显示图片类型
	    int index=filePath.lastIndexOf(".");  
	    String type=filePath.substring(index+1);
	    lblJpg.setText(type.toUpperCase());  
        
	    //显示图片名
        int index1=filePath.lastIndexOf("\\");
        String name=filePath.substring(index1+1,index);
        label_7.setText(name);
        
        // 显示图片大小
        File file=new File (filePath);
        long size=file.length();
        long sizes=size/1024;
        lblm.setText(sizes+"KB");  
       
        //显示图片分辨率
        BufferedImage bufferedImage=null;
		try {
			bufferedImage = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}   
        int width = bufferedImage.getWidth();   
        int height = bufferedImage.getHeight(); 
		lblx.setText(width+"x"+height);  
    }
    
    /**
     * 加载图片
     * @param filePath
     * @return
     */
    private Image loadImage(String filePath) {
        if (sourceImage != null && !sourceImage.isDisposed()) {
            sourceImage.dispose();
            sourceImage = null;
        }
        sourceImage = new Image(display, filePath);  //构造源图片
        imageDataSrc=sourceImage.getImageData();   //将图片拉伸到适应面板的大小
        imageDataSrc=imageDataSrc.scaledTo(srcComposite.getBounds().width,srcComposite.getBounds().height);
        sourceImage=new Image(display,imageDataSrc);
        showOriginal();  //将图片显示到cropCanvas中
        return sourceImage;
    }
    
    /**
     * 将图片重画到图片预览框
     */
    private void onViewPaint(GC gc) {
        if (viewImage == null) {
            Rectangle area = viewCanvas.getClientArea();
            int x2 = (area.width - 50) / 2;
            int y2 = (area.height - 20) / 2;

            gc.drawText("图片预览", x2, y2);
        } else {
        	if(r){
        		gc.drawImage(viewImage, 0, 0);
            }
        }
    }
    /**
     * 截图的相关操作
     * @param gc
     * @param alpha 
     */
    private void onCroppingPaint(GC gc, int alpha) {
        if (alphaImage == null) {
            Rectangle area = cropCanvas.getClientArea();
            gc.setClipping(area);  //设置裁剪区为cropCanvas的客户区域
            gc.fillRectangle(area); //填充背景色（取显示在cropCanvas上的图）

            int x2 = (area.width - CROP_WIDTH) / 2+25;
            int y2 = (area.height - CROP_HEIGHT) / 2+25;
            gc.drawText("请选择图片", x2, y2);
            return;
        }
        
        if(  gc.isDisposed()){
 	       gc =new GC(alphaImage,SWT.CENTER);
        }
        // 绘制图片
        imageDataSrc=alphaImage.getImageData();   //将图片拉伸到适应面板的大小
        imageDataSrc=imageDataSrc.scaledTo(srcComposite.getBounds().width,srcComposite.getBounds().height);
        alphaImage=new Image(display,imageDataSrc);
        
        int picwidth=alphaImage.getBounds().width;        //图片宽
		int picheight=alphaImage.getBounds().height;      //图片高
		int cropCanvasheight=srcComposite.getBounds().height;                 //cropCanva的高(可以为固定)
		int cropCanvaswidth=srcComposite.getBounds().width;                  //cropCanva的宽（可以为固定值）
		
		alphaImage=MeituUtils.TouMing(alphaImage, 0,0, alphaImage.getBounds().width, alphaImage.getBounds().height, alpha);
		gc.drawImage(alphaImage,0,0,picwidth,picheight,0,0,cropCanvaswidth,cropCanvasheight);
		// 填充背景
        fillBackground(gc);
        // 计算裁剪框
        cropRect = calculateRectangle(startX, startY, endX, endY, ratio);
        if (cropRect == null) {
            return;
        }

        // 是否在有效范围内
        Rectangle bound = getValidBound(cropCanvas.getClientArea(),alphaImage.getBounds());
        if (!cropRect.equals(bound.intersection(cropRect))) {  //判断cropRect是不是与【bound与cropRect】的交集相等
            return;
        }

        // 绘制裁剪框
        if(r){
        	gc.setLineStyle(SWT.LINE_DOT);
        	gc.drawRectangle(cropRect);
        }
        
        // 计算预览图片
        if (viewImage != null) {
            viewImage.dispose();
        }
        if(r){
			viewImage = new Image(display, CROP_WIDTH, CROP_HEIGHT); //构造一个绘图的空实例(viewImage只是一个空的Image实例,并没有真是图像)
			GC newGC = new GC(viewImage);
			newGC.setAlpha(alpha);
			newGC.drawImage(alphaImage, cropRect.x, cropRect.y, cropRect.width,
					cropRect.height, 0, 0, CROP_WIDTH, CROP_HEIGHT); //将alphaImage重画到viewImage实例中（此时，viewImage有图像，
			//newGC.dispose(); 										//当然这里并没有重画到viewCanvas，调用onViewPaint（）
																	//才会将viewImag画到viewCanvas）
        }   
    }
    
    /**
     * 放大缩小（裁剪虚线会消失）
     * @param gc
     * @param alpha 
     */
    private void onCroppingPaint_1(GC gc, int alpha) {
        if (alphaImage == null) {
            Rectangle area = cropCanvas.getClientArea();
            int x2 = (area.width - CROP_WIDTH) / 2+25;
            int y2 = (area.height - CROP_HEIGHT) / 2+25;
            gc.drawText("请选择图片", x2, y2);
            return;
        }
        
        if(  gc.isDisposed()){
 	       gc =new GC(alphaImage,SWT.CENTER);
        }
        // 绘制图片
        gc.drawImage(alphaImage, 0, 0);
        // 填充背景
        fillBackground(gc);
        
    }
    
    /**
     * 背景填充
     * @param gc
     */
    private void fillBackground(GC gc) {
        Rectangle rect = alphaImage.getBounds();
        Rectangle client = cropCanvas.getClientArea();
        int marginWidth = client.width - rect.width;
        if (marginWidth > 0) {
            gc.fillRectangle(rect.width, 0, marginWidth, client.height);
        }
        int marginHeight = client.height - rect.height;
        if (marginHeight > 0) {
            gc.fillRectangle(0, rect.height, client.width, marginHeight);
        }
    }
    
    /**
     * 绘图
     */
    private void freshCanvas() {
    	cropCanvas.redraw();
        viewCanvas.redraw();
    }

    /**
     * 鼠标方法
     * @param e
     */
    private void onMotion(MouseEvent e) {
        if (movable) {
            Rectangle rectangle = getValidBound(cropCanvas.getClientArea(),alphaImage.getBounds());
            // 是否在范围内移动
            if (rectangle.contains(e.x, e.y)) {
                endX = e.x;
                endY = e.y;
                freshCanvas();
            }
        }
    }
    
    /**
     * 鼠标的点击和移动
     * @author Weixian
     *
     */
    private final class CropMouseListener extends MouseAdapter {
        @Override
        public void mouseUp(MouseEvent e) {
            movable = false;
        }

        @Override
        public void mouseDown(MouseEvent e) {
            if (alphaImage == null) {
                return;
            }
           
            startX = e.x;
            startY = e.y;

            // 可以移动范围
            Rectangle rectangle = getValidBound(cropCanvas.getClientArea(),alphaImage.getBounds());
            if (rectangle.contains(startX, startY)) {
                movable = true;
            }
        }
    }
}
