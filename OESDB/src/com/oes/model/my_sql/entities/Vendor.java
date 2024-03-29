package com.oes.model.my_sql.entities;

// Generated by DB Importer ->
// DB Importer free version - not for commercial use.
@javax.persistence.Entity
@javax.persistence.Table(name="vendor")
public class Vendor {
  @javax.persistence.Id
  @javax.persistence.Column(name="vendorid",length=20,nullable=false)
  private String vendorid;

  public void setVendorid(String vendorid) {
    this.vendorid = vendorid;
  }

  public String getVendorid() {
    return vendorid;
  }

  @javax.persistence.Column(name="accountno",length=45)
  private String accountno;

  public void setAccountno(String accountno) {
    this.accountno = accountno;
  }

  public String getAccountno() {
    return accountno;
  }

  @javax.persistence.Column(name="address1",length=30)
  private String address1;

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public String getAddress1() {
    return address1;
  }

  @javax.persistence.Column(name="address2",length=30)
  private String address2;

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public String getAddress2() {
    return address2;
  }

  @javax.persistence.Column(name="altcontactname",length=45)
  private String altcontactname;

  public void setAltcontactname(String altcontactname) {
    this.altcontactname = altcontactname;
  }

  public String getAltcontactname() {
    return altcontactname;
  }

  @javax.persistence.Column(name="amountdue",length=12)
  private Float amountdue;

  public void setAmountdue(Float amountdue) {
    this.amountdue = amountdue;
  }

  public Float getAmountdue() {
    return amountdue;
  }

  @javax.persistence.Column(name="balance",length=12)
  private Float balance;

  public void setBalance(Float balance) {
    this.balance = balance;
  }

  public Float getBalance() {
    return balance;
  }

  @javax.persistence.Column(name="category",length=45)
  private String category;

  public void setCategory(String category) {
    this.category = category;
  }

  public String getCategory() {
    return category;
  }

  @javax.persistence.Column(name="city",length=25)
  private String city;

  public void setCity(String city) {
    this.city = city;
  }

  public String getCity() {
    return city;
  }

  @javax.persistence.Column(name="comments",length=100)
  private String comments;

  public void setComments(String comments) {
    this.comments = comments;
  }

  public String getComments() {
    return comments;
  }

  @javax.persistence.Column(name="companyname",length=45)
  private String companyname;

  public void setCompanyname(String companyname) {
    this.companyname = companyname;
  }

  public String getCompanyname() {
    return companyname;
  }

  @javax.persistence.Column(name="contactname",length=45)
  private String contactname;

  public void setContactname(String contactname) {
    this.contactname = contactname;
  }

  public String getContactname() {
    return contactname;
  }

  @javax.persistence.Column(name="creationdate",length=10)
  @javax.persistence.Temporal(value=javax.persistence.TemporalType.DATE)
  private java.util.Date creationdate;

  public void setCreationdate(java.util.Date creationdate) {
    this.creationdate = creationdate;
  }

  public java.util.Date getCreationdate() {
    return creationdate;
  }

  @javax.persistence.Column(name="creditlimit",length=12)
  private Float creditlimit;

  public void setCreditlimit(Float creditlimit) {
    this.creditlimit = creditlimit;
  }

  public Float getCreditlimit() {
    return creditlimit;
  }

  @javax.persistence.Column(name="discountdays",length=10)
  private Integer discountdays;

  public void setDiscountdays(Integer discountdays) {
    this.discountdays = discountdays;
  }

  public Integer getDiscountdays() {
    return discountdays;
  }

  @javax.persistence.Column(name="discountpercent",length=12)
  private Float discountpercent;

  public void setDiscountpercent(Float discountpercent) {
    this.discountpercent = discountpercent;
  }

  public Float getDiscountpercent() {
    return discountpercent;
  }

  @javax.persistence.Column(name="discountytd",length=12)
  private Float discountytd;

  public void setDiscountytd(Float discountytd) {
    this.discountytd = discountytd;
  }

  public Float getDiscountytd() {
    return discountytd;
  }

  @javax.persistence.Column(name="duedays",length=10)
  private Integer duedays;

  public void setDuedays(Integer duedays) {
    this.duedays = duedays;
  }

