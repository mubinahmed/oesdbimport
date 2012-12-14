package com.mbi.oes.db.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import com.mbi.oes.db.utils.DataTypes;
import com.mbi.oes.db.utils.ImportSession;
import com.mbi.oes.db.utils.OESUtils;
import com.mbi.oes.db.utils.PropsUtil;
import com.mbi.oes.db.utils.StringPool;
import com.mbi.oes.listeners.PageChangedListener;
import com.oes.db.model.util.HibernateUtil;

public class ColumnMappingScreen extends GenericDataImportScreen {
	private Composite rightComposite;
	private Composite buttonsComposite;
	private Button btnPrevious;
	private Button btnNext;
	private Button btnFinish;
	private Button btnCancel;
	private Composite titleComposite;
	private Label lblNewLabel_1;
	private Label lblNewLabel_2;
	private Label lblNewLabel_3;
	private ScrolledComposite columnMappingComposite;
	private Composite dropDownComposite;
	private java.util.List<Combo> allCombos = new java.util.ArrayList<Combo>();
	private Map<String, String> mappedCols = new HashMap<String, String>();
	private List<String> mappedCSVColsList = new ArrayList<String>();
	
	private Composite headingComposite;
	private com.mbi.oes.db.controls.Label label_1;
	private PageChangedListener pageChangedListener;
	private Composite centerComposite;
	private Composite composite_1;
	private Label label_9;
	private Label label_10;
	private Logger logger_ = Logger.getLogger(ColumnMappingScreen.class);
	private ArrayList<String> mappedDBColsList;

	private Map<String, String> dataTypes = new HashMap<String, String>();
	private ProgressComposite progressComposite;
	private List<String> unsuccessfulParsedRows = new ArrayList<String>();
	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public ColumnMappingScreen(Composite parent, int style) {
		super(parent, style);
		initComponents();
	}

