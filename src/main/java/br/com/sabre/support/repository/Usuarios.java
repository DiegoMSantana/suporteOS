package br.com.sabre.support.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sabre.support.model.Usuario;
import br.com.sabre.support.repository.helper.usuario.UsuariosQueries;

public interface Usuarios extends JpaRepository<Usuario, Long>, UsuariosQueries {

}
