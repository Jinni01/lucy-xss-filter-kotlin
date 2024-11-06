package com.jinni01.controller

import com.jinni01.dto.JsonBodyTestDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "XSS 필터링 테스트 API")
@RestController
@RequestMapping(path = ["/filter-test"])
class FilterTestController {

    @Operation(summary = "GET | Simple String Parameter")
    @GetMapping(path = ["/mappings/get/simple-string-parameter"])
    fun testSimpleStringParameter(
        @RequestParam
        @Parameter(description = "string request parameter 형식 요청 정보")
        param: String
    ): String {
        return param
    }

    @Operation(summary = "POST | Simple JSON body")
    @PostMapping(path = ["/mappings/post/simple-json-body"])
    fun testSimpleJsonBody(
        @RequestBody
        //@Parameter(description = "json body 형식 요청 정보")
        param: JsonBodyTestDto
    ): JsonBodyTestDto {
        return param
    }
}