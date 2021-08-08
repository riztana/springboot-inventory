package id.co.jriztana.app.inventory.controller;

import id.co.jriztana.app.inventory.controller.InventoryController;
import id.co.jriztana.app.inventory.dto.ItemDto;
import id.co.jriztana.app.inventory.dto.ItemMapper;
import id.co.jriztana.app.inventory.model.Item;
import id.co.jriztana.app.inventory.service.InventoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InventoryControllerTest {
    private static final String PRODUCT_NAME = "Name";
    private static final String DESCRIPTION  = "Description";

    @InjectMocks
    InventoryController controller;

    @Mock
    InventoryService service;

    @Mock
    ItemMapper mapper;

    @Test
    public void addItem_returnSuccess() {
        ItemDto dto = ItemDto.builder().name(PRODUCT_NAME).description(DESCRIPTION).quantity(1).build();
        Item item = getDummyItem();

        when(mapper.toDto(any())).thenReturn(dto);
        when(service.addItem(any())).thenReturn(item);

        ResponseEntity<ItemDto> responseEntity = controller.addItem(dto);
        ItemDto response = responseEntity.getBody();

        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo(PRODUCT_NAME);
        assertThat(response.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(response.getQuantity()).isEqualTo(1);
    }

    @Test
    public void updateItem_returnSuccess() {
        ItemDto dto = ItemDto.builder().name(PRODUCT_NAME).description(DESCRIPTION).quantity(1).build();
        Item item = getDummyItem();

        when(mapper.toDto(any())).thenReturn(dto);
        when(mapper.toModel(any())).thenReturn(item);
        when(service.updateItem(any())).thenReturn(item);

        ResponseEntity<ItemDto> responseEntity = controller.updateItem(1L, dto);
        ItemDto response = responseEntity.getBody();

        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo(PRODUCT_NAME);
        assertThat(response.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(response.getQuantity()).isEqualTo(1);
    }

    @Test
    public void deleteItem_thenReturnSuccess() {
        when(service.deleteItem(any())).thenReturn(1L);

        ResponseEntity<Long> responseEntity = controller.deleteItem(1L);
        Long itemId = responseEntity.getBody();

        assertThat(itemId).isEqualTo(1L);
    }

    @Test
    public void getDetail_thenReturnSuccess() {
        Item item = getDummyItem();
        ItemDto dto = ItemDto.builder().id(1L).name(PRODUCT_NAME).description(DESCRIPTION).quantity(1).build();

        when(service.getDetail(any())).thenReturn(item);
        when(mapper.toDto(any())).thenReturn(dto);

        ResponseEntity<ItemDto> responseEntity = controller.getDetail(1L);
        ItemDto response = responseEntity.getBody();

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo(PRODUCT_NAME);
        assertThat(response.getDescription()).isEqualTo(DESCRIPTION);
    }

    @Test
    public void searchName_thenReturnSuccess() {
        Item item = getDummyItem();
        ItemDto dto = ItemDto.builder().id(1L).name(PRODUCT_NAME).description(DESCRIPTION).quantity(1).build();

        when(service.searchItem(PRODUCT_NAME)).thenReturn(Arrays.asList(item));
        when(mapper.map(any())).thenReturn(Arrays.asList(dto));

        ResponseEntity<List<ItemDto>> responseEntity = controller.searchItem(PRODUCT_NAME);
        List<ItemDto> dtoList = responseEntity.getBody();

        assertThat(dtoList).isNotNull();
        assertThat(dtoList.size()).isEqualTo(1);

    }

    private Item getDummyItem() {
        Item item = new Item();
        item.setId(1L);
        item.setProductName(PRODUCT_NAME);
        item.setDescription(DESCRIPTION);
        item.setQuantity(1);
        return item;
    }
}
