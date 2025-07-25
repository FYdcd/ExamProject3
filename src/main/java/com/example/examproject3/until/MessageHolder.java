package com.example.examproject3.until;

import java.util.ArrayList;
import java.util.List;

public class MessageHolder {
    private static final ThreadLocal<List<String>> messages = ThreadLocal.withInitial(ArrayList::new);

    public static void addMessage(String message) {
        messages.get().add(message);
    }

    public static List<String> getMessages() {
        return new ArrayList<>(messages.get());
    }

    public static void clearMessages() {
        messages.get().clear();
    }
}
