package com.shop.entity;

import com.shop.enumeration.Size;

import javax.persistence.*;

@Entity
@Table(name = "clothes")
public class Clothes extends Product {
    @Column(nullable = false, length = 3)
    @Enumerated(EnumType.STRING)
    private Size size;

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
}
