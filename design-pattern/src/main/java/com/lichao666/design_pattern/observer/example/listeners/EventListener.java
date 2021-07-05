package com.lichao666.design_pattern.observer.example.listeners;

import java.io.File;

public interface EventListener {
    public void update(String eventType, File file);
}
