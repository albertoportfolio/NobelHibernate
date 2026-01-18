package hibernateClasses;

public class Writers {

    private int id;
    private String author;
    private int awardYear;
    private String bio;
    private String reason;

    public Writers() {
    }

    public Writers(int id, String author, int awardYear, String bio, String reason) {
        this.id = id;
        this.author = author;
        this.awardYear = awardYear;
        this.bio = bio;
        this.reason = reason;
    }

    public Writers(String author, int awardYear, String reason, String bio) {
        this.author = author;
        this.awardYear = awardYear;
        this.reason = reason;
        this.bio = bio;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public int getAwardYear() {
        return awardYear;
    }

    public String getBio() {
        return bio;
    }

    public String getReason() {
        return reason;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAwardYear(int awardYear) {
        this.awardYear = awardYear;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Writers{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", awardYear=" + awardYear +
                ", bio='" + bio + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
