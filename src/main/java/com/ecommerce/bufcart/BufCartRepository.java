package com.ecommerce.bufcart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface BufCartRepository extends JpaRepository<Bufcart,Long>{
	
	
	  List<Bufcart> findByEmail(String email);
	  
	 Bufcart findByBufcartIdAndEmail(int bufcartId,String email);
	  
	  void deleteByBufcartIdAndEmail(int bufcartId, String email);
	  
	  List<Bufcart> findAllByEmail(String email);
	  
	  List<Bufcart> findAllByOrderId(int orderId);
	  
	  long countByEmail(String email);
	 
	 

}
