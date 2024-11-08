package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TicTacToeFX extends Application {
    private char currentPlayer = 'X';
    private Button[][] buttons = new Button[3][3];
    private Label statusLabel = new Label("Player X's turn");

    @Override
    public void start(Stage primaryStage) {
        // Create the main layout container
        VBox root = new VBox(10);  // Spacing of 10 between elements
        root.setAlignment(Pos.CENTER);  // Center everything in VBox

        // Create the 3x3 grid of buttons
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(5); // Vertical gap between buttons
        grid.setHgap(5); // Horizontal gap between buttons

        // Initialize grid buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new Button(" ");
                buttons[i][j].setFont(new Font(24));
                buttons[i][j].setMinSize(100, 100);

                final int row = i;
                final int col = j;
                buttons[i][j].setOnAction(e -> handleButtonClick(row, col));

                grid.add(buttons[i][j], j, i);
            }
        }

        // Create the reset button
        Button resetButton = new Button("Reset Game");
        resetButton.getStyleClass().add("reset-button");
        resetButton.setOnAction(e -> resetGame());

        // Add status label, grid, and reset button to VBox
        root.getChildren().addAll(statusLabel, grid, resetButton);

        // Set up scene and stage
        Scene scene = new Scene(root, 400, 450);  // Adjusted dimensions for centering
        scene.getStylesheets().add("styles.css");  // Link to the CSS file
        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleButtonClick(int row, int col) {
        // Check if the button is empty before making a move
        if (buttons[row][col].getText().equals(" ")) {
            buttons[row][col].setText(String.valueOf(currentPlayer));
            buttons[row][col].setStyle(currentPlayer == 'X' ? "-fx-text-fill: blue;" : "-fx-text-fill: red;");

            // Check for a win or tie
            if (checkWin(currentPlayer)) {
                statusLabel.setText("Player " + currentPlayer + " wins!");
                highlightWinningLine(currentPlayer);
                disableAllButtons();
            } else if (isBoardFull()) {
                statusLabel.setText("The game is a tie!");
            } else {
                // Switch the current player
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                statusLabel.setText("Player " + currentPlayer + "'s turn");
            }
        }
    }

    private boolean checkWin(char player) {
        for (int i = 0; i < 3; i++) {
            if (isRowWin(i, player) || isColWin(i, player)) return true;
        }
        return isPrimaryDiagonalWin(player) || isSecondaryDiagonalWin(player);
    }

    private boolean isRowWin(int row, char player) {
        return buttons[row][0].getText().equals(String.valueOf(player)) &&
                buttons[row][1].getText().equals(String.valueOf(player)) &&
                buttons[row][2].getText().equals(String.valueOf(player));
    }

    private boolean isColWin(int col, char player) {
        return buttons[0][col].getText().equals(String.valueOf(player)) &&
                buttons[1][col].getText().equals(String.valueOf(player)) &&
                buttons[2][col].getText().equals(String.valueOf(player));
    }

    private boolean isPrimaryDiagonalWin(char player) {
        return buttons[0][0].getText().equals(String.valueOf(player)) &&
                buttons[1][1].getText().equals(String.valueOf(player)) &&
                buttons[2][2].getText().equals(String.valueOf(player));
    }

    private boolean isSecondaryDiagonalWin(char player) {
        return buttons[0][2].getText().equals(String.valueOf(player)) &&
                buttons[1][1].getText().equals(String.valueOf(player)) &&
                buttons[2][0].getText().equals(String.valueOf(player));
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals(" ")) return false;
            }
        }
        return true;
    }

    private void disableAllButtons() {
        for (Button[] row : buttons) {
            for (Button button : row) {
                button.setDisable(true);
            }
        }
    }

    private void resetGame() {
        currentPlayer = 'X';
        statusLabel.setText("Player X's turn");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(" ");
                buttons[i][j].setStyle("");  // Reset style
                buttons[i][j].setDisable(false);
            }
        }
    }

    private void highlightWinningLine(char player) {
        // Highlight winning line based on which line has the win
        for (int i = 0; i < 3; i++) {
            if (isRowWin(i, player)) {
                for (int j = 0; j < 3; j++) {
                    buttons[i][j].setStyle("-fx-background-color: yellow;");
                }
            }
            if (isColWin(i, player)) {
                for (int j = 0; j < 3; j++) {
                    buttons[j][i].setStyle("-fx-background-color: yellow;");
                }
            }
        }
        if (isPrimaryDiagonalWin(player)) {
            for (int i = 0; i < 3; i++) {
                buttons[i][i].setStyle("-fx-background-color: yellow;");
            }
        }
        if (isSecondaryDiagonalWin(player)) {
            for (int i = 0; i < 3; i++) {
                buttons[i][2 - i].setStyle("-fx-background-color: yellow;");
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
