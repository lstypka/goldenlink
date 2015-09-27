package pl.jsolve.goldenlink.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.jsolve.goldenlink.rest.dto.Notification;
import pl.jsolve.goldenlink.rest.dto.Notifications;
import pl.jsolve.goldenlink.rest.service.NotificationService;

@RestController
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@RequestMapping(value = "/notifications", method = RequestMethod.GET)
	public Notifications getNotifications(@RequestParam("page") final Integer page, @RequestParam("resultsPerPage") final Integer resultsPerPage) {
		return notificationService.getNotifications(page, resultsPerPage);
	}

	@RequestMapping(value = "/notifications/{publicId}", method = RequestMethod.GET)
	public Notification getNotification(@PathVariable("publicId") String publicId) {
		return notificationService.getNotification(publicId);
	}

	@RequestMapping(value = "/notifications/{notificationPublicId}", method = RequestMethod.DELETE)
	public Void deleteNotification(@PathVariable("notificationPublicId") final String notificationPublicId) {
		notificationService.deleteNotification(notificationPublicId);
		return null;
	}

}
