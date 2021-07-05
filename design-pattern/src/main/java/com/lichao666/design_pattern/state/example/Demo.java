package com.lichao666.design_pattern.state.example;

import com.lichao666.design_pattern.state.example.ui.Player;
import com.lichao666.design_pattern.state.example.ui.UI;

/**
 * EN: Demo class. Everything comes together here.
 *
 * RU: Демо-класс. Здесь всё сводится воедино.
 */
public class Demo {
    public static void main(String[] args) {
        Player player = new Player();
        UI ui = new UI(player);
        ui.init();
    }
}
