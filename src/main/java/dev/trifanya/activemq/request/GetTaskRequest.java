package dev.trifanya.activemq.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GetTaskRequest {
    private String requestTitle;
    private int taskId;
}
