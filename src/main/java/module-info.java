module com.example.standard_huffman {
    requires javafx.controls;
    requires javafx.fxml;
    opens standard_huffman to javafx.fxml;
    exports standard_huffman;
}