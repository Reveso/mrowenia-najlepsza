package application.langtonsant.entity;

import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Wrapper dla klasy javafx.scene.paint.Color, umożliwiający jego serializację.
 */
public class SavableColor implements Serializable {
    private long serialVersionUID = 1L;

    /**
     * Czerwony.
     */
    private double r;
    /**
     * Zielony.
     */
    private double g;
    /**
     * Niebieski.
     */
    private double b;
    /**
     * Przeźroczystość.
     */
    private double opacity;

    /**
     * Ustala wartości pól z instancji javafx.scene.paint.Color.
     * @param color Instancja klasy javafx.scene.paint.Color.
     */
    public SavableColor(Color color) {
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();
        opacity = color.getOpacity();
    }

    /**
     * Ustala wartości pól z instancji javafx.scene.paint.Color.
     * @param color Instancja klasy javafx.scene.paint.Color.
     */
    public void setFromColorClass(Color color) {
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();
        opacity = color.getOpacity();
    }

    /**
     * Zwraca instancję javafx.scene.paint.Color.
     * @return Instancja klasy Obiekt klasy javafx.scene.paint.Color.
     */
    public Color toColorClass() {
        return new Color(r, g, b, opacity);
    }
}
