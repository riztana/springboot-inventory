package id.co.jriztana.app.inventory.controller;

import com.fasterxml.jackson.annotation.JsonView;
import id.co.jriztana.app.inventory.dto.CommonView;
import id.co.jriztana.app.inventory.dto.ItemDto;
import id.co.jriztana.app.inventory.dto.ItemMapper;
import id.co.jriztana.app.inventory.model.Item;
import id.co.jriztana.app.inventory.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/item")
@AllArgsConstructor
public class InventoryController {
    private final InventoryService service;
    private final ItemMapper       mapper;

    @GetMapping("/")
    public ResponseEntity<List<ItemDto>> getList(@RequestParam int page, @RequestParam int size) {
        Page<Item> itemPage = service.getList(page, size);
        List<ItemDto> itemDtos = itemPage.stream().map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok()
                .header("X-Pagination-Limit", String.valueOf(itemPage.getTotalPages()))
                .header("X-Pagination-Page", String.valueOf(itemPage.getPageable().getPageNumber()))
                .header("X-Pagination-Total", String.valueOf(itemPage.getTotalElements()))
                .body(itemDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getDetail(@PathVariable Long id) {
        Item item = service.getDetail(id);
        return ResponseEntity.ok(mapper.toDto(item));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> searchItem(@RequestParam String name) {
        List<Item> itemList = service.searchItem(name);
        return ResponseEntity.ok(mapper.map(itemList));
    }

    @PostMapping("/")
    @JsonView(value = { CommonView.POST.class })
    public ResponseEntity<ItemDto> addItem(@RequestBody @JsonView(value = { CommonView.POST.class }) ItemDto dto) {
        Item item = service.addItem(dto.getName());
        return new ResponseEntity<>(mapper.toDto(item), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @JsonView(value = { CommonView.PUT.class })
    public ResponseEntity<ItemDto> updateItem(@PathVariable Long id,
            @RequestBody @JsonView(value = { CommonView.PUT.class }) ItemDto dto) {
        dto.setId(id);
        Item item = service.updateItem(mapper.toModel(dto));
        return ResponseEntity.ok(mapper.toDto(item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteItem(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteItem(id));
    }

}
