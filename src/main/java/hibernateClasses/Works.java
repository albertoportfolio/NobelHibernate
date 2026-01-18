package hibernateClasses;

public class Works {

    private int id;
    private int authorId;
    private String title;
    private int year;

    public Works() {
    }

    public Works(int id, int authorId, String title, int year) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.year = year;
    }

    public Works(int authorId, String title, int year) {
        this.authorId = authorId;
        this.title = title;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Works{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", title='" + title + '\'' +
                ", year=" + year +
                '}';
    }
}
