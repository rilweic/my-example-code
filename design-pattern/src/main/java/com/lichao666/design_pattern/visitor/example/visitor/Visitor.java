package com.lichao666.design_pattern.visitor.example.visitor;

import com.lichao666.design_pattern.visitor.example.shapes.Circle;
import com.lichao666.design_pattern.visitor.example.shapes.CompoundShape;
import com.lichao666.design_pattern.visitor.example.shapes.Dot;
import com.lichao666.design_pattern.visitor.example.shapes.Rectangle;

public interface Visitor {
    public String visitDot(Dot dot);

    public String visitCircle(Circle circle);

    public String visitRectangle(Rectangle rectangle);

    public String visitCompoundGraphic(CompoundShape cg);
}
