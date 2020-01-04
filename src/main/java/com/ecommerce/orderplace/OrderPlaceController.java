package com.ecommerce.orderplace;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.bufcart.BufCartRepository;
import com.ecommerce.bufcart.Bufcart;
import com.ecommerce.controller.ApiResponse;
import com.ecommerce.controller.BaseController;
import com.ecommerce.controller.GoMessageType;
import com.ecommerce.product.Product;
import com.ecommerce.product.ProductRepository;
import com.ecommerce.util.Validator;

@Scope("request")
@RestController
@RequestMapping("orderPlace")
public class OrderPlaceController extends BaseController{
	
	@Autowired
	OrderPlaceRepository orderPlaceRepo;
	@Autowired
	BufCartRepository bufCartRepo;
	@Autowired
	ProductRepository prodRepo;

	
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
		}else {
			OrderPlace op = orderPlaceRepo.findByOrderId(orderId);
			op.setOrderStatus(orderStatus);
			orderPlaceRepo.save(op);
			
			  List<Bufcart> bfList= bufCartRepo.findAllByOrderId(Integer.parseInt(orderId.toString()));
			  
			  for(Bufcart bfcart:bfList) {
				 
				  bufCartRepo.delete(bfcart);
			  }
		}
		
		return renderResponse();
	}
	
	@PostMapping("placeOrder")
	public ApiResponse placeOrder(@RequestParam("email") String email) {
		
		OrderPlace op = new OrderPlace();
		op.setEmail(email);
		Date date = new Date();
		op.setOrderDate(date);
		op.setOrderStatus("PENDING");
		double total = 0;
		List<Bufcart> buflist = bufCartRepo.findAllByEmail(email);
		for (Bufcart buf : buflist) {
			total = +(buf.getQuantity() * buf.getPrice());
		}
		op.setTotalCost(total);
		OrderPlace res = orderPlaceRepo.save(op);
		buflist.forEach(bufcart -> {
			bufcart.setOrderId((int) res.getOrderId());
			bufCartRepo.save(bufcart);

		});
		return renderResponse();
	}

}
