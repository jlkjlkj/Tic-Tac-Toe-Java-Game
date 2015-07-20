/*
  Tic-Tac-Toe Spiel als Java-Anwendung
  erstellt und veröffentlicht von Philipp Schuster
    @phip1611
    http://phip1611.de
*/
package de.phip1611.tictactoe;

import de.phip1611.tictactoe.GUI.JButtons;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * @version 1.0
 * @author Philipp Schuster (@phip1611)
 */
public class TicTacToe {
    /**
     * Symbol für Spieler 1.
     */
    public static final String PLAYER_ONE_SYMBOL = "x";
    
    /**
     * Symbol für Spieler 2.
     */
    public static final String PLAYER_TWO_SYMBOL = "o";
    
    /**
     * Instanz der GUI-Klasse.
     */
    private GUI gui;
    
    /**
     * Wenn nur ein Spieler spielt und daher die KI Spieler 2
     * übernimmt, dann ist dieser Wert wahr, andernfalls falsch.
     */
    private boolean ki;
    
    /**
     * Gibt an, ob Spieler 1 gerade dran ist oder nicht.
     */
    private boolean playerOneNow;
    
    /**
     * Wenn ein Spiel bereits läuft kann es
     * nicht unterbrochen/(durch ein neues) überschrieben werden.
     */
    private boolean gameInProgress;
    
    /**
     * Speichert das Spielfeld für die interne Verwendung in einer
     * 3x3 Matrix.
     */
    private String[][] gamefield;
    
    private JButtons[][] gamefieldButtons;

    /**
     * Die aktuelle Spielrunde.
     */
    private int round;
    
    /**
     * Startet eine Instanz der Tic-Tac-Toe-Klasse.
     */
    public static void main(String[] args) {
        new TicTacToe();
    }
    
    /**
     * Erstellt ein neues Tic-Tac-Toe-Spiel und initialisiert 
     * das GUI.
     */
    public TicTacToe() {
        makeGuiReady();
        resetGame();
        
        gamefieldButtons = new JButtons[3][3];
        gamefieldButtons[0][0] = GUI.JButtons.GAME_TOP_LEFT;
        gamefieldButtons[0][1] = GUI.JButtons.GAME_TOP_CENTER;
        gamefieldButtons[0][2] = GUI.JButtons.GAME_TOP_RIGHT;
        gamefieldButtons[1][0] = GUI.JButtons.GAME_MIDDLE_LEFT;
        gamefieldButtons[1][1] = GUI.JButtons.GAME_MIDDLE_CENTER;
        gamefieldButtons[1][2] = GUI.JButtons.GAME_MIDDLE_RIGHT;
        gamefieldButtons[2][0] = GUI.JButtons.GAME_BOTTOM_LEFT;
        gamefieldButtons[2][1] = GUI.JButtons.GAME_BOTTOM_CENTER;
        gamefieldButtons[2][2] = GUI.JButtons.GAME_BOTTOM_RIGHT;
    }
    
    /**
     * Macht das GUI spielbereit.
     */
    private void makeGuiReady() {
        gui = new GUI("Tic Tac Toe - Philipp Schuster (@phip1611 | 2015)");
        gui.buildGUI();
        gui.show();
        setActionListeners();
    }
    
    /**
     * Initialisiert ein neues Tic-Tac-Toe-Spiel.
     */
    private void startNewGame() {
        // wenn das Spiel bereits läuft, kann kein neues erstellt werden, ohne
        //dass vorher die reset-Methode aufgerufen wird.
        if (gameInProgress) {
            return;
        } else {
            gameInProgress = true;
        }
        int playerCount;
        playerCount = gui.getNumberOfPlayers();
        
        /* Dieser Fall tritt auf, wenn der Start-Button gedrückt wird,
           bei den Radio-Buttons aber noch keine Auswahl getätigt wurde.
           Dieser Fall dürfte aber sowieso nicht auftreten, da der
           Start-Button mittels ActionListenern gedisabled wird wenn keine
           Auswahl der Spieleranzahl besteht.
        */
        if (playerCount == 0) {
            System.err.println("Es wurde noch nicht die Spieleranzahl ausgewählt!");
            return; // beenden
        } else if (playerCount == 1) {
            this.ki = true;
        } else {
            this.ki = false;
        }
    }
    
    /**
     * Setzt das bestehende Tic-Tac-Toe-Spiel zurück beziehungsweise
     * setzt die Variablen so, dass ein neues Spiel losgehen kann.
     */
    private void resetGame() {
        gameInProgress = false;
        playerOneNow = true;
        round = 0;
        gamefield = new String[3][3];
    }
    
