package lock.file.lockerinfo.model;

public class DataModel {
    String key;
    String title;
    String description;
    String date;
    String time;

    public DataModel() {
    }

    public DataModel( String key, String title, String description, String date, String time) {
       this.key = key;
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
