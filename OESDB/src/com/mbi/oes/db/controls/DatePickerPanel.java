package com.mbi.oes.db.controls;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;

public class DatePickerPanel extends Composite {

	private Combo monthCombo = null;

	private Spinner yearSpinner = null;

	private Composite headerComposite = null;

	private Composite calendarComposite = null;

	private Calendar date = null;

	private DateFormatSymbols dateFormatSymbols = null;

	//private Label[] calendarLabels = null;
	private MyControl[] calendarLabels = null;
	public DatePickerPanel(Composite parent, int style) {
		super(parent, style);
		initialize();
		updateCalendar();
	}

	private void initialize() {
		date = new GregorianCalendar();
		dateFormatSymbols = new DateFormatSymbols();
		calendarLabels = new MyControl[42];//new Label[42];
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.horizontalSpacing = 3;
		gridLayout.verticalSpacing = 3;
		this.setLayout(gridLayout);
		setSize(new Point(277, 228));
		createCombo();
		createYearSpinner();
		createComposite();
		createComposite1();
		createCalendarData();
	}

	/**
	 * This method initializes combo
	 * 
	 */
	private void createCombo() {
		monthCombo = new Combo(this, SWT.READ_ONLY);
		monthCombo.setItems(dateFormatSymbols.getMonths());
		monthCombo.remove(12);
		monthCombo.select(date.get(Calendar.MONTH));
		monthCombo.setVisibleItemCount(12);
		monthCombo.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent arg0) {
				date.set(Calendar.MONTH, monthCombo.getSelectionIndex());
				updateCalendar();
			}

		});
	}

	private void createYearSpinner() {
		GridData gridData1 = new org.eclipse.swt.layout.GridData();
		gridData1.horizontalAlignment = org.eclipse.swt.layout.GridData.BEGINNING;
		gridData1.grabExcessVerticalSpace = false;
		gridData1.grabExcessHorizontalSpace = false;
		gridData1.heightHint = -1;
		gridData1.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
		yearSpinner = new Spinner(this, SWT.BORDER | SWT.READ_ONLY);
		yearSpinner.setMinimum(1900);
		yearSpinner.setBackground(Display.getDefault().getSystemColor(
				SWT.COLOR_WHITE));
		yearSpinner.setDigits(0);
		yearSpinner.setMaximum(3000);
		yearSpinner.setLayoutData(gridData1);
		yearSpinner.setSelection(date.get(Calendar.YEAR));
		yearSpinner.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent arg0) {
				date.set(Calendar.YEAR, yearSpinner.getSelection());
				updateCalendar();
			}

		});
		
		yearSpinner.addTraverseListener(new TraverseListener() {
			
			@Override
			public void keyTraversed(TraverseEvent arg0) {
				if(arg0.detail == SWT.TRAVERSE_TAB_NEXT) {
					Calendar cal = new GregorianCalendar(date.get(Calendar.YEAR), date
							.get(Calendar.MONTH), 1);
					int dayofWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
					calendarLabels[date.get(Calendar.DAY_OF_MONTH) + dayofWeek].setFocus();
					calendarLabels[date.get(Calendar.DAY_OF_MONTH) + dayofWeek].forceFocus();
				}
			}
		});
	}

	/**
	 * This method initializes composite
	 * 
	 */
	private void createComposite() {
		String[] weekDays = dateFormatSymbols.getWeekdays();
		GridLayout gridLayout1 = new GridLayout();
		gridLayout1.numColumns = 7;
		gridLayout1.makeColumnsEqualWidth = true;
		GridData gridData = new org.eclipse.swt.layout.GridData();
		gridData.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = false;
		gridData.horizontalSpan = 2;
		gridData.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
		headerComposite = new Composite(this, SWT.NONE);
		headerComposite.setLayoutData(gridData);
		headerComposite.setLayout(gridLayout1);
		GridData labelGridData = new org.eclipse.swt.layout.GridData();
		labelGridData.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		labelGridData.grabExcessHorizontalSpace = true;
		labelGridData.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
		for (int i = 1; i < 8; ++i) {
			Label headerLabel = new Label(headerComposite, SWT.CENTER);
			headerLabel.setText(weekDays[i].substring(0, 3));
			headerLabel.setLayoutData(labelGridData);
		}
	}

	/**
	 * This method initializes composite1
	 * 
	 */
	private void createComposite1() {
		GridLayout gridLayout2 = new GridLayout();
		gridLayout2.verticalSpacing = 2;
		gridLayout2.numColumns = 7;
		gridLayout2.makeColumnsEqualWidth = true;
		GridData gridData1 = new org.eclipse.swt.layout.GridData();
		gridData1.horizontalSpan = 2;
		gridData1.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData1.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData1.grabExcessVerticalSpace = true;
		gridData1.grabExcessHorizontalSpace = true;
		calendarComposite = new Composite(this, SWT.BORDER);
		calendarComposite.setBackground(Display.getDefault().getSystemColor(
				SWT.COLOR_WHITE));
		calendarComposite.setLayout(gridLayout2);
		calendarComposite.setLayoutData(gridData1);
	}

	private void createCalendarData() {
		/*java.util.List<Label> allLabels = new java.util.ArrayList<Label>();
		GridData gridData = new org.eclipse.swt.layout.GridData();
		gridData.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;*/
		/*for (int i = 0; i < 42; ++i) {
			final Label headerLabel = new Label(calendarComposite, SWT.CENTER);
			headerLabel.setText("99");
			headerLabel.setLayoutData(gridData);
			calendarLabels[i] = headerLabel;
			headerLabel.addMouseListener(new MouseListener() {

				public void mouseDoubleClick(MouseEvent arg0) {
					unSellectAll();
					Label label = (Label) arg0.getSource();
					if (!label.getText().equals("")) {
						label.setBackground(Display.getDefault()
								.getSystemColor(SWT.COLOR_LIST_SELECTION));
						label.setForeground(Display.getDefault()
								.getSystemColor(SWT.COLOR_WHITE));
						
						setDate(label.getText());
					}

				}

				@Override
				public void mouseDown(MouseEvent arg0) {
					unSellectAll();
					Label label = (Label) arg0.getSource();
					if (!label.getText().equals("")) {
						label.setBackground(Display.getDefault()
								.getSystemColor(SWT.COLOR_LIST_SELECTION));
						label.setForeground(Display.getDefault()
								.getSystemColor(SWT.COLOR_WHITE));
					}
					
				}

				@Override
				public void mouseUp(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			
			calendarLabels[i].addKeyListener(new KeyAdapter(){

				@Override
				public void keyPressed(KeyEvent e) {
					if(e.keyCode == SWT.ARROW_LEFT){
						
					}else if(e.keyCode == SWT.ARROW_RIGHT) {
						
					} else if(e.keyCode == 13) {
						System.out.println("Key Pressed");
						setDate(headerLabel.getText());
					}
				}
				
			});
			allLabels.add(headerLabel);
		}*/
		
		doIt();
		
	}

	public void doIt(){
		
		java.util.List<MyControl> allLabels = new java.util.ArrayList<MyControl>();
		GridData gridData = new org.eclipse.swt.layout.GridData();
		gridData.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.heightHint = 20;
		gridData.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
		
		for (int i = 0; i < 42; ++i) {
			final MyControl headerLabel = new MyControl(calendarComposite, SWT.CENTER);
			headerLabel.setText("99");
			headerLabel.setLayoutData(gridData);
			calendarLabels[i] = headerLabel;
			headerLabel.addMouseListener(new MouseListener() {

				public void mouseDoubleClick(MouseEvent arg0) {
					unSellectAll();
					MyControl label = (MyControl) arg0.getSource();
					if (!label.getText().equals("")) {
						setDate(label.getText());
					}

				}

				@Override
				public void mouseDown(MouseEvent arg0) {
					unSellectAll();
					MyControl label = (MyControl) arg0.getSource();
					if (!label.getText().equals("")) {
						label.setBackground(Display.getDefault()
								.getSystemColor(SWT.COLOR_LIST_SELECTION));
						label.setForeground(Display.getDefault()
								.getSystemColor(SWT.COLOR_WHITE));
					}
					
				}

				@Override
				public void mouseUp(MouseEvent arg0) {
				
				}
			});
			final int j = i;
			calendarLabels[i].addKeyListener(new KeyAdapter(){
				
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.keyCode == SWT.ARROW_LEFT){
						if (j != 0 && calendarLabels[j-1].getText() != null && calendarLabels[j-1].getText() != "") {
							unSellectAll();
							calendarLabels[j - 1].setBackground(Display
									.getDefault().getSystemColor(
											SWT.COLOR_LIST_SELECTION));
							calendarLabels[j - 1].setForeground(Display
									.getDefault().getSystemColor(
											SWT.COLOR_WHITE));
							calendarLabels[j-1].setFocus();
						}
					}else if(e.keyCode == SWT.ARROW_RIGHT) {
						if (j != 41 && calendarLabels[j+1].getText() != null && calendarLabels[j+1].getText() != "") {
							unSellectAll();
							calendarLabels[j + 1].setBackground(Display
									.getDefault().getSystemColor(
											SWT.COLOR_LIST_SELECTION));
							calendarLabels[j + 1].setForeground(Display
									.getDefault().getSystemColor(
											SWT.COLOR_WHITE));
							calendarLabels[j+1].setFocus();
						}
					} else if(e.keyCode == 13) {
						setDate(headerLabel.getText());
					}
				}
				
			});
			allLabels.add(headerLabel);
		}
	}
	protected void setDate(String dateStr) {
		date.set(Calendar.YEAR, yearSpinner.getSelection());
		date
				.set(Calendar.MONTH, monthCombo
						.getSelectionIndex());
		date.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateStr));
		DatePickerPanel.this.getShell().close();
	}

	private void updateCalendar() {
		unSellectAll();
		// Fill Labels
		Calendar cal = new GregorianCalendar(date.get(Calendar.YEAR), date
				.get(Calendar.MONTH), 1);
		int dayofWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;

		for (int i = 0; i < dayofWeek; ++i) {
			calendarLabels[i].setText("");
		}

		for (int i = 1; i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); ++i) {
			calendarLabels[i + dayofWeek - 1].setText("" + i);
		}

		for (int i = cal.getActualMaximum(Calendar.DAY_OF_MONTH) + dayofWeek; i < 42; ++i) {
			calendarLabels[i].setText("");
		}

		calendarLabels[date.get(Calendar.DAY_OF_MONTH) + dayofWeek]
				.setBackground(Display.getDefault().getSystemColor(
						SWT.COLOR_LIST_SELECTION));
		calendarLabels[date.get(Calendar.DAY_OF_MONTH) + dayofWeek]
				.setForeground(Display.getDefault().getSystemColor(
						SWT.COLOR_WHITE));

	}

	protected void unSellectAll() {
		for (int i = 0; i < 42; ++i) {
			calendarLabels[i].setForeground(Display.getDefault()
					.getSystemColor(SWT.COLOR_BLACK));
			calendarLabels[i].setBackground(Display.getCurrent()
					.getSystemColor(SWT.COLOR_WHITE));
		}
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
		updateCalendar();
	}

}




