package standard_huffman;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.util.Duration;

import java.io.File;

public class HuffmanMainGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Standard Huffman");

        Label headerLabel = new Label("Welcome to Standard Huffman!");
        headerLabel.setStyle("-fx-text-fill: white; -fx-font-family: 'Comic Sans MS';-fx-font-weight: bold; -fx-font-size: 30px; -fx-padding: 20 0 30 0;");

        Button compressButton = new Button("Compression");
        Button decompressButton = new Button("Decompression");

        compressButton.setStyle("-fx-background-color: navy; -fx-text-fill: white; -fx-font-size: 19px; -fx-background-radius: 8;");
        decompressButton.setStyle("-fx-background-color: navy; -fx-text-fill: white; -fx-font-size: 19px; -fx-background-radius: 8;");

        decompressButton.setCursor(Cursor.HAND);
        compressButton.setCursor(Cursor.HAND);

        Label completed = new Label("");

        VBox v1 = new VBox(10);
        v1.getChildren().addAll(compressButton, decompressButton);
        v1.setAlignment(Pos.CENTER);

        VBox v2 = new VBox(10);
        v2.getChildren().addAll(headerLabel,v1,completed);
        v2.setAlignment(Pos.CENTER);

        Scene scene = new Scene(v2, 700, 300);
        v2.setBackground(new Background(new BackgroundFill(Color.BLACK.desaturate(), CornerRadii.EMPTY, Insets.EMPTY)));
        primaryStage.setScene(scene);



        compressButton.setOnAction(e -> {
            compressButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: black; -fx-font-size: 19px; -fx-background-radius: 8;");

            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));


            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                File dataFile = new File(selectedFile.getPath());
                try {
                    compressFile(dataFile);
                    completed.setStyle("-fx-text-fill: green; -fx-font-size: 18px;");
                    completed.setText("Compression and saving to compressed.bin is successful");

                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else{
                completed.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                completed.setText("Compression failed");
            }

            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> {
                resetButton(compressButton);
                completed.setText("");
            });
            pause.play();


        });


        decompressButton.setOnAction(e -> {
            decompressButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: black; -fx-font-size: 19px; -fx-background-radius: 8;");

            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.bin"));

            File selectedFile = fileChooser.showOpenDialog(primaryStage);

            if (selectedFile != null) {
                File compressedFile = new File(selectedFile.getPath());
                try {
                    decompressFile(compressedFile);
                    completed.setStyle("-fx-text-fill: green; -fx-font-size: 18px;");
                    completed.setText("Decompression and saving to decompressed.txt is successful");
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else{
                completed.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                completed.setText("Decompression failed");
            }

            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> {
                resetButton(decompressButton);
                completed.setText("");
            });
            pause.play();
        });

        primaryStage.show();
    }

    private void compressFile(File selectedFile) throws InterruptedException {
        String filePath = selectedFile.getPath();
        Huffman huff = new Huffman(filePath);
        huff.compress();
        System.out.println("Compression and saving to CompressedFile.txt is successful.");
    }
    private void decompressFile(File selectedFile) throws InterruptedException {
        String filePath = selectedFile.getPath();
        Huffman huff = new Huffman(filePath);
        huff.decompress(filePath);
        Thread.sleep(2000);
        System.out.println("Decompression and saving to decompressed.txt is successful.");
    }

    public void resetButton(Button s){
        s.setStyle("-fx-background-color: navy; -fx-text-fill: white; -fx-font-size: 19px; -fx-background-radius: 8;");
    }
    public static void main(String[] args) {
        launch(args);
    }
}
