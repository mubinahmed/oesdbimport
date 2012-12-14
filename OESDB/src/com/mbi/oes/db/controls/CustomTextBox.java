package com.mbi.oes.db.controls;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.SimpleContentProposalProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Caret;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.wb.swt.SWTResourceManager;

import com.mbi.oes.db.utils.OES;

//public class CustomTextBox extends StyledText{
public class CustomTextBox extends StyledText{

	// Keep all the fields protected so that they can be access in subclasses directly
	protected Composite composite_;
	protected int style_;
	protected String dataType_;
	protected Control next_;
	protected Control previous_;
	protected boolean validate_;
	protected boolean isMandatory_ = false;
	protected int length_ = 100; //default 100 characters/digits
	
	protected boolean byPassVerifyListener_ = false;
	protected boolean isFieldAssistEnabled_ = false;
	protected String fieldAssistTableName_;
	protected String fieldAssistColumn_;
	protected int minSize_ = -1;
	protected int maxSize_ = -1;
	protected Label myLabel_ = null;
	protected boolean isPrimary_ = false;
	protected boolean ignore_ = false;
	protected String validationErrorMsg = "";
	private Color background_ = SWTResourceManager.getColor(255, 255, 255);
	
	public CustomTextBox(Composite composite, int style){
		super(composite, style);
		dataType_ = "String";
		isMandatory_ = false;
		validate_ = false;
		setDefaultFormatting();
	}
	
	public CustomTextBox(Composite composite, int style, String dataType){
		super(composite, style);
		this.dataType_ = dataType;
		isMandatory_ = false;
		validate_ = false;
		addConstraint();
		setDefaultFormatting();
	}
	
	public CustomTextBox(Composite composite, int style, boolean isMandatory){
		super(composite, style);
		dataType_ = "String";
		this.isMandatory_ = isMandatory;
		validate_ = isMandatory;
		setDefaultFormatting();
	}
	
	public CustomTextBox(Composite composite, int style, Control next){
		super(composite, style);
		this.next_ = next;
		dataType_ = "String";
		isMandatory_ = false;
		validate_ = false;
		setDefaultFormatting();
	}
	
	public CustomTextBox(Composite composite, int style, Control next, boolean isMandatory){
		super(composite, style);
		this.next_ = next;
		this.isMandatory_ = isMandatory;
		dataType_ = "String";
		validate_ = false;
		setDefaultFormatting();
	}
	
	public CustomTextBox(Composite composite, int style, boolean isMandatory, String dataType){
		super(composite, style);
		this.isMandatory_ = isMandatory;
		this.dataType_ = dataType;
		validate_ = false;
		addConstraint();
		setDefaultFormatting();
	}
	
	public CustomTextBox(Composite composite, int style, Control next, Control previous){
		super(composite, style);
		this.next_ = next;
		this.previous_ = previous;
		dataType_ = "String";
		isMandatory_ = false;
		validate_ = false;
		setDefaultFormatting();
	}
	
	public CustomTextBox(Composite composite, int style, Control next, Control previous, boolean isMandatory){
		super(composite, style);
		this.next_ = next;
		this.previous_ = previous;
		this.isMandatory_ = isMandatory;
		dataType_ = "String";
		validate_ = false;
		setDefaultFormatting();
	}
	
	
	public CustomTextBox(Composite composite, int style, Control next, Control previous, String dataType, boolean validate){
		super(composite, style);
		this.next_ = next;
		this.previous_ = previous;
		this.dataType_ = dataType;
		this.validate_ = validate;
		this.isMandatory_ = false;
		addConstraint();
		setDefaultFormatting();
	}
	
	public CustomTextBox(Composite composite, int style, Control next, Control previous, String dataType, boolean validate, boolean isMandatory){
		super(composite, style);
		this.next_ = next;
		this.previous_ = previous;
		this.dataType_ = dataType;
		this.validate_ = validate;
		this.isMandatory_ = isMandatory;
		addConstraint();
		setDefaultFormatting();
	}
	
	public CustomTextBox(Composite composite, int style, String dataType, boolean validate, boolean isMandatory){
		super(composite, style);
		this.isMandatory_ = isMandatory;
		this.dataType_ = dataType;
		this.validate_ = validate;
		addConstraint();
		setDefaultFormatting();
	}
	
	protected void addConstraint(){
		// custom text box constraints...
	}
	
