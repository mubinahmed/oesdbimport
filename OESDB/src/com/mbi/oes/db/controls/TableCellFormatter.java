package com.mbi.oes.db.controls;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.wb.swt.SWTResourceManager;

public class TableCellFormatter {

	private Color foregroundColor;
	private Color backgroundColor;
	private HyperLinkListener linkListener;
	private int column;
	private int row;
	private boolean forAllRows;
	private boolean forWholeTable;
	private boolean forWholeRow;

	private boolean formatForeground;
	private boolean formatBackground;
	private boolean  formatLink;
	protected static Logger logger_ = Logger.getLogger(TableCellFormatter.class);
	
	public TableCellFormatter() {
		super();
	}

	public TableCellFormatter(Color foregroundColor, Color backgroundColor,
			 int column, int row) {
		super();
		this.foregroundColor = foregroundColor;
		this.backgroundColor = backgroundColor;
		this.column = column;
		this.row = row;
		formatForeground = true;
		formatBackground = true;
		formatLink = true;
	}

	public TableCellFormatter(Color foregroundColor, Color backgroundColor,
			 int column) {
		super();
		this.foregroundColor = foregroundColor;
		this.backgroundColor = backgroundColor;
		this.column = column;
		formatForeground = true;
		formatBackground = true;
		formatLink = true;
	}


	public TableCellFormatter(Color foregroundColor, Color backgroundColor) {
		super();
		this.foregroundColor = foregroundColor;
		this.backgroundColor = backgroundColor;
		formatForeground = true;
		formatBackground = true;
		formatLink = true;
	}
	
	public Color getForegroundColor() {
		return foregroundColor;
	}
	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
		formatForeground = true;
	}
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
		formatBackground = true;
	}
	public TableCellFormatter(Color foregroundColor) {
		super();
		this.foregroundColor = foregroundColor;
		formatForeground = true;
	}


	public boolean isForWholeRow() {
		return forWholeRow;
	}

	public void setForWholeRow(boolean forWholeRow) {
		this.forWholeRow = forWholeRow;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public boolean isForAllRows() {
		return forAllRows;
	}

	public void setForAllRows(boolean forAllRows) {
		this.forAllRows = forAllRows;
	}

	public void addLinkListener(HyperLinkListener listener) {
		this.linkListener = listener;
	}
	public boolean isForWholeTable() {
		return forWholeTable;
	}

	public void setForWholeTable(boolean forWholeTable) {
		this.forWholeTable = forWholeTable;
	}

	public boolean shouldFormatForeground() {
		return formatForeground;
	}

	public boolean shouldFormatBackground() {
		return formatBackground;
	}

	public boolean shouldFormatLink() {
		return formatLink;
	}

	public void applyFormatting(ItemEntryTableItem item) {
		if(formatBackground) {
			item.setBackground(column, backgroundColor);
		}
		if(formatForeground) {
			item.setForeground(column, foregroundColor);
		}
		if(formatLink) {
			final TableEditor editor = new TableEditor(item.myTable);
			editor.minimumWidth = 95;
			editor.horizontalAlignment = SWT.LEFT;
			final Hyperlink hyperLink = new Hyperlink(item.myTable, SWT.WRAP);
			hyperLink.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.BOLD));
			hyperLink.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
			hyperLink.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
			hyperLink.setUnderlined(true);
			hyperLink.setText(item.getText(column));
			
			hyperLink.addListener(SWT.MouseDown, new Listener() {
				
				@Override
				public void handleEvent(Event arg0) {
					logger_.info("Link Listener activated");
					if(linkListener != null) {
						LinkEvent e = new LinkEvent();
						e.linkText = hyperLink.getText();
						linkListener.doAction(e);
					}
				}
			});
			
			editor.setEditor(hyperLink, item, column);
			item.addDisposeListener(new DisposeListener() {
				
				@Override
				public void widgetDisposed(DisposeEvent arg0) {
					logger_.info("Disposing the item.");
					editor.getEditor().dispose();
				}
			});
		}
	}

	public void setHyperLink(boolean b) {
		formatLink = b;		
	}	
	
	
}
