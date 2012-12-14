package com.mbi.oes.db.ui;

import javax.swing.JFileChooser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.mbi.oes.db.controls.Label;
import com.mbi.oes.db.utils.ImportSession;
import com.mbi.oes.listeners.PageChangedListener;
import com.oes.db.model.util.BasicDAO;

public class SelectImportFileScreen extends GenericDataImportScreen{
	private Composite titleComposite;
	private Composite tableComposite;
	private Label lblTitle;
	
	private static BasicDAO dao = new BasicDAO();
	private Composite centerComposite;
	private org.eclipse.swt.widgets.Label lblNewLabel_7;
	private org.eclipse.swt.widgets.Label lblNewLabel_8;
	private Composite buttonsComposite;
	private Button btnNext;
	private Button btnCancel;
	private PageChangedListener pageChangedListener;
	private org.eclipse.swt.widgets.Label lblNewLabel_9;
	private Button btnPrevious;
	private Composite fileSelectComposite;
	private Text txtSelectedFile;
	private Button btnBrowse;
	private ProgressComposite progressComposite;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SelectImportFileScreen(Composite parent, int style) {
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
		
		progressComposite = new ProgressComposite(tableComposite, SWT.NONE, 3);
		progressComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		progressComposite.setLayout(new GridLayout(1, false));
		
		centerComposite = new Composite(tableComposite, SWT.BORDER);
		centerComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		centerComposite.setLayout(new GridLayout(2, false));
		
		lblNewLabel_7 = new org.eclipse.swt.widgets.Label(centerComposite, SWT.NONE);
		lblNewLabel_7.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblNewLabel_7.setFont(SWTResourceManager.getFont("Traditional Arabic", 14, SWT.BOLD));
		lblNewLabel_7.setText("Import File");
		
		lblNewLabel_8 = new org.eclipse.swt.widgets.Label(centerComposite, SWT.NONE);
		lblNewLabel_8.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblNewLabel_8.setFont(SWTResourceManager.getFont("Traditional Arabic", 11, SWT.BOLD));
		lblNewLabel_8.setText("Select the CSV file to be imported");
		
		lblNewLabel_9 = new org.eclipse.swt.widgets.Label(centerComposite, SWT.NONE);
		lblNewLabel_9.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.BOLD));
		lblNewLabel_9.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_9.setText("File:");
		
		fileSelectComposite = new Composite(centerComposite, SWT.NONE);
		fileSelectComposite.setLayout(new GridLayout(2, false));
		
		txtSelectedFile = new Text(fileSelectComposite, SWT.BORDER);
		GridData gd_txtSelectedFile = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtSelectedFile.widthHint = 251;
		txtSelectedFile.setLayoutData(gd_txtSelectedFile);
		
		btnBrowse = new Button(fileSelectComposite, SWT.NONE);
		btnBrowse.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		GridData gd_btnBrowse = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnBrowse.widthHint = 63;
		btnBrowse.setLayoutData(gd_btnBrowse);
		btnBrowse.setText("Browse");
		
		buttonsComposite = new Composite(centerComposite, SWT.NONE);
		buttonsComposite.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		buttonsComposite.setLayout(new GridLayout(3, false));
		
		btnPrevious = new Button(buttonsComposite, SWT.NONE);
		btnPrevious.setFont(SWTResourceManager.getFont("Tahoma", 8, SWT.BOLD));
		btnPrevious.setText("< Previous");
		
		btnNext = new Button(buttonsComposite, SWT.NONE);
		btnNext.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		GridData gd_btnNext = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnNext.widthHint = 83;
		btnNext.setLayoutData(gd_btnNext);
		btnNext.setEnabled(false);
		btnNext.setText("Next>");
		
		btnCancel = new Button(buttonsComposite, SWT.NONE);
		btnCancel.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		GridData gd_btnCancel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnCancel.widthHint = 78;
		btnCancel.setLayoutData(gd_btnCancel);
		btnCancel.setText("Cancel");
		
		addListeners();
	}

	
	private void addListeners() {
		btnCancel.addSelectionListener(new SelectionAdapter() {			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				System.exit(0);
			}
		});
		
		btnNext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ImportSession.set("FILE_PATH",
						txtSelectedFile.getText());
				if (pageChangedListener != null) {
					pageChangedListener.nextPage();
				}
			}
		});
		
		btnPrevious.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ImportSession.set("FILE_PATH",
						txtSelectedFile.getText());
				if (pageChangedListener != null) {
					pageChangedListener.previousPage();
				}
			}
		});
		
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				 fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
				      txtSelectedFile.setText(fileChooser.getSelectedFile().getAbsolutePath());
						ImportSession.set("FILE_PATH",
								txtSelectedFile.getText());
						btnNext.setEnabled(true);
				 }
				    
				     
				 /*FileDialog fd = new FileDialog(SelectImportFileScreen.this.getShell(), SWT.OPEN);
			        fd.setText("Open");
			        String[] filterExt = { "*.*", ".csv", ".txt" };
			        fd.setFilterExtensions(filterExt);
			        String selected = fd.open();
			        if (selected != null && selected.length() > 0) {
						txtSelectedFile.setText(selected);
						ImportSession.set("FILE_PATH",
								txtSelectedFile.getText());
						btnNext.setEnabled(true);
					}*/
			}
		});
		
		txtSelectedFile.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if(txtSelectedFile.getText().trim().length() > 0) {
					btnNext.setEnabled(true);
				} else {
					btnNext.setEnabled(false);
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
