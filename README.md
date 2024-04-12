# Exercises 8
- Sinh viên: **Nguyễn Văn Sơn**
- Hướng dẫn: **TS. Võ Văn Hải**

## Demo Code
### The Microkernel Architecture Style - Dictionary basic

https://github.com/sonnees/software-architecture-and-design/assets/110987763/91cb9c73-96a1-46ce-bd1d-9fa81f71ac51

## Mô Tả Các Thức Thực Hiện:
### Sơ đồ Tree
```
root
--EnglishLanguage
--VietnameseLanguage
--Language
src
build.gradle
settings.gradle
```

- `EnglishLanguage, VietnameseLanguage` được xem như `Plugin` cần thì gắn vào để sử dụng
- `Plugin` quan trọng nhất là file dictionary.txt
- `Language` có hàm quan trọng là `public Map<String, String> readDictionary(String filePath)`. func này muốn gọi cần có `Plugin` vì cần truyền file `dictionary.txt` vào










