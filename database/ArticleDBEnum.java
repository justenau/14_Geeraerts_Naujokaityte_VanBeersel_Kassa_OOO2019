package database;

public enum ArticleDBEnum {
    IN_MEMORY("database.ArticleDBInMemory"),
    SQL("database.ArticleDBSQL");

    private final String className;

    ArticleDBEnum(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
