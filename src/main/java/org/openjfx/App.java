package org.openjfx;
 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class App extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gerador de Contas Office 365!");
        Button btn = new Button();
        btn.setText("Gerar Conta");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                try {
                    URI uri = new URI("http://172.16.6.127:8080/fetch");
                    URL url = uri.toURL();
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Account Return");
                    alert.setHeaderText(null);

                    TextArea textArea = new TextArea(response.toString());
                    textArea.setEditable(false);
                    textArea.setWrapText(true);
                    alert.getDialogPane().setContent(textArea);
                    alert.showAndWait();

                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
}