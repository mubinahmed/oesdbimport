package com.mbi.oes.db.controls;

public interface TableBaseQuery {
	public void execute();
	public void execute(int start, int count);
}
