package com.jinni01.components.filter.lucy

import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import jakarta.servlet.ServletRequest
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import mu.KotlinLogging
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

class XssEscapeServletFilterWrapper(
    request: ServletRequest
) : HttpServletRequestWrapper(request as HttpServletRequest) {
    private val log = KotlinLogging.logger {}

    private val path: String
    private var rawData: ByteArray? = null

    private val openedUrlList = listOf("add opened url")

    init {
        val contextPath = (request as HttpServletRequest).contextPath
        path = contextPath

        val contentType = request.contentType
        val isPostMethod = request.method.equals(HttpMethod.POST.name(), ignoreCase = true)
        val isJson = MediaType.APPLICATION_JSON.toString() == contentType
        val isWhiteList = isWhiteList(request)

        if (isPostMethod && isJson && !isWhiteList) {
            // 별도로 request의 body정보를 필터링해 보관한다. 반환시 사용
            rawData = doFilterBytes(request.inputStream.readAllBytes())
        }
    }

    private fun isWhiteList(request: HttpServletRequest): Boolean {
        return openedUrlList.contains(request.requestURI)
    }

    override fun getParameter(paramName: String): String? {
        val value = super.getParameter(paramName)
        return doFilter(paramName, value)
    }

    override fun getParameterValues(paramName: String): Array<String> {
        val values = super.getParameterValues(paramName) ?: return arrayOf()
        for (index in values.indices) {
            values[index] = doFilter(paramName, values[index]!!)
        }
        return values
    }

    override fun getParameterMap(): MutableMap<String, Array<String>> {
        val paramMap = super.getParameterMap()
        val newFilteredParamMap = mapOf<String, Array<String>>()

        val entries = paramMap.entries
        for (entry in entries) {
            val paramName = entry.key
            val value = entry.value
            val filteredValue = arrayOfNulls<String>(value.size)

            for ((index, item) in value.withIndex()) {
                filteredValue[index] = doFilter(paramName, item)
            }
        }

        return newFilteredParamMap.toMutableMap()
    }

    /**
     * json type 필터링을 위한 추가 오버라이드.
     */
    override fun getInputStream(): ServletInputStream {
        if (this.rawData == null) {
            return super.getInputStream()
        }

        val byteArrayInputStream = ByteArrayInputStream(this.rawData)
        return object : ServletInputStream() {

            override fun read(): Int {
                return byteArrayInputStream.read()
            }

            override fun isFinished(): Boolean {
                return false
            }

            override fun isReady(): Boolean {
                return false
            }

            override fun setReadListener(listener: ReadListener?) {
                log.info { "Not Implementation ReadListener" }
            }
        }
    }

    /**
     * 기존 스트림을 사용하지 않기 위해서 새로운 리더 객체를 반환한다.
     */
    override fun getReader(): BufferedReader {
        return BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8.name()))
    }

    private fun doFilter(paramName: String, value: String?): String? {
        return XssEscapeFilter.doFilter(path, paramName, value)
    }

    private fun doFilterBytes(value: ByteArray): ByteArray {
        return XssEscapeFilter.doFilter(value)
    }
}
