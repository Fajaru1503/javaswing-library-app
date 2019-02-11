package perpustakaan;

public class Transaction {
    private int id;
    private String username;
    private String title;
    private String author;
    private String year;
    private String type;
    private String start_date;
    private String return_date;
    
    public Transaction(int from_id, String from_username, String from_title, String from_author, String from_type, String from_sdate, String from_rdate)
    {
        this.id = from_id;
        this.username = from_username;
        this.title = from_title;
        this.author = from_author;
        this.type = from_type;
        this.start_date = from_sdate;
        this.return_date = from_rdate;
    }
    
    public int getId()
    {
        return id;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public String getAuthor()
    {
        return author;
    }
    
    public String getType()
    {
        return type;
    }
    
    public String getSdate()
    {
        return start_date;
    }
    
    public String getRdate()
    {
        return return_date;
    }
}
