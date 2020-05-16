package br.com.sabre.support.config.init;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import br.com.sabre.support.config.JPAConfig;
import br.com.sabre.support.config.SecurityConfig;
import br.com.sabre.support.config.ServiceConfig;
import br.com.sabre.support.config.WebConfig;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { JPAConfig.class, ServiceConfig.class, SecurityConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}

	@Override
	protected Filter[] getServletFilters() {

		/*
		 * CharacterEncodingFilter characterEncodingFilter = new
		 * CharacterEncodingFilter(); characterEncodingFilter.setEncoding("UTF-8");
		 * characterEncodingFilter.setForceEncoding(true);
		 */

		HttpPutFormContentFilter httpPutFormContentFilter = new HttpPutFormContentFilter();

		return new Filter[] { httpPutFormContentFilter };

	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}

}
