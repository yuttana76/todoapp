import { Component, OnInit } from '@angular/core';
import { Todo } from './todo';
import { NgForm } from '@angular/forms';
import { TodoService } from './todo.service';

@Component({
    selector: 'todo-list',
    templateUrl: './todo-list.component.html'
})

export class TodoListComponent implements OnInit {
    todos: Todo[];
    newTodo: Todo = new Todo();
    editing: boolean = false;
    edittingTodo: Todo = new Todo();

    constructor(
        private todoService: TodoService,
    ) { }

    ngOnInit(): void {
        this.getTodos();
    }

    getTodos(): void {
        this.todoService.getTodos()
            .then(todos => this.todos = todos);
    }

    createTodo(todoForm: NgForm): void {
        this.todoService.createTodo(this.newTodo)
            .then(createTodo => {
                todoForm.reset();
                this.newTodo = new Todo();
                this.todos.unshift(createTodo)
            });
    }

    deleteTodo(id: string): void {
        this.todoService.deleteTodo(id)
        .then(()=>{
            this.todos = this.todos.filter(todo => todo.id != id);
        });

    }

    updateTodo(todoData: Todo): void {
        console.log(todoData);
        this.todoService.updateTodo(todoData)
        .then(updateTodo =>{
            let exitingTodo = this.todos.find(todo => todo.id === updateTodo.id);
            Object.assign(exitingTodo,todoData);
            this.clearEditing();
        });

    }

    toggleCompleted(todoData: Todo): void {
        todoData.completed = ! todoData.completed;
        this.todoService.updateTodo(todoData)
        .then(updatedTodo =>{
            let exitingTodo = this.todos.find(todo => todo.id === updatedTodo.id);
            Object.assign(exitingTodo,updatedTodo);
        });
    }

    editTodo(todoData: Todo): void {
        this.editing = true;
        Object.assign(this.edittingTodo,todoData);
    }

    clearEditing(): void {
        this.edittingTodo = new Todo;
        this.editing = false;

    }
}