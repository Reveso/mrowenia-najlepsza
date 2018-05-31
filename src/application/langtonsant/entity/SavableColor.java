package application.langtonsant.entity;

import javafx.scene.paint.Color;

import java.io.Serializable;

public class SavableColor implements Serializable {
    private long serialVersionUID = 1L;

    private double r;
    private double g;
    private double b;
    private double opacity;

    public SavableColor(double r, double g, double b, double opacity) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.opacity = opacity;
    }

    public SavableColor(Color color) {
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();
        opacity = color.getOpacity();
    }

    public void setFromColorClass(Color color) {
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();
        opacity = color.getOpacity();
    }

    public Color toColorClass() {
        return new Color(r, g, b, opacity);
    }
}
