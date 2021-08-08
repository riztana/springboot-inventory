package id.co.jriztana.app.inventory.dto;

import id.co.jriztana.app.inventory.model.Item;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemMapperTest {
    private static       ItemMapper mapper;
    private static final String     PRODUCT_NAME        = "Product Name";
    private static final String     PRODUCT_DESCRIPTION = "Product Description";

    @BeforeClass
    public static void setup() {
        mapper = Mappers.getMapper(ItemMapper.class);
    }

    @Test
    public void toDto_thenReturnItemDto_nonNullValue() {
        Item item = getDummyItem();

        ItemDto dto = mapper.toDto(item);

        assertThat(dto).isNotNull();
        assertThat(dto.getName()).isEqualTo(PRODUCT_NAME);
        assertThat(dto.getDescription()).isEqualTo(PRODUCT_DESCRIPTION);
        assertThat(dto.getQuantity()).isEqualTo(1);
    }

    @Test
    public void toModel_thenReturnItem_nonNullValue() {
        ItemDto itemDto = ItemDto.builder().id(2L).name(PRODUCT_NAME).description(PRODUCT_DESCRIPTION).quantity(2)
                .build();

        Item item = mapper.toModel(itemDto);

        assertThat(item).isNotNull();
        assertThat(item.getProductName()).isEqualTo(PRODUCT_NAME);
        assertThat(item.getDescription()).isEqualTo(PRODUCT_DESCRIPTION);
        assertThat(item.getQuantity()).isEqualTo(2);
    }

    @Test
    public void map_thenReturnList_nonNullValues() {
        Item item = getDummyItem();
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);

        List<ItemDto> dtos = mapper.map(itemList);

        assertThat(dtos).isNotNull();
        assertThat(dtos.size()).isEqualTo(1);
        assertThat(dtos.get(0).getName()).isEqualTo(PRODUCT_NAME);
        assertThat(dtos.get(0).getDescription()).isEqualTo(PRODUCT_DESCRIPTION);
        assertThat(dtos.get(0).getQuantity()).isEqualTo(1);
    }

    @Test
    public void toDto_thenReturnNull() {
        assertThat(mapper.toDto(null)).isNull();
    }

    @Test
    public void toModel_thenReturnNull() {
        assertThat(mapper.toModel(null)).isNull();
    }

    @Test
    public void map_thenReturnNull() {
        assertThat(mapper.map(null)).isNull();
    }

    private Item getDummyItem() {
        Item item = new Item();
        item.setId(1L);
        item.setProductName(PRODUCT_NAME);
        item.setDescription(PRODUCT_DESCRIPTION);
        item.setQuantity(1);
        return item;
    }
}
