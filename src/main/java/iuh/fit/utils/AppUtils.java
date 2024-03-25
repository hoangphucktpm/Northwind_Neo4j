/*
 * @(#) AppUtils.java       1.0  24/03/2024
 *
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package iuh.fit.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.types.Node;

import java.net.URI;
import java.util.Map;

/*
 * @description:
 * @author: Hoang Phuc
 * @date:   24/03/2024
 * @version:    1.0
 */
public class AppUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Driver initDriver() {
        URI uri = URI.create("neo4j://localhost:7687");
        String user = "neo4j";
        String pass = "12345678";
        return GraphDatabase.driver(uri, AuthTokens.basic(user, pass));
}

        public static <T> T convert(Node node, Class<T> clazz) {
            Map<String, Object> properties = node.asMap();
            try {
                String json = objectMapper.writeValueAsString(properties);
                return objectMapper.readValue(json, clazz);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Convert a node to Customer
         *
         * @param node
         */


    }

