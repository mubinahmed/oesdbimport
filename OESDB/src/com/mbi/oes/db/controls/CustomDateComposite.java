package com.mbi.oes.db.controls;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.mbi.oes.db.utils.FormatUtil;
import com.mbi.oes.db.utils.StringPool;

public class CustomDateComposite extends Composite {
	private Combo monthCombo;
	private Combo dateCombo;
	private Combo yearCombo;
	private Control nextControl;
	private boolean todaysDateAsDefault;
	CustomDateActionHandler actionHandler;
	private boolean excludeDate = false;
	private Label myLabel_;
	private int fromYear = -25;
	private int toYear = 25;
	private boolean showYearInReverseOrder_ = false;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	
	public CustomDateComposite(Composite parent, int style) {
		super(parent, style);
		initComponents();
	}
	
	public CustomDateComposite(Composite parent, int style, boolean todaysDateAsDefault, boolean excludeDate) {
		super(parent, style);
		this.todaysDateAsDefault = todaysDateAsDefault;
		this.excludeDate = excludeDate;
		initComponents();
	}
	
	public CustomDateComposite(Composite parent, int style, boolean todaysDateAsDefault, boolean excludeDate,
			boolean showYearInReverse) {
		super(parent, style);
		this.todaysDateAsDefault = todaysDateAsDefault;
		this.excludeDate = excludeDate;
		this.showYearInReverseOrder_ = showYearInReverse;
		initComponents();
	}
	
	public CustomDateComposite(Composite parent, int style, boolean todaysDateAsDefault) {
		super(parent, style);
		this.todaysDateAsDefault = todaysDateAsDefault;
		initComponents();
	}
	
	public CustomDateComposite(Composite parent, int style,
			boolean todaysDateAsDefault, boolean excludeDate, int fromYear, int toYear) {
			super(parent, style);
			this.todaysDateAsDefault = todaysDateAsDefault;
			this.excludeDate = excludeDate;
			this.fromYear = fromYear;
			this.toYear = toYear;
			initComponents();
	}

	public void setRange(int from, int to) {
		fromYear = from;
		toYear = to;
		setYearData();
	}
	
