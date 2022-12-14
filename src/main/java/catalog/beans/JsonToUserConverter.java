package catalog.beans;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author dominicwood - ddwood2@dmacc.edu
 * CIS175 - Fall 2022
 * Nov 17, 2022
 */
@Component
public class JsonToUserConverter implements Converter<String, User> {
	@Override
	public User convert(String json){
		ObjectMapper mapper = new ObjectMapper();
		try {
			User user = mapper.readValue(json, User.class);
			return user;
		}catch(Exception e) {
			return null;
		}
	}
}
