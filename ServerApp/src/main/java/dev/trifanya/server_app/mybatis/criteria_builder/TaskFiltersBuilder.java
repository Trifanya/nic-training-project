package dev.trifanya.server_app.mybatis.criteria_builder;

import org.mybatis.dynamic.sql.*;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.ColumnSortSpecification;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

import java.util.*;
import java.time.LocalDateTime;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

public class TaskFiltersBuilder {
    private static final SqlTable sqlTable = SqlTable.of("task");

    public static SelectStatementProvider generateSelectStatement(Map<String, String> filters, String sortByColumn, String sortDir) {
        SelectStatementProvider selectStatement;
        if (filters == null || filters.isEmpty()) {
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
            } else if (key.endsWith("Start")) {
                String columnName = key.substring(0, key.length() - "Start".length());
                LocalDateTime dateTime = LocalDateTime.parse(filter.getValue());
                criteriaGroups.add(createRangeStartCriterion(columnName, dateTime));
            } else if (key.endsWith("End")) {
                String columnName = key.substring(0, key.length() - "End".length());
                LocalDateTime dateTime = LocalDateTime.parse(filter.getValue());
                criteriaGroups.add(createRangeEndCriterion(columnName, dateTime));
            } else if (key.equals("searchStr")) {
                String columnName = "title";
                String value = "%" + filter.getValue() + "%";
                criteriaGroups.add(createLikeCriterion(columnName, value));
            } else {
                criteriaGroups.add(createEqualsCriterion(filter.getKey(), filter.getValue()));
            }
        }
        return criteriaGroups;
    }

    public static SortSpecification buildSortCriterion(String sortByColumn, String sortDir) {
        SortSpecification specification = new ColumnSortSpecification("task", sqlTable.column(sortByColumn));
        if (sortDir.equals("DESC")) specification = specification.descending();
        return specification;
    }

    public static AndOrCriteriaGroup createEqualsCriterion(String columnName, String value) {
        ColumnAndConditionCriterion criterion = new ColumnAndConditionCriterion.Builder()
                .withColumn(sqlTable.column(columnName))
                .withCondition(isEqualTo(value))
                .build();
        return new AndOrCriteriaGroup.Builder()
                .withInitialCriterion(criterion)
                .withConnector("and")
                .build();
    }

    public static AndOrCriteriaGroup createLikeCriterion(String columnName, String value) {
        ColumnAndConditionCriterion criterion = new ColumnAndConditionCriterion.Builder()
                .withColumn(sqlTable.column(columnName))
                .withCondition(isLikeCaseInsensitive(value))
                .build();
        return new AndOrCriteriaGroup.Builder()
                .withInitialCriterion(criterion)
                .withConnector("and")
                .build();
    }

    public static AndOrCriteriaGroup createInCriterion(String columnName, Set<String> columnValues) {
        ColumnAndConditionCriterion criterion = new ColumnAndConditionCriterion.Builder()
                .withColumn(sqlTable.column(columnName))
                .withCondition(isIn(columnValues))
                .build();
        return new AndOrCriteriaGroup.Builder()
                .withInitialCriterion(criterion)
                .withConnector("and")
                .build();
    }

    public static AndOrCriteriaGroup createRangeStartCriterion(String columnName, LocalDateTime rangeStart) {
        ColumnAndConditionCriterion criterion = new ColumnAndConditionCriterion.Builder()
                .withColumn(sqlTable.column(columnName))
                .withCondition(isGreaterThan(rangeStart))
                .build();
        return new AndOrCriteriaGroup.Builder()
                .withInitialCriterion(criterion)
                .withConnector("and")
                .build();
    }

    public static AndOrCriteriaGroup createRangeEndCriterion(String columnName, LocalDateTime rangeEnd) {
        ColumnAndConditionCriterion criterion = new ColumnAndConditionCriterion.Builder()
                .withColumn(sqlTable.column(columnName))
                .withCondition(isLessThan(rangeEnd))
                .build();
        return new AndOrCriteriaGroup.Builder()
                .withInitialCriterion(criterion)
                .withConnector("and")
                .build();
    }

}
