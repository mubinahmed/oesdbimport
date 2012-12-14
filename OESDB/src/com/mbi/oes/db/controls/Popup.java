package com.mbi.oes.db.controls;

import org.eclipse.swt.widgets.TableItem;

public abstract class Popup {
    public static final int WIDTH = 865;
    public static final int HEIGHT = 125;
    public abstract void dispose();
    public abstract void setInitiatedControl(TableItem tableItem);
    public abstract void open();
    public abstract void setSearchText(String searchText);
    public abstract void setPosition(int xPos, int yPos);
    protected String searchText;
}
