package com.test.springboot.domain.entity.vo;

import java.util.HashSet;
import java.util.Set;

public class DAOUtils {
    private static final String COLUMN_REGEX = "^\\*$|^\\w+$";
    private static final String ORDER_BY_REGEX = "[\\w\\s,]+$";
    private static final Set<String> FUNCTION_SET = new HashSet<String>();

    static {
        FUNCTION_SET.add("count");
        FUNCTION_SET.add("distinctcount");
        FUNCTION_SET.add("sum");
        FUNCTION_SET.add("max");
        FUNCTION_SET.add("min");
        FUNCTION_SET.add("avg");
    }

    public static void checkColumn(String column) {
        if (!column.matches(COLUMN_REGEX)) {
            throw new IllegalArgumentException("Illegal column: " + column);
        }
    }

    public static void checkOrderBy(String column) {
        String column_tmp = column;
        column_tmp = column_tmp.replaceAll("CHAR_LENGTH\\([\\w\\s]+\\)", "");//放行字段长度函数
        column_tmp = column_tmp.replaceAll("ABS\\([\\w\\s]+\\)", "");//放行字段长度函数
        if (!column_tmp.matches(ORDER_BY_REGEX)) {
            throw new IllegalArgumentException("Illegal order by: " + column);
        }
    }

    public static void checkFunction(String function) {
        if (!FUNCTION_SET.contains(function)) {
            throw new IllegalArgumentException("Illegal function: " + function);
        }
    }

    public static String buildAggregateSql(String[] functions, String[] columns) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < functions.length; ++i) {
            if (i > 0) {
                sb.append(", ");
            }
            String function = functions[i].toLowerCase();
            String column = columns[i];
            if ("*".equals(column)) {
                throw new RuntimeException("error column for *");
            }
            checkFunction(function);
            checkColumn(column);
            switch (function.toUpperCase()) {
                case "DISTINCTCOUNT":
                    sb.append("COUNT( DISTINCT ").append("`").append(column).append("`").append(")");
                    break;
                case "SUM":
                    sb.append("IFNULL(").append(function.toUpperCase()).append("(").append("`").append(column).append("`").append("), 0)");
                    break;
                default:
                    sb.append(function.toUpperCase()).append("(").append("`").append(column).append("`").append(")");
                    break;
            }
            sb.append(" AS ");
            sb.append(function).append("_");
            sb.append(column);
        }
        return sb.toString();
    }

    private DAOUtils() {
    }

    public static void main(String[] args) {
        String a = "select count(*) from aaa where aaa.user_Id = 123";
        a = "dffdDFGG sdd_sd asc , id DESC;";
        checkOrderBy(a);
    }
}


