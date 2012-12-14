package com.mbi.oes.db.controls;

import org.eclipse.swt.graphics.Image;

public class TableColumnInfo {
    private int index;
    private String text;
    private Image image;
    private int width;
    private boolean resizable;
    private boolean moveable;
    boolean visible;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getText() {
	return text;
    }

    public void setText(String text) {
	this.text = text;
    }

    public Image getImage() {
	return image;
    }

    public void setImage(Image image) {
	this.image = image;
    }

    public int getWidth() {
	return width;
    }

    public void setWidth(int width) {
	this.width = width;
    }

    public boolean isResizable() {
	return resizable;
    }

    public void setResizable(boolean resizable) {
	this.resizable = resizable;
    }

    public boolean isMoveable() {
	return moveable;
    }

    public void setMoveable(boolean moveable) {
	this.moveable = moveable;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
