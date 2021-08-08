package id.co.jriztana.app.inventory.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {
    @JsonView(value = {CommonView.POST.class, CommonView.PUT.class, CommonView.GET.class })
    private Long id;

    @JsonView(value = {CommonView.POST.class, CommonView.GET.class})
    private String name;

    @JsonView(value = {CommonView.PUT.class, CommonView.GET.class})
    private String description;

    @JsonView(value = {CommonView.PUT.class, CommonView.GET.class})
    private Integer quantity;

    @JsonView(value = {CommonView.PUT.class, CommonView.GET.class})
    private BigDecimal price;

}
