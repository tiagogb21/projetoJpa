package UserTest;

import UserManager.DAO;
import UserModel.User;

public class Test {
	public static void main(String[] args) {
		User admin = new User(null, "admin", "admin@gmail.com", "secret_admin");
		User user = new User(null, "user", "user@gmail.com", "secret_user");
		DAO<User> daoUser = new DAO<User>(User.class);
		
		daoUser.open()
			.create(admin)
			.create(user)
			.close();

//		daoUser.update(2, "newUser", "newUser@gmail.com", "secret_newuser");
		
//		daoUser.abrir()
//		.delete(1)
//		.fechar();
	}
}
