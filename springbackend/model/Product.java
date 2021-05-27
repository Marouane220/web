package inpt.sud.springbackend.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Product implements Serializable {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY )
	protected Long id;
	protected String name;
	protected String description;
	protected double currentPrice;
	protected boolean promotion;
	protected boolean selected;
	protected boolean available;
	private String photoName;
	@ManyToOne
	private Category category;
	

}
