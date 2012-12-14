package com.mbi.oes.db.controls;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import com.mbi.oes.db.utils.OES;


public class CustomDialog extends Dialog {
	private Label messageLabel;
	private Button btnNewButton;
	private CustomTextBox txtControlToUpdate;
	private String messageStr;
	
	String title;
	String columnName;
	String tableName;
	private boolean changeOkToYes = false;
	private boolean autoClose = OES.DIALOG_AUTOCLOSE_ENABLED;
	private long autoCloseTimeOut = OES.DIALOG_AUTOCLOSE_TIMEOUT;
	public String OK_LABEL  = IDialogConstants.OK_LABEL;
	public String CANCEL_LABEL= IDialogConstants.CANCEL_LABEL;
	/**
	 * Create the dialog.
	 * @param txtCustomerID 
	 */
	public CustomDialog(String message, CustomTextBox txtCustomerID) {
		//super(parentShell);
		super(Display.getCurrent().getActiveShell());
		setShellStyle(SWT.APPLICATION_MODAL);
		this.messageStr = message;
		this.txtControlToUpdate = txtCustomerID;
	}
	
	public CustomDialog(String message, String title) {
		//super(parentShell);
		super(Display.getCurrent().getActiveShell());
		setShellStyle(SWT.APPLICATION_MODAL);
		this.messageStr = message;
		this.title = title;
	}
	
	public CustomDialog(String message) {
		//super(parentShell);
		super(Display.getCurrent().getActiveShell());
		setShellStyle(SWT.APPLICATION_MODAL);
		this.messageStr = message;
	}
	public CustomDialog(String message, String OK_LABEL, String CANCEL_LABEL) {
		//super(parentShell);
		super(Display.getCurrent().getActiveShell());
		setShellStyle(SWT.APPLICATION_MODAL);
		this.messageStr = message;
		this.OK_LABEL = OK_LABEL;
		this.CANCEL_LABEL = CANCEL_LABEL;
	}
	
	public void setMessage(String message) {
		this.messageStr = message;
	}
	
	public CustomDialog(String message, boolean changeOkToYes) {
		//super(parentShell);
		super(Display.getCurrent().getActiveShell());
		this.changeOkToYes = changeOkToYes;
		setShellStyle(SWT.APPLICATION_MODAL);
		this.messageStr = message;
	}
	
	public CustomDialog(String message, CustomTextBox txtCustomerID, String title) {
		//super(parentShell);
		super(Display.getCurrent().getActiveShell());
		setShellStyle(SWT.APPLICATION_MODAL);
		this.messageStr = message;
		this.txtControlToUpdate = txtCustomerID;
		this.title = title;
	}
	
	public CustomDialog(String message, CustomTextBox txtCustomerID, String title, String colName, String tableName) {
		//super(parentShell);
		super(Display.getCurrent().getActiveShell());
		setShellStyle(SWT.APPLICATION_MODAL);
		this.messageStr = message;
		this.txtControlToUpdate = txtCustomerID;
		this.title = title;
		this.columnName = colName;
		this.tableName = tableName;
	}
	
	public static int showMessage(String message) {
		return showMessage(message, false);
	}
	
	public static int showMessage(String message, boolean changeYesToOk) {
		CustomDialog msgBox = new CustomDialog(message, changeYesToOk);
		return msgBox.open();
	}
	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(null);
		
		messageLabel = new Label(container, SWT.WRAP);
		messageLabel.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.BOLD));
		messageLabel.setBounds(22, 41, 343, 62);
		messageLabel.setText(messageStr);
		
		btnNewButton = new Button(container, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		btnNewButton.setBounds(371, 60, 68, 23);
		btnNewButton.setText("Change ID");
		
		if(columnName == null || tableName == null || txtControlToUpdate == null){
			btnNewButton.setVisible(false);
		}
		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		
		if(changeOkToYes) {
			OK_LABEL = IDialogConstants.YES_LABEL;
			CANCEL_LABEL = IDialogConstants.NO_LABEL;
		}
		createButton(parent, IDialogConstants.OK_ID, OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 184);
	}
	
	@Override
	public int open() {
		if(autoClose){
			autoClose();
		}
		return super.open();
	}

	/*public int open(boolean autoClose) {
		if(autoClose){
			autoClose();
		}
		return super.open();
	}*/
	
	public void setTimeOut(long timeout) {
		autoCloseTimeOut = timeout;
	}
	public void setAutoClose(boolean autoClose) {
		this.autoClose  = autoClose;
	}
	/**
	 * Auto closes the dialog box after waiting the configured amount of time
	 */
	private void autoClose() {
		Thread trigger = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(autoCloseTimeOut );   //let the new thread sleep for configured time
				} catch (InterruptedException e) {
					System.err.println("Received Interrupted Exception in CustomDialog. Ignored");
				}
				
				getParentShell().getDisplay().asyncExec(new Runnable() {           //now schedule a task on the UI thread to close the dialog
					@Override
					public void run() {
							close();    // close the dialog
					}
				});
				
			}
		});
		
		trigger.start();
	}
}
