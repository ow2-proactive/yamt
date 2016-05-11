package org.ow2.proactive.cloud_watch.notificators;

import com.espertech.esper.client.UpdateListener;
import org.ow2.proactive.cloud_watch.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class NotificatorMapping {

	private Map<Notification, SmartWatchNotificator> notificatorMap;

	@Autowired
	public NotificatorMapping(List<SmartWatchNotificator> notificators) {
		notificatorMap = notificators.stream()
				.collect(Collectors.toMap(SmartWatchNotificator::getNotificatorType, Function.identity()));
	}

	public Optional<UpdateListener> getUpdateListener(Notification notification) {
		return Optional.ofNullable(notificatorMap.get(notification));

	}

}
