package com.yc.utils;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class UiUtils {
		
		/**
		 * 全屏显示
		 * @param shell
		 * @param display
		 */
		public static void showFullScreen(Shell shell,Display display){
			//shell　　窗口  display显示设备
			int height=display.getBounds().height;
			int width=display.getBounds().width;
			shell.setBounds(0,0,width,height);
			
		}
		
		/**
		 * 居中显示
		 */
		public static void showInCenter (  Shell shell ,Display  display){
			Rectangle displayBounds = display.getPrimaryMonitor().getBounds(); 
	        Rectangle shellBounds = shell.getBounds(); 
	        int x = displayBounds.x + (displayBounds.width - shellBounds.width)>>1; 
	        int y = displayBounds.y + (displayBounds.height - shellBounds.height)>>1; 
	        shell.setLocation(x, y); 
		}
}
