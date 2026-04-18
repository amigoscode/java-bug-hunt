package com.amigoscode.bughunt.medium.bug55;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryBuilderTest {

    @Test
    void secondBuildDoesNotCarryConditionsFromFirst() {
        QueryBuilder builder = new QueryBuilder();

        String firstQuery = builder
                .select("*")
                .from("users")
                .where("age > 18")
                .where("active = true")
                .build();

        // Reuse builder for a different query
        String secondQuery = builder
                .select("name")
                .from("products")
                .where("price < 100")
                .build();

        // Second query should only have its own condition
        assertThat(secondQuery).isEqualTo("SELECT name FROM products WHERE price < 100");
    }

    @Test
    void builderConditionCountResetsOnNewQuery() {
        QueryBuilder builder = new QueryBuilder();

        builder.select("*").from("orders").where("status = 'open'").where("total > 50").build();

        // Start fresh query
        builder.select("id").from("customers").where("vip = true");

        assertThat(builder.conditionCount()).isEqualTo(1);
    }

    @Test
    void buildProducesCorrectQueryOnFirstUse() {
        QueryBuilder builder = new QueryBuilder();

        String query = builder
                .select("id, name")
                .from("employees")
                .where("department = 'Engineering'")
                .orderBy("name")
                .limit(10)
                .build();

        assertThat(query).isEqualTo(
                "SELECT id, name FROM employees WHERE department = 'Engineering' ORDER BY name LIMIT 10"
        );
    }
}
