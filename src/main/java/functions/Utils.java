package functions;

import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.IOException;

public class Utils {
    public String readJson(String path){
        String json;
        try {
            json = Files.readString(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public static List<ArrayList<String>> getColour(String colour){
        List<ArrayList<String>> listColour = new ArrayList<>();
        ArrayList<String> a = new ArrayList<>();
        a.add(colour);
        listColour.add(a);
        return listColour;
    }

    public static ArrayList<String> getListColour(String colour1, String colour2){
        ArrayList <String> listColour = new ArrayList<>();
        listColour.add(colour1);
        listColour.add(colour2);
        return listColour;
    }
}