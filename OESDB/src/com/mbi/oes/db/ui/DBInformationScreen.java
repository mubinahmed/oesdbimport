package com.mbi.oes.db.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.PopupList;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.hibernate.exception.ExceptionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.mbi.oes.db.controls.CustomIntegerBox;
import com.mbi.oes.db.controls.CustomLabelAsField;
import com.mbi.oes.db.controls.CustomPasswordBox;
import com.mbi.oes.db.controls.CustomTextBox;
import com.mbi.oes.db.controls.Label;
import com.mbi.oes.db.utils.ImportSession;
import com.mbi.oes.listeners.PageChangedListener;
import com.oes.db.model.util.BasicDAO;
import com.oes.db.model.util.DBProfile;
import com.oes.db.model.util.HibernateUtil;

public class DBInformationScreen extends GenericDataImportScreen{
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
	private ProgressComposite progressComposite;
	private Composite detailsComposite;
	private org.eclipse.swt.widgets.Label lblNewLabel;
	private CustomTextBox txtDBServerIP;
	private org.eclipse.swt.widgets.Label lblNewLabel_1;
	private CustomTextBox comboDatabaseName;
	private org.eclipse.swt.widgets.Label lblNewLabel_2;
	private CustomIntegerBox txtPortNumber;
	private org.eclipse.swt.widgets.Label lblNewLabel_3;
	private CustomTextBox txtUserName;
	private org.eclipse.swt.widgets.Label lblNewLabel_4;
	private CustomPasswordBox txtPassword;
	protected Logger logger_ = Logger.getLogger(DBInformationScreen.class);

