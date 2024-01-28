package dev.trifanya.service;

import org.mybatis.dynamic.sql.*;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.ColumnSortSpecification;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

import java.util.*;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

public class TaskFiltersBuilder {
    private static final SqlTable sqlTable = SqlTable.of("task");

    public static SelectStatementProvider generateSelectStatement(Map<String, String> filters, String sortByColumn, String sortDir) {
        SelectStatementProvider selectStatement;
        if (filters.isEmpty() || filters == null) {
            selectStatement = select(sqlTable.allColumns())
                    .from(SqlTable.of("task"))
                    .orderBy(buildSortCriterion(sortByColumn, sortDir))
                    .build()
                    .render(RenderingStrategies.MYBATIS3);
        } else {
            selectStatement = select(sqlTable.allColumns())
                    .from(sqlTable)
                    .where(buildFilterCriterion(filters))
                    .orderBy(buildSortCriterion(sortByColumn, sortDir))
                    .build()
                    .render(RenderingStrategies.MYBATIS3);
        }
        return selectStatement;
    }

    public static List<AndOrCriteriaGroup> buildFilterCriterion(Map<String, String> filters) {
        List<AndOrCriteriaGroup> criteriaGroups = new ArrayList<>();

        for (Map.Entry<String, String> filter : filters.entrySet()) {
            String key = filter.getKey();
            if (key.endsWith("Values")) {
                String columnName = key.substring(0, key.length() - "Values".length());
                Set<String> columnValues = new HashSet<>(Arrays.asList(filter.getValue().split(",")));
                criteriaGroups.add(createInCriterion(columnName, columnValues));
            }
        }
        return criteriaGroups;
    }

    public static SortSpecification buildSortCriterion(String sortByColumn, String sortDir) {
        SortSpecification specification = new ColumnSortSpecification("task", sqlTable.column(sortByColumn));
        if (sortDir.equals("DESC")) specification = specification.descending();
        return specification;
    }

    public static AndOrCriteriaGroup createInCriterion(String columnName, Set<String> columnValues) {
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
