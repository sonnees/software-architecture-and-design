package com.companyname.test;

import com.companyname.parser.DirExplorer;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.javadoc.JavadocBlockTag;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ListClassesExample {
    public static void listClasses(File projectDir) {
        new DirExplorer(
                (level, path, file) -> path.endsWith(".java"),
                (level, path, file) -> {
                    try {
                        new VoidVisitorAdapter<Object>() {
                            /*
                                1. Các package trong dự án phải theo mẫu: com.companyname.* (*:tên bất kỳ)
                             */
                            @Override
                            public void visit(PackageDeclaration n, Object arg) {
                                super.visit(n, arg);
                                if(!CheckNamePackage(n.getName().asString()))
                                    System.out.println("* Invalid 1 principle: "+ path );
                            }

                            /*
                                2. Các class phải có tên là một danh từ hoặc cụm danh ngữ và phải bắt đầu bằng chữ hoa.
                             */
                            @Override
                            public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                                super.visit(n, arg);

                                if(!checkFieldFinal(n))
                                    System.out.println("* Invalid 5 principle: "+ path);

                                if(!CheckNameClass(n.getName().asString()))
                                    System.out.println("* Invalid 2 principle: "+ path );
                            }

                            /*
                                3. Mỗi lớp phải có một comment mô tả cho lớp. Trong comment đó phải có ngày tạo (created-date) và author.
                             */
                            @Override
                            public void visit(CompilationUnit n, Object arg) {
                                super.visit(n, arg);
                                if(!CheckComment(n))
                                    System.out.println("* Invalid 3 principle: "+ path);
                            }

                            /*
                                4. Các fields trong các class phải là danh từ hoặc cụm danh ngữ và phải bắt đầu bằng một chữ thuong
                             */
                            @Override
                            public void visit(FieldDeclaration n, Object arg) {
                                super.visit(n, arg);
                                if(!CheckField(n))
                                    System.out.println("* Invalid 4 principle: "+ path);

                            }


                        }.visit(StaticJavaParser.parse(file), null);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        ).explore(projectDir);
    }

    private static boolean checkFieldFinal(ClassOrInterfaceDeclaration n) {
        List<FieldDeclaration> fields = n.getFields();
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);

        fields.forEach(f->{
            if(f.isFinal())
                if(!n.isInterface()) atomicBoolean.set(false);
        });

        return atomicBoolean.get();
    }

    private static boolean CheckField(FieldDeclaration n) {
        AtomicBoolean result = new AtomicBoolean(true);
        n.getVariables().forEach(i-> {
            if(!(i.toString().charAt(0) >= 'a' && i.toString().charAt(0) <= 'z' ))
                result.set(false);
        });
        return result.get();
    }

    private static boolean CheckComment(CompilationUnit n) {
        NodeList<TypeDeclaration<?>> types = n.getTypes();
        Optional<JavadocComment> javadocComment = types.removeLast().getJavadocComment();
        if(javadocComment.isEmpty())
            return false;
        AtomicInteger i = new AtomicInteger();
        javadocComment.get().parse().getBlockTags().forEach(t->{
            if(t.getType() == JavadocBlockTag.Type.AUTHOR) i.getAndIncrement();
            if(t.getType() == JavadocBlockTag.Type.SINCE) i.getAndIncrement();
        });

        return i.get()==2;
    }


    private static boolean CheckNamePackage(String name) {
        return name.matches("^com\\.companyname\\.[a-z]+$");
    }

    private static boolean CheckNameClass(String name) {
        return name.charAt(0) >= 'A' && name.charAt(0) <= 'Z';
    }


    public static void main(String[] args) {
        File projectDir = new File("../");
        System.out.println("#### START REPORT #####");
        listClasses(projectDir);
        System.out.println("#### END REPORT #####");
    }
}