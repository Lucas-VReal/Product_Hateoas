package br.com.produtos.api.controllers;

import br.com.produtos.domain.models.ProductModel;
import br.com.produtos.domain.services.ProductService;
import br.com.produtos.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts (){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneProduct (@PathVariable(name = "id") Long id){
        return productService.getOneProduct(id);
    }

    @PostMapping
    public ResponseEntity<Object> createNewProduct (@RequestBody ProductModel newProduct){
        return productService.createNewProduct(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct (@PathVariable(name = "id") Long id, @RequestBody ProductModel updatedProduct){
        return productService.updateProduct(id, updatedProduct);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteProduct (@PathVariable(name = "id") Long id){
        return productService.deleteProduct(id);
    }

}
