package dev.trifanya.activemq.criteria_builder;

import org.mybatis.dynamic.sql.AndOrCriteriaGroup;
import org.mybatis.dynamic.sql.ColumnAndConditionCriterion;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.ColumnSortSpecification;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

import static org.mybatis.dynamic.sql.SqlBuilder.select;

public class UserFiltersBuilder {
    private static final SqlTable sqlTable = SqlTable.of("tms_user");

    public static SelectStatementProvider generateSelectStatement(String sortByColumn, String sortDir) {
        SelectStatementProvider selectStatement;
        selectStatement = select(sqlTable.allColumns())
                .from(sqlTable)
                .orderBy(buildSortCriterion(sortByColumn, sortDir))
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return selectStatement;
    }

    public static SortSpecification buildSortCriterion(String sortByColumn, String sortDir) {
        SortSpecification specification = new ColumnSortSpecification("tms_user", sqlTable.column(sortByColumn));
        if (sortDir.equals("DESC")) specification = specification.descending();
        return specification;
    }
}
