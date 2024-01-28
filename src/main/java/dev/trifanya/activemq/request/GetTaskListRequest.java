package dev.trifanya.activemq.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
public class GetTaskListRequest {
    private String requestTitle;
    private Map<String, String> filters;
    private String sortBy;
    private String sortDir;
}
