package com.mbi.oes.db.controls;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

import com.mbi.oes.db.utils.DataTypes;
import com.mbi.oes.db.utils.FormatUtil;
import com.mbi.oes.db.utils.OES;
import com.mbi.oes.db.utils.StringPool;
import com.mbi.oes.db.utils.Validator;


public class ItemEntryTable extends Table {
	private boolean initialized = false;
	private TableColumn[] initialTableColumns;
	List <String[]> myData = new ArrayList<String[]>();
	String colsRequired[];
	private boolean containsSlNo_ = true;
	protected int prevSelectedIndex = -1;
	private Menu headerMenu;
	private Menu tableMenu;
	private int EDITABLECOLUMN  = -1;
	private TableEditor editor;
	List<TableItemChangeListener> listeners = new ArrayList<TableItemChangeListener>();
	protected ItemEntryTableItem currentEditableRow;
	private boolean enableCellSelection;
	private String tableName;
	private CustomTextBox newEditor;
	private TableCursor cursor;
	private boolean editingMode = false;
	private TableEditorData editorData;
	private int currentSelectedRowNumber = -1;
	protected int checkedItemsCount = -1;
	private boolean editOnMouseEvent = false;
	private boolean listenersAdded = false;

	private static Logger logger_ = Logger.getLogger(ItemEntryTable.class);
	
	public ItemEntryTable(Composite parent, int style, String tableName) {
		super(parent, style);
		this.tableName = tableName;
		initComponents();
	}

	public ItemEntryTable(Composite parent, int style) {
		super(parent, style);
		initComponents();
	}

	protected void initComponents() {
		editor = new TableEditor(this);
		//The editor must have the same size as the cell and must
		//not be any smaller than 50 pixels.
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
	} 

	protected void itemChecked(Widget widget) {
		logger_.info("Item checked");
		ItemEntryTableItem item = (ItemEntryTableItem)widget;
		logger_.info("Item # "+item.rowNum);
		if(checkedItemsCount + 1 == getItemCount()) {
			checkAll(false);
		}
		removeCurrentInputField();
		TableItemChangeEvent event = new TableItemChangeEvent();
		event.rowNumber = item.rowNum;
		event.item = item;
		for (TableItemChangeListener ticl : listeners) {
			ticl.itemChecked(event);
		}
	}
	
	protected void itemUnchecked(Widget widget) {
		logger_.info("Item Unchecked");
		ItemEntryTableItem item = (ItemEntryTableItem)widget;
		logger_.info("Item # "+item.rowNum);
		unCheckSelectAll(false);
		TableItemChangeEvent event = new TableItemChangeEvent();
		event.rowNumber = item.rowNum;
		event.item = item;
		for (TableItemChangeListener ticl : listeners) {
			ticl.itemUnchecked(event);
		}
		removeCurrentInputField();
	}
	
	public void checkAll(boolean fireListeners) {
		if (fireListeners) {
			for (TableItemChangeListener ticl : listeners) {
				ticl.selectAllChecked();
			}
		}
		TableColumn colunmns[] = getColumns();
		for (TableColumn tableColumn : colunmns) {
			if(tableColumn instanceof ItemEntryTableColumn) {
				ItemEntryTableColumn col = (ItemEntryTableColumn)tableColumn;
				if (col.containsSelectAll()) {
					col.setImage(ResourceManager.getImageDescriptor(
							ItemEntryTable.class,
							"/com/oes/resources/icons/checked.png")
							.createImage());
					CustomTableColumnProperties props = col.getProperties();
					if(props != null){
						props.setSelectAllChecked(true);
					}
				}
			}
		}
		for (TableItem tableItem : getItems()) {
			tableItem.setChecked(true);
		}
		
		checkedItemsCount = getItemCount();
	}
	
	public void checkAll() {
		checkAll(true);
	}
	
	public void unCheckAll() {
		unCheckAll(true);
	}
	
	public void unCheckAll(boolean fireListeners) {

		if (fireListeners) {
			for (TableItemChangeListener ticl : listeners) {
				ticl.selectAllUnchecked();
			}
		}
		TableColumn colunmns[] = getColumns();
		for (TableColumn tableColumn : colunmns) {
			if(tableColumn instanceof ItemEntryTableColumn) {
				ItemEntryTableColumn col = (ItemEntryTableColumn)tableColumn;
				if (col.containsSelectAll()) {
					col.setImage(ResourceManager.getImageDescriptor(
							ItemEntryTable.class,
							"/com/oes/resources/icons/unchecked.png")
							.createImage());
				}
			}
		}
		for (TableItem tableItem : getItems()) {
			tableItem.setChecked(false);
		}
	
	}
	
	public void unCheckSelectAll(boolean fireListeners) {

		if (fireListeners) {
			for (TableItemChangeListener ticl : listeners) {
				ticl.selectAllUnchecked();
			}
		}
		TableColumn colunmns[] = getColumns();
		for (TableColumn tableColumn : colunmns) {
			if(tableColumn instanceof ItemEntryTableColumn) {
				ItemEntryTableColumn col = (ItemEntryTableColumn)tableColumn;
				if (col.containsSelectAll()) {
					col.setImage(ResourceManager.getImageDescriptor(
							ItemEntryTable.class,
							"/com/oes/resources/icons/unchecked.png")
							.createImage());
					CustomTableColumnProperties props = col.getProperties();
					if(props != null){
						props.setSelectAllChecked(false);
					}
				}
			}
		}
	}
	
