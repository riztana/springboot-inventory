package id.co.jriztana.app.inventory.repository;

import id.co.jriztana.app.inventory.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByProductName(String name);

    List<Item> findAll();

    List<Item> findByProductNameContainsIgnoreCase(String name);
}
