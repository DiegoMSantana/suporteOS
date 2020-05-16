package br.com.sabre.support.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.sabre.support.service.CadastroClienteService;
import br.com.sabre.support.storage.FotoStorage;
import br.com.sabre.support.storage.local.FotoStorageLocal;

@Configuration
@ComponentScan(basePackageClasses = CadastroClienteService.class)
public class ServiceConfig {

	@Bean
	public FotoStorage fotoStorage() {
		return new FotoStorageLocal();
	}

}
