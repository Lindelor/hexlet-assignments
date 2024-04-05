package exercise;

import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CompletableFuture;
import java.nio.file.Paths;
import java.nio.file.Files;

class App {

    public static CompletableFuture<String> unionFiles(String srcPath1, String srcPath2, String dstPath) {
        CompletableFuture<String> content1 = readFile(srcPath1);
        CompletableFuture<String> content2 = readFile(srcPath2);

        CompletableFuture<String> result = content1.thenCombine(content2, (str1, str2) -> {
            String content = str1 + str2;
            try {
                Files.writeString(Paths.get(dstPath), content, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return content;
        }).exceptionally(ex -> {
            System.out.println(ex.getMessage());
            return null;
        });

        return result;
    }

    public static void main(String[] args) throws Exception {
        CompletableFuture<String> result = unionFiles(
                "src/main/resources/file1.txt",
                "src/main/resources/file2.txt",
                "src/main/resources/file3.txt"
        );

        result.get();

    }

    public static CompletableFuture<Long> getDirectorySize(String path) {
        CompletableFuture<Long> result = null;
            result = CompletableFuture.supplyAsync(() -> {
                try {
                    return Files.walk(Paths.get(path), 1)
                            .filter(file -> !Files.isDirectory(file.getFileName()))
                            .reduce(0L, (subtotal, element) -> {
                                try {
                                    return subtotal + Files.size(element);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }, Long::sum);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        return result;
    }

    private static CompletableFuture<String> readFile(String path) {
        return CompletableFuture.supplyAsync(() -> {
            String result = null;
            try {
                result = Files.readString(Paths.get(path));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            return result;
        });
    }
}

