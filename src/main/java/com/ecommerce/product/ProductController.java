package com.ecommerce.product;

import java.io.InputStream;
import java.util.ArrayList;

import javax.websocket.server.PathParam;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties.Storage;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ecommerce.controller.ApiResponse;
import com.ecommerce.controller.BaseController;
import com.ecommerce.controller.GoMessageType;

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
	
	public ApiResponse getProductList() {
		
		setData("data", productRepository.findAll());
	//	setData("image",)
		
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
	@ApiOperation(value = "Get Product  list By tags")
	@GetMapping("/getProductListByTags/{tags}")
	
	public ApiResponse getProductListByTags(@PathVariable("tags") String tag) {
		System.out.println("tags"+tag);
		setData("data", productRepository.getAllByTags(tag));
	//	setData("image",)
		
		return renderResponse();
	}

	
}
