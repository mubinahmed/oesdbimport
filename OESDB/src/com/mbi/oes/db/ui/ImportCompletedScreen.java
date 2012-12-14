package com.mbi.oes.db.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.hibernate.Session;

import com.mbi.oes.db.controls.Label;
import com.mbi.oes.db.utils.ImportSession;
import com.mbi.oes.db.utils.StringPool;
import com.mbi.oes.listeners.PageChangedListener;
import com.oes.db.model.util.BasicDAO;
import com.oes.db.model.util.HibernateUtil;

public class ImportCompletedScreen extends GenericDataImportScreen{
	private Composite titleComposite;
	private Composite tableComposite;
	private Label lblTitle;
	
	private static BasicDAO dao = new BasicDAO();
	private Composite centerComposite;
	private org.eclipse.swt.widgets.Label lblNewLabel_7;
	private org.eclipse.swt.widgets.Label lblStatistics;
	private Composite buttonsComposite;
	private Button btnFinish;
	private Button btnCancel;

	private PageChangedListener pageChangedListener;
	private String dataLoadQuery;
	private Logger logger_ = Logger.getLogger(ImportCompletedScreen.class);
	private ProgressComposite progressComposite;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ImportCompletedScreen(Composite parent, int style) {
		super(parent, style);
		initComponents();
	}
	private void initComponents() {
		dataLoadQuery = (String)ImportSession.get("DATA_LOAD_QUERY");
		
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
		
		progressComposite = new ProgressComposite(tableComposite, SWT.NONE, 6);
		progressComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		progressComposite.setLayout(new GridLayout(1, false));
		
		centerComposite = new Composite(tableComposite, SWT.BORDER);
		centerComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		centerComposite.setLayout(new GridLayout(1, false));
		
		lblNewLabel_7 = new org.eclipse.swt.widgets.Label(centerComposite, SWT.NONE);
		lblNewLabel_7.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblNewLabel_7.setFont(SWTResourceManager.getFont("Traditional Arabic", 16, SWT.BOLD));
		lblNewLabel_7.setText("Import Completed !!!");
		
		lblStatistics = new org.eclipse.swt.widgets.Label(centerComposite, SWT.WRAP);
		GridData gd_lblStatistics = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblStatistics.heightHint = 158;
		lblStatistics.setLayoutData(gd_lblStatistics);
		lblStatistics.setFont(SWTResourceManager.getFont("Lucida Calligraphy", 14, SWT.BOLD | SWT.ITALIC));
		
		buttonsComposite = new Composite(centerComposite, SWT.NONE);
		buttonsComposite.setLayout(new GridLayout(2, false));
		
		btnFinish = new Button(buttonsComposite, SWT.NONE);
		btnFinish.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		GridData gd_btnNext = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnNext.widthHint = 141;
		btnFinish.setLayoutData(gd_btnNext);
		btnFinish.setText("Another Import");
		
		btnCancel = new Button(buttonsComposite, SWT.NONE);
		btnCancel.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		GridData gd_btnCancel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnCancel.widthHint = 78;
		btnCancel.setLayoutData(gd_btnCancel);
		btnCancel.setText("Exit");
		
		addListeners();
		
		showStatistics();
	}

	
	private void showStatistics() {
		int inputRowsToBeImported = (Integer) ImportSession.get("ROWS_TO_BE_IMPORTED");
		int actualRowsImported = inputRowsToBeImported - (Integer) ImportSession.get("NO_OF_ROWS_FAILED");
		long loadTime = (Long) ImportSession.get("LOAD_TIME");
		

		StringBuffer sb = new StringBuffer(999);
		sb.append("No of rows in input CSV file: ").append(inputRowsToBeImported).append(StringPool.NEW_LINE)
			.append("No of rows imported successfully: ").append(actualRowsImported).append(StringPool.NEW_LINE)
			.append("Time taken to load the data: ").append(loadTime).append(" milliseconds").append(StringPool.NEW_LINE);
		lblStatistics.setText(sb.toString());
	}
	private void addListeners() {
		btnCancel.addSelectionListener(new SelectionAdapter() {			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				System.exit(0);
			}
		});
		
		btnFinish.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (pageChangedListener != null) {
					pageChangedListener.nextPage();
				}
			}
		});
	}
	
	protected void runLoadDataRoutine() {
		logger_.info("Loading data to database");
		long start_time = System.currentTimeMillis();

		
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(HibernateUtil.MY_SQL);
		session.beginTransaction();
		int result = session.createSQLQuery(dataLoadQuery).executeUpdate();
		logger_.info("Result: "+result);
		session.getTransaction().commit();
		long end_time = System.currentTimeMillis();
		logger_.info("Total time  = " + (end_time - start_time));
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override
	public void addPageChangeListener(PageChangedListener listener) {
		pageChangedListener = listener;
	}
}
