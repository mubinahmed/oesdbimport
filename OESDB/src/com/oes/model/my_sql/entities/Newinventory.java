package com.oes.model.my_sql.entities;

import java.util.Arrays;

import com.mbi.oes.db.utils.StringPool;



// Generated by DB Importer ->
// DB Importer free version - not for commercial use.
@javax.persistence.Entity
@javax.persistence.Table(name="NewInventory")
public class Newinventory {
  @javax.persistence.Id
  //@javax.persistence.GeneratedValue(strategy=javax.persistence.GenerationType.AUTO)
  //@javax.persistence.Column(name="ProductID",length=19,nullable=false)
  private String productid;

  public void setProductid(String productid) {
    this.productid = productid;
  }

  public String getProductid() {
    return productid;
  }

  @javax.persistence.Column(name="Active",length=10)
  private Boolean active;

  public void setActive(Boolean active) {
    this.active = active;
  }

  public Boolean getActive() {
    return active;
  }

  @javax.persistence.Column(name="AllowByLocal",length=5)
  private String allowbylocal;

  public void setAllowbylocal(String allowbylocal) {
    this.allowbylocal = allowbylocal;
  }

  public String getAllowbylocal() {
    return allowbylocal;
  }

  @javax.persistence.Column(name="AverageCost",length=12)
  private Double averagecost;

  public void setAveragecost(Double averagecost) {
    this.averagecost = averagecost;
  }

  public Double getAveragecost() {
    return averagecost;
  }

  @javax.persistence.Column(name="AverageLocalCost",length=12)
  private Double averagelocalcost;

  public void setAveragelocalcost(Double averagelocalcost) {
    this.averagelocalcost = averagelocalcost;
  }

  public Double getAveragelocalcost() {
    return averagelocalcost;
  }

  @javax.persistence.Column(name="BuyingPrice",length=12)
  private Double buyingprice;

  public void setBuyingprice(Double buyingprice) {
    this.buyingprice = buyingprice;
  }

  public Double getBuyingprice() {
    return buyingprice;
  }

  @javax.persistence.Column(name="Category",length=50)
  private String category;

  public void setCategory(String category) {
    this.category = category;
  }

  public String getCategory() {
    return category != null ? category : StringPool.BLANK;
  }

  @javax.persistence.Column(name="CheckStock",length=5)
  private String checkstock;

  public void setCheckstock(String checkstock) {
    this.checkstock = checkstock;
  }

  public String getCheckstock() {
    return checkstock;
  }

  @javax.persistence.Column(name="Commission",length=12)
  private Double commission;

  public void setCommission(Double commission) {
    this.commission = commission;
  }

  public Double getCommission() {
    return commission;
  }

  @javax.persistence.Column(name="Costs",length=12)
  private Double costs;

  public void setCosts(Double costs) {
    this.costs = costs;
  }

  public Double getCosts() {
    return costs;
  }

  @javax.persistence.Column(name="DateCreated",length=10)
  @javax.persistence.Temporal(value=javax.persistence.TemporalType.DATE)
  private java.util.Date datecreated;

  public void setDatecreated(java.util.Date datecreated) {
    this.datecreated = datecreated;
  }

  public java.util.Date getDatecreated() {
    return datecreated;
  }

