package com.example.finalprojecttodolist;

import java.io.*;

public class Todo {
    String todo;

    //Constructor
    Todo(String todo){
        this.todo = todo;
    }

    //Requires: String
    //Effect: write the To-do object to a file of the user's choice
    public void write_to_file(String file) throws IOException {
        FileWriter file_writer = new FileWriter(file, true);
        BufferedWriter buffered_writer = new BufferedWriter(file_writer);
        buffered_writer.write("\r"+todo + ",\r");
        buffered_writer.write(";");
        buffered_writer.close();
    }

    //Requires: String
    //Effect: It will remove everything stored in the file.
    public void clear_file(String f) throws IOException{
        File file = new File(f);
        PrintWriter pw = new PrintWriter(file);
        pw.close();
    }

    public String toString(){
        return todo;
    }

    //getters and setters
    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }
}
