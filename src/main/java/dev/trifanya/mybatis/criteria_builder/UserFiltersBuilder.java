package dev.trifanya.mybatis.criteria_builder;

import org.mybatis.dynamic.sql.AndOrCriteriaGroup;
import org.mybatis.dynamic.sql.ColumnAndConditionCriterion;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.ColumnSortSpecification;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

import java.time.LocalDateTime;
import java.util.*;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

public class UserFiltersBuilder {
    private static final SqlTable sqlTable = SqlTable.of("tms_user");

    public static SelectStatementProvider generateSelectStatement(Map<String, String> filters, String sortByColumn, String sortDir) {
        SelectStatementProvider selectStatement;
        System.out.println("filters: " + filters);
        if (filters == null || filters.isEmpty()) {
            selectStatement = select(sqlTable.allColumns())
                    .from(sqlTable)
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
            if (key.equals("searchStr")) {
                String column1Name = "name";
                String column2Name = "surname";
                String value = "%" + filter.getValue() + "%";
                criteriaGroups.add(createLikeCriterion(column1Name, column2Name, value));
            } else {
                criteriaGroups.add(createEqualsCriterion(filter.getKey(), filter.getValue()));
            }
        }
        return criteriaGroups;
    }

    public static SortSpecification buildSortCriterion(String sortByColumn, String sortDir) {
        SortSpecification specification = new ColumnSortSpecification("tms_user", sqlTable.column(sortByColumn));
        if (sortDir.equals("DESC")) specification = specification.descending();
        return specification;
    }

    public static AndOrCriteriaGroup createLikeCriterion(String column1Name, String column2Name, String value) {
        System.out.println("column: " + column1Name + " searchStr: " + value);

        ColumnAndConditionCriterion criterion1 = new ColumnAndConditionCriterion.Builder()
                .withColumn(sqlTable.column(column1Name))
                .withCondition(isLikeCaseInsensitive(value))
                .build();
        ColumnAndConditionCriterion criterion2 = new ColumnAndConditionCriterion.Builder()
                .withColumn(sqlTable.column(column2Name))
                .withCondition(isLikeCaseInsensitive(value))
                .build();

        AndOrCriteriaGroup builtCriterion2 = new AndOrCriteriaGroup.Builder()
                .withInitialCriterion(criterion2)
                .withConnector("or")
                .build();
        return new AndOrCriteriaGroup.Builder()
                .withInitialCriterion(criterion1)
                .withSubCriteria(Collections.singletonList(builtCriterion2))
                .withConnector("and")
                .build();
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
}
