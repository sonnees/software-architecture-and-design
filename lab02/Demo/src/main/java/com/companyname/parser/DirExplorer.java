package com.companyname.parser;

import java.io.File;
import java.util.Objects;

public class DirExplorer {
    public interface FileHandler {
        void handle(int level, String path, File file);
    }
    public interface Filter {
        boolean interested(int level, String path, File file);
    }


    private final FileHandler fileHandler;
    private final Filter filter;


    public DirExplorer(Filter filter, FileHandler fileHandler) {
        this.filter = filter;
        this.fileHandler = fileHandler;
    }

    public void explore(File root) {
        explore(0, "", root);
    }

    private void explore(int level, String path, File file) {
        if (file.isDirectory()) {
            for (File child : Objects.requireNonNull(file.listFiles())) {
                explore(level + 1, path + "/" + child.getName(), child);
            }
        } else {
            if (filter.interested(level, path, file)) {
                fileHandler.handle(level, path, file);
            }
        }
    }
}