	private Control[] controlsOrder ;
	private Button btnSaveProfile;
	private Button btnLoadProfile;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public DBInformationScreen(Composite parent, int style) {
		super(parent, style);
		initComponents();
	}
	private void initComponents() {
		createFirstProfile();
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
		
		progressComposite = new ProgressComposite(tableComposite, SWT.NONE, 0);
		progressComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		progressComposite.setLayout(new GridLayout(1, false));
		
		centerComposite = new Composite(tableComposite, SWT.BORDER);
		centerComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		centerComposite.setLayout(new GridLayout(1, false));
		
		lblNewLabel_7 = new org.eclipse.swt.widgets.Label(centerComposite, SWT.NONE);
		lblNewLabel_7.setFont(SWTResourceManager.getFont("Traditional Arabic", 14, SWT.BOLD));
		lblNewLabel_7.setText("Database Information");
		
		lblNewLabel_8 = new org.eclipse.swt.widgets.Label(centerComposite, SWT.NONE);
		lblNewLabel_8.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_8.setFont(SWTResourceManager.getFont("Traditional Arabic", 11, SWT.BOLD));
		lblNewLabel_8.setText("Provide the details of database to which the data is being imported");
		
		detailsComposite = new Composite(centerComposite, SWT.NONE);
		detailsComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		detailsComposite.setLayout(new GridLayout(2, false));
		
		lblNewLabel = new org.eclipse.swt.widgets.Label(detailsComposite, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		lblNewLabel.setText("Database Server IP/Name:");
		
		txtDBServerIP = new CustomTextBox(detailsComposite, SWT.BORDER | SWT.SINGLE);
		GridData gd_txtDBServerIP = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtDBServerIP.widthHint = 170;
		txtDBServerIP.setLayoutData(gd_txtDBServerIP);
		
		lblNewLabel_1 = new org.eclipse.swt.widgets.Label(detailsComposite, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		lblNewLabel_1.setText("Database Name:");
		
		comboDatabaseName = new CustomTextBox(detailsComposite, SWT.BORDER | SWT.SINGLE);
		GridData gd_comboDatabaseName = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_comboDatabaseName.widthHint = 175;
		comboDatabaseName.setLayoutData(gd_comboDatabaseName);
		
		lblNewLabel_2 = new org.eclipse.swt.widgets.Label(detailsComposite, SWT.NONE);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setText("Port No:");
		
		txtPortNumber = new CustomIntegerBox(detailsComposite, SWT.BORDER  | SWT.SINGLE);
		txtPortNumber.setText("3306");
		GridData gd_txtPortNumber = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtPortNumber.widthHint = 73;
		txtPortNumber.setLayoutData(gd_txtPortNumber);
		
		lblNewLabel_3 = new org.eclipse.swt.widgets.Label(detailsComposite, SWT.NONE);
		lblNewLabel_3.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		lblNewLabel_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_3.setText("User Name:");
		
		txtUserName = new CustomTextBox(detailsComposite, SWT.BORDER | SWT.SINGLE);
		GridData gd_txtUserName = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtUserName.widthHint = 161;
		txtUserName.setLayoutData(gd_txtUserName);
		
		lblNewLabel_4 = new org.eclipse.swt.widgets.Label(detailsComposite, SWT.NONE);
		lblNewLabel_4.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		lblNewLabel_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_4.setText("Password:");
		
		txtPassword = new CustomPasswordBox(detailsComposite, SWT.BORDER | SWT.SINGLE);
		GridData gd_txtPassword = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtPassword.widthHint = 175;
		txtPassword.setLayoutData(gd_txtPassword);
		new org.eclipse.swt.widgets.Label(centerComposite, SWT.NONE);
		
		buttonsComposite = new Composite(centerComposite, SWT.NONE);
		buttonsComposite.setLayout(new GridLayout(4, false));
		
		btnNext = new Button(buttonsComposite, SWT.NONE);
		btnNext.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		GridData gd_btnNext = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnNext.widthHint = 83;
		btnNext.setLayoutData(gd_btnNext);
		btnNext.setText("Next>");
		
		btnSaveProfile = new Button(buttonsComposite, SWT.NONE);
		btnSaveProfile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnSaveProfile.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		btnSaveProfile.setText("Save Profile");
		
		btnLoadProfile = new Button(buttonsComposite, SWT.NONE);
		btnLoadProfile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnLoadProfile.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		btnLoadProfile.setText("Load Profile");
				
		btnCancel = new Button(buttonsComposite, SWT.NONE);
		btnCancel.setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		GridData gd_btnCancel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnCancel.widthHint = 78;
		btnCancel.setLayoutData(gd_btnCancel);
		btnCancel.setText("Cancel");
		
		addListeners();
		setNavigationRules();
	}

	private void setNavigationRules() {
		controlsOrder = new Control[] {
			txtDBServerIP, comboDatabaseName, txtPortNumber, txtUserName, txtPassword	
		};
		
		txtDBServerIP.addKeyListener(controlsOrder);
		comboDatabaseName.addKeyListener(controlsOrder);
		txtPortNumber.addKeyListener(controlsOrder);
		txtUserName.addKeyListener(controlsOrder);
		txtPassword.addKeyListener(controlsOrder);
	}
	
	private void addListeners() {
		
		btnSaveProfile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				try {
					if (validInput()) {
						if(saveProfile()) {
							clearScreen();
						}
					}
				} catch (SAXException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (TransformerException e1) {
					e1.printStackTrace();
				} catch (ParserConfigurationException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnLoadProfile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
					openPopUp();
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
				Properties p = new Properties();
				p.put("hibernate.connection.url", "jdbc:mysql://"+txtDBServerIP.getText()+":"+txtPortNumber.getText()+"/"+comboDatabaseName.getText());
				p.put("hibernate.connection.username", txtUserName.getText());
				p.put("hibernate.connection.password", txtPassword.getPassword());
				
				ImportSession.set("DBInformation", p);
				String status = HibernateUtil.reloadDBConfig(p);
				
				System.out.println("Password: "+txtPassword.getPassword());
				if(status.contains("Unknown database")) {
					boolean createDB = MessageDialog.openConfirm(DBInformationScreen.this.getShell(), "DB Error", "DB doesn't exist. You want to create?");
					if (createDB) {
						String dbcreatestatus = HibernateUtil.createDB(comboDatabaseName.getText(), txtDBServerIP.getText(), txtUserName.getText(),
								txtPassword.getPassword());
						if(!"SUCCESS".equalsIgnoreCase(dbcreatestatus)) {
							MessageDialog.openConfirm(DBInformationScreen.this.getShell(), "DB Create Error", "Error while creating the DB: "+dbcreatestatus);
							return;
						} else {
							status = HibernateUtil.loadDBConfig(p);
						}
					} else {
						return;
					}
				}
				
				if(!"Loaded".equalsIgnoreCase(status)) {
					MessageDialog.openError(DBInformationScreen.this.getShell(), "DB Error", "Invalid DB Information: "+status);
					return;
				}
				if (pageChangedListener != null) {
					pageChangedListener.nextPage();
				}
			}
		});
	}
	
	private void openPopUp() {
		Display display = Display.getDefault();
		Shell shell = new Shell(DBInformationScreen.this.getShell(), SWT.APPLICATION_MODAL);
		initializePopUpUI(shell);
		shell.setLocation(btnLoadProfile.toDisplay(0, 0));
		shell.layout();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	private void initializePopUpUI(final Shell shell) {
			shell.setSize(241, 221);
			shell.setLayout(new GridLayout(1, false));
			
			Composite c = new Composite(shell, SWT.NONE);
			GridData gd_c = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
			gd_c.widthHint = 225;
			gd_c.heightHint = 204;
			c.setLayoutData(gd_c);
			c.setLayout(new GridLayout(2, false));
			
			CustomLabelAsField txtItemID = new CustomLabelAsField(c, SWT.NONE);
			txtItemID.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
			txtItemID.setText("Select a profile to load");
			
			final Table profilesTable = new Table(c, SWT.BORDER | SWT.FULL_SELECTION);
			profilesTable.setHeaderVisible(true);
			GridData gd_priceVariationTable = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
			gd_priceVariationTable.widthHint = 200;
			profilesTable.setLayoutData(gd_priceVariationTable);
			
			TableColumn profilesList = new TableColumn(profilesTable, SWT.NONE);
			profilesList.setWidth(208);
			profilesList.setText("Saved Profiles");
			List<String> allProfiles = getAllProfileNames();
			for (String profileName : allProfiles) {
				TableItem ti = new TableItem(profilesTable, SWT.NONE);
				ti.setText(new String[] {profileName});
			}
			
			profilesTable.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					if(e.keyCode == 13) {
						String selectedProfile = profilesTable.getSelection()[0].getText(0);
						DBProfile profile = loadProfile(selectedProfile);
						fillInProfile(profile);
						shell.dispose();
					}
				}
			});
			
