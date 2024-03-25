/*
 * @(#) ProductDao.java       1.0  24/03/2024
 *
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package iuh.fit.dao;

import iuh.fit.entity.Product;
import iuh.fit.utils.AppUtils;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.types.Node;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * @description:
 * @author: Hoang Phuc
 * @date:   24/03/2024
 * @version:    1.0
 */
public class ProductDao {

    private Driver driver;
    SessionConfig sessionConfig;

    public ProductDao(Driver driver, String dbName) {
        this.driver = driver;
        sessionConfig = SessionConfig.builder().withDatabase(dbName).build();
    }

    // 2. Tìm các sản phẩm được cung cấp bởi một nhà cung cấp nào đó khi biết tên nhà cung cấp
    public List<Product> listProductsBySupplier(String supplierName) {
        String query = "MATCH (s:Supplier {companyName: $name})-[r:SUPPLIES]->(p:Product) RETURN p";
        Map<String, Object> params = Map.of("name", supplierName);
        try (Session session = driver.session(sessionConfig)) {
            return session.executeRead(tx -> {
                Result result = tx.run(query, params);
                if (!result.hasNext()) {
                    return null;
                }
                return result.stream()
                        .map(record -> {
                            Node node = record.get("p").asNode();
                            return AppUtils.convert(node, Product.class);
                        })
                        .toList();
            });
        }
    }

    //3. Tìm danh sách sản phẩm có giá cao nhất.
    public List<Product> listProductsHaveHighestPrice() {
        String query = "MATCH (p:Product) RETURN p ORDER BY p.unitPrice DESC LIMIT 1";
        try (Session session = driver.session(sessionConfig)) {
            return session.executeRead(tx -> {
                Result result = tx.run(query);
                if (!result.hasNext()) {
                    return null;
                }
                return result.stream()
                        .map(record -> {
                            Node node = record.get("p").asNode();
                            return AppUtils.convert(node, Product.class);
                        })
                        .toList();
            });
        }
    }

    //7. Tính tổng số lượng của từng sản phẩm đã bán ra.
    //+ getTotalProduct(): Map<String, Integer>
    public Map<String, Integer> getTotalProduct() {
        String query = "MATCH ()-[r:ORDERS]->(p:Product) RETURN p.productName AS productName, count(r.orderID) AS total";
        try (Session session = driver.session(sessionConfig)) {
            return session.executeRead(tx -> {
                Result result = tx.run(query);
                if (!result.hasNext()) {
                    return null;
                }
                return result.stream()
                        .collect(Collectors.toMap(
                                record -> record.get("productName").asString(),
                                record -> record.get("total").asInt()
                        ));
            });
        }
    }

    // 8. Dùng text search để tìm kiếm sản phẩm theo tên sản phẩm.
    // CREATE FULLTEXT INDEX text_index_pName for (p:Product) on EACH [p.productName]
    public List<Product> searchProductByName(String name) {

        String query = "CALL db.index.fulltext.queryNodes('text_index_pName', $name) YIELD node RETURN node";
        Map<String, Object> pars = Map.of("name", name);

        try (Session session = driver.session(sessionConfig)) {
            return session.executeRead(tx -> {
                Result result = tx.run(query, pars);
                if (!result.hasNext()) {
                    return null;
                }
                return result.stream()
                        .map(record -> {
                            Node node = record.get("node").asNode();
                            return AppUtils.convert(node, Product.class);
                        })
                        .toList();
            });
        }
    }




    public void close() {
        driver.close();
    }
}
