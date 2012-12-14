package com.mbi.oes.db.controls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.mbi.oes.db.utils.DataTypes;
import com.mbi.oes.db.utils.TableColumnProperties.Sortable;
import com.pfcomponents.grid.TreeListColumn;
import com.pfcomponents.grid.TreeListView;
import com.pfcomponents.grid.enums.Align;
import com.pfcomponents.grid.enums.SelectionType;
import com.pfcomponents.grid.render.VistaTreeListRenderer;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.wb.swt.SWTResourceManager;

import com.pfcomponents.grid.TreeListCell;
import com.pfcomponents.grid.TreeListRow;
import com.pfcomponents.grid.cells.CheckBoxCell;
import com.pfcomponents.grid.cells.ProgressBarCell;


public class MultiLineTable {

	private static TreeListView treeList;
	private static List<String> columnNames = new ArrayList<String>();
	List <MultiLineTableData> myData = new ArrayList<MultiLineTableData>();
	
	public static void main(String argss[]) {
		
			List<MultiLineTableData> testData = new ArrayList<MultiLineTableData>();
			for(int i = 0; i<10; i++) {
				String[] mr = new String[10];
				for(int j = 0; j < 10; j++) {
					mr[j] = "Child "+i+" Col "+j;
				}
				List<String> cr = new ArrayList<String>();
				for(int j = 0; j < 10; j++) {
					cr.add("Child "+i+" Col "+j);
				}
				testData.add(new MultiLineTableData(mr, cr));
			}
		// prepare SWT environment display and shell
				Display display = new Display();
				Shell shell = new Shell(display);
				shell.setLayout(new FillLayout());
				shell.setText("Testing ");
				shell.setSize(700, 600);
				shell.setLocation(350, 300);	
				MultiLineTable table = new MultiLineTable(shell);
				List<CustomTableColumnProperties> tableCols = new ArrayList<CustomTableColumnProperties>();
				tableCols.add(new CustomTableColumnProperties(40, "Sl #", Sortable.FALSE));
				tableCols.add(new CustomTableColumnProperties(80, "Item Code"));
				tableCols.add(new CustomTableColumnProperties(180, "Description"));
				tableCols.add(new CustomTableColumnProperties(60, "On Hand", DataTypes.LONG));
				tableCols.add(new CustomTableColumnProperties(60, "Warehouse 1", DataTypes.LONG));
				tableCols.add(new CustomTableColumnProperties(60, "Warehouse 2", DataTypes.LONG));
				tableCols.add(new CustomTableColumnProperties(60, "Available", DataTypes.LONG));
				tableCols.add(new CustomTableColumnProperties(60, "List Price", DataTypes.DOUBLE));
				tableCols.add(new CustomTableColumnProperties(60, "On Order/PO", DataTypes.LONG));
				tableCols.add(new CustomTableColumnProperties(60, "Price A", DataTypes.DOUBLE));
				tableCols.add(new CustomTableColumnProperties(120, "Product Category"));
				tableCols.add(new CustomTableColumnProperties(120, "Main Category"));
				tableCols.add(new CustomTableColumnProperties(120, "Sub Category"));
				tableCols.add(new CustomTableColumnProperties(60, "Jan"));
				tableCols.add(new CustomTableColumnProperties(60, "Feb"));
				tableCols.add(new CustomTableColumnProperties(60, "Mar"));
				tableCols.add(new CustomTableColumnProperties(60, "Apr"));
				tableCols.add(new CustomTableColumnProperties(60, "May"));
				tableCols.add(new CustomTableColumnProperties(60, "Jun"));
				tableCols.add(new CustomTableColumnProperties(60, "Jul"));
				tableCols.add(new CustomTableColumnProperties(60, "Aug"));
				tableCols.add(new CustomTableColumnProperties(60, "Sep"));
				tableCols.add(new CustomTableColumnProperties(60, "Oct"));
				tableCols.add(new CustomTableColumnProperties(60, "Nov"));
				tableCols.add(new CustomTableColumnProperties(60, "Dec"));
				
				//table.createColumns();
				table.setupColumns(tableCols);
				table.createRows(testData);
				// show component on screen
				shell.open();
				while (!shell.isDisposed()) {
					if (!display.readAndDispatch())
						display.sleep();
				}
				display.dispose();
	}

