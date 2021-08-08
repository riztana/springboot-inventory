package id.co.jriztana.app.inventory.service;

import id.co.jriztana.app.inventory.exception.DataNotFoundException;
import id.co.jriztana.app.inventory.model.Item;
import id.co.jriztana.app.inventory.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InventoryServiceTest {
    private static final String PRODUCT_NAME        = "Product Name";
    private static final String PRODUCT_DESCRIPTION = "Product Description";

    @InjectMocks
    InventoryServiceImpl service;

    @Mock
    ItemRepository repository;

    @Test
    public void getDetail_thenReturnItem() {
        when(repository.findById(1L)).thenReturn(Optional.of(getDummyItem()));

        Item item = service.getDetail(1L);

        assertThat(item).isNotNull();
        assertThat(item.getProductName()).isEqualTo(PRODUCT_NAME);
        assertThat(item.getDescription()).isEqualTo(PRODUCT_DESCRIPTION);
        assertThat(item.getQuantity()).isEqualTo(1);
    }

    @Test(expected = DataNotFoundException.class)
    public void getDetail_notFound_thenReturnDataNotFoundException() {
        service.getDetail(2L);
    }

    @Test
    public void searchName_thenReturnItemList() {
        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(getDummyItem());

        when(repository.findByProductNameContainsIgnoreCase(any())).thenReturn(itemList);

        List<Item> resultList = service.searchItem("test");

        assertThat(resultList.size()).isEqualTo(1);
    }

    @Test
    public void addItem_newItem_thenReturnQuantity1() {
        when(repository.findByProductName(any())).thenReturn(Optional.empty());

        Item item = service.addItem("new");

        assertThat(item).isNotNull();
        assertThat(item.getProductName()).isEqualTo("new");
        assertThat(item.getQuantity()).isEqualTo(1);
    }

    @Test
    public void addItem_existingItem_thenReturnQuantity2() {
        when(repository.findByProductName(PRODUCT_NAME)).thenReturn(Optional.of(getDummyItem()));

        Item item = service.addItem(PRODUCT_NAME);

        assertThat(item).isNotNull();
        assertThat(item.getProductName()).isEqualTo(PRODUCT_NAME);
        assertThat(item.getQuantity()).isEqualTo(2);
    }

    @Test
    public void updateItem_thenReturnSuccess() {
        when(repository.findById(any())).thenReturn(Optional.of(getDummyItem()));

        Item item = service.updateItem(getDummyItem());

        assertThat(item).isNotNull();
        assertThat(item.getProductName()).isEqualTo(PRODUCT_NAME);
        assertThat(item.getDescription()).isEqualTo(PRODUCT_DESCRIPTION);
        assertThat(item.getQuantity()).isEqualTo(1);
    }

    @Test(expected = DataNotFoundException.class)
    public void updateItem_thenReturnDataNotFoundException() {
        service.updateItem(getDummyItem());
    }

    @Test
    public void deleteItem_thenReturnSuccess() {
        Long id = service.deleteItem(1L);
        assertThat(id).isEqualTo(1L);
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
