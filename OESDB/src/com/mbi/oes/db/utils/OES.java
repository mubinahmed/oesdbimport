package com.mbi.oes.db.utils;

import org.eclipse.swt.SWT;

public class OES {
	
	public static final String NEW = "New"; 
	public static final String EDIT = "Edit"; 
	public static final String COPY = "Copy";
	public static final String BARCODE = "Barcode";
	public static final String PRODUCTID = "ProductID";
	public static String ORDER_NUMBER = "SelectedOrderNumber";
	public static String ITEM_CODE = "SelectedItemCode";
	public static String CUSTOMER_ID_SEARCHED = "SelectedCustomerID";
	public static final String CURRENT_POPUP = "currentPopup";
	public static final String EMAIL_OBJECT = "emailObject";
	public static final int SEARCH_HISTORY_SIZE = 10;
	
	
	/* Configuration Parameters */
	public static final int CARET_WIDTH = 8;
	public static final int CARET_HEIGHT = 14;
	public static final float TABLEITEM_HEIGHT_FACTOR = 1.3f;
	public static final int ALL_ROWS = -1;
	public static final int ROWS_PER_PAGE = 19;
	public static final int DESC = 1;
	public static final int ASC = 0;
	public static boolean CLOSING = false;
	public static String DB_MULTIVAL_SEPERATOR = "|";
	public static String GENERIC_ORDER_COMPOSITE = "GenericOrderComposite";
	public static String GENERIC_INVENTORY_COMPOSITE = "GenericInventoryComposite";
	public static String GENERIC_CUSTOMER_COMPOSITE = "GenericCustomerComposite";
	public static String GENERIC_ADMIN_COMPOSITE = "GenericAdminComposite";
	public static final String DATE_FORMAT = "MM-dd-yyyy";
	public static final String GENERIC_AR_COMPOSITE = "GenericARComposite";
	public static final String SALESORDER_SELECTEDCUSTOMER = "selected_customer";
	public static final int GRACE = 10;
	public static final String SALESORDER_SELECTEDITEM = "selected_item";
	
	//Dialog parameters
	public static final boolean DIALOG_AUTOCLOSE_ENABLED = true;
	public static final long DIALOG_AUTOCLOSE_TIMEOUT = 2000;   //in ms
	//Payment Types
	public static String TOUNACCOUNTED = "To Unaccounted";
	public static String CASH = "Cash";
	public static String CREDIT_CARD = "Credit Card";
	public static String CHECK = "Check";
	public static String UNAPPLIED = "Unapplied";
	public static String UNACCOUNTED = "Unaccounted";
	
	//Hibernate SQL Operators
	public static final String OPR_EQ = "Equal To";
	public static final String OPR_GT = "Greater than";
	public static final String OPR_LT = "Less than";
	public static final String OPR_GE = "Greater than or equal to";
	public static final String OPR_LE = "Less than or equal to";
	public static final String OPR_NE = "Not equal to";
	public static final String OPR_LIKE = "Like";
	public static final String OPR_BTWN = "Between";	//Greater than or less than on Same attribute
	public static final String OPR_IS_TRUE = "Is True";
	public static final String OPR_IS_FALSE = "Is False";
	public static final String OPR_IN = "In";
	
	
	
	//User parameters
	
	public static final String ADMIN_USER = "admin";
	public static final String ADMIN_PASSWORD = "admin";
	public static final String LOGIN_SUCCESSFUL = "login_successful";
	public static final String ANONYMOUS_ID = "anonymous";
	public static final String ANONYMOUS_NAME = "Anonymous";
	
	public static final String ADD_SHIP_ADDRESS = "New Shipping Address";
	public static final String INPUT_DIALOG_VALUE = "Input Value";
	public static final String SALES_NOTES = "Sales Notes";
	public static final String SALES_OREDERS_TABNAME = "Sales Order";
	public static final String DELETE_NOTE = "Delete Existing Note";
	
	public static String SO_PRICE_CODE = "SelectedPriceCode";
	
