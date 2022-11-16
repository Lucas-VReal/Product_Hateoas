package br.com.produtos.domain.services;

import br.com.produtos.api.controllers.ProductController;
import br.com.produtos.domain.models.ProductModel;
import br.com.produtos.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> products = productRepository.findAll();
        if(products.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            for (ProductModel product : products){
                //linkto creates a link and methodOn show the response link info
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(product.getId())).withSelfRel());
            }
            return new ResponseEntity<List<ProductModel>>(products, HttpStatus.OK);
        }
    }

    public ResponseEntity<Object> getOneProduct(Long id) {
        if(!productRepository.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("the product was not found");
        }else{
            Optional<ProductModel> optionalProductModel = productRepository.findById(id);
            optionalProductModel.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("Product List"));
            return ResponseEntity.status(HttpStatus.OK).body(optionalProductModel);
        }

    }

    public ResponseEntity<Object> createNewProduct(ProductModel newProduct) {
        if(!productRepository.findByName(newProduct.getName()).isPresent()) {
            productRepository.save(newProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("The product has already been created");
    }

    public ResponseEntity<Object> updateProduct(Long id, ProductModel updatedProduct) {
        if(productRepository.findById(id).isPresent()) {
            updatedProduct.setId(id);
            productRepository.save(updatedProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedProduct);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("the product was not found");
    }

    @Transactional
    public ResponseEntity<Object> deleteProduct(Long id) {
        if(productRepository.findById(id).isPresent()) {
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("The product has been successfully removed");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("the product was not found");
    }


}
