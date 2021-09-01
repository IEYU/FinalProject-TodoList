package com.example.finalprojecttodolist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.util.ArrayList;

public class Controller {
    public TextField txt_todo;
    public Button btn_add;
    public Button btn_today;
    public Button btn_all;
    public TextField txt_list;
    public Button btn_create_list;
    public ListView list_todo;
    public Button btn_complete;
    public ListView list_custom;
    public Button btn_delete_list;
    public Button btn_reset;
    public TextField txt_goal;
    public Button btn_set;

    @FXML
    /*
    Effect:
    When the "add" button is clicked, this method creates a To-do object.
    The to-do task will be displayed.
    If no custom to-do list is selected, the to-do task will be written to the "today.txt" file, which contains all
    the todos created under the "TODAY" list.
    They will also be written to the file "all.txt", which contains the history of all to-do tasks.
     */
    public void add_todo(MouseEvent mouseEvent) throws IOException {
        String todo = "";
        if (!txt_todo.getText().isEmpty()) {
            todo = txt_todo.getText();
            Todo new_todo = new Todo(todo);
            list_todo.getItems().add(new_todo);
            new_todo.write_to_file("all.txt");
            //System.out.println(list_custom.getSelectionModel().getSelectedItems().toString());
            if (!list_custom.getSelectionModel().getSelectedItems().isEmpty()) {
                new_todo.write_to_file(list_custom.getSelectionModel().getSelectedItems().get(0) + ".txt");
            } else {
                new_todo.write_to_file("today.txt");
            }
        }
        txt_todo.clear();
    }

    /*
    Effect:
    When the "Completed!" button is clicked, if no custom to-do list is selected, this method creates a CompletedToday
    object. Otherwise, this method creates a CompletedCustom object instead.
    The selected to-do task will be marked as completed and removed.
     */
    public void remove_todo(MouseEvent mouseEvent) throws IOException {
        if (!list_todo.getSelectionModel().isEmpty()) {
            int index = list_todo.getSelectionModel().getSelectedIndex();
            String todo = list_todo.getSelectionModel().getSelectedItems().get(0) + "";
            if(list_custom.getSelectionModel().isEmpty()){
                CompletedToday completed_todo = new CompletedToday(todo);
                completed_todo.write_to_file();
            }
            else{
                String file_name = (String) "completed_" + list_custom.getSelectionModel().getSelectedItem() + ".txt";
                CompletedCustom completed_todo = new CompletedCustom(todo);
                completed_todo.write_to_file(file_name);
            }

            list_todo.getItems().remove(index);
            System.out.println(todo + " is completed!");
        }
    }