	//Table Names
	public static String TABLE_SO_SHIP_PICK = "SalesOrderShipPickTable";
	public static String TABLE_SO_QUOTES = "SalesOrderQuotesTable";
	public static String TABLE_SO_ORDER_LOG = "SalesOrderOrderLogTable";
	public static String TABLE_SO_ITEM_ENTRY = "SalesOrderItemTable";
	public static String TABLE_SO_AR_HISTORY = "SalesOrderARHistoryTable";
	public static String TABLE_SO_OPEN_ARS = "SalesOrderOpenARsTable";
	public static String TABLE_SO_ITEM_HISTORY = "SalesOrderItemHistoryTable";
	public static String TABLE_IL_INVOICE_LOG = "InvoiceLogInvoiceLogTable";
	public static String TABLE_AR_ARSEARCH = "ARSearchTable";
	public static String TABLE_OIR_SEARCH_RESULT = "OrderInquireySearchResult";
	public static String TABLE_SO_CUSTOMER_SEARCH_RESULT = "SalesOrderCustomerSearchResult";
	
	//Printer configuration
	public static final String PRINTER_NAME = "printerName";
	public static String PRINT_SPOOLER = "Print Spooler";
	public static final int PRINT_ALL_PAGES = 1000;   // NV - seems 1000 pages are OK to have when noOfPages not known 
	public static final String REMEMBER_PRINTER_NAME = "rememberPrinterName";
	
	//Transaction log constants
	public static String TX_CRASHED_OPEN = "Open";
	public static String TX_CRASHED_RECOVERED = "Recovered";
	public static String TX_CRASHED_IGNORED = "Unrecovered";
	public static String TX_ITEM_STATUS_DELETED = "Deleted";
	public static String TX_ITEM_STATUS_ACTIVE = "Active";
	public static String TX_SALESORDER_DELETEDITEM = "DeltedSalesItem";

	//Order Entry - SalesOrder
	public static String SO_BARCODE_MAPPED_PRODUCTID = "SalesOrderBarcodeProductIDMapping";
	public static String SO_FIRST_TIME_IN_CURRENT_SESSION = "SalesOrderFirstTimeInCurrentSession";
	
	//Order Status
	/*public static final String OPEN_ORDER = "Open";
	public static final String PRINTED_ORDER = "Printed";
	public static final String PACKING_SLIP_PRINTED = "Packing Slip Printed";
	public static final String LOADED_ON_TRUCK = "Loaded On Truck";
	public static final String ORDER_SHIPPED = "Shipped";
	public static final String ORDER_INVOICED = "Invoiced";
	public static final String ORDER_PARTIALLY_SHIPPED = "Partially Shipped";
	public static final String ORDER_PARTIALLY_INVOICED = "Partially Invoiced";
	public static final String ORDER_PTLY_SHIPPED_PTLY_INVOICED = "Partially Shipped, Partially Invoiced";
	public static final String ORDER_SHIPPED_PTLY_INVOICED = "Shipped, Partially Invoiced";*/
	
	public enum OrderStatus {
		OPEN("Open"), PRINTED("Printed"), PACKING_SLIP_PRINTED("Packing Slip Printed"),
		LOADED_ON_TRUCK("Loaded on Truck"), SHIPPED("Shipped"), INVOICED("Invoiced"), PARTIALLY_SHIPPED("Partially Shipped"),
		PTLY_SHIPPED_PTLY_INVOICED("Partially Shipped, Partially Invoiced"),
		SHIPPED_PTLY_INVOICED("Completely Shipped, Partially Invoiced");
		public String val;
		private OrderStatus(String val) {
			this.val = val;
		}
		
		public String getValue() {
			return val;
		}
	}
	
	//Constants for Message Board information in Sales Order (Order Entry)
	public final static int MB_LAST_ORDER_DETAILS = 0;
	public final static int MB_ITEM_INFORMATION = 1;
	public final static int MB_ITEM_PRICE_INFO = 2;
	
	//Constants for Notes on Sales Items
	public static final String NOTES = "notes";
	public static final String NOTES_MAP_KEY = "notes_map_key";
	public static final String NOTES_MAP_COL =  "notes_map_col";
	
	public static final String ITEM_ADDED_FROM_SALES_ENTRY = "ItemAddedFromSalesEntry";
	public static final String LOGOUT = "Logout";
	public static boolean TRAY_ICON_EXISTS = false;
	public static boolean SWALLOW_MOUSE_CLICKS = false;
	public static boolean INVALID_VALUE_IN_TABLE = false;
	public static final String CUSTOMER_EDITED = "CustomerEditedFromOtherScreen";
	public static final String SELECTED_OPTION = "SelectedOption";
	public static final String LOWER_TABLE = "LowerTable";

	//Email Configuration
	public static final String EMAIL_FROM = "oesuser@gmail.com";
	public static final String EMAIL_PWD = "oesuser1234";
	public static final String EMAIL_SUBJECT = StringPool.BLANK;
	public static final String EMAIL_CONTENT = StringPool.BLANK;
	public static final String EMAIL_ATTACH_PATH = StringPool.BLANK;
	//Tooltip configuration
	public static final long TOOLTIP_AUTOHIDE_TIMEOUT = 3000;
	