  public Integer getDuedays() {
    return duedays;
  }

  @javax.persistence.Column(name="email",length=25)
  private String email;

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  @javax.persistence.Column(name="fax",length=15)
  private String fax;

  public void setFax(String fax) {
    this.fax = fax;
  }

  public String getFax() {
    return fax;
  }

  @javax.persistence.Column(name="glaccount",length=45)
  private String glaccount;

  public void setGlaccount(String glaccount) {
    this.glaccount = glaccount;
  }

  public String getGlaccount() {
    return glaccount;
  }

  @javax.persistence.Column(name="lastactivity",length=45)
  private String lastactivity;

  public void setLastactivity(String lastactivity) {
    this.lastactivity = lastactivity;
  }

  public String getLastactivity() {
    return lastactivity;
  }

  @javax.persistence.Column(name="lastpaydate",length=10)
  @javax.persistence.Temporal(value=javax.persistence.TemporalType.DATE)
  private java.util.Date lastpaydate;

  public void setLastpaydate(java.util.Date lastpaydate) {
    this.lastpaydate = lastpaydate;
  }

  public java.util.Date getLastpaydate() {
    return lastpaydate;
  }

  @javax.persistence.Column(name="monthdueday",length=45)
  private String monthdueday;

  public void setMonthdueday(String monthdueday) {
    this.monthdueday = monthdueday;
  }

  public String getMonthdueday() {
    return monthdueday;
  }

  @javax.persistence.Column(name="paymentterms",length=45)
  private String paymentterms;

  public void setPaymentterms(String paymentterms) {
    this.paymentterms = paymentterms;
  }

  public String getPaymentterms() {
    return paymentterms;
  }

  @javax.persistence.Column(name="phone1",length=15)
  private String phone1;

  public void setPhone1(String phone1) {
    this.phone1 = phone1;
  }

  public String getPhone1() {
    return phone1;
  }

  @javax.persistence.Column(name="phone2",length=15)
  private String phone2;

  public void setPhone2(String phone2) {
    this.phone2 = phone2;
  }

  public String getPhone2() {
    return phone2;
  }

  @javax.persistence.Column(name="productgroup",length=45)
  private String productgroup;

  public void setProductgroup(String productgroup) {
    this.productgroup = productgroup;
  }

  public String getProductgroup() {
    return productgroup;
  }

  @javax.persistence.Column(name="purchasedlastyear",length=12)
  private Float purchasedlastyear;

  public void setPurchasedlastyear(Float purchasedlastyear) {
    this.purchasedlastyear = purchasedlastyear;
  }

  public Float getPurchasedlastyear() {
    return purchasedlastyear;
  }

  @javax.persistence.Column(name="purchasedytd",length=12)
  private Float purchasedytd;

  public void setPurchasedytd(Float purchasedytd) {
    this.purchasedytd = purchasedytd;
  }

  public Float getPurchasedytd() {
    return purchasedytd;
  }

  @javax.persistence.Column(name="resaleno",length=45)
  private String resaleno;

  public void setResaleno(String resaleno) {
    this.resaleno = resaleno;
  }

  public String getResaleno() {
    return resaleno;
  }

  @javax.persistence.Column(name="salesperson",length=45)
  private String salesperson;

  public void setSalesperson(String salesperson) {
    this.salesperson = salesperson;
  }

  public String getSalesperson() {
    return salesperson;
  }

  @javax.persistence.Column(name="state",length=15)
  private String state;

  public void setState(String state) {
    this.state = state;
  }

  public String getState() {
    return state;
  }

  @javax.persistence.Column(name="stores",length=45)
  private String stores;

  public void setStores(String stores) {
    this.stores = stores;
  }

  public String getStores() {
    return stores;
  }

  @javax.persistence.Column(name="type",length=25)
  private String type;

  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  @javax.persistence.Column(name="vendorurl",length=100)
  private String vendorurl;

  public void setVendorurl(String vendorurl) {
    this.vendorurl = vendorurl;
  }

  public String getVendorurl() {
    return vendorurl;
  }

  @javax.persistence.Column(name="zipcode",length=15)
  private String zipcode;

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }

  public String getZipcode() {
    return zipcode;
  }
// <- Generated by DB Importer
}