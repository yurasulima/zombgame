package io.mblueberry.core;

public class Vector2D {
    private double x;
    private double y;

    // Constructor
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Getters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // Setters
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    // Vector magnitude (length)
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    // Normalize the vector
    public Vector2D normalized() {
        double mag = magnitude();
        if (mag == 0) return new Vector2D(0, 0);
        return new Vector2D(x / mag, y / mag);
    }

    // Vector multiplication by a scalar
    public Vector2D multiply(double scalar) {
        return new Vector2D(x * scalar, y * scalar);
    }

    // Add another vector
    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    // Subtract another vector
    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    // Dot product of two vectors
    public double dot(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    @Override
    public String toString() {
        return "Vector2D(" + x + ", " + y + ")";
    }
}
