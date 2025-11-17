package br.com.challenge_java.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

@Configuration
public class I18nConfig implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(I18nConfig.class);

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // CORREÇÃO ESSENCIAL: Usa o ponto (.) para resolver resources/i18n/messages
        messageSource.setBasename("i18n.messages"); 
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true); 
        
        log.info("MessageSource (ResourceBundleMessageSource) criado. Encoding: UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(new Locale("pt", "BR")); // Seu padrão é pt_BR
        log.info("LocaleResolver criado. Default Locale: pt_BR"); // Log de criação
        return resolver;
    }

    // MODIFICAR ESTE MÉTODO
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
                // Log para ver o parâmetro antes de mudar
                String newLocale = request.getParameter(this.getParamName());
                if (newLocale != null) {
                    log.warn(">>> Tentativa de mudança de idioma! Parâmetro 'lang' detectado: {}", newLocale);
                }
                
                // Executa a lógica original
                return super.preHandle(request, response, handler); 
            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
                 // Log para ver o Locale *depois* que ele foi resolvido
                Locale currentLocale = localeResolver().resolveLocale(request);
                log.warn("<<< Idioma final resolvido para esta requisição: {}", currentLocale);

                super.afterCompletion(request, response, handler, ex);
            }
        };
        interceptor.setParamName("lang");
        log.info("LocaleChangeInterceptor criado. Parâmetro de mudança: 'lang'"); // Log de criação
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}