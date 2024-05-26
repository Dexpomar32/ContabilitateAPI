package com.task.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EndpointDetail {
    private String title;
    private String endpoint;
    private String type;
    private List<String> params;
    private List<String> paramsAlias;
}