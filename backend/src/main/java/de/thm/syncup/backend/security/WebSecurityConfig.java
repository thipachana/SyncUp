package de.thm.syncup.backend.security;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {
    private final SessionSecurity security;
    public WebSecurityConfig(SessionSecurity security) { this.security = security; }
    @Override public void addInterceptors(InterceptorRegistry registry) { registry.addInterceptor(security).addPathPatterns("/api/**"); }
    @Override public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**").allowedOrigins("http://localhost:5173", "http://localhost:5175", "http://127.0.0.1:5173", "http://127.0.0.1:5175", "https://macbook-pro-von-david.taila8e803.ts.net")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedHeaders("Content-Type", "X-CSRF-TOKEN").allowCredentials(true);
    }
}
