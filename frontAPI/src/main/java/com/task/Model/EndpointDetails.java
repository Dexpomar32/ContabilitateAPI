package com.task.Model;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class EndpointDetails {
    private String className;
    private List<EndpointDetail> navigation;
}