package com.amigoscode.bughunt.medium.bug55;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QueryBuilder {

    private String selectClause;
    private String fromClause;
    private final List<String> conditions = new ArrayList<>();
    private String orderByClause;
    private Integer limitValue;

    public QueryBuilder select(String columns) {
        this.selectClause = columns;
        return this;
    }

    public QueryBuilder from(String table) {
        this.fromClause = table;
        return this;
    }

    public QueryBuilder where(String condition) {
        conditions.add(condition);
        return this;
    }

    public QueryBuilder orderBy(String column) {
        this.orderByClause = column;
        return this;
    }

    public QueryBuilder limit(int limit) {
        this.limitValue = limit;
        return this;
    }

    public String build() {
        if (selectClause == null || selectClause.isBlank()) {
            throw new IllegalStateException("SELECT clause is required");
        }
        if (fromClause == null || fromClause.isBlank()) {
            throw new IllegalStateException("FROM clause is required");
        }

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(selectClause);
        sql.append(" FROM ").append(fromClause);

        if (!conditions.isEmpty()) {
            String whereClause = conditions.stream()
                    .collect(Collectors.joining(" AND "));
            sql.append(" WHERE ").append(whereClause);
        }

        if (orderByClause != null) {
            sql.append(" ORDER BY ").append(orderByClause);
        }

        if (limitValue != null) {
            sql.append(" LIMIT ").append(limitValue);
        }

        return sql.toString();
    }

    public QueryBuilder reset() {
        selectClause = null;
        fromClause = null;
        conditions.clear();
        orderByClause = null;
        limitValue = null;
        return this;
    }

    public int conditionCount() {
        return conditions.size();
    }

    public boolean hasOrderBy() {
        return orderByClause != null;
    }

    public boolean hasLimit() {
        return limitValue != null;
    }

    public String preview() {
        try {
            return build();
        } catch (IllegalStateException e) {
            return "<incomplete query: " + e.getMessage() + ">";
        }
    }
}
