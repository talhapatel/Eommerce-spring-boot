package com.ecommerce.orderplace;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderPlaceRepository extends JpaRepository<OrderPlace,Long>{
	
	OrderPlace findByOrderId(Long orderId);

}
