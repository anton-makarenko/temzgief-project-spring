package com.shop.entity;

import com.shop.enumeration.Size;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "clothes")
public class Clothes extends Product {
    @Enumerated(EnumType.STRING)
    private Size size;

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
}
