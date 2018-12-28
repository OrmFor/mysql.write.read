package com.test.springboot.domain.entity.vo;

import java.util.List;

public class Expressions {
    public static Expression isNull(String column) {
        return new Expression(0, column, "IS NULL");
    }

    public static Expression isNotNull(String column) {
        return new Expression(0, column, "IS NOT NULL");
    }

    public static Expression eq(String column, Object value) {
        return new Expression(1, column, "=", value);
    }

    public static Expression ne(String column, Object value) {
        return new Expression(1, column, "<>", value);
    }

    public static Expression lt(String column, Object value) {
        return new Expression(1, column, "<", value);
    }

    public static Expression le(String column, Object value) {
        return new Expression(1, column, "<=", value);
    }

    public static Expression gt(String column, Object value) {
        return new Expression(1, column, ">", value);
    }

    public static Expression ge(String column, Object value) {
        return new Expression(1, column, ">=", value);
    }

    public static Expression like(String column, Object value) {
        return new Expression(1, column, "LIKE", value);
    }

    public static Expression notLike(String column, Object value) {
        return new Expression(1, column, "NOT LIKE", value);
    }

    public static Expression between(String column, Object value, Object value1) {
        return new Expression(2, column, "BETWEEN", value, value1);
    }

    public static Expression notBetween(String column, Object value, Object value1) {
        return new Expression(2, column, "NOT BETWEEN", value, value1);
    }

    public static Expression in(String column, List<?> values) {
        return new Expression(3, column, "IN", values);
    }

    public static Expression notIn(String column, List<?> values) {
        return new Expression(3, column, "NOT IN", values);
    }

    private Expressions() {
    }
}

