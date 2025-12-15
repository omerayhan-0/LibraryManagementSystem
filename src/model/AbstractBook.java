package model;

public abstract class AbstractBook {
    protected int id;
    protected String title;
    protected String author;

    public abstract String getCategory();
}