	private void setYearData() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		String years[] = new String[toYear - fromYear];
		if (showYearInReverseOrder_) {
			for (int j = 0, i = year + toYear; j < years.length; j++, i--) {
				years[j] = i + StringPool.BLANK;
			}
		} else {
			for (int j = 0, i = year + fromYear; j < years.length; j++, i++) {
				years[j] = i + StringPool.BLANK;
			}
		}
		yearCombo.removeAll();
		yearCombo.setItems(years);
		if (showYearInReverseOrder_ && todaysDateAsDefault) {
			yearCombo.select(0);
		}/*else {
			yearCombo.select(year - Integer.parseInt(yearCombo.getItem(0)));
		}*/
	}
	private void initComponents() {
		if (excludeDate) {
			setLayout(new GridLayout(2, true));
		} else {
			setLayout(new GridLayout(3, true));
		}
		
		monthCombo = new Combo(this, SWT.NONE | SWT.READ_ONLY);
		monthCombo.setItems(new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
		monthCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		if(todaysDateAsDefault) {
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH);
			monthCombo.select(month);
		}
		monthCombo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.keyCode == 13) {
					if(monthCombo.getSelectionIndex() == -1) {
						Calendar cal = Calendar.getInstance();
						int month = cal.get(Calendar.MONTH);
						monthCombo.select(month);
					}
					if (excludeDate) {
						yearCombo.setFocus();
					} else {
						dateCombo.setFocus();
					}
				} 
			}
		});
		
		if (!excludeDate) {
			dateCombo = new Combo(this, SWT.NONE | SWT.READ_ONLY);
			dateCombo.setItems(new String[] { "1", "2", "3", "4", "5", "6",
					"7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
					"17", "18", "19", "20", "21", "22", "23", "24", "25", "26",
					"27", "28", "29", "30", "31" });
			dateCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
					false, 1, 1));
			if (todaysDateAsDefault) {
				Calendar cal = Calendar.getInstance();
				int day = cal.get(Calendar.DAY_OF_MONTH);
				dateCombo.select(day - 1);
			}
			dateCombo.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					if (e.keyCode == 13) {
						if (dateCombo.getSelectionIndex() == -1) {
							Calendar cal = Calendar.getInstance();
							int day = cal.get(Calendar.DAY_OF_MONTH);
							dateCombo.select(day - 1);
						}
						yearCombo.setFocus();
					}
				}
			});
		}
		yearCombo = new Combo(this, SWT.NONE | SWT.READ_ONLY);
		yearCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		setYearData();
		if(todaysDateAsDefault) {
			if (showYearInReverseOrder_) {
				yearCombo.select(0);
			}else {
				yearCombo.select(year - Integer.parseInt(yearCombo.getItem(0)));
			}
		}
		yearCombo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.keyCode == 13) {
					if(yearCombo.getSelectionIndex() == -1) {
						Calendar cal = Calendar.getInstance();
						int year = cal.get(Calendar.YEAR);
						if (showYearInReverseOrder_) {
							System.out.println("Showing year in reverse order");
							yearCombo.select(0);
						}else {
							yearCombo.select(year - Integer.parseInt(yearCombo.getItem(0)));
						}
					}
					if(nextControl != null) {
						System.out.println("Movingt to next control");
						nextControl.setFocus();
					} 
					System.out.println("enter actionhandler null? "+(actionHandler == null));
					if(actionHandler != null) {
						actionHandler.enterPressed();
					}
				} 
			}
		});
		
		yearCombo.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				CustomDateEvent evt = new CustomDateEvent();
				evt.data = CustomDateEvent.Values.YEAR;
				System.out.println("Is ActionHandler null? "+(actionHandler == null));
				if(actionHandler != null) {
					actionHandler.itemChanged(evt);
				}
			}
		});
		
		monthCombo.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				CustomDateEvent evt = new CustomDateEvent();
				evt.data = CustomDateEvent.Values.MONTH;
				System.out.println("Is ActionHandler null? "+(actionHandler == null));
				if(actionHandler != null) {
					actionHandler.itemChanged(evt);
				}
			}
		});
		
		if (!excludeDate) {
			dateCombo.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					CustomDateEvent evt = new CustomDateEvent();
					evt.data = CustomDateEvent.Values.DATE;
					if (actionHandler != null) {
						actionHandler.itemChanged(evt);
					}
				}
			});
		}
		

	}

	public void setNextControl(Control nextControl) {
		this.nextControl = nextControl;
	}
	
	@Override
	public boolean setFocus() {
		if(monthCombo != null) {
			return monthCombo.setFocus();
		} else if(dateCombo != null) {
			return dateCombo.setFocus();
		}
		return true;
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void addActionHandler(CustomDateActionHandler handler) {
		System.out.println("Adding action handler: is null? "+(handler == null));
		actionHandler = handler;
	}

	public String getText() {
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(yearCombo.getText()), monthCombo.getSelectionIndex(),
				Integer.parseInt(!excludeDate ? dateCombo.getText() : "1"));
		return FormatUtil.format(cal.getTime());
	}
	
	public  void setDate(java.util.Date date) {
	    if(date == null) {
			monthCombo.setText(StringPool.BLANK);
			if (!excludeDate) {
				dateCombo.setText(StringPool.BLANK);
			}
			yearCombo.setText(StringPool.BLANK);			
		return;
	    }
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int month = cal.get(Calendar.MONTH);
		monthCombo.select(month);
		
		int day = cal.get(Calendar.DAY_OF_MONTH);
		if (!excludeDate) {
			dateCombo.select(day - 1);
		}
		int year = cal.get(Calendar.YEAR);
		if (showYearInReverseOrder_) {
			System.out.println("Showing year in reverse order");
			yearCombo.select(0);
		}else {
			yearCombo.select(year - Integer.parseInt(yearCombo.getItem(0)));
		}
	}
	
	public java.util.Date getDate() {
		
		if(yearCombo.getSelectionIndex() == -1) {
			return null;
		}
		
		Calendar cal = Calendar.getInstance();
		try {
			int date = Integer.parseInt(dateCombo != null ? dateCombo.getText() : "1");
			cal.set(Integer.parseInt(yearCombo.getText()), monthCombo.getSelectionIndex(),
					date);
		} catch (NumberFormatException e) {
			return null;
		}
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 1);
		cal.set(Calendar.AM_PM, Calendar.AM);
		return cal.getTime();
	}
	
	public java.util.Date getTrailingDate() {
		
		if(yearCombo.getSelectionIndex() == -1) {
			return null;
		}
		
		Calendar cal = Calendar.getInstance();
		try {
			int date = Integer.parseInt(dateCombo != null ? dateCombo.getText() : "1");
			cal.set(Integer.parseInt(yearCombo.getText()), monthCombo.getSelectionIndex(),
					date);
		} catch (NumberFormatException e) {
			return null;
		}
		cal.set(Calendar.HOUR, 11);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.AM_PM, Calendar.PM);
		return cal.getTime();
	}

	public int getDay() {
		try {
			return Integer.parseInt(!excludeDate ? dateCombo.getText() : "1");
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
	public int getYear() {
		try {
			return Integer.parseInt(yearCombo.getText());
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
	public int getMonth() {
		try {
			return monthCombo.getSelectionIndex();
		} catch (Exception e) {
			return -1;
		}
	}

	public void setLabel(Label myLabel) {
		this.myLabel_ = myLabel;
	}

	@Override
	public void setVisible(boolean visible) {
		myLabel_.setVisible(visible);
		//super.setVisible(visible);
		monthCombo.setVisible(visible);
		yearCombo.setVisible(visible);
		if(!excludeDate) {
			dateCombo.setVisible(visible);
		}
	}

	public void showYearInReverseOrder(boolean b) {
		showYearInReverseOrder_  = b;		
	}
	
	//first date for selected year
	public Date getFromDateForYear() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, getYear());
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DATE, 1);
		Date fromDate = c.getTime();
		return fromDate;
	}
	
	public Date getFromDateForCurrentYear() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DATE, 1);
		Date fromDate = c.getTime();
		return fromDate;
	}
	
	//Last date for selected year
	public Date getToDateForYear() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, getYear());
		c.set(Calendar.MONTH, 11);
		c.set(Calendar.DATE, 31);
		Date fromDate = c.getTime();
		return fromDate;
	}
	
	public Date getToDateForCurrentYear() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, 11);
		c.set(Calendar.DATE, 31);
		Date fromDate = c.getTime();
		return fromDate;
	}
	
	//Last Date for the selected month and year
	public Date getToDateForMonth() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, getYear());
		c.set(Calendar.MONTH, getMonth());
		c.set(Calendar.DATE, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date fromDate = c.getTime();
		return fromDate;
	}
	
	//First date for the selected month and year
	public Date getFromDateForMonth() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, getYear());
		c.set(Calendar.MONTH, getMonth());
		c.set(Calendar.DATE, 1);
		Date fromDate = c.getTime();
		return fromDate;
	}

	public void fireItemChangeListener() {
		if(actionHandler != null) {
			actionHandler.itemChanged(null);
		}
	}
	
	public void reset() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		if (monthCombo != null) {
			monthCombo.select(month);
		}
		int day = cal.get(Calendar.DAY_OF_MONTH);
		if (dateCombo != null) {
			dateCombo.select(day - 1);
		}
		int year = cal.get(Calendar.YEAR);
		if (yearCombo != null) {
			if (showYearInReverseOrder_) { 
				yearCombo.select(0);
			} else {
				yearCombo.select(year - Integer.parseInt(yearCombo.getItem(0)));
			}
		}

	}

	public void clear() {
		if (monthCombo != null) {
			monthCombo.deselectAll();
		}
		if (dateCombo != null) {
			dateCombo.deselectAll();
		}
		if (yearCombo != null) {
			yearCombo.deselectAll();
		}
	}
}
