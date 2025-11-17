package br.com.challenge_java.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource; 
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class I18nConfig implements WebMvcConfigurer { 

    /**
     * 3. ADICIONAR ESTE BEAN
     * Define explicitamente onde os arquivos de tradução estão.
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        // Define o caminho base para os arquivos de tradução (a partir de /resources)
        messageSource.setBasename("classpath:i18n/messages");
        // Garante que os acentos e caracteres especiais funcionem
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * Define como o idioma do usuário será armazenado (na Sessão HTTP).
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver(); 
        resolver.setDefaultLocale(new Locale("pt", "BR"));
        return resolver;
    }

    /**
     * Define qual parâmetro na URL irá disparar a mudança de idioma.
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    /**
     * Registra o interceptor na aplicação.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}