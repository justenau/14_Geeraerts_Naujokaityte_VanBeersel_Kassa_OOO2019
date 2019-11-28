package database;

public class LoadSaveFactory {

    private static LoadSaveFactory instance;

    private LoadSaveFactory(){}

    public static LoadSaveFactory getInstance(){
        if (instance==null){
            instance = new LoadSaveFactory();
        }
        return instance;
    }

    public  LoadSaveStrategy getLoadSaveStrategy(LoadSaveEnum loadSaveEnum){
        return null;
    }
}