    /**
     * Der nächste Zug wird durchgeführt.
     * @param x  X-Koordinate im Spielfeld [1;3]
     * @param y  Y-Koordinate im Spielfeld [1;3]
     */
    private void nextTurn(int x, int y, JButtons button) {        
        // abbrechen, wenn zum Beispiel ein Sieger fest steht!
        if (!gameInProgress) {
            return;
        }
        
        if (gui.canUseThisButton(button)) {
            round++; // nächste Spielrunde
            if (playerOneNow) {
                gui.setGamefieldButtonValue(button, PLAYER_ONE_SYMBOL);
                gamefield[x][y] = PLAYER_ONE_SYMBOL;
                checkForWinner();
                if (ki) {
                    nextKITurn();
                }
            } else {
                gui.setGamefieldButtonValue(button, PLAYER_TWO_SYMBOL);
                gamefield[x][y] = PLAYER_TWO_SYMBOL;
            }
            checkForWinner();
            nextPlayer();
        }        
    }
    
    private void nextKITurn() {
        boolean stop = false;
        int a, b;
        
        if (!gameInProgress) {
            return;
        }
        
        /*
          Wenn Spieler 1 den letzten Zug gemacht hat und damit alle
          9 Felder besetzt sind, kann der folgende Algorithmus kein freies
          Feld mehr finden und es gäbe eine Endlosschleife.
        */
        if (round < 9) { // noch nicht alle 9 Felder belegt / Züge ausgespielt
            round++; // KI ist am Zug, Beginn der nächsten Runde
            do {
                a = (int) Math.round((Math.random() * 2));
                b = (int) Math.round((Math.random() * 2));
                if (gamefield[a][b] == null) {
                    stop = true;
                    gamefield[a][b] = PLAYER_TWO_SYMBOL;
                    gui.setGamefieldButtonValue(gamefieldButtons[a][b], PLAYER_TWO_SYMBOL);
                }
            } while (!stop);
        }        
        checkForWinner();
        nextPlayer();
    }
    
