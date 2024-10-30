package br.com.sp.fatec.backend_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sp.fatec.backend_app.entity.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {

}
