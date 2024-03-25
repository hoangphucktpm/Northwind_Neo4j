/*
 * @(#) Customer.java       1.0  24/03/2024
 *
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package iuh.fit.entity;

import lombok.*;

/*
 * @description:
 * @author: Hoang Phuc
 * @date:   24/03/2024
 * @version:    1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {

    private String customerID;
    private String contactName;
    private String companyName;
    private String country;
    private String contactTitle;
    private String address;
    private String phone;
    private String city;
    private String postalCode;
    private String fax;
    private String region;

}