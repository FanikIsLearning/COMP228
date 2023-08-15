package game;

public class PlayerAndGame {
	  // Declare fields to store player information
	    Integer id;
	    String name;
	    String address;
	    String postalCode;
	    String province;
	    String phoneNumber;
	    // Declare fields to store game-related information
	    String gameTitle;
	    Integer score;
	    String datePlayed;
	    // Default constructor (empty constructor)
	    PlayerAndGame(){}

	 // Parameterized constructor to initialize the fields with values
	    public PlayerAndGame(Integer id, String name, String address, String postalCode, String province, String phoneNumber, String gameTitle, Integer score, String datePlayed) {
	        this.id = id;
	        this.name = name;
	        this.address = address;
	        this.postalCode = postalCode;
	        this.province = province;
	        this.phoneNumber = phoneNumber;
	        this.gameTitle = gameTitle;
	        this.score = score;
	        this.datePlayed = datePlayed;
	    }
	    
	    // Getters and setters for each field

	    public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getAddress() {
	        return address;
	    }

	    public void setAddress(String address) {
	        this.address = address;
	    }

	    public String getPostalCode() {
	        return postalCode;
	    }

	    public void setPostalCode(String postalCode) {
	        this.postalCode = postalCode;
	    }

	    public String getProvince() {
	        return province;
	    }

	    public void setProvince(String province) {
	        this.province = province;
	    }

	    public String getPhoneNumber() {
	        return phoneNumber;
	    }

	    public void setPhoneNumber(String phoneNumber) {
	        this.phoneNumber = phoneNumber;
	    }

	    public String getGameTitle() {
	        return gameTitle;
	    }

	    public void setGameTitle(String gameTitle) {
	        this.gameTitle = gameTitle;
	    }

	    public Integer getScore() {
	        return score;
	    }

	    public void setScore(Integer score) {
	        this.score = score;
	    }

	    public String getDatePlayed() {
	        return datePlayed;
	    }

	    public void setDatePlayed(String datePlayed) {
	        this.datePlayed = datePlayed;
	    }
}
