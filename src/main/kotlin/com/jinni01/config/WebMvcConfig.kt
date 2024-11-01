package com.jinni01.config

import com.jinni01.components.filter.lucy.XssEscapeServletFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig: WebMvcConfigurer {

    @Bean
    fun getXssFilterRegistrationBean(): FilterRegistrationBean<XssEscapeServletFilter> {
        val xssFilterRegistrationBean = FilterRegistrationBean<XssEscapeServletFilter>()
        xssFilterRegistrationBean.filter = XssEscapeServletFilter()
        xssFilterRegistrationBean.order = Ordered.LOWEST_PRECEDENCE
        xssFilterRegistrationBean.addUrlPatterns("/*")
        return xssFilterRegistrationBean
    }
}