package com.example.inventory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product_inventory")
@Getter
@Setter
public class ProductInventory {

    @Id
    @Column(name = "sku_id")
    private String skuId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "inventory")
    private Integer inventory;
}
