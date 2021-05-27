package inpt.sud.springbackend.controller;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import inpt.sud.springbackend.dao.ProductRepository;
import inpt.sud.springbackend.model.Product;


@RestController
public class MyRestController {

	protected ProductRepository productRepository;
	
	public MyRestController(ProductRepository p) {
		productRepository = p;
	}
	@GetMapping(path = "/photoProduct/{id}", produces=MediaType.IMAGE_PNG_VALUE)
	public byte[] getPhoto(@PathVariable("id") Long id) throws Exception{
		Product p = productRepository.findById(id).get();
		return Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/ecom/products/"+p.getPhotoName()));
		
	}
	
	@PostMapping(path="/uploadPhoto/{id}")
	public void uploadPhoto(MultipartFile file, @PathVariable Long id) throws Exception{
		Product p = productRepository.findById(id).get();
		p.setPhotoName(file.getOriginalFilename());
		Files.write(Paths.get(System.getProperty("user.home")+"/ecom/products/"+p.getPhotoName()), file.getBytes());
		productRepository.save(p);
	}
}
