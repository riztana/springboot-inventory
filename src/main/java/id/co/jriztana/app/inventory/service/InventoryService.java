package id.co.jriztana.app.inventory.service;

import id.co.jriztana.app.inventory.model.Item;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InventoryService {
    Page<Item> getList(int page, int size);

    Item getDetail(Long id);

    Item addItem(String name);

    Item updateItem(Item item);

    Long deleteItem(Long id);

    List<Item> searchItem(String name);
}
