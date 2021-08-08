package id.co.jriztana.app.inventory.service;

import id.co.jriztana.app.inventory.exception.DataNotFoundException;
import id.co.jriztana.app.inventory.model.Item;
import id.co.jriztana.app.inventory.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final ItemRepository itemRepository;

    @Override
    public Page<Item> getList(int page, int size) {
        return itemRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Item getDetail(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Item " + id + " not found"));
    }

    @Override
    public List<Item> searchItem(String name) {
        return itemRepository.findByProductNameContainsIgnoreCase(name);
    }

    @Override
    @Transactional
    public Item addItem(String name) {
        Item item = new Item();
        Optional<Item> existing = itemRepository.findByProductName(name);
        if (existing.isPresent()) {
            item = existing.get();
        } else {
            item.setProductName(name);
        }
        addQuantity(item);
        return item;
    }

    @Override
    @Transactional
    public Item updateItem(Item item) {
        Item existing = itemRepository.findById(item.getId())
                .orElseThrow(() -> new DataNotFoundException("Item " + item.getId() + " not found"));
        existing.setDescription(item.getDescription());
        existing.setQuantity(item.getQuantity());
        existing.setPrice(item.getPrice());
        return existing;
    }

    @Override
    public Long deleteItem(Long id) {
        itemRepository.deleteById(id);
        return id;
    }



    private void addQuantity(Item item) {
        item.setQuantity(item.getQuantity() + 1);
    }
}
