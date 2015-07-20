/*
  Tic-Tac-Toe Spiel als Java-Anwendung
  erstellt und veröffentlicht von Philipp Schuster
    @phip1611
    http://phip1611.de
*/
package de.phip1611.tictactoe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

/**
 * Erstellt und verwaltet das GUI für die TicTactoe-Anwendung.
 * Dabei bildet <b>ein Hauptfenster</b> die Grundlage.
 */
public class GUI {
    
    /**
     * Erstellt ein GUI-Fenster mit leerem Titel.
     * @see setTitle()
     */
    public GUI() {
        this("");
    }
    
    /**
     * Erstellt ein GUI-Fenster mit dem übergebenen Titel.
     * @param title  Fenstername
     */
    public GUI(String title) {
        this.setTitle(title);
    }
    
    public final void setTitle(String title) {
        JFrames.MAIN.get().setTitle(title);
    }
    
    /**
     * Setzt die Grafik-Elemente alle zusammen in den Frame ein.
     */
    public void buildGUI() {
        /*      Tic-Tac-Toe-Buttons      */
        this.setButtonsSize();
        this.addButtonsToPanel();
        this.setGamefieldPanelLayout();
        /* ----------------------------- */
        /*         Radio-Buttons         */
        this.buildRadioButtonsUnit();
        this.buildControlButtonsUnit();
        this.buildControlUnit();
        /* ----------------------------- */
        /*       GUI fertig machen       */
        this.addAllComponents();
        this.setDefaultComponentSettings();
        this.setDefaultActionListeners();
        this.makeGuiReady();
    }
    
    /**
     * Das fertig gebaute GUI wird nun ausgegeben.
     */
    public void show() {
        JFrames.MAIN.get().setVisible(true);
    }
    
    /**
     * Das Fenster wird unsichtbar.
     */
    public void hide() {
        JFrames.MAIN.get().setVisible(false);
    }
    
    /**
     * Setzt für jeden Button der Teil des Spielfelds ist
     * die richtigen Dimensionen.
     */
    private void setButtonsSize() {
        for (JButtons button : JButtons.values()) {
            if (button.toString().contains("GAME_")) {
                button.get().setPreferredSize(
                    new Dimension(120, 120)
            );
            }
        }
    }
    
    /**
     * Fügt die Buttons die zum Spielfeld gehören in das
     * zugehörige JPanel ein.
     */
    private void addButtonsToPanel() {
        /* keine Schleife die iteriert, damit die Buttons in der
        richtigen Reihenfolge erscheinen! */
        JPanels.GAME_FIELD.get().add(
            JButtons.GAME_TOP_LEFT.get()
        );
        JPanels.GAME_FIELD.get().add(
            JButtons.GAME_TOP_CENTER.get()
        );
        JPanels.GAME_FIELD.get().add(
            JButtons.GAME_TOP_RIGHT.get()
        );
        JPanels.GAME_FIELD.get().add(
            JButtons.GAME_MIDDLE_LEFT.get()
        );
        JPanels.GAME_FIELD.get().add(
            JButtons.GAME_MIDDLE_CENTER.get()
        );
        JPanels.GAME_FIELD.get().add(
            JButtons.GAME_MIDDLE_RIGHT.get()
        );
        JPanels.GAME_FIELD.get().add(
            JButtons.GAME_BOTTOM_LEFT.get()
        );
        JPanels.GAME_FIELD.get().add(
            JButtons.GAME_BOTTOM_CENTER.get()
        );
        JPanels.GAME_FIELD.get().add(
            JButtons.GAME_BOTTOM_RIGHT.get()
        );
    }
    
    /**
     * Setzt das Layout für das Spielfeld-Panel. Achtung, 
     * damit ist nicht das gesamte Layout des GUI gemeint,
     * sondern nur das des Panels, welches die Buttons aufnimmt.
     */
    private void setGamefieldPanelLayout() {
        int d = 7;        
        JPanels.GAME_FIELD.get().setBorder(
            new EmptyBorder(d, d, d, d)
        );        
        JPanels.GAME_FIELD.get().setLayout(
            new GridLayout(3, 3, d, d)
        );
    }
    
