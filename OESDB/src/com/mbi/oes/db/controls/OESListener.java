
package com.mbi.oes.db.controls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;

import com.mbi.oes.db.utils.OES;

public class OESListener implements Listener {

	Widget widget;

	public OESListener(Widget w) {
		this.widget = w;
	}

	public OESListener() {
	}

	@Override
	public void handleEvent(Event e) {

		switch (e.type) {
		case SWT.MeasureItem:
			measureEvent(e);
			break;
		}
	}

	protected void measureEvent(Event event) {
		//System.out.println("OES Listner MI");
		try {
			event.height = (int) (event.gc.getFontMetrics().getHeight()*OES.TABLEITEM_HEIGHT_FACTOR);
		} catch (NullPointerException e) {

		}

	}

}
