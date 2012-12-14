package com.oes.model.my_sql.entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mbi.oes.db.utils.FormatUtil;


@Entity
@Table(name = "Customer")
public class Customer {
  
	@Id
	@javax.persistence.Column(name="customerID",length=10)
  private String customerid;

  public void setCustomerid(String customerid) {
    this.customerid = customerid;
  }

  public String getCustomerid() {
    return customerid;
  }

  @javax.persistence.Column(name="Active",length=1)
  private Boolean active;

  public void setActive(Boolean active) {
    this.active = active;
  }

  public Boolean getActive() {
    return active;
  }

  @javax.persistence.Column(name="Address",length=50)
  private String address;

  public void setAddress(String address) {
    this.address = address;
  }

  public String getAddress() {
    return address;
  }

  @javax.persistence.Column(name="Address2",length=50)
  private String address2;

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public String getAddress2() {
    return address2;
  }

  @javax.persistence.Column(name="AlphaSort",length=10)
  private String alphasort;

  public void setAlphasort(String alphasort) {
    this.alphasort = alphasort;
  }

  public String getAlphasort() {
    return alphasort;
  }

  @javax.persistence.Column(name="UnaccountedAmount",length=12)
  private Float unaccountedamount;

  public void setUnaccountedamount(Float unaccountedamount) {
    this.unaccountedamount = unaccountedamount;
  }

  public Double getUnaccountedamount() {
    return FormatUtil.formatPrecision(unaccountedamount);
  }
  
  @javax.persistence.Column(name="AveragePayDays",length=12)
  private Float averagepaydays;

  public void setAveragepaydays(Float averagepaydays) {
    this.averagepaydays = averagepaydays;
  }

  public Float getAveragepaydays() {
    return averagepaydays;
  }

  @javax.persistence.Column(name="Balance",length=12)
  private Float balance;

  public void setBalance(Float balance) {
    this.balance = balance;
  }

  public Double getBalance() {
	if(balance == null) {
		return 0.0;
	} else{
		return FormatUtil.formatPrecision(balance);
	}
  }

  @javax.persistence.Column(name="CCExpireDate")
  @javax.persistence.Temporal(value=javax.persistence.TemporalType.DATE)
  private java.util.Date ccexpiredate;

  public void setCcexpiredate(java.util.Date ccexpiredate) {
    this.ccexpiredate = ccexpiredate;
  }

  public java.util.Date getCcexpiredate() {
    return ccexpiredate;
  }

  @javax.persistence.Column(name="CallCustomer",length=50)
  private String callcustomer;

  public void setCallcustomer(String callcustomer) {
    this.callcustomer = callcustomer;
  }

  public String getCallcustomer() {
    return callcustomer;
  }

  @javax.persistence.Column(name="City",length=30)
  private String city;

  public void setCity(String city) {
    this.city = city;
  }

  public String getCity() {
    return city;
  }

  @javax.persistence.Column(name="ContactName",length=50)
  private String contactname;

  public void setContactname(String contactname) {
    this.contactname = contactname;
  }

  public String getContactname() {
    return contactname;
  }

  @javax.persistence.Column(name="CreatedBy",length=50)
  private String createdby;

  public void setCreatedby(String createdby) {
    this.createdby = createdby;
  }

  public String getCreatedby() {
    return createdby;
  }

  @javax.persistence.Column(name="CreditCardNo",length=20)
  private String creditcardno;

  public void setCreditcardno(String creditcardno) {
    this.creditcardno = creditcardno;
  }

  public String getCreditcardno() {
    return creditcardno;
  }

  @javax.persistence.Column(name="CreditLimit",length=12)
  private Float creditlimit;

  public void setCreditlimit(Float creditlimit) {
    this.creditlimit = creditlimit;
  }

  public Float getCreditlimit() {
    return creditlimit;
  }

  @javax.persistence.Column(name="CustomerHoldDate")
  @javax.persistence.Temporal(value=javax.persistence.TemporalType.DATE)
  private java.util.Date customerholddate;

  public void setCustomerholddate(java.util.Date customerholddate) {
    this.customerholddate = customerholddate;
  }

  public java.util.Date getCustomerholddate() {
    return customerholddate;
  }

  @javax.persistence.Column(name="CustomerName",length=50)
  private String customername;

  public void setCustomername(String customername) {
    this.customername = customername;
  }

  public String getCustomername() {
    return customername;
  }

  @javax.persistence.Column(name="DateCreated")
  @javax.persistence.Temporal(value=javax.persistence.TemporalType.DATE)
  private java.util.Date datecreated;

  public void setDatecreated(java.util.Date datecreated) {
    this.datecreated = datecreated;
  }

  public java.util.Date getDatecreated() {
    return datecreated;
  }

  @javax.persistence.Column(name="DisregardCreditLimit",length=3)
  private Boolean disregardcreditlimit;

  public void setDisregardcreditlimit(Boolean disregardcreditlimit) {
    this.disregardcreditlimit = disregardcreditlimit;
  }

  public Boolean getDisregardcreditlimit() {
    return disregardcreditlimit;
  }

  @javax.persistence.Column(name="Email",length=50)
  private String email;

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  @javax.persistence.Column(name="Fax",length=50)
  private String fax;

  public void setFax(String fax) {
    this.fax = fax;
  }

  public String getFax() {
    return fax;
  }

  @javax.persistence.Column(name="FirstName",length=50)
  private String firstname;

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getFirstname() {
    return firstname;
  }

  @javax.persistence.Column(name="UnappliedAmount",length=12)
  private Float unappliedamount;

