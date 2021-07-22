package org.amdocs.elearning.user.service;

import java.time.LocalDate;
import java.util.Optional;

import org.amdocs.elearning.user.service.user.User;
import org.amdocs.elearning.user.service.user.UserController;
import org.amdocs.elearning.user.service.user.UserDetails;
import org.amdocs.elearning.user.service.user.UserService;
import org.amdocs.elearning.user.service.user.UserType;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

public class UserControllerTest {

	UserService userService = Mockito.mock(UserService.class);
	final UserController controller = new UserController(userService);

	@Test
	public void getUserById_Match() {

		Mockito.when(userService.getUserById(Mockito.anyString())).thenReturn(Optional
				.of(new User("id", "firstName", "lastName", "middleInitial", UserType.PATRON, LocalDate.now())));

		final ResponseEntity<User> responseEntity = this.controller.getUser("id");
		Assert.assertEquals(200, responseEntity.getStatusCodeValue());
		Assert.assertNotNull(responseEntity.getBody());
	}

	@Test
	public void getUserById_NoMatch() {
		Mockito.when(userService.getUserById(Mockito.anyString())).thenReturn(Optional.empty());
		final ResponseEntity<User> responseEntity = this.controller.getUser("id");
	}

	@Test
	public void createUser() {

		final UserDetails createRequestUser = new UserDetails("test", "test", "t", UserType.PATRON, LocalDate.now());
		final User createdUser = new User("1", "test", "test", "t", UserType.PATRON, LocalDate.now());

		Mockito.when(userService.createUser(Mockito.any())).thenReturn(createdUser);
		final ResponseEntity<User> responseEntity = this.controller.createUser(createRequestUser);

		Assert.assertEquals(201, responseEntity.getStatusCodeValue());
		Assert.assertEquals(createdUser, responseEntity.getBody());
	}

	@Test
	public void updateUser() {
		final UserDetails updateRequestUser = new UserDetails("test", "test", "t", UserType.PATRON, LocalDate.now());
		final User updatedUser = new User("1", "test", "test", "t", UserType.PATRON, LocalDate.now());

		Mockito.when(userService.updateUser(Mockito.anyString(), Mockito.any())).thenReturn(updatedUser);
		final ResponseEntity<User> responseEntity = this.controller.updateUser("1", updateRequestUser);

		Assert.assertEquals(200, responseEntity.getStatusCodeValue());
		Assert.assertEquals(updatedUser, responseEntity.getBody());
	}

	@Test
	public void deleteUser() {

		final User user = new User("1", "test", "test", "t", UserType.PATRON, LocalDate.now());

		Mockito.when(userService.getUserById(Mockito.anyString())).thenReturn(Optional.of(user));

		final ResponseEntity responseEntity = this.controller.deleteUser("1");
		Assert.assertEquals(204, responseEntity.getStatusCodeValue());
		Assert.assertNull(responseEntity.getBody());
	}

}
