package com.lichao666.design_pattern.decorator.example.decorators;

public interface DataSource {
    void writeData(String data);

    String readData();
}
