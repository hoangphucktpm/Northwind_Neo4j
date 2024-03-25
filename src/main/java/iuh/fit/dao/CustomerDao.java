/*
 * @(#) CustomerDao.java       1.0  24/03/2024
 *
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package iuh.fit.dao;

import iuh.fit.entity.Customer;
import iuh.fit.utils.AppUtils;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * @description:
 * @author: Hoang Phuc
 * @date:   24/03/2024
 * @version:    1.0
 */
public class CustomerDao {
    private Driver driver;
    SessionConfig sessionConfig;

    public CustomerDao(Driver driver, String dbName) {
        this.driver = driver;
        sessionConfig = SessionConfig.builder().withDatabase(dbName).build();
    }

    // 4. Thống kê số khách hàng theo từng thành phố (sắp xếp theo số khách hàng / theo city).
    //+ getNumberCustomerByCity() : Map<String, Integer>

    public Map<String, Integer> getNumberCustomerByCity() {
        String query = "MATCH (c:Customer) RETURN c.city AS city, count(c) AS number ORDER BY number DESC";
        try (Session session = driver.session(sessionConfig)) {
            return session.executeRead(tx -> {
                Result result = tx.run(query);
                if (!result.hasNext()) {
                    return null;
                }
                return result.stream()
                        .collect(Collectors.toMap(
                                record -> record.get("city").asString(),
                                record -> record.get("number").asInt(),
                                (x, y) -> y,
                                LinkedHashMap::new
                        ));
            });
        }
    }

    //6.  Đếm số đơn hàng của từng khách hàng.
    //+ getOrdersByCustomers() : Map<Customer, Integer>
    public Map<Customer, Integer> getOrdersByCustomers() {
        String query = "MATCH (c:Customer)-[r:PURCHASED]->(o:Order) RETURN c, count(o) AS count";
        try (Session session = driver.session(sessionConfig)) {
            return session.executeRead(tx -> {
                Result result = tx.run(query);
                if (!result.hasNext()) {
                    return null;
                }
                return result.stream()
                        .collect(Collectors.toMap(
                                record -> AppUtils.convert(record.get("c").asNode(), Customer.class),
                                record -> record.get("count").asInt()
                        ));
            });
        }
    }

    public void close() {
        driver.close();
    }
}
