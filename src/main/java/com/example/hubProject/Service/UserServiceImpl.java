package com.example.hubProject.Service;

import com.example.hubProject.Commons.ApiResponse;
import com.example.hubProject.DTO.UserEmailDTO;
import com.example.hubProject.DTO.UserDto;
import com.example.hubProject.Model.User;
import com.example.hubProject.Repository.UserDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService {

	private UUID corrId;


	@Autowired
	private UserDao userDao;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByEmail(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user.getUserType()));
	}

	private List<SimpleGrantedAuthority> getAuthority(String role) {
		return Arrays.asList(new SimpleGrantedAuthority(role));
	}

	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	public void delete(Long id) {
		userDao.deleteById(id);
	}

	public User findOne(String username) {
		return userDao.findByEmail(username);
	}

	public User findById(Long id) {
		Optional<User> optionalUser = userDao.findById(id);
		return optionalUser.isPresent() ? optionalUser.get() : null;
	}

    public UserDto update(UserDto userDto, Long id) {
        User user = findById(id);
        if(user != null) {
            BeanUtils.copyProperties(userDto, user, "password");
            userDao.save(user);
        }
        return userDto;
    }

    public ApiResponse save(UserDto user) {
		User founduser = userDao.findByEmail(user.getEmail());
		if(founduser == null) {
			User newUser = new User();
			newUser.setEmail(user.getEmail());

			newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
			newUser.setUserType(user.getUserType());
			newUser.setActive(user.getActive());
			return new ApiResponse<>(HttpStatus.OK.value(), "User saved successfully.",	userDao.save(newUser));//return ;
		}else{
			return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), "User Already exsist.",null);//return ;
		}

    }

    public String forgotPassword(UserEmailDTO userEmailDTO)
	{
		User user = userDao.findByEmail(userEmailDTO.getEmail());
		String randomId=UUID.randomUUID().toString();
		if(user == null){
			return "{\"Invalid Username\":1}";
		}
		else
		{
			user.setPassword(bcryptEncoder.encode(randomId));
			userDao.save(user);
			return "{\"Password send to your email\":1}";
		}

	}

	public String postNewUser(UserEmailDTO userEmailDTO)
	{

		User userObj=userDao.findByEmail(userEmailDTO.getEmail().toLowerCase());
		if(userObj==null) {

			String password = UUID.randomUUID().toString();

			User user = new User(userEmailDTO.getEmail(), "", bcryptEncoder.encode(password), true, userEmailDTO.getUserType());

			userDao.save(user);

			return "{\"New user created\":1}";
		}

		else {
			return "{\"User already present\":1}";
		}

	}

	public String updateUser(Long id,UserDto user)
	{
		Optional<User> obj=userDao.findById(id);
		System.out.println(user.getUserType()+"========================>>>>>>>>>>>>>>");
		System.out.println(user.getEmail()+"==============>>>>>");
		if(obj.isPresent())
		{
			User responseUser=obj.get();
			responseUser.setUserType(user.getUserType());
			userDao.save(responseUser);
			return "{\"User updated Successfully\":1}";

		}
		else
			return "{\"An Error occured while updating the user\":1}";

	}

}
