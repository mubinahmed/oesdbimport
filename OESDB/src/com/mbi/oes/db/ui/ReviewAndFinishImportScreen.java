package com.mbi.oes.db.ui;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
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
import com.mbi.oes.db.utils.OES;
import com.mbi.oes.db.utils.StringPool;
import com.mbi.oes.listeners.PageChangedListener;
import com.oes.db.model.util.BasicDAO;
import com.oes.db.model.util.HibernateUtil;

public class ReviewAndFinishImportScreen extends GenericDataImportScreen{
	private Composite titleComposite;
	private Composite tableComposite;
	private Label lblTitle;
	
	private static BasicDAO dao = new BasicDAO();
	private Composite centerComposite;
	private org.eclipse.swt.widgets.Label lblNewLabel_7;
	private org.eclipse.swt.widgets.Label lblNewLabel_8;
	private Composite buttonsComposite;
	private Button btnFinish;
	private Button btnCancel;
	private Text failedRows;
	private Button btnPrevious;

	private PageChangedListener pageChangedListener;
	private String dataLoadQuery;
	private Logger logger_ = Logger.getLogger(ReviewAndFinishImportScreen.class);
	private ProgressComposite progressComposite;
	private org.eclipse.swt.widgets.Label lblRowsCount;
	private String jdbcURL;
	private String username;
	private String password;
	private String tableName;
	private Connection conn;
	private String fieldsToImport;
	private String primaryKey;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ReviewAndFinishImportScreen(Composite parent, int style) {
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
		
		progressComposite = new ProgressComposite(tableComposite, SWT.NONE, 5);
		progressComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		progressComposite.setLayout(new GridLayout(1, false));
		
		centerComposite = new Composite(tableComposite, SWT.BORDER);
		centerComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		centerComposite.setLayout(new GridLayout(1, false));
		
		lblNewLabel_7 = new org.eclipse.swt.widgets.Label(centerComposite, SWT.NONE);
		lblNewLabel_7.setFont(SWTResourceManager.getFont("Traditional Arabic", 14, SWT.BOLD));
		lblNewLabel_7.setText("Review Import");
		
		lblNewLabel_8 = new org.eclipse.swt.widgets.Label(centerComposite, SWT.WRAP);
		GridData gd_lblNewLabel_8 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel_8.heightHint = 46;
		lblNewLabel_8.setLayoutData(gd_lblNewLabel_8);
		lblNewLabel_8.setFont(SWTResourceManager.getFont("Traditional Arabic", 11, SWT.BOLD));
		lblNewLabel_8.setText("The rows displayed below will not be imported as they have incompatible/incomplete/incorrect\r\n data. To import below rows, please correct the input CSV file\r\nand start over again.");
		
		failedRows = new Text(centerComposite, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		failedRows.setEditable(false);
		GridData gd_instructionsText = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_instructionsText.heightHint = 173;
		failedRows.setLayoutData(gd_instructionsText);
		
		lblRowsCount = new org.eclipse.swt.widgets.Label(centerComposite, SWT.NONE);
		lblRowsCount.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.NORMAL));
		lblRowsCount.setText("# of rows that will not be imported: ");
		
		buttonsComposite = new Composite(centerComposite, SWT.NONE);
		buttonsComposite.setLayout(new GridLayout(3, false));
		
		btnPrevious = new Button(buttonsComposite, SWT.NONE);
		btnPrevious.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		GridData gd_btnPrevious = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnPrevious.widthHint = 72;
		btnPrevious.setLayoutData(gd_btnPrevious);
		btnPrevious.setText("< Back");
		
		btnFinish = new Button(buttonsComposite, SWT.NONE);
		btnFinish.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		GridData gd_btnNext = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnNext.widthHint = 83;
		btnFinish.setLayoutData(gd_btnNext);
		btnFinish.setText("Finish");
		
		btnCancel = new Button(buttonsComposite, SWT.NONE);
		btnCancel.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		GridData gd_btnCancel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnCancel.widthHint = 78;
		btnCancel.setLayoutData(gd_btnCancel);
		btnCancel.setText("Cancel");
		
		addListeners();
		
