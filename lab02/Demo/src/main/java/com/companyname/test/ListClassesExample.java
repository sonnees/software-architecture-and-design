package com.companyname.test;

import com.companyname.parser.DirExplorer;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;

public class ListClassesExample {
    public static void listClasses(File projectDir) {
        new DirExplorer(
                (level, path, file) -> path.endsWith(".java"),
                (level, path, file) -> {
                    System.out.println(path);
                    try {
                        new VoidVisitorAdapter<Object>() {
                            @Override
                            public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                                super.visit(n, arg);
                                System.out.println(" * " + n.getName());
                            }

                        }.visit(StaticJavaParser.parse(file), null);
                        System.out.println();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        ).explore(projectDir);
    }

    public static void main(String[] args) {
        File projectDir = new File("../Demo/src/main/java/com/companyname/sample");
        listClasses(projectDir);
    }
}