/*
 * @(#) OrderDao.java       1.0  24/03/2024
 *
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package iuh.fit.dao;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * @description:
 * @author: Hoang Phuc
 * @date:   24/03/2024
 * @version:    1.0
 */
public class OrderDao {
    private Driver driver;
    SessionConfig sessionConfig;

    public OrderDao(Driver driver, String dbName) {
        this.driver = driver;
        this.sessionConfig = SessionConfig.forDatabase(dbName);
    }

    //5. Tính tổng tiền của đơn hàng khi biết mã số đơn hàng.
    public double calculateTotalPrice(String orderID) {
        String query = "MATCH (o:Order {orderID: $orderID})-[r:ORDERS]->(p:Product) RETURN sum(toInteger(r.unitPrice) * r.quantity) AS totalPrice";
        try (Session session = driver.session(sessionConfig)) {
            return session.executeRead(tx -> {
                Result result = tx.run(query, Map.of("orderID", orderID));
                if (!result.hasNext()) {
                    return 0.0;
                }
                return result.single().get("totalPrice").asDouble();
            });
        }
        }

        // 9. Tính tổng tiền của tất cả các hóa đơn trong một ngày nào đó.
        public double totalPriceOfOrderInSpecificDate(String date) {
            String query = "MATCH (o:Order)-[r:ORDERS]->(p:Product) where o.orderDate= datetime($date) RETURN sum(toFloat(r.unitPrice)*r.quantity) as totalAmount";
            try (Session session = driver.session(sessionConfig)) {
                return session.executeRead(tx -> {
                    Result result = tx.run(query, Map.of("date", date));
                    if (!result.hasNext()) {
                        return 0.0;
                    }
                    return result.single().get("totalAmount").asDouble();
                });
            }
        }

        // 10. Thống kê tổng tiền hóa đơn theo tháng / năm.
        public double totalPriceOfOrderInSpecificMonthYear(int month, int year) {
            String query = "MATCH (o:Order)-[r:ORDERS]->(p:Product) where o.orderDate.month= $month and o.orderDate.year= $year RETURN sum(toFloat(r.unitPrice)*r.quantity) as totalAmount";
            try (Session session = driver.session(sessionConfig)) {
                return session.executeRead(tx -> {
                    Result result = tx.run(query, Map.of("month", month, "year", year));
                    if (!result.hasNext()) {
                        return 0.0;
                    }
                    return result.single().get("totalAmount").asDouble();
                });
            }
        }


    public void close() {
        driver.close();
    }
}