	public static final int EDITABLE_CELL_BGCOLOR_INACTIVE = SWT.COLOR_GREEN;
	//Audit
	public static final String CREATE_FILE_AUDIT = "createFileAudit";
	public static final String REMEMBER_CREATE_FILE_AUDIT = "rememberCreateFileAudit";
	//Reports
	public static final String REPORT_INPUT_FIELDS = "Report Input Fields";
	public static final String REPORT_OUTPUT_FIELDS = "Report Output Fields";
	public static final String REPORT_BASE_TABLE = "Report Base Table";
	public static final String REPORT_FROM_SAVED_REPORTS = "From Saved Reports";
	public static final String REPORT_TITLE = "Report Title";
	public static final String REPORT_SUMMARY_FIELDS = "Report Summary Fields";
	
	//Directories
	public static enum UserDirectory {
		BASE_DIRECTORY("OES"), PRINT_DIRECTORY("Prints"), EMAIL_DIRECTORY("Emails");
		String val;
		private UserDirectory(String c) {
			val = c;
		}
		
		public String getValue() {
			return val;
		}
	}
	
	//Delivery Type
	public static enum DeliveryType {
		PICKUP, SHIP, BOTH 
	};
	
	//Designations
	public static enum Designation {
		ADMIN("Administrator"), DRIVER("Driver"), PACKER("Packer"), COUNTERMAN("Counterman"), OTHER("Other") ;
		
		private String desig;
		private Designation(String c) {
			desig = c;
		}
		
		public String getValue() {
			return desig;
		}
		
		public static String[] getValues() {
			String retVal[] = new String[values().length];
			int i = 0;
			for (Designation desig : values()) {
				retVal[i++] = desig.getValue();
			}
			return retVal;
		}
	};
	
	
	public static enum PrintModules {
		ORDER("Order"), INVOICE("Invoice"), PACKINGSLIP("Packing Slip") ;
		
		private String printModule;
		private PrintModules(String c) {
			printModule = c;
		}
		
		public String getValue() {
			return printModule;
		}
		
		public static String[] getValues() {
			String retVal[] = new String[values().length];
			int i = 0;
			for (PrintModules desig : values()) {
				retVal[i++] = desig.getValue();
			}
			return retVal;
		}
	};
	
	public static enum Templates {
		ORDER("Order"), INVOICE("Invoice"), PACKINGSLIP("Packing Slip"), DELIVERY("Delivery") ;
		
		private String printModule;
		private Templates(String c) {
			printModule = c;
		}
		
		public String getValue() {
			return printModule;
		}
		
		public static String[] getValues() {
			String retVal[] = new String[values().length];
			int i = 0;
			for (Templates desig : values()) {
				retVal[i++] = desig.getValue();
			}
			return retVal;
		}
	};
	
	//Constants used for QuickBooks
	public static final String STOP_ON_ERROR = "stopOnError";
	public static final String COUNTRY = "USA";
	public static final String QB_FILE_PATH = "QBCompanyFilePath";
	public static final String COGS = "Cost of Goods Sold";
	public static final String QB_EMPTY_ORDER = "ERROR: Empty Order";
	public static final String QB_INVALID_PAYMENT = "ERROR: Invalid Payment";
	public static enum QBSyncStatus {
		PENDING("Insert Pending"), UPDATE_PENDING("Update Pending"), SYNCHED("Synchronized"), UPDATE_SYNCHED("Update Synchronized"), SYNCH_FAILED("Synchronization Failed"), SYNC_IGNORED("Synchronization Ignored");
		String val;
		private QBSyncStatus(String c) {
			val = c;
		}
		
		public String getValue() {
			return val;
		}
	}
	
	public static enum InventoryTableCols {
		SL_NO(0), ITEM_CODE(1), ITEM_DESC(2), ON_HAND(3), WH_1(4), WH_2(5), AVAILABLE(6), LIST_PRICE(7), ON_ORDER(8), PRICE_A(9),
		PROD_CAT(10), MAIN_CAT(11), SUB_CAT(12), JAN(13), FEB(14), MAR(15), APR(16), MAY(17), JUN(18), JUL(19), AUG(20), SEP(21),
		OCT(22), NOV(23), DEC(24);
		public int val;
		private InventoryTableCols(int x){
			val = x;
		}
	};
}
