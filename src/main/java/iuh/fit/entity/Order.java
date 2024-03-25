/*
 * @(#) Order.java       1.0  24/03/2024
 *
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package iuh.fit.entity;

import lombok.*;

import java.time.LocalDate;

/*
 * @description:
 * @author: Hoang Phuc
 * @date:   24/03/2024
 * @version:    1.0
 */
@Getter
@ToString
@Setter
@NoArgsConstructor
public class Order {

    private String orderID;
    private String shipCity;
    private String freight;
    private String requiredDate;
    private String employeeID;
    private String shipName;

    private String shipPostalCode;
    private String shipCountry;
    private String shipAddress;
    private String shipVia;

    private LocalDate shippedDate;
    private LocalDate orderDate;
    private String shipRegion;

    @ToString.Exclude
    private Customer customerID;

    public Order(String orderID, String shipCity, String freight, String requiredDate, String employeeID, String shipName, String shipPostalCode, String shipCountry, String shipAddress, String shipVia, LocalDate shippedDate, LocalDate orderDate, String shipRegion) {
        this.orderID = orderID;
        this.shipCity = shipCity;
        this.freight = freight;
        this.requiredDate = requiredDate;
        this.employeeID = employeeID;
        this.shipName = shipName;
        this.shipPostalCode = shipPostalCode;
        this.shipCountry = shipCountry;
        this.shipAddress = shipAddress;
        this.shipVia = shipVia;
        this.shippedDate = shippedDate;
        this.orderDate = orderDate;
        this.shipRegion = shipRegion;
    }
}

