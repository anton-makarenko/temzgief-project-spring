package com.shop.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "deliveries")
public class Delivery implements Serializable {
    @Id
    @OneToOne
    @JoinColumn(name = "order_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(name = "begin_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date beginDate;

    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(length = 1023)
    private String details;

    @Basic(optional = false)
    @Column(name = "create_date", insertable = false, updatable = false, columnDefinition = "timestamp not null default current_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Basic(optional = false)
    @Column(name = "last_update", insertable = false, updatable = false, columnDefinition = "timestamp not null default current_timestamp on update current_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }
}
