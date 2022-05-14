package za.ac.iie.opsc.firebaseprac;

public class User {
//this is where all the users details will be stored.
public String username, userSurname, email;

    //Create a constructor
    public User(){}

    //This will store the username, userSurname, and userEmail.
    public User(String username, String userSurname, String email)
    {
        this.username = username;
        this.userSurname = userSurname;
        this.email = email;
    }
}
