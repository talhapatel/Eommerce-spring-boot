package com.ecommerce.orderplace;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrderPlace {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	private long orderId;
	private String email,orderStatus;
	private Date orderDate;
	private double totalCost;
	public OrderPlace() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderPlace(long orderId, String email, String orderStatus, Date orderDate, double totalCost) {
		super();
		this.orderId = orderId;
		this.email = email;
		this.orderStatus = orderStatus;
		this.orderDate = orderDate;
		this.totalCost = totalCost;
	}
	@Override
	public String toString() {
		return "OrderPlace [orderId=" + orderId + ", email=" + email + ", orderStatus=" + orderStatus + ", orderDate="
				+ orderDate + ", totalCost=" + totalCost + "]";
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	

}
