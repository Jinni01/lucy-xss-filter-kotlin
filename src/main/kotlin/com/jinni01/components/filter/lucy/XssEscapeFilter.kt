package com.jinni01.components.filter.lucy

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeFilterConfig
import mu.KotlinLogging
import org.apache.commons.lang3.StringUtils

typealias Handler = (String) -> String

object XssEscapeFilter {

    private val log = KotlinLogging.logger {}
    private val config = XssEscapeFilterConfig()

    fun doFilter(url: String, paramName: String, value: String?): String? {
        if (StringUtils.isBlank(value)) {
            return value
        }
        val urlRule = config.getUrlParamRule(url, paramName)
            ?: return config.defaultDefender.doFilter(value)

        if (!urlRule.isUseDefender) {
            log(url, paramName, value)
            return value
        }
        return urlRule.defender.doFilter(value)
    }

    /**
     * custom doFilter method for ByteArray Type
     */
    fun doFilter(value: ByteArray): ByteArray {
        val string = String(value)
        return replaceXSS(string).toByteArray()
    }

    private fun replaceXSS(origin: String): String {
        val handlers = listOf<Handler>(
            { value -> value.replace("<", "&lt;") },
            { value -> value.replace(">", "&gt;") }
            //{ value -> value.replace("(", "&#40;") },
            //{ value -> value.replace(")", "&#41;") }
        )

        var filtered = origin
        handlers.forEach { filtered = it(filtered) }

        return filtered
    }

    private fun log(url: String, paramName: String, value: String?) {
        if (log.isDebugEnabled) {
            log.debug("Unfiltered Parameter. Request url: $url, Parameter name: $paramName, Parameter value: $value")
        }
    }
}
