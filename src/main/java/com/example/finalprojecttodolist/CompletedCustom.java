package com.example.finalprojecttodolist;

import java.io.*;

public class CompletedCustom {
    String todo;

    //Constructor
    //A CompletedCustom object is created according to a to-do task in a custom to-do list that the user has completed
    CompletedCustom(String todo){
        this.todo = todo;
    }

    //Requires: String
    //Effect: write the CompletedCustom object to a specific file of the user's choice
    public void write_to_file(String file) throws IOException {
        FileWriter file_writer = new FileWriter(file, true);
        BufferedWriter buffered_writer = new BufferedWriter(file_writer);
        buffered_writer.write("\r"+todo);
        buffered_writer.close();
    }

    //Requires: String
    //Effect: It will create a file in the root directory
    public void create_file(String f) throws  IOException{
        File file = new File(f);
        file.createNewFile();
    }

    //Requires: String
    //Effect: It will remove everything stored in the file
    public void clear_file(String f) throws IOException{
        File file = new File(f);
        PrintWriter pw = new PrintWriter(file);
        pw.close();
    }

    //Requires: String
    //Effect: It will delete the file from the root directory
    public void delete_file(String f){
        File file = new File(f);
        file.delete();
    }

    //getters and setters
    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }
}
