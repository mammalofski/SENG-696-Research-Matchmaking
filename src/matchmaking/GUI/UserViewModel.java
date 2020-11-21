package matchmaking.GUI;

public class UserViewModel {
	public String name;
	public String email;
	public String userName;
	public String password;
	public Boolean validated;
	public int hourlyCompensation;
	public String specialKeywords;
	public String logo;
	public String website;
	public String cv;
	
	public UserViewModel(String name1, String email1, String userName1, String password1, Boolean validated1,
			int hourlyCompensation1, String specialKeywords1, String logo1, String website1, String cv1) {
		name = name1;
		email = email1;
		userName = userName1;
		password = password1;
		validated = validated1;
		hourlyCompensation = hourlyCompensation1;
		specialKeywords = specialKeywords1;
		logo = logo1;
		website = website1;
		cv = cv1;
	}
}