    /**
     * Setzt die Grafik-Einheit für die Radio-Buttons zusammen.
     */
    private void buildRadioButtonsUnit() {      
        // Button-Gruppierung registrieren, damit immer nur ein Eintrag
        // gewählt werden kann.
        ButtonGroups.RADIO_BUTTONS.get().add(JRadioButtons.ONE_PLAYER.get());
        ButtonGroups.RADIO_BUTTONS.get().add(JRadioButtons.TWO_PLAYER.get());
       
        JPanels.CONTROL_RADIO_BUTTONS.get().add(
           JLabels.SELECT_PLAYER_COUNT.get()
       );
       JPanels.CONTROL_RADIO_BUTTONS.get().add(JRadioButtons.ONE_PLAYER.get());
       JPanels.CONTROL_RADIO_BUTTONS.get().add(JRadioButtons.TWO_PLAYER.get());
    }
    
    /**
     * Erstellt die Grafik-Einheit für die Kontroll-Buttons zur Steuerung.
     */
    private void buildControlButtonsUnit() {
        JButtons.START.get().setText("Starten");
        JButtons.RESET.get().setText("Zurücksetzen");
        JButtons.EXIT.get().setText("Beenden");
        
        JPanels.CONTROL_BUTTONS.get().add(JButtons.START.get());
        JPanels.CONTROL_BUTTONS.get().add(JButtons.RESET.get());
        JPanels.CONTROL_BUTTONS.get().add(JButtons.EXIT.get());
        
        JPanels.CONTROL_BUTTONS.get().setLayout(
            new FlowLayout()
        );
    }
    
    /**
     * Bildet die Kontrolleinheit, in der man die Spieleinstellungen
     * einstellen kann.
     */
    private void buildControlUnit() {       
        // hinzufügen der Elemente zum Panel
        JPanels.CONTROL.get().add(JLabels.SELECT_PLAYER_COUNT.get());
        JPanels.CONTROL.get().add(JPanels.CONTROL_RADIO_BUTTONS.get());
        JPanels.CONTROL.get().add(JPanels.CONTROL_BUTTONS.get());
        JPanels.CONTROL.get().add(JLabels.USER_INFORMATION_OUTPUT.get());
        JPanels.CONTROL.get().add(JLabels.COPYRIGHT.get());
        
        JPanels.CONTROL.get().setLayout(
            new GridLayout(JPanels.CONTROL.get().getComponentCount(), 1)
        );        
    }
    
    /**
     * Fügt alle Komponenten, in der Regel mehrere "Ober-Panels" 
     * in das endgültige GUI-Fenster ein.
     */
    private void addAllComponents() {
        JFrames.MAIN.get().add(JPanels.CONTROL.get());
        JFrames.MAIN.get().add(JPanels.GAME_FIELD.get());
    }
    
    /**
     * Einige Standardeinstellungen der Komponenten werden hier gesetzt,
     * zum Beispiel disabled, Farbe, ....
     */
    private void setDefaultComponentSettings() {
        JFrames.MAIN.get().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFrames.MAIN.get().setResizable(false);
        
        JRadioButtons.ONE_PLAYER.get().setText("Ein Spieler");
        JRadioButtons.TWO_PLAYER.get().setText("Zwei Spieler");
        JRadioButtons.ONE_PLAYER.get().setFocusPainted(false);
        JRadioButtons.TWO_PLAYER.get().setFocusPainted(false);
        
        JButtons.EXIT.get().setFocusPainted(false);
        JButtons.RESET.get().setFocusPainted(false);
        JButtons.START.get().setFocusPainted(false);
        
        JLabels.SELECT_PLAYER_COUNT.get()
                .setText("Anzahl der Spieler bitte Auswählen");
        JLabels.USER_INFORMATION_OUTPUT.get()
                .setText("Bitte Spieleranzahl wählen!");
        
        JLabels.USER_INFORMATION_OUTPUT.get().setForeground(Color.red);
        
        // erst wenn bei Radio-Buttons eine Aktion durchgeführt
        // wurde, wird Button aktiv
        JButtons.START.get().setEnabled(false);
        
        // Copyright Message
        JLabels.COPYRIGHT.get().setText("Philipp Schuster (@phip1611 | 2015)");
        JLabels.COPYRIGHT.get().setForeground(new Color(190, 190, 190));
        JLabels.COPYRIGHT.get().setBorder(new EmptyBorder(40, 0, 0, 0));
        
        // alle Buttons zunächst disable'n, Schriftgröße der Buttons einstellen
        prepareGamefield();
    }
    
