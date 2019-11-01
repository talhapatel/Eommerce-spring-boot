package com.grokonez.jwtauthentication.bufcart;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grokonez.jwtauthentication.controller.ApiResponse;
import com.grokonez.jwtauthentication.controller.BaseController;
import com.grokonez.jwtauthentication.controller.GoMessageType;
import com.grokonez.jwtauthentication.product.Product;
import com.grokonez.jwtauthentication.product.ProductRepository;
import com.grokonez.jwtauthentication.repository.UserRepository;

@Scope("request")
@RestController
public class BufCartController extends BaseController{
	
	@Autowired
	ProductRepository productRepo;
	BufCartRepository bufcartRepo;
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/addToCart")
	public ApiResponse addToCart(@RequestParam String prodId,@RequestParam String email)
	{
		
		Product cartItem=productRepo.findByProductid(Long.parseLong(prodId));
		BufCart bufcart =new BufCart();
		bufcart.setEmail(email);
		bufcart.setProductId(Integer.parseInt(prodId));
		bufcart.setProductname(cartItem.getProductname());
		bufcart.setQuantity(1);
		bufcart.setPrice(cartItem.getPrice());
		Date date=new Date();
		bufcart.setDateAdded(date);
		
		bufcartRepo.save(bufcart);
		
		addSuccess(GoMessageType.ADD_SUCCESS);
		return renderResponse();
	}

}