class MyControl extends Canvas implements Listener {

    String string = "";

    public MyControl(Composite parent, int style) {

        super(parent, style);

        addListener(SWT.Paint, this);

        addListener(SWT.FocusIn, this);

        addListener(SWT.FocusOut, this);

        addListener(SWT.KeyDown, this);
    }

    public String getText(){
    	return string;
    }
    public void setText(String string) {

        checkWidget();

        this.string = string == null ? "" : string;

    }

    public void handleEvent(Event event) {

        switch (event.type) {

            case SWT.Paint:

                GC gc = event.gc;

                //Rectangle rect = getClientArea();

                Point extent = gc.textExtent(string);
               // System.out.println("Rect: "+rect);
                //int x = (rect.width - extent.x) / 2;
                int x = extent.x;
//
                int y = extent.y;
                gc.drawText(string, x-2, y-10);

                /*if (isFocusControl()) {

                    x -= 2; y -=2;

                    extent.x +=3; extent.y +=3;

                    gc.drawFocus(x, y, extent.x, extent.y);

                }*/

                break;

            case SWT.FocusIn:

            case SWT.FocusOut:

                redraw();

                break;

            case SWT.KeyDown:

                if (event.character == ' ') {

                    notifyListeners(SWT.Selection, null);

                }

                break;

        }

    }

}