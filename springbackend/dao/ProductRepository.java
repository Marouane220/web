package inpt.sud.springbackend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import inpt.sud.springbackend.model.Product;

@CrossOrigin("*")
@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Long> {

	@RestResource(path="/selectedProducts")
	public List<Product> findBySelectedIsTrue();
	
	@RestResource(path="/productsByKeyword")
	public List<Product> findByNameContains(@Param("mc") String mc);
	
	@RestResource(path="/promoProducts")
	public List<Product> findByPromotionIsTrue();
	
	@RestResource(path="/dispoProducts")
	public List<Product> findByAvailableIsTrue();
}
