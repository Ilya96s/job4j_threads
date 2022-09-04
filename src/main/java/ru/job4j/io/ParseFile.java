package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.function.Predicate;

/**
 * Visibility. Общий ресурс вне критической секции
 *
 * @author Ilya Kaltygin
 */
public final class ParseFile {
    private static final Logger LOG = LoggerFactory.getLogger(ParseFile.class.getName());
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized String getContent() {
        return  content(data -> data > 0);
    }

    public synchronized String getContentWithoutUnicode() {
        return content(data -> data < 0x80);
    }

    public synchronized String content(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            LOG.error("IOException", e);
        }
        return output.toString();
    }
}
