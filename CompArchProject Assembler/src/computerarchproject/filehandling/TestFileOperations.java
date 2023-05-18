package computerarchproject.filehandling;

import java.util.ArrayList;
import java.util.List;

class TestFileOperations {
    public static void main(String[] args) {
        String readFile = "src/main/java/computerarchproject/filehandling/test reading.txt";
        String writeFile = "src/main/java/computerarchproject/filehandling/test writing.txt";
        testReading(readFile);
        testWriting(writeFile);
    }

    private static void testReading(String readFile) {
        String fileContent = """
                Hello this is a dummy text for the sake of testing

                Lorem ipsum dolor sit amet, consectetuer adipiscing elit.
                Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et
                magnis dis parturient montes, nascetur ridiculus mus.
                Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem.
                Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec,
                vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae,
                justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus.
                Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula,\s
                porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis,
                feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet.
                Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi.
                Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus,
                sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc,\s
                blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt
                tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros\s
                faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat,
                leo eget bibendum sodales, augue velit cursus nunc.

                I'm the last line!!
                """;

        List<String> resultOfReading = FileHandler.read(readFile);
        assert resultOfReading.equals(List.of(fileContent.split(" *\n")));
        assert resultOfReading.get(resultOfReading.size() - 1).equals("I'm the last line!!");
        assert resultOfReading.size() == 21;
        try {
            FileHandler.read("dummy.dummy");
            System.out.println("File not found is not handled");
        } catch (Exception ignored) {
        }

        System.out.println("All tests passed.");
    }

    private static void testWriting(String writeFile) {
        FileHandler.write(writeFile, new ArrayList<>(List.of("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam", " rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.", "Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui")));

        assert FileHandler.read(writeFile).equals(List.of("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam", " rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.", "Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui"));
        try {
            FileHandler.write(writeFile, null);
            FileHandler.write("dummy.txt", new ArrayList<>());
            System.out.println("Exceptions is not handled.");
        } catch (Exception ignored) {
        }

        System.out.println("All tests passed.");
    }
}
