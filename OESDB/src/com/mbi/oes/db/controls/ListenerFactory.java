/**
 * File name: ListenerFactory.java
 * 
 * Major Change Log:
 * 
 *    Date:                Name:           Description:
 *    -------           -------------    -------------------------------
 *    Jun 20, 2012		 nvelakanti			created
 *    
 **/

package com.mbi.oes.db.controls;

import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;


/*
 * @author nvelakanti
 */

public class ListenerFactory {

	public static Listener getListener(){
		Listener oesListener = new OESListener();
		return oesListener;
	}
	public static Listener getListener(Widget w){
		Listener oesListener = new OESListener(w);
		return oesListener;
	}
}
