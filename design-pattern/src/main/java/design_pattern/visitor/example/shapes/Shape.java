package design_pattern.visitor.example.shapes;

import design_pattern.visitor.example.visitor.Visitor;

public interface Shape {
    public void move(int x, int y);
    public void draw();
    public String accept(Visitor visitor);
}
