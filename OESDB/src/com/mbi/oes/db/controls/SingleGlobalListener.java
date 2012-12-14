package com.mbi.oes.db.controls;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import com.mbi.oes.db.utils.OES;

public class SingleGlobalListener implements Listener {
	private static SingleGlobalListener listener = new SingleGlobalListener();

	public static SingleGlobalListener getListener() {
		return listener;
	}

	@Override
	public void handleEvent(Event e) {
		if((e.type == SWT.MouseDoubleClick || e.type == SWT.MouseDown || e.type == SWT.Selection) && OES.SWALLOW_MOUSE_CLICKS){
			e.doit = false;
			System.out.println("Swallowing mouse clicks");
			return;
		}
		System.out.println(e.type+" "+e.widget);
		switch (e.type) {
		case SWT.Close:        
			OES.CLOSING = true;
			closeWindow(e);
			break;
		}
	}


	private void closeWindow(Event e) {
		System.out.println("Closing window");
		e.doit = false;
	}
}
