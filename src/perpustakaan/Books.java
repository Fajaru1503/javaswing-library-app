package perpustakaan;

public class Books {
    private int id;
    private String title;
    private String author;
    private String year;
    private String type;
    
    public Books(int from_id, String from_title, String from_author, String from_year, String from_type)
    {
        this.id = from_id;
        this.title = from_title;
        this.author = from_author;
        this.year = from_year;
        this.type = from_type;
    }
    
    public int getId()
    {
        return id;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public String getAuthor()
    {
        return author;
    }
    
    public String getYear(){
        return year;
    }
    
    public String getType(){
        return type;
    }
}
