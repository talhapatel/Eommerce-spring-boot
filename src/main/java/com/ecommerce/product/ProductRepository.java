package com.ecommerce.product;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long>{
	
	
	  Product findByProductid(long productid);
	  
	  void deleteByProductid(long productid);
	  
	  @Query(value="SELECT *   FROM product p1 join product_tags p2 on p1.productid = p2.product_productid where p2.tags like :tag '%' ",nativeQuery = true)
	  //  List<String> findIngredientsByLikeName(@Param("ing_name") String name);
	  List<Product> getAllByTags(String tag);
	 

}
