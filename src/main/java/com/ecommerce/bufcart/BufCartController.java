package com.ecommerce.bufcart;

import java.util.Date;
import java.util.List;

import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.controller.ApiResponse;
import com.ecommerce.controller.BaseController;
import com.ecommerce.controller.GoMessageType;
import com.ecommerce.product.Product;
import com.ecommerce.product.ProductRepository;

@Scope("request")
@RestController
public class BufCartController extends BaseController{
	
	@Autowired
	ProductRepository productRepo;
	@Autowired
	BufCartRepository bufcartRepo;
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/addToCart")
	public ApiResponse addToCart(@RequestParam String prodId,@RequestParam String email)
	{
		
		Product cartItem=productRepo.findByProductid(Long.parseLong(prodId));
		Bufcart bufcart =new Bufcart();
		bufcart.setEmail(email);
		bufcart.setProductId(Integer.parseInt(prodId));
		bufcart.setProductname(cartItem.getProductname());
		bufcart.setQuantity(1);
		bufcart.setPrice(cartItem.getPrice());
		Date date=new Date();
		bufcart.setDateAdded(date);
		bufcartRepo.save(bufcart);

		setData("count", bufcartRepo.countByEmail(email));
		addSuccess(GoMessageType.ADD_SUCCESS);
		return renderResponse();
	}
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/viewCart")
	public ApiResponse viewCart(@RequestParam String email)
	{
	
		setData("cart",bufcartRepo.findByEmail(email));
	//	addSuccess(GoMessageType.ADD_SUCCESS);
		return renderResponse();
		
	}
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/viewCartBadge")
	public ApiResponse viewCartBadge(@RequestParam String email)
	{
	
		setData("cart",bufcartRepo.countByEmail(email));
	//	addSuccess(GoMessageType.ADD_SUCCESS);
		return renderResponse();
		
	}
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/updateCart")
	public ApiResponse updateCart(@RequestParam String bufcartid,@RequestParam String qty,@RequestParam String email) {
		Bufcart selCart = bufcartRepo.findByBufcartIdAndEmail(Integer.parseInt(bufcartid), email);
		selCart.setQuantity(Integer.parseInt(qty));
		bufcartRepo.save(selCart);
		
		List<Bufcart> bufcartlist = bufcartRepo.findByEmail(email);
		setData("cartList",bufcartlist);
		
		return renderResponse();
	}

}