	protected boolean validateLength(){
		if(minSize_ == -1 || maxSize_ == -1){
			return true;
		}
		if(getText().length() >= minSize_ && getText().length() <= maxSize_)
			return true;
		else 
			return false;
	}
	public void addKeyListener(final Control allControls[]){
		
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode == 13 || e.keyCode == SWT.ESC) {
					if(!validateLength()){
						if ( (!isMandatory_ && getText().trim().length() > 0) || isMandatory_){
							MessageBox msg = new MessageBox(
									CustomTextBox.this.getShell(), SWT.NONE);
							msg.setMessage(validationErrorMsg);
							msg.open();
							return;
						}
					}
					for (int i = 0; i < allControls.length; i++) {
						String controlClassName = allControls[i].getClass().getName();
						//if("com.oes.controls.CustomTextBox".equalsIgnoreCase(controlClassName)){
						if( allControls[i] instanceof com.mbi.oes.db.controls.CustomTextBox){
							CustomTextBox text = (CustomTextBox)allControls[i];
							if(text == CustomTextBox.this) {
								previous_ = (i >0 ) ? allControls[i-1] : null;
								next_ = (i+1 < allControls.length) ? allControls[i+1] : null;
								break;
							}
						}
					}
				}
				if(e.keyCode == 13){
					if(isMandatory() && getText().length() == 0 && !ignore_ ){
						MessageDialog.openError(CustomTextBox.this.getShell(), "Invalid value", "Mandatory field cannot be empty");
						setFocus();
						return;
					}
					if(ignore_) {
						ignore_ = false;
						return;
					}
					if (next_ != null) {
						next_.setFocus();
					}
				} else if(e.keyCode == SWT.ESC) {
					if(previous_ != null){
						previous_.setFocus();
					}
				}
			}

			
		});
	}

	
	public void setInt(long x){
		byPassVerifyListener_ = true;
		setText(x+"");
		byPassVerifyListener_ = false;
	}
	
	@Override
	protected void checkSubclass() {
		//Do nothing
	}
	
	public void setNext(Control c){
		this.next_ = c;
	}

	public void setLabel(Label label){
		myLabel_ = label;
	}
	
	public boolean hasLabel(){
		return myLabel_ != null;
	}
	
	public Label getLabel(){
		return myLabel_;
	}
	public void setPrevious(Control c) {
		this.previous_ = c;		
	}
	
	public Object getData(){
		return null;
	}
	
	public boolean isEmpty(){
		return getText() == null || getText().length() == 0;
	}

	public String getDataType() {
		return dataType_;
	}

	public void setDataType(String dataType) {
		this.dataType_ = dataType;
	}

	public boolean isValidate() {
		return validate_;
	}

	public void setValidate(boolean validate) {
		this.validate_ = validate;
	}

	public boolean isMandatory() {
		return isMandatory_;
	}

	public void setMandatory(boolean isMandatory) {
		this.isMandatory_ = isMandatory;
	}

	public Control getNext() {
		return next_;
	}

	public Control getPrevious() {
		return previous_;
	}

	public void setLength(int length) {
		this.length_ = length;
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if(hasLabel()) {
			myLabel_.setVisible(visible);
		}
	}
	
	protected void setDefaultFormatting(){
		setFont(SWTResourceManager.getFont("Tahoma", 9, SWT.BOLD));
		
		Caret c = new Caret(this, SWT.NONE);
		c.setSize(OES.CARET_WIDTH, OES.CARET_HEIGHT);
		this.setCaret(c);
	}
	
	public void setFieldAssist(String tableName, String colName){
		/*BasicDAO dao = new BasicDAO();
		java.util.List list = dao.listColumnValues(tableName, colName, true);
		String possibleValues[] = new String[list.size()];
		for(int i=0; i<list.size(); i++){
			possibleValues[i] = String.valueOf(list.get(i));
		}
		
		SimpleContentProposalProvider proposalDriver = new SimpleContentProposalProvider(possibleValues);
		proposalDriver.setFiltering(true);
		final ContentProposalAdapter adapter = new ContentProposalAdapter(
				this, new StyledTextContentAdapter(), 
				proposalDriver,
				null, null);
		adapter.setEnabled(true);
		adapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
		fieldAssistTableName_ = tableName;
		fieldAssistColumn_ = colName;
		isFieldAssistEnabled_ = true;*/
	}
	
	public void refreshFieldAssist(){
		if (isFieldAssistEnabled_) {
			setFieldAssist(fieldAssistTableName_, fieldAssistColumn_);
		}
	}
	

	public boolean isFieldAssistRequired(){
		return isFieldAssistEnabled_;
	}
	//static int count = 0;
	public void clear() {
		try {
				byPassVerifyListener_ = true;
				setText("");
				byPassVerifyListener_ = false;
		} catch (ClassCastException e) {
			
		}
		
	}

	public void setTextLength(int i) {
		minSize_ = i;
		maxSize_ = i;
		length_ = i;
	}

	public boolean isPrimary() {
		return isPrimary_ ;
	}

	public void setAsPrimary(){
		isPrimary_ = true;
	}

	public void setIgnore() {
		ignore_ = true;
		
	}
	
	public void setValidationErrorMsg(String valString) {
		this.validationErrorMsg = valString;
	}
	@Override
	public void setBackground(Color color) {
		super.setBackground(color);
		background_  = color;
	}
	public void setEnabled(boolean enabled, Color backGroundColor) {
		super.setEnabled(enabled);
		if(!enabled){
			setBackground(backGroundColor);
		}else {
			setBackground(background_);
		}
	}
	@Override
	public int hashCode() {
		int hash =  super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
