package com.lichao666.design_pattern.factory_method.example.factory;

import com.lichao666.design_pattern.factory_method.example.buttons.Button;
import com.lichao666.design_pattern.factory_method.example.buttons.HtmlButton;

/**
 * EN: HTML Dialog will produce HTML buttons.
 *
 * RU: HTML-диалог.
 */
public class HtmlDialog extends Dialog {

    @Override
    public Button createButton() {
        return new HtmlButton();
    }
}
