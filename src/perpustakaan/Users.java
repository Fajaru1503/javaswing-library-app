package perpustakaan;

public class Users {
    private int id;
    private String username;
    private String name;
    private String nim;
    
    public Users(int from_id, String from_username, String from_name, String from_nim)
    {
        this.id = from_id;
        this.username = from_username;
        this.name = from_name;
        this.nim = from_nim;
    }
    
    public int getId()
    {
        return id;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getNim(){
        return nim;
    }
}
