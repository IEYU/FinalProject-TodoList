package com.example.finalprojecttodolist;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CreateHistory {

    private static FileReader file_reader;
    private static BufferedReader buffered_reader;
    private static ArrayList<Todo> todos = new ArrayList<>();

    /*
    Requires: String
    Effect: This method reads the file that stores all the to-do tasks the user has created in a certain to-do list
    and stores them in an array list.
     */
    public static ArrayList create_history(String file) throws IOException {
        todos.clear();
        file_reader = new FileReader(file);
        buffered_reader = new BufferedReader(file_reader);
        String line;
        String todo_string = "";

        while ((line = buffered_reader.readLine()) != null){
            if (!line.equals(";")){
                todo_string += line;
            }
            else{
                parse_todo(todo_string);
                todo_string = "";
            }
        }
        return todos;
    }

    /*
    Requires: String
    Effect: This method parses the string to find the to-do task stored; it then creates To-do objects from that
    information and also ensures that tasks with redundant names don't occur twice.
     */
    private static void parse_todo(String string){
        String[] split_string = string.split(",");
        String task = split_string[0];

        Todo todo = new Todo(task);

        int count = 0;
        for (int i = 0; i < todos.size(); i++){
            if(todo.todo.equals(todos.get(i).todo)){
                count ++;
            }
        }
        if(count == 0){
            todos.add(todo);
        }
    }
}
