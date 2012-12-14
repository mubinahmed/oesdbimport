package com.mbi.oes.db.utils;

public interface TableColumnProperties {
	public enum Sortable { 
		TRUE(true),
		FALSE(false);
		
		public boolean value;
		private Sortable(boolean b) {
			value = b;
		}
	}
	
	public enum NeedsSelectAllCheckBox {
		TRUE(true),
		FALSE(false);
		
		public boolean value;
		private NeedsSelectAllCheckBox(boolean b) {
			value = b;
		}
	}
	
	public enum IsInitiallyHidden {
		TRUE(true),
		FALSE(false);
		
		public boolean value;
		private IsInitiallyHidden(boolean b) {
			value = b;
		}
	}
}
