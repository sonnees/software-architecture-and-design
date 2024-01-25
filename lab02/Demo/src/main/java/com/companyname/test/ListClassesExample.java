package com.companyname.test;

import com.companyname.parser.DirExplorer;
import com.companyname.valueglobal.ValueGlobal;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.javadoc.JavadocBlockTag;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * @author sonnees
 * @since 2024-01-23
 */
public class ListClassesExample {
    static int totalError = 0;
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
                                if(!checkNamePackage(n.getName().asString())){
                                    System.out.println("* Invalid 1 principle: "+ n.getName().asString());
                                    totalError++;
                                }
                            }

                            /*
                                2. Các class phải có tên là một danh từ hoặc cụm danh ngữ và phải bắt đầu bằng chữ hoa.
                                5. Tất cả các hằng số phải là chữ viết hoa và phải nằm trong một interface
                                7. Mỗi method phải có một ghi chú mô tả cho công việc của method trừ phương thức default constructor, accessors/mutators, hashCode, equals, toString
                             */
                            @Override
                            public void visit(ClassOrInterfaceDeclaration n, Object arg) {
                                super.visit(n, arg);

                                // principle 2
                                if(!checkNameClass(n.getName().asString())){
                                    System.out.println("* Invalid 2 principle: "+ path +" | "+ n.getName());
                                    totalError++;
                                }

                                // principle 5
                                ValueGlobal fieldFinalNotHavePrinciple2 = new ValueGlobal();
                                if(!checkFieldFinal(n,fieldFinalNotHavePrinciple2)){
                                    System.out.println("* Invalid 5 principle: "+ path +" | " +fieldFinalNotHavePrinciple2.toString());
                                    totalError++;
                                }

                                // principle 7
                                ValueGlobal fieldFinalNotHavePrinciple7 = new ValueGlobal();
                                if(!checkMethodComment(n,fieldFinalNotHavePrinciple7)){
                                    System.out.println("* Invalid 7 principle: "+ path + " | " + fieldFinalNotHavePrinciple7.toString());
                                    totalError++;
                                }
                            }

                            /*
                                3. Mỗi lớp phải có một comment mô tả cho lớp. Trong comment đó phải có ngày tạo (created-date) và author.
                             */
                            @Override
                            public void visit(CompilationUnit n, Object arg) {
                                super.visit(n, arg);
                                if(!checkComment(n)){
                                    System.out.println("* Invalid 3 principle: "+path);
                                    totalError++;
                                }
                            }

                            /*
                                4. Các fields trong các class phải là danh từ hoặc cụm danh ngữ và phải bắt đầu bằng một chữ thuong
                             */
                            @Override
                            public void visit(FieldDeclaration n, Object arg) {
                                super.visit(n, arg);
                                ValueGlobal fieldFinalNotHavePrinciple4 = new ValueGlobal();
                                if(!checkField(n,fieldFinalNotHavePrinciple4)){
                                    System.out.println("* Invalid 4 principle: "+ path + " | " + fieldFinalNotHavePrinciple4.toString());
                                    totalError++;
                                }
                            }

                            /*
                                6. Tên method phải bắt đầu bằng một động từ và phải là chữ thuong
                             */
                            @Override
                            public void visit(MethodDeclaration n, Object arg) {
                                super.visit(n, arg);
                                if(!checkMethod(n)){
                                    System.out.println("* Invalid 6 principle: "+ path +" | "+ n.getName());
                                    totalError++;
                                }
                            }
                        }.visit(StaticJavaParser.parse(file), null);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        ).explore(projectDir);
    }

    private static boolean checkMethodComment(ClassOrInterfaceDeclaration n, ValueGlobal valueGlobal) {
        List<MethodDeclaration> methods = n.getMethods();
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        methods.forEach(m->{
            boolean checkMethodType1 = m.getName().asString().equals("equals") || m.getName().asString().equals("toString") || m.getName().asString().equals("hashCode");
            boolean checkMethodType2 = m.getName().asString().equals("<init>") && m.getParameters().isEmpty();
            boolean isHaveComment = m.getComment().isPresent();

            if(!((checkMethodType1 || checkMethodType2) || isHaveComment)){
                atomicBoolean.set(false);
                valueGlobal.add(m.getName().asString());
            }
        });
        return atomicBoolean.get();
    }

    private static boolean checkMethod(MethodDeclaration n) {
        return n.getName().asString().charAt(0)>='a' && n.getName().asString().charAt(0)<='z';
    }

    private static boolean checkFieldFinal(ClassOrInterfaceDeclaration n, ValueGlobal valueGlobal) {

        List<FieldDeclaration> fields = n.getFields();
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        fields.forEach(f->{
            if(f.isFinal())
                if(!n.isInterface()){
                    atomicBoolean.set(false);
                    valueGlobal.add(f.toString());
                }
        });

        return atomicBoolean.get();
    }

    private static boolean checkField(FieldDeclaration n, ValueGlobal valueGlobal) {
        AtomicBoolean result = new AtomicBoolean(true);
        n.getVariables().forEach(i-> {
            if(!(i.toString().charAt(0) >= 'a' && i.toString().charAt(0) <= 'z' )){
                result.set(false);
                valueGlobal.add(i.getNameAsString());
            }
        });
        return result.get();
    }

    private static boolean checkComment(CompilationUnit n) {
        NodeList<TypeDeclaration<?>> types = n.getTypes();
        TypeDeclaration<?> typeDeclaration = types.removeLast();

        if(!typeDeclaration.isClassOrInterfaceDeclaration()) return true;

        Optional<JavadocComment> javadocComment = typeDeclaration.getJavadocComment();
        if(javadocComment.isEmpty())
            return false;
        AtomicInteger i = new AtomicInteger();
        javadocComment.get().parse().getBlockTags().forEach(t->{
            if(t.getType() == JavadocBlockTag.Type.AUTHOR) i.getAndIncrement();
            if(t.getType() == JavadocBlockTag.Type.SINCE) i.getAndIncrement();
        });

        return i.get()==2;
    }

    private static boolean checkNamePackage(String name) {
        return name.matches("^com\\.companyname\\.[a-z]+$");
    }

    private static boolean checkNameClass(String name) {
        return name.charAt(0) >= 'A' && name.charAt(0) <= 'Z';
    }

    public static void main(String[] args) {
        File projectDir = new File("../");
        System.out.println("#### START REPORT #####");
        listClasses(projectDir);
        System.out.println("\nTOTAL: " + totalError);
        System.out.println("#### END REPORT #####");
    }
}