package com.lichao666.design_pattern.abstract_factory.example.app;

import com.lichao666.design_pattern.abstract_factory.example.buttons.Button;
import com.lichao666.design_pattern.abstract_factory.example.checkboxes.Checkbox;
import com.lichao666.design_pattern.abstract_factory.example.factories.GUIFactory;

/**
 * EN: Factory users don't care which concrete factory they use since they work
 * with factories and products through abstract interfaces.
 *
 */
public class Application {
    private Button button;
    private Checkbox checkbox;

    public Application(GUIFactory factory) {
        button = factory.createButton();
        checkbox = factory.createCheckbox();
    }

    public void paint() {
        button.paint();
        checkbox.paint();
    }
}
