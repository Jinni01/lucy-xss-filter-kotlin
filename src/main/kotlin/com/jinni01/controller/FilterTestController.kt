package com.jinni01.controller

import com.jinni01.dto.JsonBodyTestDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/filter-test"])
class FilterTestController {

    @GetMapping(path = ["/mappings/get/simple-string-parameter"])
    fun testSimpleStringParameter(@RequestParam param: String): String {
        return param
    }

    @PostMapping(path = ["/mappings/post/simple-json-body"])
    fun testSimpleJsonBody(@RequestBody param: JsonBodyTestDto): JsonBodyTestDto {
        return param
    }
}