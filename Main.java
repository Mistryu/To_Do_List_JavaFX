package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("To Do List");


        var layout = new VBox();
        layout.setId("layout");

        var top = new HBox();
        var top_text = new Label("To Do List");
        top.getChildren().add(top_text);
        top.setAlignment(Pos.CENTER);
        top.setId("top");

        var mid = new HBox();
        mid.setId("top-of-mid");
        var time_text = new Label("Time");
        var todo_text = new Label("Do This Today");
        mid.getChildren().addAll(time_text, todo_text);

        var bottom = new VBox();

        for (int i = 0; i < 5; i++) {
            var box = new HBox();
            box.setId("time");

            var time_txt = new TextField("00:00-00:00");
            time_txt.setMinWidth(90);
            var content_txt = new TextArea("Do sth useful");
            content_txt.setWrapText(true);
            content_txt.setFont(new Font(15));
            content_txt.setMinWidth(300);
            content_txt.setMinHeight(80);

            box.getChildren().addAll(time_txt, content_txt);
            box.setAlignment(Pos.CENTER_LEFT);
            bottom.getChildren().add(box);
        }
        layout.getChildren().addAll(top, mid, bottom);

        List<HBox> list = new ArrayList<>();
        for (Node h : bottom.getChildren())
            list.add((HBox) h);

        loader(list);

        layout.getStylesheets().add("sample/stylesheet.css");
        var scene = new Scene(layout, 500, 650);

        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            saver(list);
            primaryStage.close();
        });
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private static void saver(List<HBox> list) {
        try (final var writer = new BufferedWriter(new FileWriter("src/saves/save.txt"))) {
            list.forEach(i -> i.getChildren().forEach(j -> {

                try {
                    writer.write(((TextInputControl) j).getText());
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loader(List<HBox> list) {
        try (final var reader = new BufferedReader(new FileReader("src/saves/save.txt"))) {
            list.forEach(i -> i.getChildren().forEach(j -> {

                try {
                    ((TextInputControl) j).setText(reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
