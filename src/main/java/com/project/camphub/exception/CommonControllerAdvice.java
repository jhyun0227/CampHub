package com.project.camphub.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.project.camphub.common.dto.Response;
import com.project.camphub.common.dto.enumaration.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CommonControllerAdvice {

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Response<Void>> invalidFormatException(InvalidFormatException e) {

        String originalMessage = e.getOriginalMessage();
        String campDataParsingFailedMessage =
                "Cannot coerce empty String (\"\") to `com.project.camphub.domain.openapi.dto.OpenApiResponse$Items`";

        /**
         * openApi를 통해 전달된 응답에서 변경된 데이터가 없어 Items가 비어있을 경우, 에러 사항이 아니기에 정상흐름으로 변경
         */
        if (originalMessage.contains(campDataParsingFailedMessage)) {
            log.info("initializeCampList or refreshCampListFromAPI 완료, OpenApiResponse 변동 데이터 없음");
            return ResponseEntity.ok(Response.success(ResponseCode.CODE_200, "OpenApiResponse 변동 데이터 없음", null));
        }

        log.error("invalidFormatException = {}", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.exception(ResponseCode.CODE_500));
    }
}