	private void initComponents() {
//		HibernateUtil.loadDBConfig(); //This may be the first place where we need to talk with DB. So load the config here.
		setLayout(new GridLayout(1, true));

		headingComposite = new Composite(this, SWT.NONE);
		headingComposite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER,
				false, false, 1, 1));
		headingComposite.setLayout(new GridLayout(1, false));

		label_1 = new com.mbi.oes.db.controls.Label(headingComposite, SWT.NONE);
		label_1.setText("OES Data Import");
		label_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		label_1.setFont(SWTResourceManager.getFont("Vijaya", 24, SWT.BOLD));

		centerComposite = new Composite(this, SWT.NONE);
		GridData gd_centerComposite = new GridData(SWT.FILL, SWT.FILL, false,
				false, 1, 1);
		gd_centerComposite.heightHint = 373;
		centerComposite.setLayoutData(gd_centerComposite);
		centerComposite.setLayout(new GridLayout(2, false));
		
		progressComposite = new ProgressComposite(centerComposite, SWT.NONE, 4);
		progressComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));
		progressComposite.setLayout(new GridLayout(1, false));

		rightComposite = new Composite(centerComposite, SWT.BORDER);
		GridData gd_rightComposite = new GridData(SWT.FILL, SWT.FILL, true,
				false, 1, 1);
		gd_rightComposite.heightHint = 360;
		rightComposite.setLayoutData(gd_rightComposite);
		rightComposite.setLayout(new GridLayout(1, true));

		titleComposite = new Composite(rightComposite, SWT.NONE);
		titleComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false, 1, 1));
		titleComposite.setLayout(new GridLayout(1, false));

		lblNewLabel_1 = new Label(titleComposite, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Traditional Arabic",
				14, SWT.BOLD));
		lblNewLabel_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false, 1, 1));
		lblNewLabel_1.setText("Column Mapping");

		lblNewLabel_2 = new Label(titleComposite, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("Traditional Arabic",
				11, SWT.BOLD));
		lblNewLabel_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false, 1, 1));
		lblNewLabel_2.setText("Map columns from input CSV file to Database");

		lblNewLabel_3 = new Label(titleComposite, SWT.NONE);
		
		composite_1 = new Composite(rightComposite, SWT.NONE);
		GridData gd_composite_1 = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1);
		gd_composite_1.widthHint = 461;
		composite_1.setLayoutData(gd_composite_1);
		GridLayout gl_composite_1 = new GridLayout(2, false);
		gl_composite_1.horizontalSpacing = 50;
		composite_1.setLayout(gl_composite_1);
		
		label_9 = new Label(composite_1, SWT.NONE);
		label_9.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		label_9.setText("Column in Excel");
		label_9.setFont(SWTResourceManager.getFont("Tahoma", 14, SWT.NORMAL));
		
		label_10 = new Label(composite_1, SWT.NONE);
		label_10.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1));
		label_10.setText("Column in DB");
		label_10.setFont(SWTResourceManager.getFont("Tahoma", 14, SWT.NORMAL));
		columnMappingComposite = new ScrolledComposite(rightComposite,
				SWT.V_SCROLL);
		columnMappingComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				true, true, 1, 1));
		columnMappingComposite.setAlwaysShowScrollBars(true);
		dropDownComposite = new Composite(columnMappingComposite, SWT.NONE);
		GridLayout gl_columnMappingComposite = new GridLayout(2, false);
		gl_columnMappingComposite.horizontalSpacing = 20;
		dropDownComposite.setLayout(gl_columnMappingComposite);
		dropDownComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				true, 1, 1));

		Label lcombo = new Label(dropDownComposite, SWT.NONE);
		lcombo.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false, 1,
				1));
		lcombo.setText("Column in Excel");
		lcombo.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.BOLD));
		lcombo.setVisible(false);

		Label lcombo_1 = new Label(dropDownComposite, SWT.NONE);
		lcombo_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, false,
				1, 1));
		lcombo_1.setText("Column in DB");
		lcombo_1.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.BOLD));
		lcombo_1.setVisible(false);

		dropDownComposite.setSize(SWT.DEFAULT, 26 * 50);
		columnMappingComposite.setContent(dropDownComposite);
		columnMappingComposite.setExpandHorizontal(true);
		columnMappingComposite.setMinHeight(400);

		columnMappingComposite.layout();
		dropDownComposite.layout();

		buttonsComposite = new Composite(this, SWT.NONE);
		buttonsComposite.setLayout(new GridLayout(8, true));
		buttonsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false, 7, 1));

		btnPrevious = new Button(buttonsComposite, SWT.NONE);
		btnPrevious.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		btnPrevious.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				false, 1, 1));
		btnPrevious.setText("   < Back   ");

		btnNext = new Button(buttonsComposite, SWT.NONE);
		btnNext.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		btnNext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1,
				1));
		btnNext.setText("Next >");
		

		btnFinish = new Button(buttonsComposite, SWT.NONE);
		btnFinish.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		btnFinish.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false,
				1, 1));
		btnFinish.setText("Finish");

		btnCancel = new Button(buttonsComposite, SWT.NONE);
		btnCancel.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false,
				1, 1));
		btnCancel.setText("Cancel");
		new Label(buttonsComposite, SWT.NONE);
		new Label(buttonsComposite, SWT.NONE);
		new Label(buttonsComposite, SWT.NONE);
		new Label(buttonsComposite, SWT.NONE);
		
		addDropDownsDynamically();
		addListeners();
		resolveFieldsDataTypes();
	}

	private void resolveFieldsDataTypes() {
		try {
			Class c = Class.forName((String) ImportSession.get("TABLE_NAME"));
			Field fields[] = c.getDeclaredFields();
			for (Field field : fields) {
				dataTypes.put(field.getName(), field.getType().getName());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
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
				reMapCombos();
				prepareImportData();
				for (Iterator<Combo> iterator = allCombos.iterator(); iterator
						.hasNext();) {
					Combo combo = iterator.next();
					combo.dispose();
				}
				if (pageChangedListener != null) {
					ImportSession.set("FAILED_ROWS", unsuccessfulParsedRows);
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

	protected void reMapCombos() {
		for (int i = 1; i < allCombos.size(); i += 2) {
			final Combo dbCombo = allCombos.get(i);
			final Combo excelCombo = allCombos.get(i - 1);
			mappedCols.put(dbCombo.getText(), excelCombo.getText()); // Use dbColumn Name as key - NV
		}
		ImportSession.set("MAPPED_COLUMNS", mappedCols);

	}

	private void prepareImportData() {
		logger_.info("importData ENTRY: "+mappedCols);
		String entityName = (String) ImportSession.get("TABLE_NAME");
		logger_.info("Mapped Columns "+mappedCols);
		logger_.info("Mapped Columns Size: "+mappedCols.size());
		mappedCSVColsList = new ArrayList<String>(mappedCols.values());
		mappedDBColsList = new ArrayList<String>(mappedCols.keySet());
		mappedDBColsList.remove(StringPool.BLANK);
		logger_.info("Mapped Cols List: "+mappedCSVColsList);
		logger_.info("Mapped DB Cols List: "+mappedDBColsList);
		
		List<String> data = new ArrayList<String>();
		try {
			String fileName = (String) ImportSession.get("FILE_PATH");
			File f = new File(fileName);
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String head = br.readLine();
			//int csvColumnCount = head.split(StringPool.COMMA).length;
			int csvColumnCount = head.split("\\|").length;
			logger_.info("File Size : " + f.length());
			String str = "";
			long current_time = System.currentTimeMillis();
			
			Map<String, Integer> indexOfColsInCSV = new HashMap<String, Integer>();
			indexOfColsInCSV = generateCSVColumnsIndexes(head);
			logger_.info("Index Of Cols in CSV: "+indexOfColsInCSV);
			int lineNoInCSV = 0;
			while ((str = br.readLine()) != null) {
				lineNoInCSV++;
				String unmodifiedLine = new String(str);
				StringBuilder buffer = new StringBuilder(9999);

				/*while (str.indexOf(",,") != -1) {
					str = str.replaceAll(",,", ",|,");
				}
				if(str.endsWith(StringPool.COMMA)) {
					str = str + StringPool.PIPE;
				}*/
				
				while (str.indexOf("\\|\\|") != -1) {
					str = str.replaceAll("\\|\\|", ",yx,");
				}
				//str = str.replaceAll(",|[ ]*|,", ",yx,");
				
				if(str.endsWith("\\|")) {
					str = str + "yx";
				}
				
				/*int singleQuotesCount = StringUtils.countMatches(str, StringPool.APOSTROPHE);
				if(singleQuotesCount == 1) {
					str = str.replace(StringPool.APOSTROPHE, StringPool.BLANK);
				}else if(singleQuotesCount%2 == 1) {
					unsuccessfulParsedRows.add("# "+lineNoInCSV+" -- " + unmodifiedLine);
					continue;
				} *///TODO Can we handle even single quotes, but in different columns?

				//"([^"]*)"|'([^']*)'|([^"',]+) -- Regex being used as of now
				/*String regex = "\"([^\"]*)\"|'([^']*)'|([^\"',]+)";

			    Matcher m = Pattern.compile(regex).matcher(str);
			    StringBuffer b = new StringBuffer();
				while(m.find()) {
					String value = m.group(1); //Group 1 contains the field value enclosed in double quotes
					if(StringUtils.isNotBlank(value)) {
						value = value.replaceAll(StringPool.COMMA, StringPool.BLANK); //Remove the commas present within double quotes
					}
					if(StringUtils.isBlank(value)) {
						value = m.group(2); //Group 2 contains the field value enclosed in single quotes
					}
					if(StringUtils.isBlank(value)) {
						value = m.group(3); //Group 3 contains the field value that is neither enclosed in single nor in double quotes
					}
					b.append(value).append("JAFFA"); //Use a Custom Delimiter so that its easy to tokenize later
				}*/
				
				//String thisLineTokens[] = b.toString().split("JAFFA");
				String thisLineTokens [] = str.split("\\|");
				boolean firstRowCaptured = false;
				if(thisLineTokens.length != csvColumnCount) {
					/* thisLineTokens.length > csvColumnCount when there is a value with multiple consecutive double or single quotes in it.
					 * Example - 2,4443,2132,abcd, "dk,de",""""Dont Mail""""
					 * thisLineTokens.length < csvColumnCount when there are even number of single quotes (apostrophes) in a line but in different fields.
					 */
					 if(!firstRowCaptured) {
						 logger_.info("Line contains more columns than the header "+thisLineTokens.length+" "+csvColumnCount);
						 logger_.info("Line No in CSV: "+lineNoInCSV+" - "+unmodifiedLine);
						 firstRowCaptured = true;
					 }
					unsuccessfulParsedRows.add("# "+lineNoInCSV+" -- " + unmodifiedLine);
					continue;
				}
				for (String colName : mappedDBColsList) {
					if (StringUtils.isNotBlank(colName)) {
						String value = StringPool.BLANK;
						try {
							value = thisLineTokens[indexOfColsInCSV
									.get(colName.trim())].trim();
							if(value.startsWith("-")) {
								String temp = value.substring(1).trim();
								value = "-"+temp;
							}
						} catch (Exception e) {
							//logger_.info("Exception: "+value+" "+colName+" "+indexOfColsInCSV);
						}
						if("yx".equalsIgnoreCase(value) || StringPool.BLANK.equalsIgnoreCase(value)) {
							value = getDefaultValue(colName);
						}
						buffer.append(value)
								.append(StringPool.PIPE);
					}
				}
				
				if (buffer.length() > 1) {
					buffer.deleteCharAt(buffer.length() - 1);
				}
				data.add(buffer.toString());
			}
			ImportSession.set("ROWS_TO_BE_IMPORTED", lineNoInCSV - 1);
			
			
			long end_time = System.currentTimeMillis();

			logger_.info("Total time for reading and Transforming: "
					+ (end_time - current_time));
			br.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String columnNameCSV = OESUtils.toCSVString(mappedDBColsList,
				StringPool.COMMA);
		
		String tableName = HibernateUtil.getTableNameFromEntity(entityName);
		String fileName = PropsUtil.normalize(FileUtils.getUserDirectoryPath()) + "/" + tableName + ".csv";

		try {
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter bw = new BufferedWriter(fw);
			
			//bw.write(columnNameCSV + StringPool.NEW_LINE); //No need of header row in the processed CSV file
			long current_time = System.currentTimeMillis();
			for (String str1 : data) {
				bw.write(str1 + StringPool.NEW_LINE);
			}
			long end_time = System.currentTimeMillis();
			logger_.info("Total time for writing: "
					+ (end_time - current_time));
			bw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		data.clear();
		Runtime.getRuntime().gc();
		// load CSV to database
		String query = "LOAD DATA LOCAL INFILE '"
				+ fileName
				+ "'  REPLACE  INTO TABLE "
				+ tableName
				+ " FIELDS  TERMINATED BY \"|\"  "
				+ "(" + columnNameCSV + ")" 
				+ ";";

		logger_.info("Data Load Query: "+query);

		ImportSession.set("DATA_LOAD_QUERY", query);
		//Moving the actual loading to next page
		/*logger_.info("Loading data to database");
		long start_time = System.currentTimeMillis();

		
		HibernateUtil hib_util = HibernateUtil.getInstance();
		Session session = hib_util.currentSession(HibernateUtil.MY_SQL);
		session.beginTransaction();
		int result = session.createSQLQuery(query).executeUpdate();
		logger_.info("Result: "+result);
		session.getTransaction().commit();
		long end_time = System.currentTimeMillis();
		logger_.info("Total time  = " + (end_time - start_time));*/
	
	}
	private String getDefaultValue(String colName) {
		String dataType = dataTypes.get(colName);
		
		switch(DataTypes.getDataType(dataType)){
		case DataTypes.STRING:
			return StringPool.BLANK;

		case DataTypes.BOOL:
		case DataTypes.BOOLEAN:
			return "false";

		case DataTypes.BYTE:
		case DataTypes.BYTE_P://TODO
			break;
			
		case DataTypes.CHAR:
		case DataTypes.CHARACTER:
			return StringPool.BLANK;

		case DataTypes.DATE:
			return new java.util.Date()+"";

		case DataTypes.FLOAT:
		case DataTypes.FLOAT_P:
		case DataTypes.DOUBLE:
		case DataTypes.DOUBLE_P:
			return "0.0";
			
		case DataTypes.INT:
		case DataTypes.INTEGER:
		case DataTypes.SHORT:
		case DataTypes.SHORT_P:
		case DataTypes.LONG:
		case DataTypes.LONG_P:
			return "0";
	}
		
		return StringPool.BLANK;
	}

	private Map<String, Integer> generateCSVColumnsIndexes(String csvHeader) {
		Map<String, Integer> indexMap = new HashMap<String, Integer>();
		//String tokens[] = csvHeader.split(",");
		String tokens[] = csvHeader.split("\\|");
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = tokens[i].trim();
			String dbColName = getDBColumnName(tokens[i]);
			if (StringUtils.isNotBlank(dbColName)) {
				indexMap.put(dbColName, new Integer(i));
			}
		}
		return indexMap;
	}

	private String getDBColumnName(String colName) {
		//logger_.info("getDBColumnName ENTRY: "+colName);
		for (String key : mappedCols.keySet()) {
			String val = mappedCols.get(key);
			//logger_.info("Comparing: "+key+": "+val);
			if(StringUtils.isNotBlank(val) && val.equalsIgnoreCase(colName)) {
				return key;
			}
		}
		return StringPool.BLANK;
	}

	private void addEventListeners() {

		for (int i = 1; i < allCombos.size(); i += 2) {
			final Combo dbCombo = allCombos.get(i);
			final Combo excelCombo = allCombos.get(i - 1);
			dbCombo.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (alreadyMapped(dbCombo)) {
						MessageDialog.openError(
								null,
								"Error",
								dbCombo.getText()
										+ " already mapped to other excel field");
						dbCombo.setText("");
					} else {
						// mappedCols.put(excelCombo.getText(),
						// dbCombo.getText());
						mappedCols.put(dbCombo.getText(), excelCombo.getText()); // Use
																					// dbColumn
																					// Name
																					// as
																					// key
																					// -
																					// NV
						ImportSession.set("MAPPED_COLUMNS", mappedCols);
					}
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}
			});
		}

	}

	private boolean alreadyMapped(Combo selectedCombo) {

		for (int j = 1; j < allCombos.size(); j += 2) {
			final Combo combo = allCombos.get(j);
			if (combo != selectedCombo
					&& combo.getText()
							.equalsIgnoreCase(selectedCombo.getText())) {
				return true;
			}
		}
		return false;
	}

	private void addDropDownsDynamically() {
		String tableName = (String) ImportSession.get("TABLE_NAME");
		logger_.info("Table Name: " + tableName);
		List<String> list = HibernateUtil.getTableColumns(tableName); // getTableColumns(tableName);
		String fields[] = list.toArray(new String[list.size()]);

		String excelHeaders[] = getHeaders(); 

		for (int i = 0; i < excelHeaders.length * 2; i++) {
			Combo combo = new Combo(dropDownComposite, SWT.READ_ONLY);
			combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
					false, 1, 1));
			allCombos.add(combo);
		}

		String fields1[] = new String[fields.length + 1];
		System.arraycopy(fields, 0, fields1, 1, fields.length);
		fields1[0] = "";

		for (int i = 0; i < excelHeaders.length * 2; i++) {
			Combo combo = allCombos.get(i);
			combo.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.BOLD));
			if (i % 2 == 0) {
				combo.setItems(new String[] { excelHeaders[i / 2] });
				combo.select(0);
			} else {
				combo.setItems(fields1);
				// set the combo selected if csv and db columns match
				int mathedIndex = getMatchedIndex(fields1, excelHeaders[i / 2]);
				if (mathedIndex != -1) {
					combo.select(mathedIndex);
				}
			}

		}

		addEventListeners();
		dropDownComposite.setSize(SWT.DEFAULT, 30 * excelHeaders.length + 50);
		columnMappingComposite.setMinSize(400, 30 * excelHeaders.length + 50);
		columnMappingComposite.layout();
		dropDownComposite.layout();
	}

	private int getMatchedIndex(String[] fields, String csvColName) {
		csvColName = csvColName.replace(" ", "");
		csvColName = csvColName.replace("-", "");
		for (int i = 0; i < fields.length; i++) {
			String str = fields[i].toLowerCase();
			if (str.contains(csvColName.toLowerCase())) {
				return i;
			}
		}
		return -1;
	}

	private String[] getHeaders() {
		String[] headers = null;
		try {
			String fileName = (String) ImportSession.get("FILE_PATH");
			System.out.println("Original File Path: "+fileName);
			FileReader fr = new FileReader(fileName);
			BufferedReader buf = new BufferedReader(fr);
			String head = buf.readLine();
			//headers = head.split(",");
			headers = head.split("\\|");
			System.out.println(headers.length);
			for(int i=0; i<headers.length; i++) {
				headers[i] = headers[i].trim();
			}
			ImportSession.set("headers", headers);
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return headers;
	}

	@Override
	protected void checkSubclass() {
	}

	@Override
	public void addPageChangeListener(PageChangedListener listener) {
		pageChangedListener = listener;
	}

}
