package com.project.camphub.externalapi.dto.openapi;

import lombok.Data;

@Data
public class Body {

    private Items items;

    private int numOfRows;
    private int pageNo;
    private int totalCount;
}
