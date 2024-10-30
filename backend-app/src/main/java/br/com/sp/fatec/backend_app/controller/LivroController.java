package br.com.sp.fatec.backend_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import br.com.sp.fatec.backend_app.entity.Livro;
import br.com.sp.fatec.backend_app.repository.LivroRepository;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    @ResponseBody
    @GetMapping()
    public ResponseEntity<List<Livro>> listar() {
        List<Livro> livros = livroRepository.findAll();

        if (livros.size() <= 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(livros);
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<Livro> obterLivro(@PathVariable Long id) {
        Livro livro = livroRepository.findById(id).orElse(null);
        if (livro == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(livro);
    }

    @ResponseBody
    @Transactional
	@PostMapping()
	public void salvar(@RequestBody Livro livro) {
        livroRepository.save(livro);
	}

    @GetMapping("/{id}/edit")
    public ResponseEntity<String> editarLivro(@PathVariable Long id) {
        Livro livro = livroRepository.findById(id).orElse(null);
        if (livro == null) {
            return ResponseEntity.notFound().build();
        }
        
        String formHtml = "<form hx-put='/api/livros/" + livro.getIsbn() + "' hx-target='#tabela-livros' hx-swap='outerHTML'>"
                        + "<label for='titulo'>Título:</label>"
                        + "<input type='text' name='titulo' value='" + livro.getTitulo() + "' required />"
                        + "<label for='autor'>Autor:</label>"
                        + "<input type='text' name='autor' value='" + livro.getAutor() + "' required />"
                        + "<label for='categoria'>Categoria:</label>"
                        + "<input type='text' name='categoria' value='" + livro.getCategoria() + "' required />"
                        + "<label for='modelo'>Modelo:</label>"
                        + "<input type='text' name='modelo' value='" + livro.getModelo() + "' required />"
                        + "<label for='edicao'>Edição:</label>"
                        + "<input type='number' name='edicao' value='" + livro.getEdicao() + "' required />"
                        + "<button type='submit'>Salvar</button>"
                        + "</form>";
        return ResponseEntity.ok(formHtml);
    }

    @PutMapping("/{id}")
    public Livro atualizarLivro(@PathVariable Long id, @RequestBody Livro livroAtualizado) {
         // Verifica se o livro existe
         Livro livroExistente = livroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        // Atualiza os campos desejados
        livroExistente.setTitulo(livroAtualizado.getTitulo());
        livroExistente.setAutor(livroAtualizado.getAutor());
        livroExistente.setCategoria(livroAtualizado.getCategoria());
        livroExistente.setEdicao(livroAtualizado.getEdicao());
        livroExistente.setModelo(livroAtualizado.getModelo());

        // Salva a entidade atualizada
        return livroRepository.save(livroExistente);
    }

    @DeleteMapping("/{id}")
	void deleteLivro(@PathVariable Long id) {
        livroRepository.deleteById(id);
	}
}
