package br.com.produtos.domain.models;

import br.com.produtos.domain.services.ProductService;
import br.com.produtos.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


@Getter
@Setter
@Entity
@Table(name = "tb_products")
@NoArgsConstructor
public class ProductModel extends RepresentationModel<ProductModel> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private BigDecimal value;

}
