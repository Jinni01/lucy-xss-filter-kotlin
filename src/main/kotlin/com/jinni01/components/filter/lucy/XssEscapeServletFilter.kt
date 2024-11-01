package com.jinni01.components.filter.lucy

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse

class XssEscapeServletFilter : Filter {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        chain.doFilter(XssEscapeServletFilterWrapper(request = request), response)
    }
}
