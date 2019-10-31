package com.grokonez.jwtauthentication.product;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.grokonez.jwtauthentication.controller.ApiResponse;
import com.grokonez.jwtauthentication.controller.BaseController;
import com.grokonez.jwtauthentication.controller.GoMessageType;



@RestController
@Scope("request")
public class ProductController extends BaseController{
	
	@Autowired
	private ProductRepository productRepository;
	
	
	@PostMapping("/addProduct")
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse addProduct(@RequestBody Product product)
	{
		productRepository.save(product);
		setData("data", product);
		addSuccess(GoMessageType.ADD_SUCCESS,"data inserted successfully");
		
	
		return renderResponse();
	}
	
	@GetMapping("/getProductList")
	@PreAuthorize("hasRole('ADMIN') or hasRole(USER)")
	public ApiResponse getProductList() {
	
		
		setData("data", productRepository.findAll());
		
		return renderResponse();
	}
	
	@DeleteMapping("/deleteProduct/{productid}")
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse deleteProduct(@PathVariable Long productid)
	{
		productRepository.deleteById(productid);
		setData("product", productid);
		addSuccess(GoMessageType.DELETE_SUCCESS);
		return renderResponse();
	}

}