    /**
     * Setzt einige wichtige Standard-Action-Listener, zum Beispiel
     * Beenden des Programmes beim Drücken des Exit-Knoppfes.
     */
    private void setDefaultActionListeners() {
        JButtons.EXIT.get().addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            }
        );
        JButtons.RESET.get().addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ButtonGroups.RADIO_BUTTONS.get().clearSelection();
                    JButtons.START.get().setEnabled(false);
                    // Wenn das Spiel zurückgesetzt wird soll man
                    // wieder optionen setzen können
                    JRadioButtons.ONE_PLAYER.get().setEnabled(true);
                    JRadioButtons.TWO_PLAYER.get().setEnabled(true);
                }
            }
        );
        JButtons.START.get().addActionListener(
            new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // wenn das Spiel gestartet wird sollen
                // bestimmte Schaltflächen deaktiviert werden
                JButtons.START.get().setEnabled(false);
                JRadioButtons.ONE_PLAYER.get().setEnabled(false);                
                JRadioButtons.TWO_PLAYER.get().setEnabled(false);
                setGamefieldEnabled(true);
            }
        });
        
        JRadioButtons.ONE_PLAYER.get().addActionListener(
                new RadioButtonsActionListener()
        );
        JRadioButtons.TWO_PLAYER.get().addActionListener(
                new RadioButtonsActionListener()
        );
        
    }
    
    
    /**
     * Action-Listener für eine Aktion bei meinen Radio-Buttons.
     */
    private class RadioButtonsActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButtons.START.get().setEnabled(true);
            JLabels.USER_INFORMATION_OUTPUT.get().setText("");
        }
    }
    
    /**
     * Setzt alle Komponenten in das Hauptfenster ein und klatscht das 
     * Layout drauf.
     */
    private void makeGuiReady() {        
        JFrames.MAIN.get().setLayout(new FlowLayout());
        JFrames.MAIN.get().pack();
    }
    
    
    /**
     * Öffentliche Schnittstelle um ActionListener auf GUI-
     * Komponenten zu setzen.
     * @param c   GUI-Komponente: <code>GUI.JButtons.EXIT</code>
     * @param al  Action Listener
     */
    public void addActionListener(GUIComponent c, ActionListener al) {
        // in diesem Fall brauche ich nur Action Listener für Buttons
        if (c instanceof JButtons) {
            JButtons.valueOf(c.toString()).get().addActionListener(al);
        }
    }
    
    /**
     * Setzt den Text für einen Button des Spielfelds, beispielsweise
     * "x" oder "o".
     * @param button  Button des Spielfeldes
     * @param text    Text
     */
    public void setGamefieldButtonValue(JButtons button, String text) {
        button.get().setText(text);
    }
    
    /**
     * Ein Spielfeld-Button soll/kann nur genutzt werden, wenn dieser
     * nicht bereits besetzt ist.
     * @param button  Button des Spielfeldes
     * @return        Button ist frei (nicht besetzt)
     */
    public boolean canUseThisButton(JButtons button) {
        return "".equals(button.get().getText());
    }
    
    /**
     * Gibt die Anzahl der Spieler zurück, die anhand der
     * Radio-Buttons ausgewählt wurden. Ist kein Button aktiv,
     * wird 0 zurückgegeben.
     * @return Anzahl Spieler
     */
    public int getNumberOfPlayers() {
        if (GUI.JRadioButtons.ONE_PLAYER.get().isSelected()) {
            return 1;
        } else if (GUI.JRadioButtons.TWO_PLAYER.get().isSelected()) {
            return 2;
        } else {
            return 0;
        }
    }
    
    /**
     * Bereitet das Spielfeld vor und setzt bestimmte Eigenschaften für 
     * die Buttons, wie Schriftgröße.
     */
    private void prepareGamefield() {
        for (JButtons button : JButtons.values()) {
            if (button.toString().contains("GAME_")) {
                button.get().setFont(new Font("Arial", Font.PLAIN, 80));
                button.get().setFocusPainted(false);
                button.get().setEnabled(false);
            }
        }
    }
    
    /**
     * Stellt alle Buttons der Spielfläche auf enabled/disabled.
     * @param enabled  Spielfläche klickbar
     */
    public void setGamefieldEnabled(boolean enabled) {
        for (JButtons button : JButtons.values()) {
            if (button.toString().contains("GAME_")) {
                button.get().setEnabled(enabled);
            }
        }
    }
    
    /**
     * Setzt alle Buttons der Spielfläche zurück.
     */
    public void resetGameField() {
       for (JButtons button : JButtons.values()) {
            if (button.toString().contains("GAME_")) {
                button.get().setText("");
            }
        } 
    }
    
    /**
     * Setzt den Text 
     * @param text 
     */
    public void setUserinformationText(String text) {
        JLabels.USER_INFORMATION_OUTPUT.get().setText(text);
    }
    
    /* ************************* Komponenten-Enums ************************* */
    
    /**
     * Hält alle JFrame-Grafik-Komponenten der Anwendung.
     */
    public enum JFrames implements GUIComponent {
        MAIN;
        private final JFrame jframe = new JFrame();
        private JFrame get() {
            return jframe;
        }
    };
    
    /**
     * Hält alle JLabel-Grafik-Komponenten der Anwendung.
     */
    public enum JLabels implements GUIComponent {
        SELECT_PLAYER_COUNT,
        USER_INFORMATION_OUTPUT,
        COPYRIGHT;
        private final JLabel jlabel = new JLabel();
        private JLabel get() {
            return jlabel;
        }
    };
    
    /**
     * Hält alle JButton-Grafik-Komponenten der Anwendung.
     */
    public enum JButtons implements GUIComponent {
        START,
        EXIT,
        RESET,
        GAME_TOP_LEFT,
        GAME_TOP_CENTER,
        GAME_TOP_RIGHT, 
        GAME_MIDDLE_LEFT,
        GAME_MIDDLE_CENTER,
        GAME_MIDDLE_RIGHT, 
        GAME_BOTTOM_LEFT,
        GAME_BOTTOM_CENTER,
        GAME_BOTTOM_RIGHT;
        private final JButton jbutton = new JButton();
        private JButton get() {
            return jbutton;
        }
    };
    
    /**
     * Hält alle JPanel-Grafik-Komponenten der Anwendung.
     */
    public enum JPanels implements GUIComponent {
        CONTROL,
        CONTROL_RADIO_BUTTONS,
        CONTROL_BUTTONS,
        GAME_FIELD;
        private final JPanel jpanel = new JPanel();
        private JPanel get() {
            return jpanel;
        }
    };
    
    /**
     * Hält alle JRadioButtons-Grafik-Komponenten der Anwendung.
     */
    public enum JRadioButtons implements GUIComponent {
        ONE_PLAYER,
        TWO_PLAYER;
        private final JRadioButton jradiobutton = new JRadioButton();
        private JRadioButton get() {
            return jradiobutton;
        }
    };
    
    /**
     * Hält alle ButtonGroup-Gruppierungen.
     */
    public enum ButtonGroups implements GUIComponent {
        RADIO_BUTTONS;
        private final ButtonGroup buttongroup = new ButtonGroup();
        private ButtonGroup get() {
            return buttongroup;
        }
    }
}
