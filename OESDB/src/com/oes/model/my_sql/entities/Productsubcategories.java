package com.oes.model.my_sql.entities;

// Generated by DB Importer ->
// DB Importer free version - not for commercial use.
@javax.persistence.Entity
@javax.persistence.Table(name="productsubcategories")
public class Productsubcategories {
  @javax.persistence.Id
  //@javax.persistence.GeneratedValue(strategy=javax.persistence.GenerationType.AUTO)
  @javax.persistence.Column(name="id",length=19,nullable=false)
  private long id;

  public void setId(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  @javax.persistence.Column(name="productID",length=19)
  private Long productid;

  public void setProductid(Long productid) {
    this.productid = productid;
  }

  public Long getProductid() {
    return productid;
  }

  @javax.persistence.Column(name="subcategory",length=45,nullable=false)
  private String subcategory = "";

  public void setSubcategory(String subcategory) {
    this.subcategory = subcategory;
  }

  public String getSubcategory() {
    return subcategory;
  }
// <- Generated by DB Importer

@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("Productsubcategories [id=");
	builder.append(id);
	builder.append(", productid=");
	builder.append(productid);
	builder.append(", subcategory=");
	builder.append(subcategory);
	builder.append("]");
	return builder.toString();
}
}