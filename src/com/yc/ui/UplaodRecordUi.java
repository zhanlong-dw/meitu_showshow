package com.yc.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.yc.dao.DBHelper;
import com.yc.utils.Common;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class UplaodRecordUi extends Composite {
	private Table table;
	private int persize=4;
	private int page=1;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	
	public UplaodRecordUi(Composite parent, int style) {
		
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		SashForm sashForm = new SashForm(this, SWT.VERTICAL);
		
		Composite composite = new Composite(sashForm, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm_1 = new SashForm(composite, SWT.VERTICAL);
		
		Composite composite_2 = new Composite(sashForm_1, SWT.NONE);
		composite_2.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblNewLabel = new Label(composite_2, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("华文行楷", 25, SWT.BOLD));
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setText("我的上传记录");
		
		Composite composite_3 = new Composite(sashForm_1, SWT.NONE);
		composite_3.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		table = new Table(composite_3, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("图片名");
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(100);
		tableColumn.setText("图片分辨率");
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(100);
		tblclmnNewColumn_2.setText("图片大小");
		
		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(100);
		tableColumn_3.setText("图片格式");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("图片备注");
		
		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("共享状态");
		
		TableColumn tblclmnNewColumn_3 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_3.setWidth(151);
		tblclmnNewColumn_3.setText("上传日期");
		
		Composite composite_1 = new Composite(sashForm_1, SWT.NONE);
		
		Button button = new Button(composite_1, SWT.NONE);

		button.setText("上一页");
		button.setBounds(247, 15, 80, 27);
		
		Button button_1 = new Button(composite_1, SWT.NONE);

		button_1.setText("下一页");
		button_1.setBounds(459, 15, 80, 27);
		
		Label label = new Label(composite_1, SWT.NONE);
		label.setText("当前页0/0");
		label.setBounds(361, 20, 80, 17);
		
		Label label_1 = new Label(composite_1, SWT.NONE);
		label_1.setBounds(581, 20, 128, 17);
		label_1.setText("数据记录共：0条");
		
		Button button_2 = new Button(composite_1, SWT.NONE);

		button_2.setBounds(49, 15, 80, 27);
		button_2.setText("查询");
		sashForm_1.setWeights(new int[] {51, 346, 77});
		sashForm.setWeights(new int[] {332});
		Common.event=new Event();
		Common.event.widget=button_2;
		DBHelper db=new DBHelper();
		//查询
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				List<String> params=new ArrayList<String>();
				String sql="select * from (select A.*,rownum rn from (select * from MeituPicture where userId=?) A where rownum<?) where rn>?";
				int max= (persize*page+1);
				int min=(page-1)*persize;
				params.add(0,Common.admin.getUserId());
				params.add(1,max+"" );
				params.add(2,min+"");
				table.removeAll();
				List<Map<String,String>> list=db.find(sql, params);	
				
				if(list!=null&&list.size()>0){
					for(Map<String,String> map:list){
						TableItem ti=new TableItem(table,SWT.None);
						ti.setText(new String[]{map.get("PICNAME"),map.get("PICRESOLUTION"),map.get("PICSIZE"),map.get("PICFORMAT"),map.get("PICCOMENT"),map.get("PICSTATE"),map.get("PICSAVETIME")});
					}
				}
				
				sql="select count(*) from MeituPicture where userId=?";
				List<String> li=new ArrayList<String>();
				li.add(Common.admin.getUserId());
				int r=(int) db.doSelectFunction(sql,li);
				
				label_1.setText("数据记录共："+r+"条");
				if(r%persize>0){
					label.setText("当前页"+page+"/"+(r/persize+1));
				}else{
					label.setText("当前页"+page+"/"+r/persize);
				}
			}
			
		});
		//上一页
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				page--;

				if(page<=0){
					MessageDialog.openInformation(getShell(), "提示", "已经是第1页！");
					page=1;
					return;
				}
				button_2.notifyListeners(SWT.Selection, Common.event);
			}
		});
		
		//下一页
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				page++;
				String sql="select count(*) from MeituPicture";
				int r=(int) db.doSelectFunction(sql,null);
				int y=0;
				if(r%persize>0){
					y=r/persize+1;
				}else{
					y=r/persize;
				}	
				if(page>y){
					MessageDialog.openInformation(getShell(), "提示", "已经是最后一页了！");
					--page;
					return;
				}				
				button_2.notifyListeners(SWT.Selection, Common.event);
			}
		});		

	}

}
