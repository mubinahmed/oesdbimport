package com.mbi.oes.db.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

import com.mbi.oes.db.controls.Label;
import com.mbi.oes.db.utils.ImportSession;
import com.mbi.oes.listeners.PageChangedListener;
import com.oes.db.model.util.BasicDAO;
import com.oes.model.my_sql.entities.Newinventory;

import org.eclipse.swt.widgets.Button;

public class SelectImportTypeScreen extends GenericDataImportScreen{
	private Composite titleComposite;
	private Composite tableComposite;
	private Label lblTitle;
	
	private static BasicDAO dao = new BasicDAO();
	private Composite centerComposite;
	private org.eclipse.swt.widgets.Label lblNewLabel_7;
	private org.eclipse.swt.widgets.Label lblNewLabel_8;
	private org.eclipse.swt.widgets.List importTypeList;
	private Composite buttonsComposite;
	private Button btnNext;
	private Button btnCancel;
	private PageChangedListener pageChangedListener;
	private ProgressComposite progressComposite;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SelectImportTypeScreen(Composite parent, int style) {
		super(parent, style);
		initComponents();
	}
	private void initComponents() {
		setLayout(new GridLayout(1, false));
		
		titleComposite = new Composite(this, SWT.NONE);
		titleComposite.setFont(SWTResourceManager.getFont("Tahoma", 16, SWT.NORMAL));
		titleComposite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		titleComposite.setLayout(new GridLayout(1, false));
		
		lblTitle = new Label(titleComposite, SWT.NONE);
		lblTitle.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		lblTitle.setFont(SWTResourceManager.getFont("Vijaya", 24, SWT.BOLD));
		lblTitle.setText("OES Data Import");
		
		tableComposite = new Composite(this, SWT.NONE);
		tableComposite.setLayout(new GridLayout(2, false));
		tableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		progressComposite = new ProgressComposite(tableComposite, SWT.NONE, 1);
		progressComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		progressComposite.setLayout(new GridLayout(1, false));
		
		centerComposite = new Composite(tableComposite, SWT.BORDER);
		centerComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		centerComposite.setLayout(new GridLayout(1, false));
		
		lblNewLabel_7 = new org.eclipse.swt.widgets.Label(centerComposite, SWT.NONE);
		lblNewLabel_7.setFont(SWTResourceManager.getFont("Traditional Arabic", 14, SWT.BOLD));
		lblNewLabel_7.setText("Import Type");
		
		lblNewLabel_8 = new org.eclipse.swt.widgets.Label(centerComposite, SWT.NONE);
		lblNewLabel_8.setFont(SWTResourceManager.getFont("Traditional Arabic", 11, SWT.BOLD));
		lblNewLabel_8.setText("Select the type of Import or Update to be done");
		
		importTypeList = new org.eclipse.swt.widgets.List(centerComposite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		importTypeList.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		importTypeList.setItems(ImportSession.getTableDescriptions());
		importTypeList.setFont(SWTResourceManager.getFont("Traditional Arabic", 11, SWT.BOLD));
		
		buttonsComposite = new Composite(centerComposite, SWT.NONE);
		buttonsComposite.setLayout(new GridLayout(2, false));
		
		btnNext = new Button(buttonsComposite, SWT.NONE);
		btnNext.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		GridData gd_btnNext = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnNext.widthHint = 83;
		btnNext.setLayoutData(gd_btnNext);
		btnNext.setText("Next>");
		btnNext.setEnabled(false);
		
		btnCancel = new Button(buttonsComposite, SWT.NONE);
		btnCancel.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		GridData gd_btnCancel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnCancel.widthHint = 78;
		btnCancel.setLayoutData(gd_btnCancel);
		btnCancel.setText("Cancel");
		
		addListeners();
	}

	
	private void addListeners() {
		
		importTypeList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(importTypeList.getSelection().length == 1){
					ImportSession.set("TABLE_NAME", ImportSession.getTableName(importTypeList.getSelection()[0]));
					btnNext.setEnabled(true);
				}
			}
		});
		
		btnCancel.addSelectionListener(new SelectionAdapter() {			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				System.exit(0);
			}
		});
		
		btnNext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (pageChangedListener != null) {
					pageChangedListener.nextPage();
				}
			}
		});
	}
	
	public void addPageChangeListener(PageChangedListener listener) {
		pageChangedListener = listener;
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
