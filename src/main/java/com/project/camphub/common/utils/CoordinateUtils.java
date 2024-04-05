package com.project.camphub.common.utils;

import org.springframework.util.StringUtils;

public final class CoordinateUtils {

    public static Double parseLonOrLatToDouble(String coordinate) {
        if (StringUtils.hasText(coordinate)) {
            return Double.valueOf(coordinate);
        }

        return null;
    }
}
