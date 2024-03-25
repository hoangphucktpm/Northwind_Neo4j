/*
 * @(#) ProductDaoTest.java       1.0  24/03/2024
 *
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package dao;

import iuh.fit.dao.ProductDao;
import iuh.fit.utils.AppUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/*
 * @description:
 * @author: Hoang Phuc
 * @date:   24/03/2024
 * @version:    1.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductDaoTest {

    public static String DB_NAME = "neo4j";
    private ProductDao productDao;

    @BeforeAll
    public void setUp() {
        productDao = new ProductDao(AppUtils.initDriver(), DB_NAME);
    }

    // 2. Tìm các sản phẩm được cung cấp bởi một nhà cung cấp nào đó khi biết tên nhà cung cấp
    @Test
    public void testListProductsBySupplier() {
        productDao.listProductsBySupplier("Exotic Liquids").forEach(System.out::println);

    }

    // 3. Tìm danh sách sản phẩm có giá cao nhất.
    @Test
    public void testListProductsHaveHighestPrice() {
        productDao.listProductsHaveHighestPrice().forEach(System.out::println);
    }

    // 7. Tính tổng số lượng của từng sản phẩm đã ban ra
    @Test
    public void testCalculateTotalQuantity() {
        productDao.getTotalProduct().forEach((k, v) -> System.out.println(k + " : " + v));
    }

    // 8. Dùng text search để tìm kiếm sản phẩm theo tên sản phẩm.
    @Test
    public void testSearchProductByName() {
        productDao.searchProductByName("Chai").forEach(System.out::println);
    }



    @AfterAll
    public void tearDown() {
        productDao.close();
    }


}
