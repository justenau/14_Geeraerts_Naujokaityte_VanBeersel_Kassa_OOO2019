package database;

/**
 * @author Justė Naujokaitytė
 */
public class LoadSaveFactory {

    private static LoadSaveFactory instance;

    private LoadSaveFactory() {
    }

    public static LoadSaveFactory getInstance() {
        if (instance == null) {
            instance = new LoadSaveFactory();
        }
        return instance;
    }

    public LoadSaveStrategy createLoadSaveStrategy(String loadSaveType) {
        LoadSaveEnum loadSaveEnum = LoadSaveEnum.valueOf(loadSaveType);
        String loadSaveClassName = loadSaveEnum.getClassName();
        LoadSaveStrategy loadSaveStrategy = null;
        try {
            Class strategyClass = Class.forName(loadSaveClassName);
            loadSaveStrategy = (LoadSaveStrategy) strategyClass.newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return loadSaveStrategy;
    }
}
