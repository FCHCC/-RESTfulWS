package me.jmll.utm.config;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebMvc
@ComponentScan(
		basePackageClasses = {
				me.jmll.utm.rest.ComponentPackageMaker.class,
				me.jmll.utm.rest.exception.ComponentPackageMaker.class}, 
		useDefaultFilters = false,
		includeFilters = @ComponentScan.Filter({Controller.class,
			ControllerAdvice.class}))
public class RestServletContextConfig extends WebMvcConfigurerAdapter{
    @Inject 
    ObjectMapper objectMapper;
    @Inject 
    Marshaller marshaller;
    @Inject 
    Unmarshaller unmarshaller;

    /**
	 * Configura messageConverters
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// XML converter
		converters.add(createXMLMessageConverter());
		// JSON converter
		converters.add(createJSONMessageConverter());
	}
	/**
	 * Configura la negociación de contenido
	 */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false).favorParameter(false);
        configurer.ignoreAcceptHeader(false);
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }
    
    /**
	 * Crea JSON Message Converter
	 */
	private MappingJackson2HttpMessageConverter createJSONMessageConverter() {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		jsonConverter.setSupportedMediaTypes(
				Arrays.asList(new MediaType("application", "json"), new MediaType("text", "json")));
		jsonConverter.setObjectMapper(this.objectMapper);
		return jsonConverter;
	}
	
	/**
	 * Crea XML Message Converter
	 */
	private MarshallingHttpMessageConverter createXMLMessageConverter() {
		MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
		xmlConverter.setSupportedMediaTypes(
				Arrays.asList(new MediaType("application", "xml"), new MediaType("text", "xml")));
		xmlConverter.setMarshaller(this.marshaller);
		xmlConverter.setUnmarshaller(this.unmarshaller);
		return xmlConverter;
	}
}
