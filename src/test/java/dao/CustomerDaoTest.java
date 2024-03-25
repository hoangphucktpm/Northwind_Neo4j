/*
 * @(#) CustomerDaoTest.java       1.0  24/03/2024
 *
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package dao;

import iuh.fit.dao.CustomerDao;
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
public class CustomerDaoTest {

    public static String DB_NAME = "neo4j";
    private CustomerDao customerDao;

    @BeforeAll
    public void setUp() {
        customerDao = new CustomerDao(AppUtils.initDriver(), DB_NAME);
    }

    // 4. Thống kê số khách hàng theo từng thành phố (sắp xếp theo số khách hàng / theo city).
    //+ getNumberCustomerByCity() : Map<String, Integer>

    @Test
    public void testGetNumberCustomerByCity() {
        System.out.println(customerDao.getNumberCustomerByCity());
    }

    // 6. Đếm số đơn hàng của từng khách hàng.
    @Test
    public void testCountOrdersByCustomer() {
        customerDao.getOrdersByCustomers().forEach((k, v) -> System.out.println(k + " : " + v));
    }


    @AfterAll
    public void tearDown() {
        customerDao.close();
    }
}