	private void addListeners() {
		listenersAdded = true;
		addListener(SWT.MeasureItem, ListenerFactory.getListener(this));

		addMouseMoveListener(new MouseMoveListener() {
			
			@Override
			public void mouseMove(MouseEvent me) {
				//System.out.println("Mouse Location: ("+me.x+", "+me.y+")");
				getSelectedCell(me, false);
			}
		});
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				super.mouseDown(e);
				getSelectedCell(e, true);
			}
		});
		
		addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				logger_.info("Table item selected");
				if(e.detail == SWT.CHECK) {
					logger_.info("CHECK event fired on table");
					int checkedItems = countCheckedItems();
					if(checkedItemsCount  > checkedItems) {
						itemUnchecked(e.item);
					} else {
						itemChecked(e.item);
					}
					checkedItemsCount = checkedItems;
				} else {
					/*for (ItemEntryTableItem ti : getItems()) {
						for (int i = 1; i < getColumnCount(); i++) {
							ti.setImage(i, SWTResourceManager.getImage(
									ItemEntryTable.class,
									"/com/mbi/oes/db/resources/space.png"));
						}
					}
					for (int i = 1; i < getColumnCount(); i++) {
						((TableItem) e.item).setImage(i, SWTResourceManager.getImage(
								ItemEntryTable.class,
								"/com/mbi/oes/db/resources/down.png"));
					}*/
				}
			}
		});
		
		
		addListener(SWT.MenuDetect, new Listener() {
			@Override
			public void handleEvent(Event e) {
				Point pt = getShell().getDisplay().map(null, ItemEntryTable.this, new Point(e.x, e.y));
				Rectangle clientArea = ItemEntryTable.this.getClientArea();
				boolean header = clientArea.y <= pt.y && pt.y < (clientArea.y + ItemEntryTable.this.getHeaderHeight());

				if (header) {
					if(tableMenu != null){
						tableMenu.setVisible(false);
					}
					ItemEntryTable.super.setMenu(headerMenu);
					headerMenu.setLocation(e.x, e.y);
					headerMenu.setVisible(true);
					e.doit = false;
				} else {
					headerMenu.setVisible(false);
					ItemEntryTable.super.setMenu(tableMenu);
					if(tableMenu != null){
						tableMenu.setLocation(e.x, e.y);
						tableMenu.setVisible(true);
					}
				}
			}
		});
		
		/*addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				currentSelectedRowNumber = -1;
			}
		});*/
		
		if (editOnMouseEvent) {
			
		}
	}
	
	/**
	 * Makes the Current row first editable cell editable.
	 * @param row
	 * @param column
	 */
	public void makeCellEditable(int row, int column) {
		
		if(row == -1 || row == getItemCount()) {
			return;
		}
		currentSelectedRowNumber = row;
		/*if(column == -1) {
			column = editorData.getCell(0);
		}*/
		setFocus();

		ItemEntryTableItem item = null;
		try {
			item = getItem(row);
		} catch (Exception e) {
			logger_.error(ExceptionUtils.getStackTrace(e));
			e.printStackTrace();
			return;
		}
		setSelection(item);
		EDITABLECOLUMN = column;
		makeCellEditable(item, row, column);
	}

	private void makeCellEditable(final ItemEntryTableItem item, final int row, final int column) {
		final TableItemChangeEvent ticEvent = new TableItemChangeEvent();
		final int colDataType = editorData != null ? editorData.getType(column) : DataTypes.STRING;
		currentSelectedRowNumber = row;
		
		logger_.info("row: "+row+" col: "+column+" datatype: "+colDataType);
		for(TableItemChangeListener ticl : listeners) {
			TableItemChangeEvent ticStartEvent = new TableItemChangeEvent();
			ticStartEvent.detail = column;
			logger_.info("Firing start item change listener. "+column);
			ticl.itemStart(ticStartEvent);
		}

		switch(colDataType) {
		case DataTypes.INT:
		case DataTypes.INTEGER:
			newEditor = new CustomIntegerBox(ItemEntryTable.this, SWT.BORDER | SWT.SINGLE);
			break;
		case DataTypes.FLOAT:
		case DataTypes.FLOAT_P:
			newEditor = new CustomFloatBox(ItemEntryTable.this, SWT.BORDER | SWT.SINGLE);
			//((CustomFloatBox)newEditor).setRange(from, to);
			break;
		default:
			newEditor = new CustomTextBox(ItemEntryTable.this, SWT.BORDER | SWT.SINGLE);
		}

		newEditor.setFont(SWTResourceManager.getFont("Tahoma", 10, SWT.BOLD));
		//logger_.info("Editable Column : "+EDITABLECOLUMN);
		newEditor.setText(item.getText(EDITABLECOLUMN));
		newEditor.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent me) {
				CustomTextBox text;
				switch(colDataType) {
				case DataTypes.INT:
				case DataTypes.INTEGER:
					text = (CustomIntegerBox)editor.getEditor();
					break;
				case DataTypes.FLOAT:
				case DataTypes.FLOAT_P:
					text = (CustomFloatBox)editor.getEditor();
					break;
				default:
					text = (CustomTextBox)editor.getEditor();
				}
				String val = text.getText();
				switch(colDataType) {
				case DataTypes.INT:
				case DataTypes.INTEGER:
					editor.getItem().setText(EDITABLECOLUMN, FormatUtil.format(new Integer(Integer.parseInt(val.trim().length() == 0 ? "0" : val))));
					break;
				case DataTypes.FLOAT:
				case DataTypes.FLOAT_P:
					String str = FormatUtil.format(FormatUtil.getDoubleValue(val));
					editor.getItem().setText(EDITABLECOLUMN, str);
					break;
				default:
					editor.getItem().setText(EDITABLECOLUMN, val);
				}
				logger_.info("Modify Listener for editor");
				ticEvent.valueChanged = true;
				
				for(TableItemChangeListener ticl : listeners) {
					TableItemChangeEvent thisTicEvent = new TableItemChangeEvent();
					thisTicEvent.detail = EDITABLECOLUMN;
					//logger_.info("Firing focus listener. "+column);
					thisTicEvent.data = val;
					ticl.columnModified(thisTicEvent);
				}
			}
		});
		newEditor.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent arg0) {
				logger_.info("Focus lost");
				logger_.info("row: "+row+" col: "+column+" datatype: "+colDataType);
				CustomTextBox oldEditor;
				switch(colDataType) {
				case DataTypes.INT:
				case DataTypes.INTEGER:
					oldEditor = (CustomIntegerBox)editor.getEditor();
					break;
				case DataTypes.FLOAT:
				case DataTypes.FLOAT_P:
					oldEditor = (CustomFloatBox)editor.getEditor();
					break;
				default:
					oldEditor = (CustomTextBox)editor.getEditor();
				}

				int selectedIndex = getSelectionIndex();
				if(selectedIndex == -1) {
					selectedIndex = getRowsCount() - 1;
				}
				String val = oldEditor.getText();

				if (oldEditor != null) {
					oldEditor.dispose();
				}

				for(TableItemChangeListener ticl : listeners) {
					TableItemChangeEvent ticEvent = new TableItemChangeEvent();
					ticEvent.detail = EDITABLECOLUMN;
					//logger_.info("Firing focus listener. "+column);
					ticEvent.validValue = false;
					ticl.focusLost(ticEvent);
				}
				if(OES.SWALLOW_MOUSE_CLICKS) { //this is to ensure that when a editable column loses its focus, none of the other columns in 
					//that row should have a invalid value
					makeCellEditable(row, column);
					OES.SWALLOW_MOUSE_CLICKS = false;
					return;
				}
				TableItemValidationEvent evt = new TableItemValidationEvent();
				evt.item = getItem(row);
				evt.valueToValidate = val;
					if (editorData != null && !editorData.isValid(EDITABLECOLUMN, evt)) {//Current editable column doesn't have valid data
						OES.SWALLOW_MOUSE_CLICKS = true;
						makeCellEditable(row, column);
						OES.SWALLOW_MOUSE_CLICKS = false;
						return;
					}else{
						OES.SWALLOW_MOUSE_CLICKS = false;
						if (editorData != null && currentEditableRow != null && !currentEditableRow.isDisposed()) {
							boolean b = editorData
									.validateRow(currentEditableRow);
							logger_.info("Focus lost " + b);
						}
					}
				
				logger_.info("Column that was under editing - 1: "+EDITABLECOLUMN);
				if(firstColumnWasEdited()) {
					logger_.info("Should remove the item now if ESC was pressed - 1");
				}
				//invoke all the listeners that Item has changed
				for(TableItemChangeListener ticl : listeners) {
					TableItemChangeEvent ticEvent = new TableItemChangeEvent();
					ticEvent.detail = EDITABLECOLUMN;
					ticEvent.currentRowNumber = getSelectionIndex();
					ticl.itemChanged(ticEvent);
					ticl.itemChangeCompleted(ticEvent);
					editingMode = false;
				}
				logger_.info("Focus lost listener in editor ");
			}

			@Override
			public void focusGained(FocusEvent arg0) {

			}
		});

		newEditor.addTraverseListener(new TraverseListener() {
			
			@Override
			public void keyTraversed(TraverseEvent te) {
				te.doit = false;
			}
		});
		
		newEditor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
			//public void keyPressed(KeyEvent e) {
				if(e.keyCode == SWT.CR) {
					//completeRowChange(colDataType, item, row, column);
					logger_.info("Enter pressed in editor: "+column);
					CustomTextBox text;
					switch(colDataType) {
					case DataTypes.INT:
					case DataTypes.INTEGER:
						logger_.info("Editor morphed to CustomIntegerBox");
						text = (CustomIntegerBox)editor.getEditor();
						break;
					case DataTypes.FLOAT:
					case DataTypes.FLOAT_P:
						logger_.info("Editor morphed to CustomFloatBox");
						text = (CustomFloatBox)editor.getEditor();
						break;
					default:
						logger_.info("Editor isn't morphed");
						text = (CustomTextBox)editor.getEditor();
					}

					String val = text.getText();
					editor.getItem().setText(EDITABLECOLUMN, val);
					Control oldEditor = editor.getEditor();
					if (oldEditor != null && !oldEditor.isDisposed()) {
						logger_.info("Disposing old editor");
						oldEditor.dispose();
					} else {
						logger_.info("Editor is either already disposed or null");
					}

					switch(colDataType) {
					case DataTypes.INT:
					case DataTypes.INTEGER:
						editor.getItem().setText(EDITABLECOLUMN, FormatUtil.format(new Integer(Integer.parseInt(val.trim().length() !=0 ? val : "0"))));
						break;
					case DataTypes.FLOAT:
					case DataTypes.FLOAT_P:
						String str = FormatUtil.format(FormatUtil.getDoubleValue(val));
						editor.getItem().setText(EDITABLECOLUMN, str);
						break;
					default:
						editor.getItem().setText(EDITABLECOLUMN, val);
					}
					
					currentEditableRow = item;
					TableItemValidationEvent evt = new TableItemValidationEvent();
					evt.item = getItem(row);
					evt.valueToValidate = val;
					logger_.info("Data to validate: (row, col, value): ("+row+", "+column+", "+val+")");
						if (editorData != null && !editorData.isValid(column, evt)) {
							OES.SWALLOW_MOUSE_CLICKS = true;
							makeCellEditable(row, column);
							return;
						}else{
							OES.SWALLOW_MOUSE_CLICKS = false;
						}
					
					//currentEditableRow = item;
					//invoke all the listeners that Item has changed
					
					if(firstColumnWasEdited()) {
						ticEvent.valueChanged = true;
					}
					
					for(TableItemChangeListener ticl : listeners) {
						ticEvent.detail = EDITABLECOLUMN;
						ticEvent.currentRowNumber = getSelectionIndex();
						ticl.itemChanged(ticEvent);
						if (lastColumnWasEdited()) {
							ticl.itemChangeCompleted(ticEvent);
							editingMode = false;
						}
					}
					logger_.info("invalid value: "+OES.INVALID_VALUE_IN_TABLE);
					if (editorData != null) {
						if ((OES.INVALID_VALUE_IN_TABLE && !editorData
								.needsValidation(column))
								|| (val.trim().length() == 0 && firstColumnWasEdited())) { //TODO: firstColumnWasEdited condition here is targeted for itemcode. should make this generic
							OES.INVALID_VALUE_IN_TABLE = false;
							makeCellEditable(row, EDITABLECOLUMN);//column);
							return;
						}
					}
					
					moveToNextEditableColumn();
				} else if(e.keyCode == SWT.ESC) {
					CustomTextBox text = (CustomTextBox)editor.getEditor();
					String val = text.getText();
					editor.getItem().setText(EDITABLECOLUMN, text.getText());
					Control oldEditor = editor.getEditor();
					if (oldEditor != null) {
						oldEditor.dispose();
					}
					currentEditableRow = item;
					logger_.info("ESC CTB: "+editingMode+" Invalid value in table: "+OES.INVALID_VALUE_IN_TABLE);
					//invoke all the listeners that Item has changed
					currentEditableRow.setText(EDITABLECOLUMN, val);
					boolean rowDeleted = false;
					for(TableItemChangeListener ticl : listeners) {
						ticEvent.detail = EDITABLECOLUMN;
						logger_.info("Row being edited: " + getSelectionIndex());
						ticEvent.valueChanged = true;
						ticEvent.currentRowNumber = getSelectionIndex();
						logger_.info("Editable Column: " + EDITABLECOLUMN);
						ticl.itemChanged(ticEvent);
						if (firstColumnWasEdited()) {
							ticl.itemRemoved();
							if(!editingMode) {
								int rowToRemove = getSelectionIndex();
								if(rowToRemove != -1 && rowToRemove < getRowsCount()) {
									deleteRow(rowToRemove, true);
									OES.INVALID_VALUE_IN_TABLE = false;
									editingMode = false;
									rowDeleted = true;
								}
							}
							//editingMode = false;
						}
					}
					
					if(OES.INVALID_VALUE_IN_TABLE || (!rowDeleted && val.trim().length() == 0 && firstColumnWasEdited())) {
					//if(OES.INVALID_VALUE_IN_TABLE) {
						OES.INVALID_VALUE_IN_TABLE = false;
						makeCellEditable(row, EDITABLECOLUMN);//column);
						return;
					}
					
					moveToPreviousEditableColumn();
					//logger_.info("Moved to Previous");
					
				} else if(e.keyCode == SWT.ARROW_DOWN) {
					//completeRowChange(colDataType, item, row, column);
					CustomTextBox text;
					switch(colDataType) {
					case DataTypes.INT:
					case DataTypes.INTEGER:
						text = (CustomIntegerBox)editor.getEditor();
						break;
					case DataTypes.FLOAT:
					case DataTypes.FLOAT_P:
						text = (CustomFloatBox)editor.getEditor();
						break;
					default:
						text = (CustomTextBox)editor.getEditor();
					}

					String val = text.getText();
					editor.getItem().setText(EDITABLECOLUMN, val);
					Control oldEditor = editor.getEditor();
					if (oldEditor != null) {
						oldEditor.dispose();
					}

					switch(colDataType) {
					case DataTypes.INT:
					case DataTypes.INTEGER:
						editor.getItem().setText(EDITABLECOLUMN, FormatUtil.format(new Integer(Integer.parseInt(val.trim().length() !=0 ? val : "0"))));
						break;
					case DataTypes.FLOAT:
					case DataTypes.FLOAT_P:
						String str = FormatUtil.format(FormatUtil.getDoubleValue(val));
						editor.getItem().setText(EDITABLECOLUMN, str);
						break;
					default:
						editor.getItem().setText(EDITABLECOLUMN, val);
					}
					
					currentEditableRow = item;
					TableItemValidationEvent evt = new TableItemValidationEvent();
					evt.item = getItem(row);
					evt.valueToValidate = val;
						if (editorData != null && !editorData.isValid(column, evt)) {//to disallow 0 in price entered column of Item Entry table. TODO: Should make this generic
							OES.SWALLOW_MOUSE_CLICKS = true;
							makeCellEditable(row, column);
							return;
						}else{
							OES.SWALLOW_MOUSE_CLICKS = false;
						}
					
					//currentEditableRow = item;
					//invoke all the listeners that Item has changed
					
					if(firstColumnWasEdited()) {
						ticEvent.valueChanged = true;
					}
					
					for(TableItemChangeListener ticl : listeners) {
						ticEvent.detail = EDITABLECOLUMN;
						ticEvent.currentRowNumber = getSelectionIndex();
						ticl.itemChanged(ticEvent);
						if (lastColumnWasEdited()) {
							ticl.itemChangeCompleted(ticEvent);
							editingMode = false;
						}
					}
					logger_.info("invalid value: "+OES.INVALID_VALUE_IN_TABLE);
					if (editorData != null) {
						if ((OES.INVALID_VALUE_IN_TABLE && !editorData
								.needsValidation(column))
								|| (val.trim().length() == 0 && firstColumnWasEdited())) { //TODO: firstColumnWasEdited condition here is targeted for itemcode. should make this generic
							OES.INVALID_VALUE_IN_TABLE = false;
							makeCellEditable(row, EDITABLECOLUMN);//column);
							return;
						}
					}
					makeCellEditable(row + 1, column);
				}
				logger_.info("key presssed listener in editor");
			}
		});

		newEditor.selectAll();
		getDisplay().asyncExec(new Runnable() {  //this async action is to get control back to editor if its values doesn't fall in valid range
			@Override
			public void run() {
				if (!newEditor.isDisposed()) {
					newEditor.setFocus();
				}
			}
		});
		editor.setEditor(newEditor, item, EDITABLECOLUMN);
	}


	private void completeRowChange(int colDataType, ItemEntryTableItem item, int row, int column) {
		final TableItemChangeEvent ticEvent = new TableItemChangeEvent();
		
		CustomTextBox text;
		switch(colDataType) {
		case DataTypes.INT:
		case DataTypes.INTEGER:
			text = (CustomIntegerBox)editor.getEditor();
			break;
		case DataTypes.FLOAT:
		case DataTypes.FLOAT_P:
			text = (CustomFloatBox)editor.getEditor();
			break;
		default:
			text = (CustomTextBox)editor.getEditor();
		}

		String val = text.getText();
		editor.getItem().setText(EDITABLECOLUMN, val);
		Control oldEditor = editor.getEditor();
		if (oldEditor != null) {
			oldEditor.dispose();
		}

		switch(colDataType) {
		case DataTypes.INT:
		case DataTypes.INTEGER:
			editor.getItem().setText(EDITABLECOLUMN, FormatUtil.format(new Integer(Integer.parseInt(val.trim().length() !=0 ? val : "0"))));
			break;
		case DataTypes.FLOAT:
		case DataTypes.FLOAT_P:
			String str = FormatUtil.format(FormatUtil.getDoubleValue(val));
			editor.getItem().setText(EDITABLECOLUMN, str);
			break;
		default:
			editor.getItem().setText(EDITABLECOLUMN, val);
		}
		
		currentEditableRow = item;
		TableItemValidationEvent evt = new TableItemValidationEvent();
		evt.item = getItem(row);
		evt.valueToValidate = val;
			if (editorData != null && !editorData.isValid(column, evt)) {//to disallow 0 in price entered column of Item Entry table. TODO: Should make this generic
				OES.SWALLOW_MOUSE_CLICKS = true;
				makeCellEditable(row, column);
				return;
			}else{
				OES.SWALLOW_MOUSE_CLICKS = false;
			}
		
		//currentEditableRow = item;
		//invoke all the listeners that Item has changed
		
		if(firstColumnWasEdited()) {
			ticEvent.valueChanged = true;
		}
		
		for(TableItemChangeListener ticl : listeners) {
			ticEvent.detail = EDITABLECOLUMN;
			ticEvent.currentRowNumber = getSelectionIndex();
			ticl.itemChanged(ticEvent);
			if (lastColumnWasEdited()) {
				ticl.itemChangeCompleted(ticEvent);
				editingMode = false;
			}
		}
		logger_.info("invalid value: "+OES.INVALID_VALUE_IN_TABLE);
		if (editorData != null) {
			if ((OES.INVALID_VALUE_IN_TABLE && !editorData
					.needsValidation(column))
					|| (val.trim().length() == 0 && firstColumnWasEdited())) { //TODO: firstColumnWasEdited condition here is targeted for itemcode. should make this generic
				OES.INVALID_VALUE_IN_TABLE = false;
				makeCellEditable(row, EDITABLECOLUMN);//column);
				return;
			}
		}
	}

	protected boolean firstColumnWasEdited() {
		//logger_.info("First Editable col: "+editorData.getCell(0]);
		if(editorData != null && EDITABLECOLUMN == editorData.getCell(0))
			return true;
		else
			return false;
	}

	protected void moveToPreviousEditableColumn() {
		//logger_.info("moveToPreviousEditableColumn");
		if(editorData == null) {
			return;
		}
		
		int currentEditableColumnIndex = -1;
		for(int i=0; i<editorData.getCellCount(); i++) {
			if(editorData.getCell(i) == EDITABLECOLUMN) {
				currentEditableColumnIndex = i;
				setFocus();
				setSelection(currentEditableRow);
				break;
			}
		}
		//logger_.info("currentEditableColumnIndex: "+currentEditableColumnIndex);
		if(currentEditableColumnIndex == 0) { //We are on the first editable column
			EDITABLECOLUMN = -1;
			return;
		} 
		makeCellEditable(getSelectionIndex(), editorData.getCell(currentEditableColumnIndex-1));

	}

	protected boolean lastColumnWasEdited() {
		if(editorData != null && EDITABLECOLUMN == editorData.getCell(editorData.getCellCount() - 1))
			return true;
		else
			return false;
	}

	protected void moveToNextEditableColumn() {
		if(editorData == null) {
			return;
		}
		
		int currentEditableColumnIndex = -1;
		for(int i=0; i<editorData.getCellCount(); i++) {
			if(editorData.getCell(i) == EDITABLECOLUMN) {
				currentEditableColumnIndex = i;
				setFocus();
				setSelection(currentEditableRow);
				break;
			}
		}
		if(currentEditableColumnIndex == editorData.getCellCount() - 1) { //We are on the last editable column
			EDITABLECOLUMN = -1;
			return;
		} 
		makeCellEditable(getSelectionIndex(), editorData.getCell(currentEditableColumnIndex+1));
	}

	@Override
	public void setMenu(Menu menu) {
		tableMenu = menu;
		//super.setMenu(menu);
	}
	@Override
	public Menu getMenu() {
		return tableMenu;
	}

	/**
	 * @return the headerMenu
	 */
	public Menu getHeaderMenu() {
		return headerMenu;
	}

	public void setupColumns(List<CustomTableColumnProperties> tableCols) {
		int i = 0;
		for (CustomTableColumnProperties col : tableCols) {
			col.createTableColumn(this);
			if(editorData != null && editorData.isEditable(i)) {
				col.markAsEditable();
			}
			i++;
		}	
		initialize();
	}
	
	public void initialize() {
		if(initialized) {
			return;
		}
		initialTableColumns = this.getColumns();


		/*Menu headerMenu = new Menu(this);
	setMenu(headerMenu);*/
		headerMenu = new Menu(this);
		headerMenu.setVisible(false);
		if (!listenersAdded) {
			addListeners();
		}
		for (int i = 0; i < initialTableColumns.length; i++) {
			ItemEntryTableColumn  tableColumn = (ItemEntryTableColumn) initialTableColumns[i];
			String columnName = tableColumn.getText();
			MenuItem mntm = new MenuItem(headerMenu, SWT.CHECK);
			mntm.setText(columnName);

			mntm.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					MenuItem mi = (MenuItem) e.widget;
					String columnName = mi.getText();
					int selectedColumnIndex = getSelectedColumnIndex(columnName) ;
					ItemEntryTableColumn tableColumn = ((ItemEntryTableColumn)initialTableColumns[selectedColumnIndex]);

					if (!mi.getSelection()) {    // need to hide
						mi.setSelection(false);
						tableColumn.hide();
					} else {       //need to display back the column
						mi.setSelection(true);
						tableColumn.display();
					}
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
				}
			});

		}
		initialized = true;
	}




	

	


	protected int getSelectedColumnIndex(String columnName) {
		int selectedTableColumnIndex = -1;
		for (int i = 0; i < initialTableColumns.length; i++) {
			if (initialTableColumns[i].getText().equals(columnName)) {
				selectedTableColumnIndex = i;
				break;
			}
		}
		return selectedTableColumnIndex;
	}

	@Override
	protected void checkSubclass() {

	}

	public void setData(List<String[]> data) {
		if(Validator.isEmptyList(data)) {
			//logger_.info("List is empty");
			clearTable();
			data = new ArrayList<String[]>();
		}
		myData = data;
		refreshData();
	}

	public void refreshData() {
		populateTableWithData();
	}


	private void populateTableWithData() {
		clearTable();
		//logger_.info("Checked Rows: ");
		//logger_.info(checkedRows);
		int rowNum = 0;
		if(myData.size() == 0) {
			//logger_.info("Empty data list");
			return;
		}
		//for (Iterator iterator = myData.iterator(); iterator.hasNext();) {
		int startIndex = 0;
		int endIndex = myData.size();
		
		//logger_.info("From: "+startIndex+" To: "+endIndex);
		for (int rowNumCount = startIndex; rowNumCount < endIndex; rowNumCount++) {
			//String[] thisRow = (String[]) iterator.next();

			String[] thisRow = (String[]) myData.get(rowNumCount);
			thisRow[0] = (rowNumCount + 1) + StringPool.BLANK;
			


			ItemEntryTableItem tableItem = new ItemEntryTableItem(this, SWT.NONE, rowNum);

			tableItem.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.BOLD));
			tableItem.setUpdateData(false);
			tableItem.setText(thisRow);
			
			if (editorData != null) {
				for (int index : editorData.getEditableCells()) {
					tableItem
							.setBackground(
									index,
									Display.getCurrent().getSystemColor(
											OES.EDITABLE_CELL_BGCOLOR_INACTIVE));
				}
			}
			if (enableCellSelection) {
				addCellSelectionListener(tableItem);
			}
			
			//logger_.info("Added: "+tableItem);
			rowNum++;
		}
	}


	private void addCellSelectionListener(ItemEntryTableItem tableItem) {
		tableItem.addListener(SWT.MouseDown, new Listener() {

			@Override
			public void handleEvent(Event e) {
				logger_.info(e.getBounds());				
			}
		});
	}

	private void appendDataToTable(List<String[]> dataToAppend) {
		int rowNum = 0;
		if(myData.size() == 0) {
			return;
		}

		int startIndex = myData.size() - dataToAppend.size();
		int endIndex = myData.size();
		for (int rowNumCount = startIndex; rowNumCount < endIndex; rowNumCount++) {
			//String[] thisRow = (String[]) iterator.next();
			final String[] thisRow = (String[]) myData.get(rowNumCount);
			thisRow[0] = (rowNumCount+1) + StringPool.BLANK;

			final ItemEntryTableItem tableItem = new ItemEntryTableItem(this, SWT.NONE, rowNumCount);
			tableItem.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.BOLD));
			tableItem.setText(thisRow);
			addCellSelectionListener(tableItem);
			rowNum++;
		}
	}

	private void clearTable() {
		for (ItemEntryTableItem tableItem : getItems()) {
			tableItem.dispose();
		}
		
	}


	public void updateData(int rowNum, int colNum, String value) {
		myData.get(rowNum)[colNum] = value;
		getItem(rowNum).setUpdateData(false);
		getItem(rowNum).setText(colNum, value);
	}


	@Override
	public ItemEntryTableItem getItem(int index) {
		return getItems()[index];
	}

	public ItemEntryTableItem[] getItems() {
		TableItem allItems[] = super.getItems();
		ItemEntryTableItem customItems[] = new ItemEntryTableItem[allItems.length];
		int i = 0;
		for (TableItem tableItem : allItems) {
			if(tableItem instanceof ItemEntryTableItem) {
				customItems[i++] = (ItemEntryTableItem)tableItem; 
			}
		}
		return customItems;
	}

	public void setContainsSlNo(boolean b) {
		this.containsSlNo_  = b;
	}

	public void bufferData(List<String[]> dataList) {
		myData.addAll(dataList);
	}

	public void setCellBackground(int row, int col, Color color) {
		ItemEntryTableItem allItems[] = getItems();
		allItems[row].setBackground(col, color);
	}
	public void appendData(List<String[]> dataList) {
		myData.addAll(dataList);
		appendDataToTable(dataList);
	}

	@Override
	public List<String[]> getData() {
		return myData;
	}

	public int getRowsCount() {
		return myData.size();
	}

	public void clearData() {
		currentSelectedRowNumber = -1;
		clearTable();
		myData.clear();
		refreshData();
	}


	public void addTableItemChangeListener(TableItemChangeListener ticl) {
		if (!listenersAdded) {
			addListeners();
		}
		listeners.add(ticl);
	}
	public void removeTableItemChangeListener(TableItemChangeListener ticl) {
		listeners.remove(ticl);
	}

	public void editRow(int rowNum) {
		if(rowNum == -1 || rowNum > getRowsCount() -1 || editorData == null) {
			return;
		}
		currentEditableRow = getItem(rowNum);
		makeCellEditable(rowNum, editorData.getCell(0));
	}

	public void editCurrentRow() {
		editingMode  = true;
		editRow(getSelectionIndex());
	}

	public void deleteRow(int rowNum, boolean refresh) {
		myData.remove(rowNum);
		remove(rowNum);
		if (refresh) {
			refreshData();
		}

		if(containsSlNo_) {
			for (int i=0; i < getItemCount(); i++) {
				try {
					myData.get(i)[0] = (i+1)+StringPool.BLANK;
					getItem(i).setText(0, (i+1)+StringPool.BLANK);
				} catch (IndexOutOfBoundsException e) {

				}
			}

		}
	}

	public void deleteCurrentRow(boolean refresh) {
		deleteRow(getSelectionIndex(), refresh);
	}

	public void deleteCurrentRow() {
		deleteRow(getSelectionIndex(), true);
	}

	public TableItem getCurrentEditableRow() {
		return currentEditableRow;
	}

	public String getText(int rowNum, int index) {
		return myData.get(rowNum)[index];
	}

	public void addTableCursor() {
		cursor = new TableCursor(this, SWT.NONE);
		// create an editor to edit the cell when the user hits "ENTER" 
		// while over a cell in the table
		final ControlEditor cursorEditor = new ControlEditor(cursor);
		cursorEditor.grabHorizontal = true;
		cursorEditor.grabVertical = true;

		cursor.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.BOLD));

		cursor.addSelectionListener(new SelectionAdapter() {
			// when the TableEditor is over a cell, select the corresponding row in 
			// the table
			public void widgetSelected(SelectionEvent e) {
				logger_.info("cursor select");
				TableItem row = cursor.getRow();
				
				int column = cursor.getColumn();
				setSelection(new TableItem[] { cursor.getRow()});
				currentSelectedRowNumber = getSelectionIndex();
				final Text text = new Text(cursor, SWT.READ_ONLY | SWT.LEFT);
				text.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.BOLD));
				text.setText(row.getText(column));
				text.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));

				text.addFocusListener(new FocusAdapter() {
					public void focusLost(FocusEvent e) {
						text.dispose();
					}
				});
				cursorEditor.setEditor(text);
				text.setFocus();
			}
			// when the user hits "ENTER" in the TableCursor, pop up a text editor so that 
			// they can change the text of the cell
			public void widgetDefaultSelected(SelectionEvent e) {
				final Text text = new Text(cursor, SWT.NONE);
				TableItem row = cursor.getRow();
				int column = cursor.getColumn();
				text.setText(row.getText(column));
				/*text.addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent e) {
						// close the text editor and copy the data over 
						// when the user hits "ENTER"
						if (e.character == SWT.CR) {
							TableItem row = cursor.getRow();
							int column = cursor.getColumn();
							row.setText(column, text.getText());
							text.dispose();
						}
						// close the text editor when the user hits "ESC"
						if (e.character == SWT.ESC) {
							text.dispose();
						}
					}
				});*/
				// close the text editor when the user tabs away
				text.addFocusListener(new FocusAdapter() {
					public void focusLost(FocusEvent e) {
						text.dispose();
					}
				});
				editor.setEditor(text);
				text.setFocus();
			}
		});
		// Hide the TableCursor when the user hits the "CTRL" or "SHIFT" key.
		// This allows the user to select multiple items in the table.
		/*cursor.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.CTRL
					|| e.keyCode == SWT.SHIFT
					|| (e.stateMask & SWT.CONTROL) != 0
					|| (e.stateMask & SWT.SHIFT) != 0) {
					cursor.setVisible(false);
				}
			}
		});*/


		// When the user double clicks in the TableCursor, pop up a text editor so that 
		// they can change the text of the cell.
		cursor.addMouseListener(new MouseAdapter() {

			public void mouseDown(MouseEvent e) {
				//final Text text = new Text(cursor, SWT.NONE);
				TableItem row = cursor.getRow();
				int column = cursor.getColumn();
				if(column == 0) {
					return;
				}
				//logger_.info("Selected Index: "+getSelectionIndex()+" Column: "+column);
				//setCellBackground(getSelectionIndex(), column, Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE));
				/*text.setText(row.getText(column));
				text.addKeyListener(new KeyAdapter() {
					public void keyPressed(KeyEvent e) {
						// close the text editor and copy the data over 
						// when the user hits "ENTER"
						if (e.character == SWT.CR) {
							TableItem row = cursor.getRow();
							int column = cursor.getColumn();
							row.setText(column, text.getText());
							text.dispose();
						}
						// close the text editor when the user hits "ESC"
						if (e.character == SWT.ESC) {
							text.dispose();
						}
					}
				});
				// close the text editor when the user clicks away
				text.addFocusListener(new FocusAdapter() {
					public void focusLost(FocusEvent e) {
						text.dispose();
					}
				});
				editor.setEditor(text);
				text.setFocus();*/
			}
		});

		// Show the TableCursor when the user releases the "SHIFT" or "CTRL" key.
		// This signals the end of the multiple selection task.
		addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {/*
				if (e.keyCode == SWT.CONTROL && (e.stateMask & SWT.SHIFT) != 0)
					return;
				if (e.keyCode == SWT.SHIFT && (e.stateMask & SWT.CONTROL) != 0)
					return;
				if (e.keyCode != SWT.CONTROL
					&& (e.stateMask & SWT.CONTROL) != 0)
					return;
				if (e.keyCode != SWT.SHIFT && (e.stateMask & SWT.SHIFT) != 0)
					return;

				TableItem[] selection = getSelection();
				TableItem row = (selection.length == 0) ? getItem(getTopIndex()) : selection[0];
				showItem(row);
				cursor.setSelection(row, 0);
				cursor.setVisible(true);
				cursor.setFocus();
			 */}
		});

	}

	public void setCellSelection(boolean enable) {
		enableCellSelection = enable;	
	}


	public String getTableName() {
		return tableName;
	}


	public void setEditorData(TableEditorData data) {
		this.editorData = data;
	}
	
	public void setFocusInCurrentEditableColumn() {
		if(newEditor != null && !newEditor.isDisposed()) {
			newEditor.setFocus();
		}
	}

	public int getSelectedRowNumber() {
		return currentSelectedRowNumber;
	}
	public String getCurrentRowData(int index) {
		if(currentEditableRow == null) {
			return StringPool.BLANK;
		}
		return currentEditableRow.getText(index);
	}

	public void disposeTableCursor() {
		if (cursor !=null && !cursor.isDisposed()) {
			cursor.dispose();
		}
	}
	
	protected int countCheckedItems() {
		int checkedItems = 0;
		for (TableItem item : getItems()) {
			if(item.getChecked()) {
				checkedItems++;
			}
		}
		return checkedItems;
	}

	public List<TableItemChangeListener> getListeners() {
		return listeners;
	}

	public void setEditOnMouseEvent(boolean editOnMouseEvent) {
		this.editOnMouseEvent  = editOnMouseEvent;
	}

	public void checkItem(int currentRowNumber) {
		if(currentRowNumber == -1 || currentRowNumber >= getItemCount()) {
			return;
		}
		TableItem t = getItem(currentRowNumber);
		checkItem(t);
	}

	private void checkItem(TableItem t) {
		t.setChecked(true);
	}
	
	public void uncheckItem(int currentRowNumber) {
		if(currentRowNumber == -1 || currentRowNumber >= getItemCount()) {
			return;
		}
		TableItem t = getItem(currentRowNumber);
		uncheckItem(t);
	}

	private void uncheckItem(TableItem t) {
		t.setChecked(false);
	}
	public void recalculateSlNo(int columnNo) {
		int count = 0;
		for(ItemEntryTableItem item : this.getItems()){
			if(item != null && !item.isDisposed()){
				item.setText(columnNo, StringPool.BLANK + ++count);
			}
		}
	}

	public void removeCurrentInputField() {
		if(newEditor != null && !newEditor.isDisposed()) {
			newEditor.dispose();
		}
	}
	
	private void getSelectedCell(MouseEvent event, boolean mouseDown) {
		
		 for (ItemEntryTableItem ti : getItems()) {
				for (int k = 1; k < getColumnCount(); k++) {
					ti.setImage(k, SWTResourceManager.getImage(
							ItemEntryTable.class,
							"/com/mbi/oes/db/resources/space.png"));
				}
			}
		 
        Point pt = new Point(event.x, event.y);
        int index = getTopIndex();
        while (index < getItemCount()) {
          TableItem item = getItem(index);
          for (int i = 1; i < getColumnCount(); i++) {
            Rectangle rect = item.getBounds(i);
            rect.x = rect.x + 5;
            //rect.y = rect.y + rect.width - 20;
            rect.height = rect.height - 5;
            rect.width = 15;
            if (rect.contains(pt)) {
            	/*System.out.println("Rect: "+rect);
              System.out.println("Item " + index + " - Col:" + i);*/
				TableItem tableitem = getItem(index);
				tableitem.setImage(i, SWTResourceManager.getImage(
						ItemEntryTable.class,
						"/com/mbi/oes/db/resources/down.png"));
				if(mouseDown) {
					for (TableItemChangeListener ticl : listeners) {
						TableItemChangeEvent tice = new TableItemChangeEvent();
						tice.item = tableitem;
						ticl.itemChanged(tice);
					}
				}
			return;
            }
            /*if (!visible && rect.intersects(clientArea)) {
              visible = true;
            }*/
          }
          /*if (!visible)
            return;*/
          index++;
        }
      }
}