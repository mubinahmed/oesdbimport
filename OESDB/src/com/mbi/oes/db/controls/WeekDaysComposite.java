package com.mbi.oes.db.controls;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.wb.swt.SWTResourceManager;

import com.ibm.icu.util.Calendar;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class WeekDaysComposite extends Composite {
	private ItemEntryTable weekdaysTable;
	private TableColumn tblclmnNewColumn;
	private TableColumn tblclmnNewColumn_1;
	private TableColumn tblclmnNewColumn_2;
	private TableColumn tblclmnNewColumn_3;
	private TableColumn tblclmnNewColumn_4;
	private TableColumn tblclmnNewColumn_5;
	private TableColumn tblclmnNewColumn_6;
	private ItemEntryTableItem tableItem;
	protected Logger logger_ = Logger.getLogger(WeekDaysComposite.class);
	private java.util.List<WeekDayListener> listeners = new ArrayList<WeekDayListener>();
			
	private String[] days = new String[] {"Sat", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
	final Hyperlink hyperLink[] = new Hyperlink[8];
	protected int currentDay = -1;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public WeekDaysComposite(Composite parent, int style) {
		super(parent, style);
		initComponents();
		setDay(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
	}
	private void initComponents() {
		setLayout(new GridLayout(1, false));
		
		weekdaysTable = new ItemEntryTable(this, SWT.NONE);
		weekdaysTable.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		weekdaysTable.addListener(SWT.MeasureItem, ListenerFactory.getListener(weekdaysTable));
		
		tblclmnNewColumn = new TableColumn(weekdaysTable, SWT.NONE);
		tblclmnNewColumn.setWidth(42);
		tblclmnNewColumn.setText("Mon");
		
		tblclmnNewColumn_1 = new TableColumn(weekdaysTable, SWT.NONE);
		tblclmnNewColumn_1.setWidth(42);
		tblclmnNewColumn_1.setText("Tue");
		
		tblclmnNewColumn_2 = new TableColumn(weekdaysTable, SWT.NONE);
		tblclmnNewColumn_2.setWidth(42);
		tblclmnNewColumn_2.setText("Wed");
		
		tblclmnNewColumn_3 = new TableColumn(weekdaysTable, SWT.NONE);
		tblclmnNewColumn_3.setWidth(42);
		tblclmnNewColumn_3.setText("Thu");
		
		tblclmnNewColumn_4 = new TableColumn(weekdaysTable, SWT.NONE);
		tblclmnNewColumn_4.setWidth(42);
		tblclmnNewColumn_4.setText("Fri");
		
		tblclmnNewColumn_5 = new TableColumn(weekdaysTable, SWT.NONE);
		tblclmnNewColumn_5.setWidth(42);
		tblclmnNewColumn_5.setText("Sat");
		
		tblclmnNewColumn_6 = new TableColumn(weekdaysTable, SWT.NONE);
		tblclmnNewColumn_6.setWidth(42);
		tblclmnNewColumn_6.setText("Sun");
		
		tableItem = new ItemEntryTableItem(weekdaysTable, SWT.NONE);
		tableItem.setText(days);
		tableItem.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.BOLD));
		
		final TableEditor editor = new TableEditor(weekdaysTable);
		editor.minimumWidth = 42;
		editor.horizontalAlignment = SWT.CENTER;
		editor.minimumHeight = 25;
		
		for (int i = 0; i < 8; i++) {
			hyperLink[i] = new Hyperlink(weekdaysTable,
					SWT.WRAP);
			hyperLink[i].setFont(SWTResourceManager.getFont("Times New Roman", 12,
					SWT.BOLD));
			hyperLink[i].setBackground(Display.getCurrent().getSystemColor(
					SWT.COLOR_YELLOW));
			hyperLink[i].setForeground(Display.getCurrent().getSystemColor(
					SWT.COLOR_BLUE));
			hyperLink[i].setUnderlined(true);
			hyperLink[i].setText(tableItem.getText(i));
			final int j = i;
			hyperLink[i].addListener(SWT.MouseDown, new Listener() {

				@Override
				public void handleEvent(Event arg0) {
					currentDay = j;
					changeDisplay();
					fireListeners();
				}
			});
			editor.setEditor(hyperLink[i], tableItem, i-1);
			logger_.info("Setting editor for: "+i);
		}
	}

	public void setDay(int day) {
		currentDay = day;
		changeDisplay();
	}
	protected void changeDisplay() {
		logger_.info("Currently selected day: "+currentDay);
		for (int i = 0; i < hyperLink.length; i++) {
			if(i == currentDay) {
				hyperLink[i].setBackground(Display.getCurrent().getSystemColor(
						SWT.COLOR_CYAN));
			} else {
				hyperLink[i].setBackground(Display.getCurrent().getSystemColor(
						SWT.COLOR_YELLOW));
			}
		}
	}
	
	public void addWeekDayListener(WeekDayListener w) {
		listeners.add(w);
	}
	private void fireListeners() {
		for (WeekDayListener listener : listeners) {
			WeekDayEvent event = new WeekDayEvent();
			event.day = currentDay;
			listener.dayChanged(event);
		}
	}
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