  @javax.persistence.Column(name="Description",length=50)
  private String description;

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
	if(description == null) {
		return StringPool.BLANK;
	}
    return description;
  }

  @javax.persistence.Column(name="Description2",length=50)
  private String description2;

  public void setDescription2(String description2) {
    this.description2 = description2;
  }

  public String getDescription2() {
    return description2;
  }

  @javax.persistence.Column(name="DiscountA",length=12)
  private Double discounta;

  public void setDiscounta(Double discounta) {
    this.discounta = discounta;
  }

  public Double getDiscounta() {
    return discounta;
  }

  @javax.persistence.Column(name="DiscountB",length=12)
  private Double discountb;

  public void setDiscountb(Double discountb) {
    this.discountb = discountb;
  }

  public Double getDiscountb() {
    return discountb;
  }

  @javax.persistence.Column(name="DiscountC",length=12)
  private Double discountc;

  public void setDiscountc(Double discountc) {
    this.discountc = discountc;
  }

  public Double getDiscountc() {
    return discountc;
  }

  @javax.persistence.Column(name="DiscountD",length=12)
  private Double discountd;

  public void setDiscountd(Double discountd) {
    this.discountd = discountd;
  }

  public Double getDiscountd() {
    return discountd;
  }

  @javax.persistence.Column(name="DiscountE",length=12)
  private Double discounte;

  public void setDiscounte(Double discounte) {
    this.discounte = discounte;
  }

  public Double getDiscounte() {
    return discounte;
  }

  @javax.persistence.Column(name="ExpectedDate",length=10)
  @javax.persistence.Temporal(value=javax.persistence.TemporalType.DATE)
  private java.util.Date expecteddate;

  public void setExpecteddate(java.util.Date expecteddate) {
    this.expecteddate = expecteddate;
  }

  public java.util.Date getExpecteddate() {
    return expecteddate;
  }

  @javax.persistence.Column(name="InStock",length=10)
  private String instock;

  public void setInstock(String instock) {
    this.instock = instock;
  }

  public String getInstock() {
    return instock;
  }

  @javax.persistence.Column(name="LastCost",length=12)
  private Double lastcost;

  public void setLastcost(Double lastcost) {
    this.lastcost = lastcost;
  }

  public Double getLastcost() {
    return lastcost;
  }

  @javax.persistence.Column(name="LastLocalCost",length=12)
  private Double lastlocalcost;

  public void setLastlocalcost(Double lastlocalcost) {
    this.lastlocalcost = lastlocalcost;
  }

  public Double getLastlocalcost() {
    return lastlocalcost;
  }

  @javax.persistence.Column(name="LastSaleDate",length=10)
  @javax.persistence.Temporal(value=javax.persistence.TemporalType.DATE)
  private java.util.Date lastsaledate;

  public void setLastsaledate(java.util.Date lastsaledate) {
    this.lastsaledate = lastsaledate;
  }

  public java.util.Date getLastsaledate() {
    return lastsaledate;
  }

  @javax.persistence.Column(name="LeadTime",length=5)
  private String leadtime;

  public void setLeadtime(String leadtime) {
    this.leadtime = leadtime;
  }

  public String getLeadtime() {
    return leadtime;
  }

  @javax.persistence.Column(name="ListPrice",length=12)
  private Double listprice;

  public void setListprice(Double listprice) {
    this.listprice = listprice;
  }

  public Double getListprice() {
    return listprice;
  }

  @javax.persistence.Column(name="Maximum",length=10)
  private Integer maximum;

  public void setMaximum(Integer maximum) {
    this.maximum = maximum;
  }

  public Integer getMaximum() {
    return maximum;
  }

  @javax.persistence.Column(name="Minimum",length=10)
  private Integer minimum;

  public void setMinimum(Integer minimum) {
    this.minimum = minimum;
  }

  public Integer getMinimum() {
    return minimum;
  }

  @javax.persistence.Column(name="OrderDate",length=10)
  @javax.persistence.Temporal(value=javax.persistence.TemporalType.DATE)
  private java.util.Date orderdate;

  public void setOrderdate(java.util.Date orderdate) {
    this.orderdate = orderdate;
  }

  public java.util.Date getOrderdate() {
    return orderdate;
  }

  @javax.persistence.Column(name="OrderLocalDate",length=10)
  @javax.persistence.Temporal(value=javax.persistence.TemporalType.DATE)
  private java.util.Date orderlocaldate;

  public void setOrderlocaldate(java.util.Date orderlocaldate) {
    this.orderlocaldate = orderlocaldate;
  }

  public java.util.Date getOrderlocaldate() {
    return orderlocaldate;
  }

  @javax.persistence.Column(name="PriceA",length=12)
  private Double pricea;

  public void setPricea(Double pricea) {
    this.pricea = pricea;
  }

  public Double getPricea() {
    return pricea;
  }

  @javax.persistence.Column(name="PriceB",length=12)
  private Double priceb;

  public void setPriceb(Double priceb) {
    this.priceb = priceb;
  }

  public Double getPriceb() {
    return priceb;
  }

  @javax.persistence.Column(name="PriceC",length=12)
  private Double pricec;

  public void setPricec(Double pricec) {
    this.pricec = pricec;
  }

  public Double getPricec() {
    return pricec;
  }

  @javax.persistence.Column(name="PriceCode",length=50)
  private String pricecode;

  public void setPricecode(String pricecode) {
    this.pricecode = pricecode;
  }

  public String getPricecode() {
    return pricecode;
  }

  @javax.persistence.Column(name="PriceD",length=12)
  private Double priced;

  public void setPriced(Double priced) {
    this.priced = priced;
  }

  public Double getPriced() {
    return priced;
  }

  @javax.persistence.Column(name="PriceE",length=12)
  private Double pricee;

  public void setPricee(Double pricee) {
    this.pricee = pricee;
  }

  public Double getPricee() {
    return pricee;
  }

  @javax.persistence.Column(name="ProductCategory",length=50)
  private String productcategory;

  public void setProductcategory(String productcategory) {
    this.productcategory = productcategory;
  }

  public String getProductcategory() {
    return productcategory == null ? StringPool.BLANK : productcategory;
  }

  @javax.persistence.Column(name="PurchaseLocalQuantity",length=10)
  private Integer purchaselocalquantity;

  public void setPurchaselocalquantity(Integer purchaselocalquantity) {
    this.purchaselocalquantity = purchaselocalquantity;
  }

  public Integer getPurchaselocalquantity() {
    return purchaselocalquantity;
  }

  @javax.persistence.Column(name="PurchaseQuantity",length=10)
  private Integer purchasequantity;

  public void setPurchasequantity(Integer purchasequantity) {
    this.purchasequantity = purchasequantity;
  }

  public Integer getPurchasequantity() {
    return purchasequantity;
  }

  @javax.persistence.Column(name="Quantity",length=10)
  private Integer quantity;

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Integer getQuantity() {
    return quantity;
  }

  @javax.persistence.Column(name="Sales",length=12)
  private Double sales;

  public void setSales(Double sales) {
    this.sales = sales;
  }

  public Double getSales() {
    return sales;
  }

  @javax.persistence.Column(name="SalesQtyMonth",length=12)
  private Double salesqtymonth;

  public void setSalesqtymonth(Double salesqtymonth) {
    this.salesqtymonth = salesqtymonth;
  }

  public Double getSalesqtymonth() {
    return salesqtymonth;
  }

  @javax.persistence.Column(name="SalesQtyWeek",length=12)
  private Double salesqtyweek;

  public void setSalesqtyweek(Double salesqtyweek) {
    this.salesqtyweek = salesqtyweek;
  }

  public Double getSalesqtyweek() {
    return salesqtyweek;
  }

  @javax.persistence.Column(name="SalesQtyYear",length=12)
  private Double salesqtyyear;

  public void setSalesqtyyear(Double salesqtyyear) {
    this.salesqtyyear = salesqtyyear;
  }

  public Double getSalesqtyyear() {
    return salesqtyyear;
  }

  @javax.persistence.Column(name="SellingPrice",length=12)
  private Double sellingprice;

  public void setSellingprice(Double sellingprice) {
    this.sellingprice = sellingprice;
  }

  public Double getSellingprice() {
    return sellingprice;
  }

  @javax.persistence.Column(name="SubCategory",length=50)
  private String subcategory;

  public void setSubcategoryid(String subcategoryid) {
    this.subcategory = subcategoryid;
  }

  public String getSubcategoryid() {
    return subcategory != null ? subcategory : StringPool.BLANK;
  }

  @javax.persistence.Column(name="TaxCode",length=5)
  private String taxcode;

  public void setTaxcode(String taxcode) {
    this.taxcode = taxcode;
  }

  public String getTaxcode() {
    return taxcode;
  }

  @javax.persistence.Column(name="UPCCode",length=50)
  private String upccode;

  public void setUpccode(String upccode) {
    this.upccode = upccode;
  }

  public String getUpccode() {
	  if (upccode == null) {
		  return StringPool.BLANK;
	  }
    return upccode;
  }

  @javax.persistence.Column(name="VendorID",length=19)
  private String vendorid;

  public void setVendorid(String vendorid) {
    this.vendorid = vendorid;
  }

  public String getVendorid() {
	  if(vendorid == null) {
		  return StringPool.BLANK;
	  }
    return vendorid;
  }

  @javax.persistence.Column(name="WebDescription",length=50)
  private String webdescription;

  public void setWebdescription(String webdescription) {
    this.webdescription = webdescription;
  }

  public String getWebdescription() {
    return webdescription;
  }

  @javax.persistence.Column(name="WebGraphicsFile",length=2147483647)
  private byte[] webgraphicsfile;

  public void setWebgraphicsfile(byte[] webgraphicsfile) {
    this.webgraphicsfile = webgraphicsfile;
  }

  public byte[] getWebgraphicsfile() {
    return webgraphicsfile;
  }
