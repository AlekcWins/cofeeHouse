package ru.cofee.house.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_order", nullable = false)
    @JsonIgnore // todo remove if not nedede and add dto
    private Order order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_item", referencedColumnName = "id")
    private Item item;

    private int count;
    //todo remove if not neded
//    @JsonProperty(value = "id_order")
//    public long getOrderId() {
//        return order.getId();
//    }
}
