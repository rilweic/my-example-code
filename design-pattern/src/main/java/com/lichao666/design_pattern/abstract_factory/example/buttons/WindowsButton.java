package com.lichao666.design_pattern.abstract_factory.example.buttons;

/**
 * EN: All products families have the same varieties (MacOS/Windows).
 *
 * This is another variant of a button.
 *
 */
public class WindowsButton implements Button {

    @Override
    public void paint() {
        System.out.println("You have created WindowsButton.");
    }
}
