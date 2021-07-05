package com.lichao666.design_pattern.abstract_factory.example.factories;

import com.lichao666.design_pattern.abstract_factory.example.buttons.Button;
import com.lichao666.design_pattern.abstract_factory.example.checkboxes.Checkbox;

/**
 * EN: Abstract factory knows about all (abstract) product types.
 *
 */
public interface GUIFactory {
    public abstract Button createButton();
    public abstract Checkbox createCheckbox();
}
