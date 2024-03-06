package dev.trifanya.server_app.service;

import dev.trifanya.server_app.model.Task;
import dev.trifanya.server_app.validator.TaskValidator;
import dev.trifanya.server_app.repository.TaskRepository;
import dev.trifanya.server_app.exception.NotFoundException;
import dev.trifanya.server_app.mybatis.criteria_builder.TaskFiltersBuilder;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

import java.util.*;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mybatis.dynamic.sql.SqlBuilder.select;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskValidator taskValidatorMock;
    @Mock
    private TaskRepository taskRepoMock;
    @Mock
    private TaskFiltersBuilder taskFiltersBuilderMock;
    @InjectMocks
    private TaskService testingService;

    private final int TASK_ID = 1;

    @Test
    public void getTaskById_ifExist_returnTask() {
        Task task = new Task().setId(TASK_ID);
        when(taskRepoMock.getTaskById(TASK_ID))
                .thenReturn(Optional.of(task));

        Task resultTask = testingService.getTaskById(TASK_ID);

        assertEquals(task, resultTask);
        verify(taskRepoMock).getTaskById(TASK_ID);
    }

    @Test
    public void getTaskById_ifNotExist_throwNotFoundException() {
        when(taskRepoMock.getTaskById(TASK_ID))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> testingService.getTaskById(TASK_ID));
        verify(taskRepoMock).getTaskById(TASK_ID);
    }

    @Test
    public void getTasks_mapContainsSortParams_removeSortParamsFromMap() {
        Map<String, String> requestParams = getRequestParamsWithSortParams();
        Map<String, String> expectedRequestParams = getRequestParamsWithoutSortParams();
        String expectedSortBy = requestParams.get("sortBy");
        String expectedSortDir = requestParams.get("sortDir");
        SelectStatementProvider expectedStatement = getSelectStatement();
        List<Task> expectedList = getTaskList();
        when(taskFiltersBuilderMock.generateSelectStatement(anyMap(), anyString(), anyString())).thenReturn(expectedStatement);
        when(taskRepoMock.getTaskList(any(SelectStatementProvider.class))).thenReturn(expectedList);

        List<Task> resultList = testingService.getTasks(requestParams);

        assertIterableEquals(expectedList, resultList);
        verify(taskFiltersBuilderMock).generateSelectStatement(expectedRequestParams, expectedSortBy, expectedSortDir);
        verify(taskRepoMock).getTaskList(expectedStatement);
    }

    @Test
    public void getTasks_mapNotContainsSortParams_setDefaultSortParams() {
        Map<String, String> expectedRequestParams = getRequestParamsWithoutSortParams();
        String expectedSortBy = "id";
        String expectedSortDir = "ASC";
        SelectStatementProvider expectedStatement = getSelectStatement();
        List<Task> expectedList = getTaskList();
        when(taskFiltersBuilderMock.generateSelectStatement(anyMap(), anyString(), anyString())).thenReturn(expectedStatement);
        when(taskRepoMock.getTaskList(any(SelectStatementProvider.class))).thenReturn(expectedList);

        List<Task> resultList = testingService.getTasks(expectedRequestParams);

        assertIterableEquals(expectedList, resultList);
        verify(taskFiltersBuilderMock).generateSelectStatement(expectedRequestParams, expectedSortBy, expectedSortDir);
        verify(taskRepoMock).getTaskList(expectedStatement);
    }

    @Test
    public void createNewTask_shouldInvokeValidatorAndRepositoryMethods() {
        Task task = new Task().setId(TASK_ID);
        doNothing().when(taskValidatorMock).validateTask(task);
        doNothing().when(taskRepoMock).createNewTask(task);

        testingService.createNewTask(task);

        verify(taskValidatorMock).validateTask(task);
        verify(taskRepoMock).createNewTask(task);
    }

    @Test
    public void updateTaskInfo_shouldNotModifyCreatedAtField() {
        Task updatedTask = new Task().setId(TASK_ID).setTitle("Test");
        Task taskToUpdate = new Task().setId(TASK_ID).setCreatedAt(LocalDateTime.now());
        Task expectedTask = new Task().setId(TASK_ID).setTitle(updatedTask.getTitle()).setCreatedAt(taskToUpdate.getCreatedAt());
        when(taskRepoMock.getTaskById(TASK_ID)).thenReturn(Optional.of(taskToUpdate));

        testingService.updateTaskInfo(updatedTask);

        verify(taskValidatorMock).validateTask(updatedTask);
        verify(taskRepoMock).updateTaskInfo(expectedTask);
    }

    @Test
    public void deleteTaskById_shouldInvokeRepositoryMethod() {
        testingService.deleteTaskById(TASK_ID);

        verify(taskRepoMock).deleteTaskById(TASK_ID);
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

    public List<Task> getTaskList() {
        return Arrays.asList(
                new Task().setId(11),
                new Task().setId(12),
                new Task().setId(13)
        );
    }

    public SelectStatementProvider getSelectStatement() {
        SqlTable sqlTable = SqlTable.of("task");
        return select(sqlTable.allColumns())
                .from(sqlTable)
                .build()
                .render(RenderingStrategies.MYBATIS3);
    }
}
