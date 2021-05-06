package inpt.sud.springbackedlab1.model.projections;

import inpt.sud.springbackedlab1.model.Product;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "p1", types = Product.class)
public interface ProductProjection {
    public double getCurrentPrice();
}
