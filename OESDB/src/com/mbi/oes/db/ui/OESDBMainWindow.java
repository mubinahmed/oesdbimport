package com.mbi.oes.db.ui;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

import com.mbi.oes.listeners.PageChangedListener;


public class OESDBMainWindow {
	private static Composite topComposite;
	private static PrepareDataInstructionsScreen importInstructions;
	private static Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		/*try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}*/
		Display display = Display.getDefault();
		shell = new Shell();
		//shell.setSize(450, 300);
		shell.setText("OES DB Utility");
		shell.setMaximized(true);
		shell.setLayout(new GridLayout(1, false));
		
		topComposite = new Composite(shell, SWT.NONE);
		topComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		topComposite.setLayout(new GridLayout(1, false));
		
		createDBInfoUI();

		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private static void createDBInfoUI() {
		final DBInformationScreen dbInfoScreen = new DBInformationScreen(shell, SWT.NONE);
		dbInfoScreen.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		dbInfoScreen.setLayout(new GridLayout(1, false));
		
		dbInfoScreen.addPageChangeListener(new PageChangedListener() {
			
			@Override
			public void previousPage() {
				
			}
			
			@Override
			public void nextPage() {
				dbInfoScreen.dispose();
				createImportTypeUI();
				shell.layout(true);
			}
		});
	}
	
	private static void createImportTypeUI() {
		final SelectImportTypeScreen importTypeScreen = new SelectImportTypeScreen(shell, SWT.NONE);
		importTypeScreen.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		importTypeScreen.setLayout(new GridLayout(1, false));
		
		importTypeScreen.addPageChangeListener(new PageChangedListener() {
			
			@Override
			public void previousPage() {
				createDBInfoUI();
			}
			
			@Override
			public void nextPage() {
				importTypeScreen.dispose();
				createPrepareDataUI();
				shell.layout(true);
			}
		});
	}

	private static void createPrepareDataUI() {
		importInstructions = new PrepareDataInstructionsScreen(shell, SWT.NONE);
		importInstructions.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		importInstructions.setLayout(new GridLayout(1, false));
		importInstructions.addPageChangeListener(new PageChangedListener() {
			
			@Override
			public void previousPage() {
				importInstructions.dispose();
				createImportTypeUI();
				shell.layout(true);
			}
			
			@Override
			public void nextPage() {
				importInstructions.dispose();
				createSelectImportFileUI();
				shell.layout(true);
			}
		});
	}
	
	
	private static void createSelectImportFileUI() {
		final SelectImportFileScreen importFileScreen = new SelectImportFileScreen(shell, SWT.NONE);
		importFileScreen.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		importFileScreen.setLayout(new GridLayout(1, false));
		importFileScreen.addPageChangeListener(new PageChangedListener() {
			
			@Override
			public void previousPage() {
				importFileScreen.dispose();
				createPrepareDataUI();
				shell.layout(true);
			}
			
			@Override
			public void nextPage() {
				importFileScreen.dispose();
				createColumnMappingUI();
				shell.layout(true);
			}
		});
	}

	protected static void createColumnMappingUI() {
		final ColumnMappingScreen columnMappingScreen = new ColumnMappingScreen(shell, SWT.NONE);
		columnMappingScreen.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		columnMappingScreen.setLayout(new GridLayout(1, false));
		columnMappingScreen.addPageChangeListener(new PageChangedListener() {
			
			@Override
			public void previousPage() {
				columnMappingScreen.dispose();
				createSelectImportFileUI();
				shell.layout(true);
			}
			
			@Override
			public void nextPage() {
				columnMappingScreen.dispose();
				createReviewUI();
				shell.layout(true);
			}
		});
	}

	protected static void createReviewUI() {
		final ReviewAndFinishImportScreen reviewScreen = new ReviewAndFinishImportScreen(shell, SWT.NONE);
		reviewScreen.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		reviewScreen.setLayout(new GridLayout(1, false));
		reviewScreen.addPageChangeListener(new PageChangedListener() {
			
			@Override
			public void previousPage() {
				reviewScreen.dispose();
				createColumnMappingUI();
				shell.layout(true);
			}
			
			@Override
			public void nextPage() {
				reviewScreen.dispose();
				createFinishUI();
				shell.layout(true);
			}
		});
	}

	protected static void createFinishUI() {
		final ImportCompletedScreen completedScreen = new ImportCompletedScreen(shell, SWT.NONE);
		completedScreen.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		completedScreen.setLayout(new GridLayout(1, false));
		completedScreen.addPageChangeListener(new PageChangedListener() {
			
			@Override
			public void previousPage() {
			}
			
			@Override
			public void nextPage() {
				completedScreen.dispose();
				createDBInfoUI();
				shell.layout(true);
			}
		});
	}
}
