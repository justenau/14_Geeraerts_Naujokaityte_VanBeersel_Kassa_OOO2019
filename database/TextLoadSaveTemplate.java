package database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class  TextLoadSaveTemplate implements LoadSaveStrategy {

    public final ArrayList load() throws IOException {
        ArrayList collection;
        List<String> fileLines;
        fileLines = readFile();
        collection = convertToObjects(fileLines);
        return collection;
    }

    public abstract void save(ArrayList objects) throws FileNotFoundException;
    public abstract List<String> readFile() throws IOException;
    public abstract ArrayList convertToObjects(List<String> fileContent);



}
