package com.github.oxaoo.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class IOFile {
    private final static Logger log = Logger.getLogger(IOFile.class.getName());

    public static String read(String filename) {
        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            StringBuilder sb = new StringBuilder();
            String line = null;

            do {
                line = br.readLine();
                sb.append(line);
                sb.append(System.lineSeparator());
            } while (line != null);

            return sb.toString();
        } catch (IOException e) {
            log.error("Failed to read a text file: [" + e + "]");
            return "";
        }
    }
}
