/*
  Tic-Tac-Toe Spiel als Java-Anwendung
  erstellt und veröffentlicht von Philipp Schuster
    @phip1611
    http://phip1611.de
*/
package de.phip1611.tictactoe;

/**
 * Jedes Komponenten-Enum meiner GUI-Klasse implementiert dieses Interface,
 * damit ich alle Komponenten, unabhängig vom Enum, von außen an eine
 * GUI.Methode() übergeben kann, so dass für alle ein gleicher Datentyp gilt.
 * GUI.JButtons.EXIT --> methode(GUI.Component c)
 * 
 * @version 1.0
 * @author Philipp Schuster (@phip1611)
 */
interface GUIComponent {}