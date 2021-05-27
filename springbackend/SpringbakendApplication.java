package inpt.sud.springbackend;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import inpt.sud.springbackend.dao.CategoryRepository;
import inpt.sud.springbackend.dao.ProductRepository;
import inpt.sud.springbackend.model.Category;
import inpt.sud.springbackend.model.Product;
import net.bytebuddy.utility.RandomString;

@SpringBootApplication
public class SpringbakendApplication implements CommandLineRunner {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	RepositoryRestConfiguration repositoryRestConfiguration;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbakendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		repositoryRestConfiguration.exposeIdsFor(Product.class, Category.class);
		categoryRepository.save(new Category(null, "Computers", null, null));
		categoryRepository.save(new Category(null, "Printers", null, null));
		categoryRepository.save(new Category(null, "SmartPhones", null, null));
		
		Random rnd = new Random();
		
		categoryRepository.findAll().forEach(category -> {
			for(int i=0; i<10; i++) {
				Product p = new Product();
				p.setCurrentPrice(100 + rnd.nextInt(10000));
				p.setAvailable(rnd.nextBoolean());
				p.setPromotion(rnd.nextBoolean());
				p.setSelected(rnd.nextBoolean());
				p.setCategory(category);
				if(category.getId()== 1) {
					p.setName("Computer");
					p.setPhotoName("computer.png");
				}
				if(category.getId()== 2) {
					p.setName("Printer");
					p.setPhotoName("printer.png");
				}
				if(category.getId()== 3) {
					p.setName("Smart Phone");
					p.setPhotoName("smartphone.png");
				}
				productRepository.save(p);
			}
		});
		
	}

}
