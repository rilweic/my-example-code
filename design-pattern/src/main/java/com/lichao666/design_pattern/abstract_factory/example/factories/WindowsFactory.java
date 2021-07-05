package com.lichao666.design_pattern.abstract_factory.example.factories;

import com.lichao666.design_pattern.abstract_factory.example.buttons.Button;
import com.lichao666.design_pattern.abstract_factory.example.checkboxes.WindowsCheckbox;
import com.lichao666.design_pattern.abstract_factory.example.checkboxes.Checkbox;
import com.lichao666.design_pattern.abstract_factory.example.buttons.WindowsButton;

/**
 * EN: Each concrete factory extends basic factory and responsible for creating
 * products of a single variety.
 *
 */
public class WindowsFactory implements GUIFactory {

    @Override
    public Button createButton() {
        return new WindowsButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}
