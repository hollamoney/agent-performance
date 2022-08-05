package com.agent.agentperformance;

import static org.assertj.core.api.Assertions.assertThat;

import com.agent.agentperformance.entities.Role;
import com.agent.agentperformance.entities.User;
import com.agent.agentperformance.entities.UserPerformance;
import com.agent.agentperformance.repos.UserPerformanceRepository;
import com.agent.agentperformance.repos.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired private UserRepository repo;

	@Autowired private UserPerformanceRepository performanceRepository;
	
	@Test
	public void testCreateUser() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = passwordEncoder.encode("hollamoney");
		
		User newUser = new User("hollamoney", password);
		User savedUser = repo.save(newUser);
		
		assertThat(savedUser).isNotNull();
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	@Test
	public void testAssignRoleToUser() {
		Long userId = Long.valueOf(2);
		Long roleId = Long.valueOf(3);
		User user = repo.findById(userId).get();
		user.addRole(new Role(roleId));

		User updatedUser = repo.save(user);
		assertThat(updatedUser.getRoles()).hasSize(1);

	}


}