		showParsingFailedRows();
	}

	
	private void showParsingFailedRows() {
		List<String> rowsList = (ArrayList)ImportSession.get("FAILED_ROWS");
		logger_.info("No of failed rows: "+rowsList.size());
		StringBuffer sb = new StringBuffer(rowsList.size() * 999);
		for (String string : rowsList) {
			sb.append(string).append(StringPool.NEW_LINE);
		}
		failedRows.setText(sb.toString());
		lblRowsCount.setText(lblRowsCount.getText() + " " + rowsList.size());
		ImportSession.set("NO_OF_ROWS_FAILED", rowsList.size());
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
				startLoadingData();
				//runLoadDataRoutine();
				if (pageChangedListener != null) {
					pageChangedListener.nextPage();
				}
			}
		});
		
		btnPrevious.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (pageChangedListener != null) {
					pageChangedListener.previousPage();
				}
			}
		});
	}
	
	private void startLoadingData() {
		Properties p = (Properties)ImportSession.get("DBInformation");
		jdbcURL = (String)p.get("hibernate.connection.url");
		username = (String)p.get("hibernate.connection.username");
		password = (String)p.get("hibernate.connection.password");
		
		conn = getConnection();
		System.out.println("Got Connection");
	
		String entityName = (String) ImportSession.get("TABLE_NAME");
		tableName = HibernateUtil.getTableNameFromEntity(entityName);
		primaryKey = ImportSession.getPK(tableName);
		if(StringUtils.isBlank(primaryKey)) {
			runLoadDataRoutine();
			return;
		}
		createTempTable();
		loadDataInTemp();
		adjustTempData();
		String filename = exportNewRows();
		
		String newLoadQuery = dataLoadQuery.substring(0, dataLoadQuery.indexOf(" INFILE "));
		newLoadQuery = newLoadQuery + " INFILE '" + filename + "'";
		newLoadQuery = newLoadQuery + dataLoadQuery.substring(dataLoadQuery.indexOf(" REPLACE "));
		dataLoadQuery = newLoadQuery;
		System.out.println(dataLoadQuery);
		runLoadDataRoutine();
		File f = new File(filename);
		f.delete();
		removeInsertedRecordsFromTemp();
		updateExistingRows();
	}
	
	private void updateExistingRows() {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			String fields[] = fieldsToImport.split(",");
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < fields.length; i++) {
				sb.append("tbi."+fields[i]+"=TI."+fields[i]+", ");
			}
	      String updateparams = sb.substring(0, sb.length()-2);
	      String sql = "UPDATE "+tableName+" tbi, TEMPIMPORT TI SET "+updateparams+" WHERE tbi."+primaryKey+"=TI."+primaryKey;
	      logger_.info("Executing: "+sql);
	      stmt.executeUpdate(sql);
	      System.out.println("Existing rows updated");
	      stmt.close();
		} catch (SQLException e) {
			logger_.info(ExceptionUtils.getStackTrace(e));
		}
	}
	private void removeInsertedRecordsFromTemp() {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		
	      
	      String sql = "DELETE FROM TEMPIMPORT WHERE ROWEXISTS = 0";
	 
	      stmt.executeUpdate(sql);
	      System.out.println("Inserted rows deleted in temp table...");
	      stmt.close();
		} catch (SQLException e) {
			logger_.info(ExceptionUtils.getStackTrace(e));
		}
	}
	
	private String exportNewRows() {
		Statement stmt = null;
		String tempfile = "";
		try {
			stmt = conn.createStatement();
			tempfile = System.getProperty("java.io.tmpdir") + "tempoutfile.csv";
			File f = new File(tempfile);
			if(f.exists()) {
				boolean deleted = f.delete();
				if(!deleted) {
					tempfile = tempfile.replace("tempfile.csv", "tempfile"+new java.util.Date().getTime()+".csv");
				}
			}
			tempfile = tempfile.replaceAll("\\\\", "/");
			fieldsToImport = dataLoadQuery.substring(dataLoadQuery.indexOf("(")+1, dataLoadQuery.indexOf(")"));
	      String sql = "SELECT "+fieldsToImport +" INTO OUTFILE '"+tempfile+"' FIELDS TERMINATED BY '|' LINES TERMINATED BY '\n' FROM TEMPIMPORT WHERE ROWEXISTS = 0";
	      logger_.info("Executing: "+sql);
	      stmt.executeQuery(sql);
	      System.out.println("New Rows exported to temp  file "+tempfile);
	      stmt.close();
		} catch (SQLException e) {
			logger_.info(ExceptionUtils.getStackTrace(e));
		}
		return tempfile;
	}
	private void loadDataInTemp() {
		logger_.info("loadDataInTemp ENTRY");
		String dataLoadInTempQuery = dataLoadQuery.replace("TABLE "+tableName, "TABLE TEMPIMPORT");
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(HibernateUtil.MY_SQL);
		session.beginTransaction();
		try {
			int result = session.createSQLQuery(dataLoadInTempQuery).executeUpdate();
			logger_.info("Result: "+result);
			
			handleWarehouse(session);
		} catch (Exception e) {
			String exceptionMessage = ExceptionUtils.getRootCauseMessage(e);
			logger_.info(exceptionMessage);
			e.printStackTrace();
			MessageDialog.openInformation(ReviewAndFinishImportScreen.this.getShell(), "Import Error", exceptionMessage);
		}
		session.getTransaction().commit();
		logger_.info("loadDataInTemp EXIT");
	}
	
	private void adjustTempData() {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		
	      String sql = "UPDATE TEMPIMPORT TI, "+tableName+" tbi SET TI.ROWEXISTS = 1 WHERE tbi."+primaryKey+" = TI."+primaryKey;
	      logger_.info("Executing: "+sql);
	      stmt.executeUpdate(sql);
	      System.out.println("Temp table updated");
	      stmt.close();
		} catch (SQLException e) {
			logger_.info(ExceptionUtils.getStackTrace(e));
		}
	}
	
	
	private Connection getConnection() {
		Connection connect = null;
		    try {
		      Class.forName("com.mysql.jdbc.Driver");
		       connect = DriverManager.getConnection(jdbcURL, username, password);
   		    } catch (Exception e) {
   		    	e.printStackTrace();
	    }
		    return connect;
	}
	
	private void createTempTable() {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		
	      
	      String sql = "DROP TABLE TEMPIMPORT ";
	 
	      stmt.executeUpdate(sql);
	      System.out.println("Table  deleted in given database...");
	      stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		try {
			stmt = conn.createStatement();
			String sql = "CREATE TABLE TEMPIMPORT LIKE "+tableName;
			stmt.executeUpdate(sql);
			System.out.println("temp Table created");
			
			stmt.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			stmt = conn.createStatement();
			String sql = "ALTER TABLE TEMPIMPORT ADD ROWEXISTS INT DEFAULT 0";
			stmt.executeUpdate(sql);
			System.out.println("temp Table ALTERED");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	protected boolean runLoadDataRoutine() {
		logger_.info("Loading data to database");
		long start_time = System.currentTimeMillis();
		
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(HibernateUtil.MY_SQL);
		session.beginTransaction();
		try {
			int result = session.createSQLQuery(dataLoadQuery).executeUpdate();
			logger_.info("Result: "+result);
			
			postImportAction(session);
		} catch (Exception e) {
			String exceptionMessage = ExceptionUtils.getRootCauseMessage(e);
			logger_.info(exceptionMessage);
			e.printStackTrace();
			MessageDialog.openInformation(ReviewAndFinishImportScreen.this.getShell(), "Import Error", exceptionMessage);
			return false;
		}
		session.getTransaction().commit();
		long end_time = System.currentTimeMillis();
		logger_.info("Total time  = " + (end_time - start_time));
		ImportSession.set("LOAD_TIME", (end_time - start_time));
		return true;
	}
	private void postImportAction(Session session) {
		if(dataLoadQuery.indexOf(" TABLE items") != -1) {
			handleWarehouse(session);
		} else if(dataLoadQuery.indexOf(" TABLE orders ") != -1) {
			handleOrdersHead(session);
		} else if(dataLoadQuery.indexOf(" TABLE orderdetails ") != -1) {
			handleOrderDetails(session);
		} else if(dataLoadQuery.indexOf(" TABLE customer ") != -1) {
			handleCustomer(session);
		} 
		
		System.out.println();
		
	}
	private void handleCustomer(Session session) {
		System.out.println("Updating datecreated");
		session.createSQLQuery("UPDATE customer SET datecreated = NOW() WHERE datecreated IS NULL").executeUpdate();
	}
	private void handleOrdersHead(Session session) {
	
		/*
		mysql> DELETE FROM orderslog;
		mysql> insert into orderslog (counterman, customercode, netamount, ordernumber,
				tax, orderdate, ordercreationdate, status, shipdate, deliverytype, shippingaddre
				ss, shipzip) SELECT counterman, customercode, netamount, ordernumber, tax, order
				date, orderdate, orderstatus, shiptime, deliverytype, concat(shipname, '\n', sol
				daddr1, '\n', soldaddr2, '\n', soldaddr3), shipzip from orders;
				*/
		session.createSQLQuery("UPDATE orders SET orderstatus = '"+OES.OrderStatus.OPEN+"' WHERE orderstatus = 'O'").executeUpdate();
		session.createSQLQuery("UPDATE orders SET orderstatus = '"+OES.OrderStatus.SHIPPED+"' WHERE orderstatus = 'S'").executeUpdate();
		session.createSQLQuery("DELETE FROM orderslog").executeUpdate();
		String insertQuery = "insert into orderslog (counterman, customercode, netamount, ordernumber, tax," +
				" orderdate, ordercreationdate, status, shipdate, deliverytype," +
				" shippingaddress, shipzip) SELECT counterman, customercode," +
				" netamount, ordernumber, tax, orderdate, orderdate, orderstatus," +
				" shiptime, deliverytype, concat(shipname, '\n', soldaddr1, '\n'," +
				" soldaddr2, '\n', soldaddr3), shipzip from orders";
		session.createSQLQuery(insertQuery).executeUpdate();
	}
	
	private void handleOrderDetails(Session session) {
		session.createSQLQuery("UPDATE orderdetails SET itemstatus = 'S' WHERE quantityshipped = quantityordered").executeUpdate();
		session.createSQLQuery("UPDATE orderdetails SET itemstatus = 'O' WHERE quantityshipped = 0").executeUpdate();
		session.createSQLQuery("UPDATE orderdetails SET itemstatus = 'I' WHERE quantityinvoice = quantityordered").executeUpdate();
		session.createSQLQuery("UPDATE orderdetails SET itemstatus = 'Z' WHERE itemstatus IS NULL").executeUpdate();
		
		/*
		 *mysql> insert into itemhistory(customercode, itemcode, itemdescription, listpric
e, orderdate, ordernumber, priceentered, quantityinvoice, quantityordered, quant
ityshipped, tax) select customercode, itemcode, itemdescription, listprice, orde
rdate, ordernumber, priceentered, quantityinvoice, quantityordered, quantityship
ped, tax from orderdetails;
		 */
		session.createSQLQuery("DELETE FROM itemhistory").executeUpdate();
		String insertQuery = "insert into itemhistory(customercode, itemcode, itemdescription," +
				" listprice, orderdate, ordernumber, priceentered, quantityinvoice, quantityordered," +
				" quantityshipped, tax) select customercode, itemcode, itemdescription, listprice," +
				" orderdate, ordernumber, priceentered, quantityinvoice, quantityordered," +
				" quantityshipped, tax from orderdetails";
		session.createSQLQuery(insertQuery).executeUpdate();
	}
	
	private void handleWarehouse(Session session) {
		
			//mysql> insert into warehouseitemdetails (warehouseid, itemcode, quantity, onhand
			//, committed) select 1, productid, 10, 10, 10 from items;
			
			String deleteWarehouseData = "DELETE FROM warehouseitemdetails";
			session.createSQLQuery(deleteWarehouseData).executeUpdate();
			logger_.info("Warehouseitem details deleted");
			
			String insertIntoWarehouseQuery = "INSERT INTO warehouseitemdetails (warehouseid, itemcode, Quantity, onhand) " +
					"SELECT 1, productid, 0, 0 FROM items";// WHERE productid NOT IN (SELECT itemcode FROM warehouseitemdetails)";
			int result = session.createSQLQuery(insertIntoWarehouseQuery).executeUpdate();
			logger_.info("Warehouse items inserted");
			String updateOnhandQuery = "UPDATE warehouseitemdetails W SET W.onhand = W.onhand +" + " (SELECT I.importqty FROM items I WHERE  I.productid = W.itemcode)";
			result = session.createSQLQuery(updateOnhandQuery).executeUpdate();
			logger_.info("Warehouse onhand updated");
			String updateQuantityQuery = "UPDATE warehouseitemdetails W SET W.Quantity = W.Quantity +" + " (SELECT I.importqty FROM items I WHERE  I.productid = W.itemcode)";
			result = session.createSQLQuery(updateQuantityQuery).executeUpdate();
			logger_.info("Warehouse quantity updated");
			String updateItemsQuery = "UPDATE items SET importqty = 0";
			result = session.createSQLQuery(updateItemsQuery).executeUpdate();
			logger_.info("Warehouse updated: "+result);
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
