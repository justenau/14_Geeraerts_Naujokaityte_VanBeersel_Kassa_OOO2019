package database;

public enum LoadSaveEnum {
    EXCEL("database.ArticleExcelLoadSave"),
    TEXT("database.ArticleTextLoadSave");

    private final String className;

    LoadSaveEnum(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
