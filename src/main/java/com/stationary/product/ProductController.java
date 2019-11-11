package com.stationary.product;

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

import com.stationary.controller.ApiResponse;
import com.stationary.controller.BaseController;
import com.stationary.controller.GoMessageType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(value = "Product Controller")
@RestController
@Scope("request")
public class ProductController extends BaseController{
	
	@Autowired
	private ProductRepository productRepository;
	
	@ApiOperation(value = "Add Product ")
	@PostMapping("/addProduct")
	@PreAuthorize("hasRole('ADMIN')")
	public ApiResponse addProduct(@RequestBody Product product)
	{
		productRepository.save(product);
		setData("data", product);
		addSuccess(GoMessageType.ADD_SUCCESS,"data inserted successfully");
		
	
		return renderResponse();
	}
	@ApiOperation(value = "Get Product  list")
	@GetMapping("/getProductList")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ApiResponse getProductList() {
	
		
		setData("data", productRepository.findAll());
		
		return renderResponse();
	}
	@ApiOperation(value = "Delete Product")
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
