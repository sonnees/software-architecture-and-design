package com.companyname.test;

import com.companyname.parser.DirExplorer;

import java.io.File;
/**
 * @author sonnees
 * @since 2024-01-23
 */
public class TestDirExplorer {
    /*
        test DirExplorer
     */
    public static void main(String[] args) {
        File projectDir = new File("../Demo/src/main/java/com/companyname/sample");

        new DirExplorer(
                (level, path, file) -> path.endsWith(".java"),
                (level, path,file) -> System.out.println(path)
        ).explore(projectDir);

    }
}
