/*
 * @(#) OrderDaoTest.java       1.0  24/03/2024
 *
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package dao;

import iuh.fit.dao.OrderDao;
import iuh.fit.utils.AppUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.time.LocalDateTime;

/*
 * @description:
 * @author: Hoang Phuc
 * @date:   24/03/2024
 * @version:    1.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderDaoTest {
    private static String DB_NAME = "neo4j";
    private OrderDao orderDao;

    @BeforeAll
    public void setUp() {
        orderDao = new OrderDao(AppUtils.initDriver(), DB_NAME);
    }

    // 5. Tính tổng tiền của đơn hàng khi biết mã số đơn hàng.
    @Test
    public void testGetTotalPriceByOrderID() {
        System.out.println(orderDao.calculateTotalPrice("10251"));
    }

    // 9. Tính tổng tiền của tất cả các hóa đơn trong một ngày nào đó.
    @Test
    public void testGetTotalPriceByDate() {
        System.out.println("Tien cua tat ca cac hoa don trong ngay 1996-07-08: " + orderDao.totalPriceOfOrderInSpecificDate("1996-07-04"));

    }

    //10. Thống kê tổng tiền hóa đơn theo tháng / năm.
    @Test
    public void testGetTotalPriceByMonthYear() {
        System.out.println("Tong tien cua hoa don theo thang/nam: " + orderDao.totalPriceOfOrderInSpecificMonthYear(07,1996));
    }


    @AfterAll
    public void tearDown() {
        orderDao.close();
    }
}
