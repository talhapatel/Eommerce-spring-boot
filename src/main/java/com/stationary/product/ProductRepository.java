package com.stationary.product;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long>{
	
	
	  Product findByProductid(long productid);
	  
	  void deleteByProductid(long productid);
	 

}