    /**
     * Setzt die Action-Listener für die Elemente der GUI,
     * die für ein TicTacToe-Spiel benötigt werden.
     */
    private void setActionListeners() {
        /* Spiel-Kontroll-Buttons */
        gui.addActionListener(GUI.JButtons.RESET, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
                gui.resetGameField();
                gui.setUserinformationText("Bitte Spieleranzahl wählen!");
                gui.setGamefieldEnabled(false);
            }
        });
        gui.addActionListener(GUI.JButtons.START, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        
        /* 3x3 Tic Tac Toe Feld */
        
        // Reihe 1
        gui.addActionListener(GUI.JButtons.GAME_TOP_LEFT,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextTurn(0, 0, GUI.JButtons.GAME_TOP_LEFT);
            }
        });
        gui.addActionListener(GUI.JButtons.GAME_TOP_CENTER, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextTurn(0, 1, GUI.JButtons.GAME_TOP_CENTER);
            }
        });
        gui.addActionListener(GUI.JButtons.GAME_TOP_RIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextTurn(0, 2, GUI.JButtons.GAME_TOP_RIGHT);
            }
        });
        // Reihe 2
        gui.addActionListener(GUI.JButtons.GAME_MIDDLE_LEFT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextTurn(1, 0, GUI.JButtons.GAME_MIDDLE_LEFT);
            }
        });
        gui.addActionListener(GUI.JButtons.GAME_MIDDLE_CENTER, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextTurn(1, 1, GUI.JButtons.GAME_MIDDLE_CENTER);
            }
        });
        gui.addActionListener(GUI.JButtons.GAME_MIDDLE_RIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextTurn(1, 2, GUI.JButtons.GAME_MIDDLE_RIGHT);
            }
        });
        // Reihe 3
        gui.addActionListener(GUI.JButtons.GAME_BOTTOM_LEFT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextTurn(2, 0, GUI.JButtons.GAME_BOTTOM_LEFT);
            }
        });
        gui.addActionListener(GUI.JButtons.GAME_BOTTOM_CENTER, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextTurn(2, 1, GUI.JButtons.GAME_BOTTOM_CENTER);
            }
        });
        gui.addActionListener(GUI.JButtons.GAME_BOTTOM_RIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextTurn(2, 2, GUI.JButtons.GAME_BOTTOM_RIGHT);
            }
        });
    }
    
    /**
     * Kümmert sich um den Spielerwechsel nach jedem Zug.
     */
    private void nextPlayer() {
        // false <====> true
        this.playerOneNow = !this.playerOneNow;
    }

    /**
     * Überprüft ob es einen Sieger oder ein Unentschieden gibt.
     */
    private void checkForWinner() {
        // Es kann frühestens in Runde 5 einen Gewinner geben.
        if (round < 5) {
            return;
        } else if (gamefield[0][0] == PLAYER_ONE_SYMBOL
                && gamefield[0][1] == PLAYER_ONE_SYMBOL
                && gamefield[0][2] == PLAYER_ONE_SYMBOL
                || gamefield[1][0] == PLAYER_ONE_SYMBOL
                && gamefield[1][1] == PLAYER_ONE_SYMBOL
                && gamefield[1][2] == PLAYER_ONE_SYMBOL
                || gamefield[2][0] == PLAYER_ONE_SYMBOL
                && gamefield[2][1] == PLAYER_ONE_SYMBOL
                && gamefield[2][2] == PLAYER_ONE_SYMBOL
            
                || gamefield[0][0] == PLAYER_ONE_SYMBOL
                && gamefield[1][0] == PLAYER_ONE_SYMBOL
                && gamefield[2][0] == PLAYER_ONE_SYMBOL
                || gamefield[0][1] == PLAYER_ONE_SYMBOL
                && gamefield[1][1] == PLAYER_ONE_SYMBOL
                && gamefield[2][1] == PLAYER_ONE_SYMBOL
                || gamefield[0][2] == PLAYER_ONE_SYMBOL
                && gamefield[1][2] == PLAYER_ONE_SYMBOL
                && gamefield[2][2] == PLAYER_ONE_SYMBOL
             
                || gamefield[0][0] == PLAYER_ONE_SYMBOL
                && gamefield[1][1] == PLAYER_ONE_SYMBOL
                && gamefield[2][2] == PLAYER_ONE_SYMBOL
                || gamefield[0][2] == PLAYER_ONE_SYMBOL
                && gamefield[1][1] == PLAYER_ONE_SYMBOL
                && gamefield[2][0] == PLAYER_ONE_SYMBOL) {
            
            gameInProgress = false;
            gui.setGamefieldEnabled(false);
            gui.setUserinformationText("Spieler 1 hat gewonnen!");
        } else if (gamefield[0][0] == PLAYER_TWO_SYMBOL
                && gamefield[0][1] == PLAYER_TWO_SYMBOL
                && gamefield[0][2] == PLAYER_TWO_SYMBOL
                || gamefield[1][0] == PLAYER_TWO_SYMBOL
                && gamefield[1][1] == PLAYER_TWO_SYMBOL
                && gamefield[1][2] == PLAYER_TWO_SYMBOL
                || gamefield[2][0] == PLAYER_TWO_SYMBOL
                && gamefield[2][1] == PLAYER_TWO_SYMBOL
                && gamefield[2][2] == PLAYER_TWO_SYMBOL
              
                || gamefield[0][0] == PLAYER_TWO_SYMBOL
                && gamefield[1][0] == PLAYER_TWO_SYMBOL
                && gamefield[2][0] == PLAYER_TWO_SYMBOL
                || gamefield[0][1] == PLAYER_TWO_SYMBOL
                && gamefield[1][1] == PLAYER_TWO_SYMBOL
                && gamefield[2][1] == PLAYER_TWO_SYMBOL
                || gamefield[0][2] == PLAYER_TWO_SYMBOL
                && gamefield[1][2] == PLAYER_TWO_SYMBOL
                && gamefield[2][2] == PLAYER_TWO_SYMBOL
                 
                || gamefield[0][0] == PLAYER_TWO_SYMBOL
                && gamefield[1][1] == PLAYER_TWO_SYMBOL
                && gamefield[2][2] == PLAYER_TWO_SYMBOL
                || gamefield[0][2] == PLAYER_TWO_SYMBOL
                && gamefield[1][1] == PLAYER_TWO_SYMBOL
                && gamefield[2][0] == PLAYER_TWO_SYMBOL) {
            
            gameInProgress = false;
            gui.setGamefieldEnabled(false);
            if (ki) {
                gui.setUserinformationText("KI (Spieler 2) hat gewonnen!");
            } else {
                gui.setUserinformationText("Spieler 2 hat gewonnen!");
            }
        } else {
            if (round >= 9) {
                gui.setGamefieldEnabled(false);
                gui.setUserinformationText("Unentschieden!");
            }
        }
    }
    
}