	public void setupColumns(List<CustomTableColumnProperties> tableCols) {
		for (CustomTableColumnProperties colProp : tableCols) {
			final TreeListColumn col = new TreeListColumn(treeList);
			col.setText(colProp.getColumnName());
			col.setWidth(colProp.getWidth());
			columnNames.add(colProp.getColumnName());
		}
	}
	/**
	 * @param args
	 */
	public MultiLineTable(Composite shell) {
		// create TreeListView instance
		treeList = new TreeListView(shell, SWT.V_SCROLL | SWT.H_SCROLL);
		treeList.setShowGroupbox(false);
		treeList.setShowCellSelectionBorder(true);
		treeList.setAllowDragCopy(true);
		treeList.setAlternateBackcolor(true);
		treeList.setSelectionType(SelectionType.Row);
		treeList.setRenderer(new VistaTreeListRenderer());
	}

	private void getSelectedCell(MouseEvent event, boolean mouseDown) {
		
		 for (TreeListRow ti : treeList.getRows().getAll()) {
				for (int k = 1; k < columnNames.size(); k++) {
					TreeListCell tlc = ti.getCell(k);
					tlc.setCellImage(SWTResourceManager.getImage(
							ItemEntryTable.class,
							"/com/mbi/oes/db/resources/space.png"));
				}
			}
		 
       /*Point pt = new Point(event.x, event.y);
       int index = treeList.getTopIndex();
       while (index < getItemCount()) {
         TableItem item = getItem(index);
         for (int i = 1; i < getColumnCount(); i++) {
           Rectangle rect = item.getBounds(i);
           rect.x = rect.x + 5;
           //rect.y = rect.y + rect.width - 20;
           rect.height = rect.height - 5;
           rect.width = 15;
           if (rect.contains(pt)) {
           	System.out.println("Rect: "+rect);
             System.out.println("Item " + index + " - Col:" + i);
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
           if (!visible && rect.intersects(clientArea)) {
             visible = true;
           }
         }
         if (!visible)
           return;
         index++;
       }*/
     }
	
	public void createRows(List<MultiLineTableData> data) {
		myData = data;
		// add the root-nodes
		int rowNum = 1;
		for (MultiLineTableData thisRow : data) {
			String mainRow[] = thisRow.getMainRow();
			TreeListRow row = new TreeListRow(treeList);
			new TreeListCell(row, rowNum++);

			for (String str : mainRow) {
				TreeListCell cell = new TreeListCell(row, str);
				cell.setCellImage(SWTResourceManager.getImage(
						MultiLineTable.class,
						"/com/mbi/oes/db/resources/down.png"));
			}
			// call method addChildRows
			addChildRows(treeList, row, thisRow.getChildRows());
		}
	}
	

	public void createProgressBarCell(TreeListRow row) {
		Random r = new Random();
		int value = (int) (r.nextDouble() * 100);
		new ProgressBarCell(row, value);
	}

	public void createCheckBoxCell(TreeListRow row) {
		Random r = new Random();
		boolean value = r.nextBoolean();
		new CheckBoxCell(row, value);
	}
	
	private void addChildRows(TreeListView treeList, TreeListRow row, List<String> childData) {
		
			TreeListRow childRow = new TreeListRow(row);
			new TreeListCell(childRow, row.getChildren().size());
			childRow.setChildrenLazy(false);
			for (int j = 0; j < childData.size(); j++) {
				//new TreeListCell(childRow, j+1);
				new TreeListCell(childRow, childData.get(j));
			}
	}
}
