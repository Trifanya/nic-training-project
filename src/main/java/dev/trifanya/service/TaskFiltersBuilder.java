package dev.trifanya.service;

import dev.trifanya.model.Task;
import org.mybatis.dynamic.sql.*;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

import java.util.*;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

public class TaskFiltersBuilder {
    private static final SqlTable sqlTable = SqlTable.of("task");

    public static SelectStatementProvider generateSelectStatement(Map<String, String> filters) {
        for (Map.Entry<String, String> filter : filters.entrySet()) {
            System.out.println(filter.getKey() + " " + filter.getValue());
        }
        SelectStatementProvider selectStatement = select(sqlTable.allColumns())
                .from(SqlTable.of("task"))
                .where(buildFilterCriterion(filters))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        return selectStatement;
    }

    public static List<AndOrCriteriaGroup> buildFilterCriterion(Map<String, String> filters) {
        List<AndOrCriteriaGroup> criteriaGroups = new ArrayList<>();

        for (Map.Entry<String, String> filter : filters.entrySet()) {
            String key = filter.getKey();
            if (key.endsWith("Values")) {
                String columnName = key.substring(0, key.length() - "Values".length());
                //System.out.println(columnName);
                Set<String> columnValues = new HashSet<>(Arrays.asList(filter.getValue().split(",")));
                /*for (String value : columnValues) {
                    System.out.println(value);
                }*/
                criteriaGroups.add(createInCriterion(columnName, columnValues));
            } else if (key.endsWith("Range")) {

            }
        }

        return criteriaGroups;
    }

    public static AndOrCriteriaGroup createInCriterion(String columnName, Set<String> columnValues) {
        System.out.println(columnName);
        for (String value : columnValues) {
            System.out.println(value);
        }

        ColumnAndConditionCriterion inCriterion = new ColumnAndConditionCriterion.Builder()
                .withColumn(sqlTable.column(columnName))
                .withCondition(isIn(columnValues))
                .build();

        return new AndOrCriteriaGroup.Builder()
                .withInitialCriterion(inCriterion)
                .withConnector("and")
                .build();
    }

}