			profilesTable.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					super.widgetSelected(e);
					TableItem ti = (TableItem)e.item;
					DBProfile profile = loadProfile(ti.getText(0));
					fillInProfile(profile);
					shell.dispose();
				}
			});
			
	}
	
	private void fillInProfile(DBProfile profile) {
		if(profile != null) {
			txtDBServerIP.setText(profile.getDbIP());
			comboDatabaseName.setText(profile.getDbName());
			txtPortNumber.setText(profile.getDbPort());
			txtUserName.setText(profile.getDbUserName());
			txtPassword.setFocus();
		}
	}
	protected boolean validInput() {
		if(txtDBServerIP.getText().length() == 0 
				|| comboDatabaseName.getText().length() == 0
				|| txtPortNumber.getText().length() == 0
				|| txtUserName.getText().length() == 0) {
			return false;
		}
		return true;
	}
	
	
	private List<String> getAllProfileNames() {
		File profileFile = new File(System.getProperty("user.home") + File.separator + ".oesprofiles");
		if(!profileFile.exists()) {
			createFirstProfile();
			return null;
		}
		List<String> profileNames = new ArrayList<String>();
		try {
			
			DocumentBuilderFactory documentBuilderFactory = 
					DocumentBuilderFactory.newInstance();
					DocumentBuilder documentBuilder;
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		
				
				
		Document document = documentBuilder.parse(profileFile);
		NodeList rootElement = document.getElementsByTagName("Profile");
		for(int i = 0; i < rootElement.getLength(); i++) {
			Node node = rootElement.item(i);
			NodeList childNodes = node.getChildNodes();
			String thisProfileName = childNodes.item(0).getChildNodes().item(0).getTextContent();
			profileNames.add(thisProfileName);
		}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
		return profileNames;
	}
	
	private DBProfile loadProfile(String profileName)  {
		File profileFile = new File(System.getProperty("user.home") + File.separator + ".oesprofiles");
		if(!profileFile.exists()) {
			createFirstProfile();
			return null;
		}
		try {
		DocumentBuilderFactory documentBuilderFactory = 
				DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();;
		Document document;
		
			document = documentBuilder.parse(profileFile);
		
		NodeList rootElement = document.getElementsByTagName("Profile");
		for(int i = 0; i < rootElement.getLength(); i++) {
			Node node = rootElement.item(i);
			NodeList childNodes = node.getChildNodes();
			String thisProfileName = childNodes.item(0).getChildNodes().item(0).getTextContent();
			if (profileName.equalsIgnoreCase(thisProfileName)) {
				String dbIP = childNodes.item(1).getChildNodes().item(0)
						.getTextContent();
				String dbName = childNodes.item(2).getChildNodes().item(0)
						.getTextContent();
				String dbPort = childNodes.item(3).getChildNodes().item(0)
						.getTextContent();
				String dbUserName = childNodes.item(4).getChildNodes().item(0)
						.getTextContent();
				return new DBProfile(dbIP, dbName, dbPort, dbUserName, thisProfileName);
			}
		}
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void clearScreen() {
		txtDBServerIP.clear();
		comboDatabaseName.clear();
		txtPassword.clear();
		txtUserName.clear();
		txtPortNumber.clear();
	}
	protected boolean saveProfile() throws SAXException, IOException, TransformerException, ParserConfigurationException {
		File profileFile = new File(System.getProperty("user.home") + File.separator + ".oesprofiles");
		if(!profileFile.exists()) {
			createFirstProfile();
		}
		String profileName = "";
		InputDialog inputDlg = new InputDialog(getShell(), "Profile Name", "Enter a name for the profile", "", null);
		if(inputDlg.open() == Window.OK) {
			profileName = inputDlg.getValue();
		} else {
			return false;
		}
		
		DocumentBuilderFactory documentBuilderFactory = 
				DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();;
		Document document = documentBuilder.parse(profileFile);
		NodeList rootElement = document.getElementsByTagName("Profiles");
		Node rootNode = rootElement.item(0);
		Element e = document.createElement("Profile");
		rootNode.appendChild(e);
				
		Element ele1 = document.createElement("ProfileName");
		e.appendChild(ele1);
		Text t1 = document.createTextNode(profileName);
		ele1.appendChild(t1);
		
		Element ele2 = document.createElement("DBIP");
		e.appendChild(ele2);
		Text t2 = document.createTextNode(txtDBServerIP.getText());
		ele2.appendChild(t2);
		
		Element ele3 = document.createElement("DbName");
		e.appendChild(ele3);
		Text t3 = document.createTextNode(comboDatabaseName.getText());
		ele3.appendChild(t3);
		
		Element ele4 = document.createElement("PortNo");
		e.appendChild(ele4);
		Text t4 = document.createTextNode(txtPortNumber.getText());
		ele4.appendChild(t4);
		
		Element ele5 = document.createElement("UserName");
		e.appendChild(ele5);
		Text t5 = document.createTextNode(txtUserName.getText());
		ele5.appendChild(t5);
		
		TransformerFactory transformerFactory = 
				  TransformerFactory.newInstance();
				  Transformer transformer = null;
		transformer = transformerFactory.newTransformer();
	
	  DOMSource source = new DOMSource(document);
	  
	  OutputStream os = new FileOutputStream(profileFile);
	  StreamResult result =  new StreamResult(os);
	
		transformer.transform(source, result);

		return true;
				
	}
	
	private void createFirstProfile() {
		
		File profileFile = new File(System.getProperty("user.home")+File.separator+".oesprofiles");
		if(profileFile.exists()) {
			return;
		} /*else {
			try {
				profileFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		DocumentBuilderFactory documentBuilderFactory = 
		DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = null;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			logger_.info(ExceptionUtils.getStackTrace(e));
			return;
		}
		
				try {
					Document document = documentBuilder.newDocument();
					Element rootElement = document.createElement("Profiles");
					document.appendChild(rootElement);
					
					TransformerFactory transformerFactory = 
							  TransformerFactory.newInstance();
							  Transformer transformer = null;
					transformer = transformerFactory.newTransformer();
				
				  DOMSource source = new DOMSource(document);
				  
				  OutputStream os = new FileOutputStream(profileFile);
				  StreamResult result =  new StreamResult(os);
				
					transformer.transform(source, result);

				} catch (TransformerException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	
	public void addPageChangeListener(PageChangedListener listener) {
		pageChangedListener = listener;
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
