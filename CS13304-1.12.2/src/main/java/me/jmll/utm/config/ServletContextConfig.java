package me.jmll.utm.config;

import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.RequestToViewNameTranslator;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Establece el escaneo de componentes del paquete me.jmll.utm.web con filtro de
 * inclusión org.springframework.stereotype.Controller
 **/
@Configuration
@EnableWebMvc
@ComponentScan(
		basePackageClasses = {
				me.jmll.utm.web.ComponentPackageMaker.class }, 
		useDefaultFilters = false,
		includeFilters = @ComponentScan.Filter(Controller.class) )
public class ServletContextConfig extends WebMvcConfigurerAdapter {
	@Inject
	ObjectMapper objectMapper;
	@Inject
	Marshaller marshaller;
	@Inject
	Unmarshaller unmarshaller;

	/**
	 * Maneja las solicitudes HTTP GET para /resources/** al servir
	 * eficientemente recursos estáticos en el directorio
	 * ${webappRoot}/resources
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		super.addResourceHandlers(registry);
	}

	/**
	 * Resuelve vistas seleccionadas al interpretar .jsp por @Controllers en el
	 * directorio /WEB-INF/views
	 */
	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	/**
	 * Configura RequestToViewNameTranslator con
	 * DefaultRequestToViewNameTranslator
	 */
	@Bean
	public RequestToViewNameTranslator viewNameTranslator() {
		return new DefaultRequestToViewNameTranslator();
	}

	/**
	 * Configura MultipartResolver con StandardServletMultipartResolver
	 */
	@Bean
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}

	/**
	 * Configura messageConverters
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// XML converter
		converters.add(createXMLMessageConverter());
		// JSON converter
		converters.add(createJSONMessageConverter());

		// Converters de Spring generalmente configurados automáticamente
		converters.add(new FormHttpMessageConverter());
		converters.add(new SourceHttpMessageConverter<>());
		converters.add(new ByteArrayHttpMessageConverter());
		converters.add(new StringHttpMessageConverter());
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
	 * Configura la negociación de contenido
	 */
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(true).favorParameter(false);
		configurer.parameterName("mediaType").ignoreAcceptHeader(false);
		configurer.useJaf(false).defaultContentType(MediaType.APPLICATION_JSON);
		configurer.mediaType("json", MediaType.APPLICATION_JSON);
		configurer.mediaType("xml", MediaType.APPLICATION_XML);
	}
}