package pl.jsolve.goldenlink.rest.controller;

import java.util.UUID;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.jsolve.goldenlink.rest.dto.Author;
import pl.jsolve.goldenlink.rest.dto.Friend;
import pl.jsolve.goldenlink.rest.dto.Notification;
import pl.jsolve.goldenlink.rest.dto.ShareLink;
import pl.jsolve.goldenlink.rest.service.NotificationService;

@RestController
public class ShareController {

	@Autowired
	private NotificationService notificationService;

	@RequestMapping(value = "/share", method = RequestMethod.POST)
	public ShareLink shareLink(@RequestBody ShareLink shareLink) {

		if (!shareLink.getFriends().isEmpty()) {
			Friend firstFriend = shareLink.getFriends().get(0);
			Notification notification = new Notification(generateId(), shareLink.getLink(), new Author(firstFriend.getPublicId(), firstFriend.getName()), LocalDateTime.now());
			notificationService.addNotification(notification);
		}
		return shareLink;
	}

	private static String generateId() {
		return UUID.randomUUID().toString().substring(0, 32);
	}
}
