package com.mbi.oes.db.ui;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.GridData;

public class ProgressComposite extends Composite {
	private Composite composite;
	private Label label;
	private Label label_1;
	private Label lblImportType;
	private Label lblPrepareData;
	private Label lblSelectFile;
	private Label lblColumnMapping;
	private Label lblReview;
	private Label lblComplete;
	
	private int currentPage;
	private Label lblNewLabel;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ProgressComposite(Composite parent, int style) {
		super(parent, style);
		initComponents();
	}
	
	public ProgressComposite(Composite parent, int style, int currentPage) {
		super(parent, style);
		this.currentPage = currentPage;
		initComponents();
	}
	
	private void initComponents() {
		setLayout(new GridLayout());
		
		composite = new Composite(this, SWT.BORDER);
		composite.setLayout(new GridLayout(1, false));
		
		label = new Label(composite, SWT.NONE);
		label.setText("Steps");
		label.setFont(SWTResourceManager.getFont("Traditional Arabic", 14, SWT.BOLD));
		
		label_1 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		label_1.setFont(SWTResourceManager.getFont("Tahoma", 12, SWT.NORMAL));
		
		lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Traditional Arabic", 12, currentPage == 0 ? SWT.BOLD | SWT.ITALIC : SWT.NORMAL));
		lblNewLabel.setText("1. Database Information");
		
		lblImportType = new Label(composite, SWT.NONE);
		lblImportType.setText("2. Import Type  ");
		lblImportType.setFont(SWTResourceManager.getFont("Traditional Arabic", 12, currentPage == 1 ? SWT.BOLD | SWT.ITALIC : SWT.NORMAL));
		
		lblPrepareData = new Label(composite, SWT.NONE);
		lblPrepareData.setText("3. Prepare Data  ");
		lblPrepareData.setFont(SWTResourceManager.getFont("Traditional Arabic", 12, currentPage == 2 ? SWT.BOLD | SWT.ITALIC : SWT.NORMAL));
		
		lblSelectFile = new Label(composite, SWT.NONE);
		lblSelectFile.setText("4. Select File  ");
		lblSelectFile.setFont(SWTResourceManager.getFont("Traditional Arabic", 12, currentPage == 3 ? SWT.BOLD | SWT.ITALIC : SWT.NORMAL));
		
		lblColumnMapping = new Label(composite, SWT.NONE);
		lblColumnMapping.setText("5. Column Mapping  ");
		lblColumnMapping.setFont(SWTResourceManager.getFont("Traditional Arabic", 12, currentPage == 4 ? SWT.BOLD | SWT.ITALIC : SWT.NORMAL));
		
		lblReview = new Label(composite, SWT.NONE);
		GridData gd_lblReview = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblReview.widthHint = 150;
		lblReview.setLayoutData(gd_lblReview);
		lblReview.setText("6. Review && Finish");
		lblReview.setFont(SWTResourceManager.getFont("Traditional Arabic", 12, currentPage == 5 ? SWT.BOLD | SWT.ITALIC : SWT.NORMAL));
		
		lblComplete = new Label(composite, SWT.NONE);
		lblComplete.setText("7. Complete");
		lblComplete.setFont(SWTResourceManager.getFont("Traditional Arabic", 12, currentPage == 6 ? SWT.BOLD | SWT.ITALIC : SWT.NORMAL));
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
