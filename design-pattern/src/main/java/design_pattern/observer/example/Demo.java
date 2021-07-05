package design_pattern.observer.example;

import design_pattern.observer.example.listeners.EmailNotificationListener;
import design_pattern.observer.example.listeners.LogOpenListener;
import design_pattern.observer.example.editor.Editor;

public class Demo {
    public static void main(String[] args) {
        Editor editor = new Editor();
        editor.events.subscribe("open", new LogOpenListener("/path/to/log/file.txt"));
        editor.events.subscribe("save", new EmailNotificationListener("admin@example.com"));

        try {
            editor.openFile("test.txt");
            editor.saveFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
