package com.example.finalprojecttodolist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class Tests {
    Todo test_todo;
    ArrayList<Todo> test_history;
    CompletedToday completed_todo_today;
    CompletedCustom completed_todo_custom;
    File file_today = new File("today.txt");
    File file_completed_today = new File("completed_today.txt");

    @BeforeEach
    void setUp() {
        test_todo = new Todo("test");
        test_history = new ArrayList<>();
        completed_todo_today = new CompletedToday("test_today");
        completed_todo_custom = new CompletedCustom("test_custom");
    }

    @Test
    //Data persistence testing of creating a to-do task and add it to the default "Today" list
    void new_todo_default() throws IOException {
        //check if the test file is an existing file and is empty
        assertTrue(file_today.isFile());
        assertEquals(file_today.length(), 0);
        //write a To-do object to the "today.txt" file as if the user has written down a to-do
        test_todo.write_to_file("today.txt");
        //check if the To-do object is written to the "today.txt" file
        assertEquals(file_today.length(), 8);
        //clear the file
        test_todo.clear_file("today.txt");
        //check if the "today.txt" file is empty
        assertEquals(file_today.length(), 0);
    }

    @Test
    //Data persistence testing of creating a custom to-do list and adding a to-do task to that custom to-do list
    void new_todo_custom() throws IOException{
        //create a new file to store all the To-do objects in a custom to-do list
        completed_todo_custom.create_file("test_file.txt");
        File test_file = new File("test_file.txt");
        //check if the custom test file is successfully created
        assertTrue(test_file.isFile());
        //check if the test file is empty
        assertEquals(test_file.length(), 0);
        //write a To-do object to the "test_file.txt" file as if the user has written down a to-do in that custom list
        test_todo.write_to_file("test_file.txt");
        //check if the To-do object is written to the "test_file.txt" file
        assertEquals(test_file.length(), 8);
        //clear the file
        test_todo.clear_file("test_file.txt");
        //check if the "today.txt" file is empty
        assertEquals(test_file.length(), 0);
        //delete the test file from the root directory
        completed_todo_custom.delete_file("test_file.txt");
        //check if the test file is successfully deleted
        assertFalse(test_file.isFile());
    }

    @Test
    //Data persistence testing of completing a to-do task in the default "Today" list
    void complete_todo() throws IOException {
        //check if the "today.txt" file is an existing file and is empty
        assertTrue(file_completed_today.isFile());
        assertEquals(file_completed_today.length(), 0);
        //write a CompletedToday object to the "completed_today.txt" file as if the user has completed a to-do task
        completed_todo_today.write_to_file();
        //check if the CompletedToday object is written to the "completed_today.txt" file
        assertEquals(file_completed_today.length(), 11);
        //clear the file
        completed_todo_today.clear_file();
        //check if the "completed_today.txt" file is empty
        assertEquals(file_completed_today.length(), 0);
    }

    @Test
    //Data persistence testing of completing a to-do task in a custom to-do list
    void complete_custom_write_to_file() throws IOException {
        //create a new file to store all the To-do objects in a custom to-do list
        completed_todo_custom.create_file("test_file.txt");
        File test_file = new File("test_file.txt");
        //check if the custom test file is successfully created
        assertTrue(test_file.isFile());
        //check if the test file is empty
        assertEquals(test_file.length(), 0);
        //write a CompletedCustom object to the test file as if the user has completed a to-do task
        completed_todo_custom.write_to_file("test_file.txt");
        //check if the CompletedCustom object is written to the "test_file.txt" file
        assertEquals(test_file.length(), 12);
        //clear the test file
        completed_todo_custom.clear_file("test_file.txt");
        //check if the test file is empty
        assertEquals(test_file.length(), 0);
        //delete the test file from the root directory
        completed_todo_custom.delete_file("test_file.txt");
        //check if the test file is successfully deleted
        assertFalse(test_file.isFile());
    }

    @Test
    //Testing of displaying all the to-do tasks in a to-do list that have been created and not completed by
    //reading from the file that stores the related history of that to-do list
    void create_history() throws IOException{
        //check if the file that the user wants to read history from is an existing file and is empty
        assertTrue(file_today.isFile());
        assertEquals(file_today.length(), 0);
        //write a To-do object to that file so that there is a history that can be read
        test_todo.write_to_file("today.txt");
        //check if the arraylist that will hold the To-do object from history is empty at this point
        assertEquals(test_history.size(), 0);
        //create To-do objects according to the "today.txt" file and store them in the arraylist
        test_history = CreateHistory.create_history("today.txt");
        //check if there is one To-do object stored in the arraylist
        assertEquals(test_history.size(), 1);
        //clear the file
        test_todo.clear_file("today.txt");
        //check if the "today.txt" file is empty
        assertEquals(file_today.length(), 0);
    }
}