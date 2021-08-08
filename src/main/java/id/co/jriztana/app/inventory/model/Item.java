package id.co.jriztana.app.inventory.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "item")
@Setter
@Getter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "productname", length = 100)
    private String productName;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price", scale = 2)
    private BigDecimal price;

    @Version
    private long version;
}
