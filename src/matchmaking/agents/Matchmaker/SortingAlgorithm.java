package matchmaking.agents.Matchmaker;

import java.util.Comparator;

import matchmaking.orm.User;

public class SortingAlgorithm implements Comparator<User> {

	@Override
	public int compare(User user1, User user2) {
		if (user1.getAccountType() > user2.getAccountType())
			return -1;
		else if (user2.getAccountType() > user1.getAccountType())
			return 1;
		
		if (user1.getValidate() == true && user2.getValidate() == false)
			return -1;
		else if (user2.getValidate() == true && user1.getValidate() == false)
			return 1;
		
		return 0;
	}

}
