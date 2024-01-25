# Exercises 2
- Sinh viên: **Nguyễn Văn Sơn**
- Hướng dẫn: **TS. Võ Văn Hải**

## Demo Code

https://github.com/sonnees/software-architecture-and-design/assets/110987763/26be8ed8-81ea-4441-85f9-69715ef5859c

## Detail Code
#### 1. Các package trong dự án phải theo mẫu: com.companyname.* (*:tên bất kỳ) 
```
private static boolean checkNamePackage(String name) {
        return name.matches("^com\\.companyname\\.[a-z]+$");
    }
```
#### 2. Các class phải có tên là một danh từ hoặc cụm danh ngữ và phải bắt đầu bằng chữ hoa. 
```
private static boolean checkNameClass(String name) {
        return name.charAt(0) >= 'A' && name.charAt(0) <= 'Z';
    }
```
#### 3. Mỗi lớp phải có một comment mô tả cho lớp. Trong comment đó phải có ngày tạo (created-date) và author. 
```
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
```
#### 4. Các fields trong các class phải là danh từ hoặc cụm danh ngữ và phải bắt đầu bằng một chữ thường. 
```
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
```
#### 5. Tất cả các hằng số phải là chữ viết hoa và phải nằm trong một interface. 
```
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
```
#### 6. Tên method phải bắt đầu bằng một động từ và phải là chữ thường
```
private static boolean checkMethod(MethodDeclaration n) {
        return n.getName().asString().charAt(0)>='a' && n.getName().asString().charAt(0)<='z';
    }
```
#### 7. Mỗi method phải có một ghi chú mô tả cho công việc của method trừ phương thức default constructor, accessors/mutators, hashCode, equals, toString.
```
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
```








