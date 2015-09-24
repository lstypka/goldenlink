package pl.jsolve.goldenlink.filter

import org.springframework.stereotype.Component

import javax.servlet.*
import javax.servlet.http.HttpServletResponse

@Component
class CorsFilter implements Filter {

    void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
        HttpServletResponse response = res as HttpServletResponse
        response.setHeader 'Access-Control-Allow-Origin', '*'
        response.setHeader 'Access-Control-Allow-Methods', 'POST, GET, PUT, OPTIONS, DELETE'
        response.setHeader 'Access-Control-Max-Age', '3600'
        response.setHeader 'Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept'

        chain.doFilter req, response
    }

    void init(FilterConfig filterConfig) {}

    void destroy() {}
}
