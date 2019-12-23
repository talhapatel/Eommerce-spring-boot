package com.ecommerce.product;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class Product {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long productid;
	private String description;
	private String productname;
	private double price;
	private int qty;
	
	 @ElementCollection(targetClass=String.class)
	private List<String> tags;
	
	private String uniqueId;

	public long getProductid() {
		return productid;
	}

	public void setProductid(long productid) {
		this.productid = productid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}


	public Product(long productid, String description, String productname, double price, int qty, List<String> tags,
			String uniqueId) {
		super();
		this.productid = productid;
		this.description = description;
		this.productname = productname;
		this.price = price;
		this.qty = qty;
		this.tags = tags;
		this.uniqueId = uniqueId;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Product [productid=" + productid + ", description=" + description + ", productname=" + productname
				+ ", price=" + price + ", qty=" + qty + ", tags=" + tags + ", uniqueId=" + uniqueId + "]";
	}
	
}
