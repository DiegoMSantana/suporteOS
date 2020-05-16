package br.com.sabre.support.config;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.github.mxab.thymeleaf.extras.dataattribute.dialect.DataAttributeDialect;

import br.com.sabre.support.controller.DashboardController;
import br.com.sabre.support.controller.converter.ClienteConverter;
import br.com.sabre.support.controller.converter.GrupoConverter;
import br.com.sabre.support.controller.converter.ServicoConverter;
import br.com.sabre.support.session.TabelaItensServico;
import br.com.sabre.support.thymeleaf.SuporteDialect;
import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@ComponentScan(basePackageClasses = { DashboardController.class, TabelaItensServico.class })
@EnableWebMvc
@EnableSpringDataWebSupport
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

	private ApplicationContext application;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.application = applicationContext;

	}

	@Bean
	public ViewResolver viewResolver() {

		ThymeleafViewResolver resolver = new ThymeleafViewResolver();

		resolver.setTemplateEngine(templateEngine());
		resolver.setCharacterEncoding("UTF-8");
		// resolver.setContentType("text/html; charset=UTF-8");

		return resolver;
	}

	@Bean
	public TemplateEngine templateEngine() {

		SpringTemplateEngine engine = new SpringTemplateEngine();

		engine.setEnableSpringELCompiler(true);
		engine.setTemplateResolver(templateResolver());

		engine.addDialect(new LayoutDialect());
		engine.addDialect(new SuporteDialect());
		engine.addDialect(new DataAttributeDialect());
		engine.addDialect(new SpringSecurityDialect());

		return engine;
	}

	// Define onde os templates serão buscados sempre com o sufixo .html

	private ITemplateResolver templateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(application);
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode(TemplateMode.HTML);

		// Parâmetro abaixo necessário para permitir acentuação no conteúdo das tags
		// html, problema gerado depois da
		// inserção do spring security

		resolver.setCharacterEncoding("UTF-8");

		return resolver;
	}

	// Define onde será buscado os recursos como css e js

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}

	@Bean
	public FormattingConversionService mvcConversionService() {

		// Conversores
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		conversionService.addConverter(new ClienteConverter());
		conversionService.addConverter(new ServicoConverter());
		// conversionService.addConverter(new EstiloConverter());
		// conversionService.addConverter(new CidadeConverter());
		// conversionService.addConverter(new EstadoConverter());
		conversionService.addConverter(new GrupoConverter());

		// formatador de moeda
		NumberStyleFormatter bigDecimalFormatter = new NumberStyleFormatter("#,##0.00");
		conversionService.addFormatterForFieldType(BigDecimal.class, bigDecimalFormatter);

		NumberStyleFormatter integerFormatter = new NumberStyleFormatter("#,##0");
		conversionService.addFormatterForFieldType(Integer.class, integerFormatter);

		// formatador de data e hora
		DateTimeFormatterRegistrar dateTimeFormatterRegistrar = new DateTimeFormatterRegistrar();
		dateTimeFormatterRegistrar.setDateFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		dateTimeFormatterRegistrar.setTimeFormatter(DateTimeFormatter.ofPattern("HH:mm"));
		dateTimeFormatterRegistrar.registerFormatters(conversionService);

		return conversionService;

	}

	// Define linguagem padrão
	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}

	// Após inserção do spring security o conteúdo abaixo para de funcionar.

	/*
	 * @Bean public MessageSource messageSource() {
	 * ReloadableResourceBundleMessageSource bundle = new
	 * ReloadableResourceBundleMessageSource();
	 * bundle.setBasename("classpath:/messages");
	 * bundle.setDefaultEncoding("UTF-8"); // http://www.utf8-chartable.de/ return
	 * bundle; }
	 */

	@Bean
	public DomainClassConverter<FormattingConversionService> domainClassConverter() {
		return new DomainClassConverter<FormattingConversionService>(mvcConversionService());
	}

}
