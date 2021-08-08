package id.co.jriztana.app.inventory.dto;

import id.co.jriztana.app.inventory.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    @Mapping(source = "name", target = "productName")
    Item toModel(ItemDto itemDto);

    @Mapping(source = "productName", target = "name")
    ItemDto toDto(Item item);

    List<ItemDto> map(List<Item> items);
}
