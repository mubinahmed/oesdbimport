package com.oes.model.my_sql.entities;

@javax.persistence.Entity
@javax.persistence.Table(name="sequencegen")
public class Sequencegen {

	@javax.persistence.Id
	@javax.persistence.Column(name="entityname", length=20)
	private String entityname;
	
	@javax.persistence.Column(name="prefix",length=20)
	private String prefix;
	
	@javax.persistence.Column(name="suffix",length=20)
	private Long suffix;
	
	@javax.persistence.Column(name="length",length=2)
	private int length;

	public String getEntityname() {
		return entityname;
	}

	public void setEntityname(String entityname) {
		this.entityname = entityname;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Long getSuffix() {
		return suffix;
	}

	public void setSuffix(Long suffix) {
		this.suffix = suffix;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	
}