// <- Generated by DB Importer

@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("NewInventory [productid=");
	builder.append(productid);
	builder.append(", active=");
	builder.append(active);
	builder.append(", allowbylocal=");
	builder.append(allowbylocal);
	builder.append(", averagecost=");
	builder.append(averagecost);
	builder.append(", averagelocalcost=");
	builder.append(averagelocalcost);
	builder.append(", buyingprice=");
	builder.append(buyingprice);
	builder.append(", category=");
	builder.append(category);
	builder.append(", checkstock=");
	builder.append(checkstock);
	builder.append(", commission=");
	builder.append(commission);
	builder.append(", costs=");
	builder.append(costs);
	builder.append(", datecreated=");
	builder.append(datecreated);
	builder.append(", description=");
	builder.append(description);
	builder.append(", description2=");
	builder.append(description2);
	builder.append(", discounta=");
	builder.append(discounta);
	builder.append(", discountb=");
	builder.append(discountb);
	builder.append(", discountc=");
	builder.append(discountc);
	builder.append(", discountd=");
	builder.append(discountd);
	builder.append(", discounte=");
	builder.append(discounte);
	builder.append(", expecteddate=");
	builder.append(expecteddate);
	builder.append(", instock=");
	builder.append(instock);
	builder.append(", lastcost=");
	builder.append(lastcost);
	builder.append(", lastlocalcost=");
	builder.append(lastlocalcost);
	builder.append(", lastsaledate=");
	builder.append(lastsaledate);
	builder.append(", leadtime=");
	builder.append(leadtime);
	builder.append(", listprice=");
	builder.append(listprice);
	builder.append(", maximum=");
	builder.append(maximum);
	builder.append(", minimum=");
	builder.append(minimum);
	builder.append(", orderdate=");
	builder.append(orderdate);
	builder.append(", orderlocaldate=");
	builder.append(orderlocaldate);
	builder.append(", pricea=");
	builder.append(pricea);
	builder.append(", priceb=");
	builder.append(priceb);
	builder.append(", pricec=");
	builder.append(pricec);
	builder.append(", pricecode=");
	builder.append(pricecode);
	builder.append(", priced=");
	builder.append(priced);
	builder.append(", pricee=");
	builder.append(pricee);
	builder.append(", productcategory=");
	builder.append(productcategory);
	builder.append(", purchaselocalquantity=");
	builder.append(purchaselocalquantity);
	builder.append(", purchasequantity=");
	builder.append(purchasequantity);
	builder.append(", quantity=");
	builder.append(quantity);
	builder.append(", sales=");
	builder.append(sales);
	builder.append(", salesqtymonth=");
	builder.append(salesqtymonth);
	builder.append(", salesqtyweek=");
	builder.append(salesqtyweek);
	builder.append(", salesqtyyear=");
	builder.append(salesqtyyear);
	builder.append(", sellingprice=");
	builder.append(sellingprice);
	builder.append(", subcategoryid=");
	builder.append(subcategory);
	builder.append(", taxcode=");
	builder.append(taxcode);
	builder.append(", upccode=");
	builder.append(upccode);
	builder.append(", vendorid=");
	builder.append(vendorid);
	builder.append(", webdescription=");
	builder.append(webdescription);
	builder.append(", webgraphicsfile=");
	builder.append(Arrays.toString(webgraphicsfile));
	builder.append("]");
	return builder.toString();
}
}