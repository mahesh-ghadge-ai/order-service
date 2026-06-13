package com.labarchitecture.orderservice.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Profile("slow-api")
public class SlowApiDelayFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(SlowApiDelayFilter.class);

    private final long delayMs;

    public SlowApiDelayFilter(@Value("${app.slow-api.delay-ms:5000}") long delayMs) {
        this.delayMs = delayMs;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            log.warn("slow-api profile enabled, delaying {} ms for {} {}", delayMs, request.getMethod(), request.getRequestURI());
            Thread.sleep(delayMs);
            filterChain.doFilter(request, response);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new ServletException("Request delay interrupted", exception);
        }
    }
}
