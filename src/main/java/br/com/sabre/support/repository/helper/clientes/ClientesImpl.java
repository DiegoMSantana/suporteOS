package br.com.sabre.support.repository.helper.clientes;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.sabre.support.dto.ClienteDTO;
import br.com.sabre.support.model.Cliente;

public class ClientesImpl implements ClientesQueries {

	@PersistenceContext
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> filtrar(Pageable pageable, String busca) {

		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);

		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;

		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(totalRegistrosPorPagina);

		ProjectionList atributos = Projections.projectionList();

		atributos.add(Projections.groupProperty("Id"));
		atributos.add(Projections.groupProperty("nome"));
		atributos.add(Projections.groupProperty("razaoSocial"));
		atributos.add(Projections.groupProperty("cpfOuCnpj"));
		atributos.add(Projections.groupProperty("tipoPessoa"));

		criteria.setProjection(atributos);
		adicionarFiltro(criteria, busca);

		return new PageImpl<>(criteria.list(), pageable, total(busca));

	}

	private void adicionarFiltro(Criteria criteria, String busca) {
		if (!StringUtils.isEmpty(busca)) {
			criteria.add(Restrictions.ilike("nome", busca, MatchMode.ANYWHERE));
		}
	}

	private Long total(String busca) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Cliente.class);
		adicionarFiltro(criteria, busca);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	@Override
	public List<ClienteDTO> porNome(String nomeCliente) {

		String jpql = "select new br.com.sabre.support.dto.ClienteDTO(id, nome, cpfoucnpj) "
				+ "from Cliente where lower(nome) like lower(:nomeCliente)";

		List<ClienteDTO> clientesFiltrados = manager.createQuery(jpql, ClienteDTO.class)
				.setParameter("nomeCliente", "%" + nomeCliente + "%").getResultList();

		return clientesFiltrados;
	}

}
