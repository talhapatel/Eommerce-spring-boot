package com.stationary.product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long productid;
	private String description;
	private String productname;
	private double price;
	private int qty;
	@Lob
	private byte[] productimage;
	public Product(long productid, String description, String productname, double price, int qty, byte[] productimage) {
		super();
		this.productid = productid;
		this.description = description;
		this.productname = productname;
		this.price = price;
		this.qty = qty;
		this.productimage = productimage;
	}
	public Product() {}
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
	public byte[] getProductimage() {
		return productimage;
	}
	public void setProductimage(byte[] productimage) {
		this.productimage = productimage;
	}
}
