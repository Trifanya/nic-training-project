package dev.trifanya.server_app.service;

import dev.trifanya.server_app.model.User;
import dev.trifanya.server_app.validator.UserValidator;
import dev.trifanya.server_app.repository.UserRepository;
import dev.trifanya.server_app.exception.NotFoundException;
import dev.trifanya.server_app.mybatis.criteria_builder.UserFiltersBuilder;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static org.mybatis.dynamic.sql.SqlBuilder.select;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserValidator userValidatorMock;
    @Mock
    private UserRepository userRepoMock;
    @Mock
    private UserFiltersBuilder userFiltersBuilderMock;
    @InjectMocks
    private UserService testingService;

    private final int USER_ID = 1;

    @Test
    public void getUserById_ifExist_returnUser() {
        User user = new User().setId(USER_ID);
        when(userRepoMock.getUserById(USER_ID))
                .thenReturn(Optional.of(user));

        User resultUser = testingService.getUserById(USER_ID);

        assertEquals(user, resultUser);
        verify(userRepoMock).getUserById(USER_ID);
    }

    @Test
    public void getUserById_ifNotExist_throwNotFoundException() {
        when(userRepoMock.getUserById(USER_ID))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> testingService.getUserById(USER_ID));
        verify(userRepoMock).getUserById(USER_ID);
    }

    @Test
    public void getUserByEmail_ifExist_returnUser() {
        User user = new User().setId(USER_ID);
        when(userRepoMock.getUserById(USER_ID))
                .thenReturn(Optional.of(user));

        User resultUser = testingService.getUserById(USER_ID);

        assertEquals(user, resultUser);
        verify(userRepoMock).getUserById(USER_ID);
    }

    @Test
    public void getUserByEmail_ifNotExist_throwNotFoundException() {
        when(userRepoMock.getUserById(USER_ID))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> testingService.getUserById(USER_ID));
        verify(userRepoMock).getUserById(USER_ID);
    }

    @Test
    public void getUserList_mapContainsSortParams_removeSortParamsFromMap() {
        Map<String, String> requestParams = getRequestParamsWithSortParams();
        Map<String, String> expectedRequestParams = getRequestParamsWithoutSortParams();
        String expectedSortBy = requestParams.get("sortBy");
        String expectedSortDir = requestParams.get("sortDir");
        SelectStatementProvider expectedStatement = getSelectStatement();
        List<User> expectedList = getUserList();
        when(userFiltersBuilderMock.generateSelectStatement(anyMap(), anyString(), anyString())).thenReturn(expectedStatement);
        when(userRepoMock.getUserList(any(SelectStatementProvider.class))).thenReturn(expectedList);

        List<User> resultList = testingService.getUsers(requestParams);

        assertIterableEquals(expectedList, resultList);
        verify(userFiltersBuilderMock).generateSelectStatement(expectedRequestParams, expectedSortBy, expectedSortDir);
        verify(userRepoMock).getUserList(expectedStatement);
    }

    @Test
    public void getUserList_mapNotContainsSortParams_setDefaultSortParams() {
        Map<String, String> expectedRequestParams = getRequestParamsWithoutSortParams();
        String expectedSortBy = "id";
        String expectedSortDir = "ASC";
        SelectStatementProvider expectedStatement = getSelectStatement();
        List<User> expectedList = getUserList();
        when(userFiltersBuilderMock.generateSelectStatement(anyMap(), anyString(), anyString())).thenReturn(expectedStatement);
        when(userRepoMock.getUserList(any(SelectStatementProvider.class))).thenReturn(expectedList);

        List<User> resultList = testingService.getUsers(expectedRequestParams);

        assertIterableEquals(expectedList, resultList);
        verify(userFiltersBuilderMock).generateSelectStatement(expectedRequestParams, expectedSortBy, expectedSortDir);
        verify(userRepoMock).getUserList(expectedStatement);
    }

    @Test
    public void createNewUser_shouldInvokeValidatorAndRepositoryMethods() {
        User user = new User().setId(USER_ID);
        doNothing().when(userValidatorMock).validateUser(user);
        doNothing().when(userRepoMock).createNewUser(user);

        testingService.createNewUser(user);

        verify(userValidatorMock).validateUser(user);
        verify(userRepoMock).createNewUser(user);
    }

    @Test
    public void updateUserInfo_shouldInvokeValidatorAndRepositoryMethods() {
        User user = new User().setId(USER_ID);
        doNothing().when(userValidatorMock).validateUser(user);
        doNothing().when(userRepoMock).createNewUser(user);

        testingService.createNewUser(user);

        verify(userValidatorMock).validateUser(user);
        verify(userRepoMock).createNewUser(user);
    }

    @Test
    public void deleteUserById_shouldInvokeRepositoryMethod() {
        testingService.deleteUserById(USER_ID);

        verify(userRepoMock).deleteUserById(USER_ID);
    }


    /** ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ */

    public Map<String, String> getRequestParamsWithSortParams() {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("sortBy", "sortByValue");
        requestParams.put("sortDir", "sortDirValue");
        requestParams.put("paramName", "paramValue");
        return requestParams;
    }

    public Map<String, String> getRequestParamsWithoutSortParams() {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("paramName", "paramValue");
        return  requestParams;
    }

    public List<User> getUserList() {
        return Arrays.asList(
                new User().setId(11),
                new User().setId(12),
                new User().setId(13)
        );
    }

    public SelectStatementProvider getSelectStatement() {
        SqlTable sqlTable = SqlTable.of("tms_user");
        return select(sqlTable.allColumns())
                .from(sqlTable)
                .build()
                .render(RenderingStrategies.MYBATIS3);
    }
}