    /*
    Effect:
    When the "TODAY" button is clicked, this methods display all the To-do object that are recorded in the "today.txt"
    file and aren't yet completed by the user.
     */
    public void show_today_todo(MouseEvent mouseEvent) throws IOException {
        list_custom.getSelectionModel().clearSelection();
        list_todo.getItems().clear();
        ArrayList<Todo> all_todos = CreateHistory.create_history("today.txt");
        for (Todo todo : all_todos) {
            FileReader fr = new FileReader("completed_today.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            boolean completed = false;
            while ((line = br.readLine()) != null) {
                if (line.equals(todo.getTodo())) {
                    completed = true;
                }
            }
            if (!completed) {
                list_todo.getItems().add(todo);
            }
        }
    }

    /*
    Effect:
    When the "HISTORY" button is clicked, this method will display all the to-do tasks that the user has created, which
    are stored in the "all.txt" file, regardless of whether they are completed or not.
     */
    public void show_all_todo(MouseEvent mouseEvent) throws IOException {
        list_todo.getItems().clear();
        ArrayList<Todo> all_todos = CreateHistory.create_history("all.txt");
        for (Todo todo : all_todos) {
            list_todo.getItems().add(todo);
        }
    }

    /*
    Effect:
    When the "+" button is clicked, this method will create a custom to-do list with a name that the user has input if
    there are no other lists with the same name.
    A file with the same name will also be created in the root directory, which stores all the To-do objects created
    under this custom to-do list.
     */
    public void create_list(MouseEvent mouseEvent) throws IOException {
        //list_todo.getItems().clear();
        if (!txt_list.getText().isEmpty()) {
            String filename = txt_list.getText() + ".txt";
            File file = new File(filename);
            if (file.createNewFile()) {
                System.out.println(filename + " created");
                list_custom.getItems().add(txt_list.getText());
            } else {
                System.out.println("file already existed");
            }
            list_todo.getItems().clear();
            txt_list.clear();
        }
    }

    /*
    Effect:
    When the "DELETE" button is clicked, this method will remove the selected to-do list.
    The files that contain the history of created to-do tasks as well as completed to-do tasks from this to-do list
    will be deleted from the root directory.
     */
    public void delete_list(MouseEvent mouseEvent) throws IOException {
        list_todo.getItems().clear();
        if (!list_custom.getSelectionModel().isEmpty()) {
            int index = list_custom.getSelectionModel().getSelectedIndex();
            String file_name = (String) list_custom.getSelectionModel().getSelectedItem() + ".txt";
            //System.out.println(file_name);
            File delete_file = new File(file_name);
            File delete_save_file = new File ("completed_" + file_name);
            if (delete_file.delete() && delete_save_file.delete()) {
                System.out.println(file_name + " deleted successfully!");
            }
            list_custom.getItems().remove(index);
        }
    }

    /*
    Effect:
    This method will load all the to-do lists that the user has previously created and not deleted yet.
    When one of the custom to-do lists is selected, a new file following the naming convention of
    "completed_..." will be created in the root directory to store the information of certain CompletedCustom
    objects.
    This method will also display the to-do tasks that aren't completed yet in the selected to-do list.
     */
    public void load_list(MouseEvent mouseEvent) throws IOException {
        list_todo.getItems().clear();
        File[] all_files = new File(".").listFiles();
        ArrayList<String> files_to_load = new ArrayList<>();
        for (File f : all_files) {
            String file_name = f.getName();
            //System.out.println(file_name);
            if (f.isFile() && file_name.substring(file_name.length() - 3).equals("txt")) {
                if (!(file_name.equals("all.txt") || file_name.equals("today.txt"))) {
                    String list = file_name.substring(0, file_name.length() - 4);
                    if (!(file_name.length() > 9)){
                        //System.out.println(file_name);
                        files_to_load.add(list);
                    }
                    else if (! file_name.substring(0,9).equals("completed")){
                        //System.out.println(file_name);
                        files_to_load.add(list);
                    }
                }
            }
        }
        ObservableList<String> txt_files = FXCollections.observableArrayList(files_to_load);
        list_custom.setItems(txt_files);

        if (!list_custom.getSelectionModel().isEmpty()) {
            String list_to_load = list_custom.getSelectionModel().getSelectedItems().get(0) + ".txt";
            //System.out.println(list_to_load);
            ArrayList<Todo> todos = CreateHistory.create_history(list_to_load);
            String save_list = "completed_" + list_custom.getSelectionModel().getSelectedItems().get(0) + ".txt";
            new File(save_list).createNewFile();

            for (Todo todo : todos) {
                FileReader fr = new FileReader(save_list);
                BufferedReader br = new BufferedReader(fr);
                String line;
                boolean completed = false;
                while ((line = br.readLine()) != null) {
                    if (line.equals(todo.getTodo())) {
                        completed = true;
                    }
                }
                if (!completed) {
                    list_todo.getItems().add(todo);
                }
            }
        }
    }

    /*
    Effect:
    When the "RESET ALL TO-DO LIST HISTORY" button is clicked, this method will reset everything.
    It will delete all the files related to custom to-do lists and remove everything in the files related to
    the today to-do list or the one that stores the history of all to-do tasks.
     */
    public void reset(MouseEvent mouseEvent) throws IOException {
        File[] all_files = new File(".").listFiles();
        for (File f : all_files) {
            String file_name = f.getName();
            if (f.isFile() && file_name.substring(file_name.length() - 3).equals("txt")) {
                if (file_name.equals("completed_today.txt")){
                    System.out.println(file_name + " is cleared");
                    PrintWriter pw = new PrintWriter(f);
                    pw.close();
                }
                else if (file_name.equals("all.txt") || file_name.equals("today.txt")) {
                    System.out.println(file_name + " is cleared");
                    PrintWriter pw = new PrintWriter(f);
                    pw.close();
                }
                else{
                    if (f.delete()){
                        System.out.println(file_name + " is deleted");
                    }
                }
            }
        }
        list_custom.getItems().clear();
        list_todo.getItems().clear();
        System.out.println("everything is reset!");
    }

    /*
    Effect:
    When the "SET" button is clicked, this method will read the text the user has input and display it.
     */
    public void set_goal(MouseEvent mouseEvent) {
        String goal = txt_goal.getText();
        txt_goal.clear();
        txt_goal.setText(goal);
    }

    /*
    Effect:
    This method will clear the text field for goal when the user clicks on it.
     */
    public void clear_goal(MouseEvent mouseEvent) {
        txt_goal.clear();
    }
}