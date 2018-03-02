package com.example.todoapp.conntroller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todoapp.models.Todo;
import com.example.todoapp.repositories.TodoRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class TodoController {

	private static final Logger log = LoggerFactory.getLogger(TodoController.class);
	
	@Autowired
    TodoRepository todoRepository;
	
	@GetMapping("/todos")
    public List<Todo> getAllTodos() {
		log.info("Welcome GET todos");
        Sort sortByCreatedAtDesc = new Sort(Sort.Direction.DESC, "createdAt");
        return todoRepository.findAll(sortByCreatedAtDesc);
    }
	
	@PostMapping("/todos")
    public Todo createTodo(@Valid @RequestBody Todo todo) {
		log.info("Welcome POST todos");
        todo.setCompleted(false);
        return todoRepository.save(todo);
    }
	
	@GetMapping(value="/todos/{id}")
    public ResponseEntity<Optional<Todo>> getTodoById(@PathVariable("id") String id) {
		
		log.info("Welcome POST todos("+id+")");
		
		Optional<Todo> todo = todoRepository.findById(id);
        
        if(todo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(todo, HttpStatus.OK);
        }
    }
	
	@PutMapping(value="/todos/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable("id") String id,
                                           @Valid @RequestBody Todo todo) {
		
		log.info("Welcome PUT todos("+id+")");
		
		Optional<Todo> todoData = todoRepository.findById(id);
        
        if(!todoData.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        todoData.get().setTitle(todo.getTitle());
        todoData.get().setCompleted(todo.getCompleted());
        Todo updatedTodo = todoRepository.save(todoData.get());
        return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
    }
	
	@DeleteMapping(value="/todos/{id}")
    public void deleteTodo(@PathVariable("id") String id) {
		
		log.info("Welcome DELETE todos("+id+")");
		
        todoRepository.deleteById(id);
    }
	
	
}
