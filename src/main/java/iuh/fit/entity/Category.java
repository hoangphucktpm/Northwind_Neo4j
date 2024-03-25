/*
 * @(#) Category.java       1.0  24/03/2024
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
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Setter
@Getter
public class Category {
    private String categoryID;
    private String categoryName;
    private String description;
    private String picture;

}