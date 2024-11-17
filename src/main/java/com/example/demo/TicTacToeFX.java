package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

public class TicTacToeFX extends Application {
    private char currentPlayer = 'X';
    private Button[][] buttons = new Button[3][3];
    private Label statusLabel = new Label("PLAYER X's TURN");
    private boolean playWithComputer = false;
    private Stage primaryStage;

    // Define colors
    private final String BACKGROUND_COLOR = "#2C3E50";
    private final String BUTTON_COLOR = "#34495E";
    private final String HOVER_COLOR = "#415B76";
    private final String X_COLOR = "#2ECC71";
    private final String O_COLOR = "#E74C3C";
    private final String WIN_HIGHLIGHT = "#27AE60";
    private final String TEXT_COLOR = "#ECF0F1";

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showGameModeSelection();
    }

    private void showGameModeSelection() {
        // Create the main layout container
        VBox selectionRoot = new VBox(30);
        selectionRoot.setAlignment(Pos.CENTER);
        selectionRoot.setPadding(new Insets(25));
        selectionRoot.setBackground(new Background(new BackgroundFill(
                Color.web(BACKGROUND_COLOR),
                CornerRadii.EMPTY,
                Insets.EMPTY)));

        // Create title
        Label titleLabel = new Label("TIC TAC TOE");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        titleLabel.setTextFill(Color.web(TEXT_COLOR));
        titleLabel.setEffect(new DropShadow(10, Color.BLACK));

        // Create mode selection buttons
        Button pvpButton = createModeButton("Player vs Player");
        Button pvcButton = createModeButton("Play with Computer");

        // Add button actions
        pvpButton.setOnAction(e -> {
            playWithComputer = false;
            showGameBoard();
        });

        pvcButton.setOnAction(e -> {
            playWithComputer = true;
            showGameBoard();
        });

        // Add components to root
        selectionRoot.getChildren().addAll(titleLabel, pvpButton, pvcButton);

        // Set up scene
        Scene selectionScene = new Scene(selectionRoot);
        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.setMinWidth(450);
        primaryStage.setMinHeight(550);
        primaryStage.setScene(selectionScene);
        primaryStage.show();
    }

    private Button createModeButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        button.setTextFill(Color.web(TEXT_COLOR));
        button.setPrefWidth(250);
        button.setPrefHeight(60);

        // Add modern styling
        button.setStyle(String.format("""
            -fx-background-color: %s;
            -fx-background-radius: 20;
            -fx-border-radius: 20;
            """, BUTTON_COLOR));

        // Add hover effect
        button.setOnMouseEntered(e ->
                button.setStyle(String.format("""
                -fx-background-color: %s;
                -fx-background-radius: 20;
                -fx-border-radius: 20;
                """, HOVER_COLOR)));

        button.setOnMouseExited(e ->
                button.setStyle(String.format("""
                -fx-background-color: %s;
                -fx-background-radius: 20;
                -fx-border-radius: 20;
                """, BUTTON_COLOR)));

        // Add shadow effect
        button.setEffect(new DropShadow(5, Color.BLACK));

        return button;
    }

    private void showGameBoard() {
        // Create the main layout container with padding
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(25));
        root.setBackground(new Background(new BackgroundFill(
                Color.web(BACKGROUND_COLOR),
                CornerRadii.EMPTY,
                Insets.EMPTY)));

        // Style the status label
        statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        statusLabel.setTextFill(Color.web(TEXT_COLOR));
        statusLabel.setEffect(new DropShadow(10, Color.BLACK));

        // Create the 3x3 grid of buttons
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);

        // Initialize grid buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = createStyledButton();
                final int row = i;
                final int col = j;
                buttons[i][j].setOnAction(e -> handleButtonClick(row, col));
                grid.add(buttons[i][j], j, i);
            }
        }

        // Create control buttons
        Button resetButton = createControlButton("Reset Game");
        Button backToMenuButton = createControlButton("Back to Menu");

        resetButton.setOnAction(e -> resetGame());
        backToMenuButton.setOnAction(e -> {
            resetGame();
            showGameModeSelection();
        });

        // Add components to root
        root.getChildren().addAll(statusLabel, grid, resetButton, backToMenuButton);

        // Set up scene
        Scene gameScene = new Scene(root);
        primaryStage.setScene(gameScene);
    }

    private Button createStyledButton() {
        Button button = new Button(" ");
        button.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        button.setMinSize(100, 100);
        button.setPrefSize(120, 120);
        button.setTextFill(Color.web(TEXT_COLOR));

        // Add modern styling
        button.setStyle(String.format("""
            -fx-background-color: %s;
            -fx-background-radius: 10;
            -fx-border-radius: 10;
            """, BUTTON_COLOR));

        // Add hover effect
        button.setOnMouseEntered(e -> {
            if (button.getText().equals(" ")) {
                button.setStyle(String.format("""
                    -fx-background-color: %s;
                    -fx-background-radius: 10;
                    -fx-border-radius: 10;
                    """, HOVER_COLOR));
            }
        });

        button.setOnMouseExited(e -> {
            if (button.getText().equals(" ")) {
                button.setStyle(String.format("""
                    -fx-background-color: %s;
                    -fx-background-radius: 10;
                    -fx-border-radius: 10;
                    """, BUTTON_COLOR));
            }
        });

        // Add shadow effect
        button.setEffect(new DropShadow(5, Color.BLACK));

        return button;
    }

    private Button createControlButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        button.setTextFill(Color.web(TEXT_COLOR));
        button.setPrefWidth(200);
        button.setPrefHeight(40);

        // Add modern styling
        button.setStyle(String.format("""
            -fx-background-color: %s;
            -fx-background-radius: 20;
            -fx-border-radius: 20;
            """, BUTTON_COLOR));

        // Add hover effect
        button.setOnMouseEntered(e ->
                button.setStyle(String.format("""
                -fx-background-color: %s;
                -fx-background-radius: 20;
                -fx-border-radius: 20;
                """, HOVER_COLOR)));

        button.setOnMouseExited(e ->
                button.setStyle(String.format("""
                -fx-background-color: %s;
                -fx-background-radius: 20;
                -fx-border-radius: 20;
                """, BUTTON_COLOR)));

        // Add shadow effect
        button.setEffect(new DropShadow(5, Color.BLACK));

        return button;
    }

    private void handleButtonClick(int row, int col) {
        if (buttons[row][col].getText().equals(" ")) {
            buttons[row][col].setText(String.valueOf(currentPlayer));

            // Apply player-specific colors
            String color = currentPlayer == 'X' ? X_COLOR : O_COLOR;
            buttons[row][col].setStyle(String.format("""
                -fx-background-color: %s;
                -fx-background-radius: 10;
                -fx-border-radius: 10;
                """, color));

            if (checkWin(currentPlayer)) {
                statusLabel.setText("Player " + currentPlayer + " wins!");
                highlightWinningLine(currentPlayer);
                disableAllButtons();
            } else if (isBoardFull()) {
                statusLabel.setText("The game is a tie!");
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                statusLabel.setText("PLAYER " + currentPlayer + "'s TURN");

                if (playWithComputer && currentPlayer == 'O') {
                    computerMove();
                }
            }
        }
    }

    private void computerMove() {
        int[] bestMove = findBestMove();
        buttons[bestMove[0]][bestMove[1]].fire();
    }

    private int[] findBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals(" ")) {
                    buttons[i][j].setText("O");
                    int score = minimax(0, false);
                    buttons[i][j].setText(" ");
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{i, j};
                    }
                }
            }
        }

        return bestMove;
    }

    private int minimax(int depth, boolean isMaximizing) {
        if (checkWin('O')) return 10 - depth;
        if (checkWin('X')) return depth - 10;
        if (isBoardFull()) return 0;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttons[i][j].getText().equals(" ")) {
                        buttons[i][j].setText("O");
                        int score = minimax(depth + 1, false);
                        buttons[i][j].setText(" ");
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttons[i][j].getText().equals(" ")) {
                        buttons[i][j].setText("X");
                        int score = minimax(depth + 1, true);
                        buttons[i][j].setText(" ");
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
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
        statusLabel.setText("PLAYER X's TURN");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(" ");
                buttons[i][j].setDisable(false);
                buttons[i][j].setStyle(String.format("""
                    -fx-background-color: %s;
                    -fx-background-radius: 10;
                    -fx-border-radius: 10;
                    """, BUTTON_COLOR));
            }
        }
    }
//-fx-background-color: %s;
    private void highlightWinningLine(char player) {
        String style = String.format("""
            -fx-background-radius: 10;
            -fx-border-radius: 10;
            """, WIN_HIGHLIGHT);

        for (int i = 0; i < 3; i++) {
            if (isRowWin(i, player)) {
                for (int j = 0; j < 3; j++){
                    buttons[i][j].setStyle(style);
                }
            }
            if (isColWin(i, player)) {
                for (int j = 0; j < 3; j++) {
                    buttons[j][i].setStyle(style);
                }
            }
        }
        if (isPrimaryDiagonalWin(player)) {
            for (int i = 0; i < 3; i++) {
                buttons[i][i].setStyle(style);
            }
        }
        if (isSecondaryDiagonalWin(player)) {
            for (int i = 0; i < 3; i++) {
                buttons[i][2 - i].setStyle(style);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}