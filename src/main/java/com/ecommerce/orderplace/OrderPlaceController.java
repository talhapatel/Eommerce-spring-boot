package com.ecommerce.orderplace;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.bufcart.BufCartRepository;
import com.ecommerce.controller.ApiResponse;
import com.ecommerce.controller.BaseController;
import com.ecommerce.controller.GoMessageType;
import com.ecommerce.util.Validator;

@Scope("request")
@RestController
@RequestMapping("orderPlace")
public class OrderPlaceController extends BaseController{
	
	@Autowired
	OrderPlaceRepository orderPlaceRepo;
	@Autowired
	BufCartRepository bufCartRepo;
	
	@GetMapping("getOrders")
	public ApiResponse getOrders() {
		List<Order> orderList=new ArrayList<Order>();
		List<OrderPlace> poList = orderPlaceRepo.findAll();
		poList.forEach((po) -> {
			Order ord = new Order();
			ord.setOrderBy(po.getEmail());
			ord.setOrderId((int) po.getOrderId());
			ord.setOrderStatus(po.getOrderStatus());
			ord.setProducts(bufCartRepo.findAllByOrderId((int) po.getOrderId()));
			orderList.add(ord);
		});
		
		setData("OrderList", orderList);
		return renderResponse();	
		}
	
	@PostMapping("updateOrders")
	public ApiResponse updateOrders(@RequestParam("orderId")Long orderId,@RequestParam("orderStatus")String orderStatus) {
		
		if(Validator.isStringEmpty(orderStatus)) {
			addFailure(GoMessageType.ADD_FAILURE,"order status is missing");
		}
		
		return renderResponse();
	}

}
