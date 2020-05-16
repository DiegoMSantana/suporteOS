package br.com.sabre.support.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import br.com.sabre.support.thymeleaf.processor.MenuAttributeTagProcessor;

public class SuporteDialect extends AbstractProcessorDialect {

	public SuporteDialect() {
		super("Suporte Tag", "suporte", StandardDialect.PROCESSOR_PRECEDENCE);
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {

		final Set<IProcessor> processadores = new HashSet<>();
		// processadores.add(new MenuAttributeTagProcessor(dialectPrefix));
		processadores.add(new MenuAttributeTagProcessor(dialectPrefix));

		return processadores;
	}

}
