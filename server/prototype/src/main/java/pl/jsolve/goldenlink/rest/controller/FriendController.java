package pl.jsolve.goldenlink.rest.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.jsolve.goldenlink.rest.dto.Friend;

import com.google.common.collect.Lists;

@RestController
public class FriendController {

	@RequestMapping(value = "/friends", method = RequestMethod.GET)
	public List<Friend> getFriends() {

		List<Friend> friends = Lists.newArrayList();
		for (int i = 0; i < 50; i++) {
			boolean isGroup = isGroup();
			friends.add(new Friend(generateId(), "Znajomy nr " + (i + 1),
					thumbnail(), isGroup ? null : sex(), numberOfSharedLinks(), isGroup));
		}
		return friends;
	}

	private static String generateId() {
		return UUID.randomUUID().toString().substring(0, 32);
	}

	private static String thumbnail() {
		int random = (int) (Math.random() * 3);
		if (random == 0) {
			return "https://avatars0.githubusercontent.com/u/1587788?v=3&s=460";
		} else if (random == 1) {
			return "https://avatars3.githubusercontent.com/u/1387716?v=3&s=460";
		} else {
			return null;
		}
	}

	private static String sex() {
		int random = (int) (Math.random() * 2);
		if (random == 0) {
			return "M";
		} else {
			return "F";
		}
	}

	private static boolean isGroup() {
		int random = (int) (Math.random() * 10);
		if (random < 3) {
			return true;
		}
		return false;
	}

	private static Integer numberOfSharedLinks() {
		return (int) (Math.random() * 5000);
	}
}
