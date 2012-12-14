package com.oes.model.my_sql.entities;

// Generated by DB Importer ->
// DB Importer free version - not for commercial use.
@javax.persistence.Entity
@javax.persistence.Table(name = "warehouseitemdetails")
public class Warehouseitemdetails {
	public static final String QTY = "quantity";
	public static final String SP = "sellingprice";
	public static final String INSTOCK = "instock";
	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	@javax.persistence.Column(name = "id", length = 19, nullable = false)
	private long id;

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	@javax.persistence.Column(name = "committed", length = 10)
	private Integer committed;

	public void setCommitted(Integer committed) {
		this.committed = committed;
	}

	public Integer getCommitted() {
		if(committed == null) {
			return 0;
		}
		return committed;
	}

	@javax.persistence.Column(name = "itemcode", length = 20, nullable = false)
	private String itemcode = "";

	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}

	public String getItemcode() {
		return itemcode;
	}

	@javax.persistence.Column(name = "onhand", length = 10)
	private Integer onhand;

	public void setOnhand(Integer onhand) {
		this.onhand = onhand;
	}

	public Integer getOnhand() {
		if (onhand == null) {
			return 0;
		} else {
			return onhand;
		}
	}

	@javax.persistence.Column(name = "warehouseid", length = 10, nullable = false)
	private int warehouseid;

	public void setWarehouseid(int warehouseid) {
		this.warehouseid = warehouseid;
	}

	public int getWarehouseid() {
		return warehouseid;
	}

	@javax.persistence.Column(name = "Active", length = 10)
	private Boolean active;

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getActive() {
		return active;
	}

	@javax.persistence.Column(name = "AllowByLocal", length = 5)
	private String allowbylocal;

	public void setAllowbylocal(String allowbylocal) {
		this.allowbylocal = allowbylocal;
	}

	public String getAllowbylocal() {
		return allowbylocal;
	}

	@javax.persistence.Column(name = "AverageCost", length = 12)
	private Float averagecost;

	public void setAveragecost(Float averagecost) {
		this.averagecost = averagecost;
	}

	public Float getAveragecost() {
		return averagecost;
	}

	@javax.persistence.Column(name = "AverageLocalCost", length = 12)
	private Float averagelocalcost;

	public void setAveragelocalcost(Float averagelocalcost) {
		this.averagelocalcost = averagelocalcost;
	}

	public Float getAveragelocalcost() {
		return averagelocalcost;
	}

	@javax.persistence.Column(name = "BuyingPrice", length = 12)
	private Float buyingprice;

	public void setBuyingprice(Float buyingprice) {
		this.buyingprice = buyingprice;
	}

	public Float getBuyingprice() {
		return buyingprice;
	}

	@javax.persistence.Column(name = "CheckStock", length = 5)
	private String checkstock;

	public void setCheckstock(String checkstock) {
		this.checkstock = checkstock;
	}

	public String getCheckstock() {
		return checkstock;
	}

	@javax.persistence.Column(name = "Commission", length = 12)
	private Float commission;

	public void setCommission(Float commission) {
		this.commission = commission;
	}

	public Float getCommission() {
		return commission;
	}

	@javax.persistence.Column(name = "Costs", length = 12)
	private Float costs;

	public void setCosts(Float costs) {
		this.costs = costs;
	}

	public Float getCosts() {
		return costs;
	}

	@javax.persistence.Column(name = "DateCreated", length = 10)
	@javax.persistence.Temporal(value = javax.persistence.TemporalType.DATE)
	private java.util.Date datecreated;

	public void setDatecreated(java.util.Date datecreated) {
		this.datecreated = datecreated;
	}

	public java.util.Date getDatecreated() {
		return datecreated;
	}

	@javax.persistence.Column(name = "ExpectedDate", length = 10)
	@javax.persistence.Temporal(value = javax.persistence.TemporalType.DATE)
	private java.util.Date expecteddate;

	public void setExpecteddate(java.util.Date expecteddate) {
		this.expecteddate = expecteddate;
	}

	public java.util.Date getExpecteddate() {
		return expecteddate;
	}

	@javax.persistence.Column(name = "InStock", length = 10)
	private String instock;

	public void setInstock(String instock) {
		this.instock = instock;
	}

	public String getInstock() {
		return instock;
	}

	@javax.persistence.Column(name = "LastCost", length = 12)
	private Float lastcost;

	public void setLastcost(Float lastcost) {
		this.lastcost = lastcost;
	}

	public Float getLastcost() {
		return lastcost;
	}

	@javax.persistence.Column(name = "LastLocalCost", length = 12)
	private Float lastlocalcost;

	public void setLastlocalcost(Float lastlocalcost) {
		this.lastlocalcost = lastlocalcost;
	}

	public Float getLastlocalcost() {
		return lastlocalcost;
	}

	@javax.persistence.Column(name = "LastSaleDate", length = 10)
	@javax.persistence.Temporal(value = javax.persistence.TemporalType.DATE)
	private java.util.Date lastsaledate;

	public void setLastsaledate(java.util.Date lastsaledate) {
		this.lastsaledate = lastsaledate;
	}

	public java.util.Date getLastsaledate() {
		return lastsaledate;
	}

	@javax.persistence.Column(name = "LeadTime", length = 5)
	private String leadtime;

	public void setLeadtime(String leadtime) {
		this.leadtime = leadtime;
	}

	public String getLeadtime() {
		return leadtime;
	}

	@javax.persistence.Column(name = "Maximum", length = 10)
	private Integer maximum;

	public void setMaximum(Integer maximum) {
		this.maximum = maximum;
	}

	public Integer getMaximum() {
		return maximum;
	}

	@javax.persistence.Column(name = "Minimum", length = 10)
	private Integer minimum;

	public void setMinimum(Integer minimum) {
		this.minimum = minimum;
	}

	public Integer getMinimum() {
		return minimum;
	}

	@javax.persistence.Column(name = "OrderDate", length = 10)
	@javax.persistence.Temporal(value = javax.persistence.TemporalType.DATE)
	private java.util.Date orderdate;

	public void setOrderdate(java.util.Date orderdate) {
		this.orderdate = orderdate;
	}

	public java.util.Date getOrderdate() {
		return orderdate;
	}

	@javax.persistence.Column(name = "OrderLocalDate", length = 10)
	@javax.persistence.Temporal(value = javax.persistence.TemporalType.DATE)
	private java.util.Date orderlocaldate;

	public void setOrderlocaldate(java.util.Date orderlocaldate) {
		this.orderlocaldate = orderlocaldate;
	}

	public java.util.Date getOrderlocaldate() {
		return orderlocaldate;
	}

	@javax.persistence.Column(name = "PurchaseLocalQuantity", length = 10)
	private Integer purchaselocalquantity;

	public void setPurchaselocalquantity(Integer purchaselocalquantity) {
		this.purchaselocalquantity = purchaselocalquantity;
	}

	public Integer getPurchaselocalquantity() {
		return purchaselocalquantity;
	}

	@javax.persistence.Column(name = "PurchaseQuantity", length = 10)
	private Integer purchasequantity;

	public void setPurchasequantity(Integer purchasequantity) {
		this.purchasequantity = purchasequantity;
	}

	public Integer getPurchasequantity() {
		return purchasequantity;
	}

	@javax.persistence.Column(name = "Quantity", length = 10)
	private Integer quantity;

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getQuantity() {
		return quantity;
	}

	@javax.persistence.Column(name = "Sales", length = 12)
	private Float sales;

	public void setSales(Float sales) {
		this.sales = sales;
	}

	public Float getSales() {
		return sales;
	}

	@javax.persistence.Column(name = "SalesQtyMonth", length = 12)
	private Float salesqtymonth;

	public void setSalesqtymonth(Float salesqtymonth) {
		this.salesqtymonth = salesqtymonth;
	}

	public Float getSalesqtymonth() {
		return salesqtymonth;
	}

	@javax.persistence.Column(name = "SalesQtyWeek", length = 12)
	private Float salesqtyweek;

	public void setSalesqtyweek(Float salesqtyweek) {
		this.salesqtyweek = salesqtyweek;
	}

	public Float getSalesqtyweek() {
		return salesqtyweek;
	}

	@javax.persistence.Column(name = "SalesQtyYear", length = 12)
	private Float salesqtyyear;

	public void setSalesqtyyear(Float salesqtyyear) {
		this.salesqtyyear = salesqtyyear;
	}

	public Float getSalesqtyyear() {
		return salesqtyyear;
	}

	@javax.persistence.Column(name = "SellingPrice", length = 12)
	private Float sellingprice;

	public void setSellingprice(Float sellingprice) {
		this.sellingprice = sellingprice;
	}

	public Float getSellingprice() {
		if (sellingprice == null) {
			return 0f;
		}
		return sellingprice;
	}

}