package inpt.sud.springbackend.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Category implements Serializable {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY )
	protected Long id;
	protected String name;
	protected String description;
	@OneToMany(mappedBy="category")
	protected Collection<Product> products;

}
