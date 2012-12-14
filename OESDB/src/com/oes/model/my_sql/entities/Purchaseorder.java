package com.oes.model.my_sql.entities;

import com.mbi.oes.db.utils.StringPool;



// Generated by DB Importer ->
// DB Importer free version - not for commercial use.
@javax.persistence.Entity
@javax.persistence.Table(name="purchaseorder", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"ponumber"})})
public class Purchaseorder {
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy=javax.persistence.GenerationType.AUTO)
	@javax.persistence.Column(name="id",length=10,nullable=false)
	private int id;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	@javax.persistence.Column(name="deliverytype",length=45)
	private String deliverytype;

	public void setDeliverytype(String deliverytype) {
		this.deliverytype = deliverytype;
	}

	public String getDeliverytype() {
		return deliverytype;
	}

	@javax.persistence.Column(name="notes",length=256)
	private String notes;

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNotes() {
		return notes;
	}

	@javax.persistence.Column(name="orderdate",length=19)
	@javax.persistence.Temporal(value=javax.persistence.TemporalType.TIMESTAMP)
	private java.util.Date orderdate;

	public void setOrderdate(java.util.Date orderdate) {
		this.orderdate = orderdate;
	}

	public java.util.Date getOrderdate() {
		return orderdate;
	}

	@javax.persistence.Column(name="orderstatus",length=45)
	private String orderstatus;

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	@javax.persistence.Column(name="podate",length=19)
	@javax.persistence.Temporal(value=javax.persistence.TemporalType.TIMESTAMP)
	private java.util.Date podate;

	public void setPodate(java.util.Date podate) {
		this.podate = podate;
	}

	public java.util.Date getPodate() {
		return podate;
	}

	@javax.persistence.Column(name="ponumber",length=45)
	private String ponumber;

	public void setPonumber(String ponumber) {
		this.ponumber = ponumber;
	}

	public String getPonumber() {
		return ponumber;
	}

	@javax.persistence.Column(name="shipping",length=45)
	private String shipping;

	public void setShipping(String shipping) {
		this.shipping = shipping;
	}

	public String getShipping() {
		return shipping != null? shipping : StringPool.BLANK;
	}

	@javax.persistence.Column(name="shipto",length=45)
	private String shipto;

	public void setShipto(String shipto) {
		this.shipto = shipto;
	}

	public String getShipto() {
		return shipto != null? shipto : StringPool.BLANK;
	}

	@javax.persistence.Column(name="vendorid",length=45)
	private String vendorid;

	public void setVendorid(String vendorid) {
		this.vendorid = vendorid;
	}

	public String getVendorid() {
		return vendorid;
	}

	@javax.persistence.Column(name="wantdate",length=19)
	@javax.persistence.Temporal(value=javax.persistence.TemporalType.TIMESTAMP)
	private java.util.Date wantdate;

	public void setWantdate(java.util.Date wantdate) {
		this.wantdate = wantdate;
	}

	public java.util.Date getWantdate() {
		return wantdate;
	}

	@javax.persistence.Column(name="totalcost",length=12)
	private Double totalcost;

	public Double getTotalcost() {
		 return totalcost != null ? totalcost : 0;
	}

	public void setTotalcost(Double totalcost) {
		this.totalcost = totalcost;
	}

	// <- Generated by DB Importer
}