  public void setUnappliedamount(Float unappliedamount) {
    this.unappliedamount = unappliedamount;
  }

  public Float getUnappliedamount() {
    return unappliedamount;
  }
  
  @javax.persistence.Column(name="FuelChargeAmount",length=12)
  private Float fuelchargeamount;

  public void setFuelchargeamount(Float fuelchargeamount) {
    this.fuelchargeamount = fuelchargeamount;
  }

  public Float getFuelchargeamount() {
    return fuelchargeamount;
  }

  @javax.persistence.Column(name="LastActivity",length=10)
  private String lastactivity;

  public void setLastactivity(String lastactivity) {
    this.lastactivity = lastactivity;
  }

  public String getLastactivity() {
    return lastactivity;
  }

  @javax.persistence.Column(name="LastName",length=60)
  private String lastname;

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getLastname() {
    return lastname;
  }

  @javax.persistence.Column(name="LastYearSold",length=10)
  private String lastyearsold;

  public void setLastyearsold(String lastyearsold) {
    this.lastyearsold = lastyearsold;
  }

  public String getLastyearsold() {
    return lastyearsold;
  }

  @javax.persistence.Column(name="NoFreightCharge",length=1)
  private Boolean nofreightcharge;

  public void setNofreightcharge(Boolean nofreightcharge) {
    this.nofreightcharge = nofreightcharge;
  }

  public Boolean getNofreightcharge() {
    return nofreightcharge;
  }

  @javax.persistence.Column(name="Notes",length=250)
  private String notes;

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getNotes() {
    return notes;
  }

  @javax.persistence.Column(name="Phone1",length=15)
  private String phone1;

  public void setPhone1(String phone1) {
    this.phone1 = phone1;
  }

  public String getPhone1() {
    return phone1;
  }

  @javax.persistence.Column(name="Phone2",length=15)
  private String phone2;

  public void setPhone2(String phone2) {
    this.phone2 = phone2;
  }

  public String getPhone2() {
    return phone2;
  }

  @javax.persistence.Column(name="PriceCode",length=10)
  private String pricecode;

  public void setPricecode(String pricecode) {
    this.pricecode = pricecode;
  }

  public String getPricecode() {
    return pricecode;
  }

  @javax.persistence.Column(name="PriorYearSales",length=12)
  private Float prioryearsales;

  public void setPrioryearsales(Float prioryearsales) {
    this.prioryearsales = prioryearsales;
  }

  public Float getPrioryearsales() {
    return prioryearsales;
  }

  @javax.persistence.Column(name="ResaleNumber",length=50)
  private String resalenumber;

  public void setResalenumber(String resalenumber) {
    this.resalenumber = resalenumber;
  }

  public String getResalenumber() {
    return resalenumber;
  }

  @javax.persistence.Column(name="SalesMan",length=19)
  private String salesman;

  public void setSalesman(String salesman) {
    this.salesman = salesman;
  }

  public String getSalesman() {
    return salesman;
  }

  @javax.persistence.Column(name="State",length=50)
  private String state;

  public void setState(String state) {
    this.state = state;
  }

  public String getState() {
    return state;
  }

  @javax.persistence.Column(name="Terms",length=50)
  private String terms;

  public void setTerms(String terms) {
    this.terms = terms;
  }

  public String getTerms() {
    return terms;
  }

  @javax.persistence.Column(name="Type",length=3)
  private String type;

  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  @javax.persistence.Column(name="YtdSalesSold",length=50)
  private String ytdsalessold;

  public void setYtdsalessold(String ytdsalessold) {
    this.ytdsalessold = ytdsalessold;
  }

  public String getYtdsalessold() {
    return ytdsalessold;
  }

  @javax.persistence.Column(name="ZipCode",length=10)
  private String zipcode;

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }

  public String getZipcode() {
    return zipcode;
  }

 
@Override
public boolean equals(Object obj) {
	Customer c = (Customer)obj;
	if(c.getCustomerid().equalsIgnoreCase(customerid)){
		return true;
	} else {
		return false;
	}
}

@Override
public String toString() {
	return "Customer [customerid=" + customerid + ", active=" + active
			+ ", address=" + address + ", address2=" + address2
			+ ", alphasort=" + alphasort + ", averagepaydays=" + averagepaydays
			+ ", balance=" + balance + ", ccexpiredate=" + ccexpiredate
			+ ", callcustomer=" + callcustomer + ", city=" + city
			+ ", contactname=" + contactname + ", createdby=" + createdby
			+ ", creditcardno=" + creditcardno + ", creditlimit=" + creditlimit
			+ ", customerholddate=" + customerholddate + ", customername="
			+ customername + ", datecreated=" + datecreated
			+ ", disregardcreditlimit=" + disregardcreditlimit + ", email="
			+ email + ", fax=" + fax + ", firstname=" + firstname
			+ ", fuelchargeamount=" + fuelchargeamount + ", lastactivity="
			+ lastactivity + ", lastname=" + lastname + ", lastyearsold="
			+ lastyearsold + ", nofreightcharge=" + nofreightcharge
			+ ", notes=" + notes + ", phone1=" + phone1 + ", phone2=" + phone2
			+ ", pricecode=" + pricecode + ", prioryearsales=" + prioryearsales
			+ ", resalenumber=" + resalenumber + ", salesman=" + salesman
			+ ", state=" + state + ", terms=" + terms + ", type=" + type
			+ ", ytdsalessold=" + ytdsalessold + ", zipcode=" + zipcode + "]";
}
  
}