package design_pattern.abstract_factory.example.factories;

import design_pattern.abstract_factory.example.buttons.Button;
import design_pattern.abstract_factory.example.checkboxes.WindowsCheckbox;
import design_pattern.abstract_factory.example.checkboxes.Checkbox;
import design_pattern.abstract_factory.example.buttons.WindowsButton;

/**
 * EN: Each concrete factory extends basic factory and responsible for creating
 * products of a single variety.
 *
 * RU: Каждая конкретная фабрика знает и создаёт только продукты своей вариации.
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
