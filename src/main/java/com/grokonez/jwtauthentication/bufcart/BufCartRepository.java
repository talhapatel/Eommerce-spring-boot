package com.grokonez.jwtauthentication.bufcart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface BufCartRepository extends JpaRepository<BufCart,Long>{
	
	List<BufCart> findByEmail(String email);
	
	BufCart findByBufCartIdAndEmail(int Bufcartid,String email);
	
	void deleteByBufcartIdAndEmail(int bufcartId, String email);

	List<BufCart> findAllByEmail(String email);

	List<BufCart> findAllByOrderId(int orderId);

}
