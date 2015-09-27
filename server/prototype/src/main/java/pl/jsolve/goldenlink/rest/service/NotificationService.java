package pl.jsolve.goldenlink.rest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import pl.jsolve.goldenlink.rest.dto.Notification;
import pl.jsolve.goldenlink.rest.dto.Notifications;

import com.google.common.collect.Lists;

@Service
public class NotificationService {

	private List<Notification> notifications = Lists.newArrayList();

	public Notification addNotification(Notification notification) {

		System.out.println("DODALEM NOTYFIKACJE : " + notification.toString());
		notifications.add(notification);
		return notification;
	}

	public Notifications getNotifications(Integer page, Integer resultsPerPage) {

		int totalResults = notifications.size();
		List<Notification> sublist = notifications;
		if (totalResults > resultsPerPage) {
			int fromIndex = page * resultsPerPage;
			int toIndex = (page + 1) * resultsPerPage;
			if (toIndex > notifications.size()) {
				toIndex = notifications.size();
			}
			sublist = notifications.subList(fromIndex, toIndex);
		}
		return new Notifications(sublist, page, resultsPerPage, totalResults);
	}
	
	public Notification getNotification(String publicId) {
		for(Notification notification : notifications) {
			if(notification.getPublicId().equals(publicId)) {
				return notification;
			}
		}
		return null;
	}

	public void deleteNotification(String publicId) {
		for (int i = 0; i < notifications.size(); i++) {
			if (notifications.get(i).getPublicId().equals(publicId)) {
				notifications.remove(i);
			}
		}
	}
}
