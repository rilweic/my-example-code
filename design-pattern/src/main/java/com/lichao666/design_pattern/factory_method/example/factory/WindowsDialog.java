package com.lichao666.design_pattern.factory_method.example.factory;

import com.lichao666.design_pattern.factory_method.example.buttons.Button;
import com.lichao666.design_pattern.factory_method.example.buttons.WindowsButton;

/**
 * EN: Windows Dialog will produce Windows buttons.
 *
 * RU: Диалог на элементах операционной системы.
 */
public class WindowsDialog extends Dialog {

    @Override
    public Button createButton() {
        return new WindowsButton();
    }
}
