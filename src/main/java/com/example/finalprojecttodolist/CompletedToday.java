package com.example.finalprojecttodolist;

import java.io.*;

public class CompletedToday {
    String todo;

    //Constructor
    //A CompletedToday object is created according to a to-do task in the "Today" to-do list that the user has completed
    CompletedToday(String todo){
        this.todo = todo;
    }

    //Effect: write the CompletedToday object to the "completed_today.txt" file
    public void write_to_file() throws IOException {
        FileWriter file_writer = new FileWriter("completed_today.txt", true);
        BufferedWriter buffered_writer = new BufferedWriter(file_writer);
        buffered_writer.write("\r"+todo);
        buffered_writer.close();
    }

    //Effect: It will remove everything stored in the "completed_today.txt" file.
    public void clear_file() throws IOException{
        File file = new File("completed_today.txt");
        PrintWriter pw = new PrintWriter(file);
        pw.close();
    }

    //getters and setters
    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }
}
