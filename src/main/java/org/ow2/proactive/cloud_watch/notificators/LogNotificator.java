package org.ow2.proactive.cloud_watch.notificators;

import com.espertech.esper.client.EventBean;
import lombok.extern.log4j.Log4j;
import org.ow2.proactive.cloud_watch.model.Notification;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Log4j
@Component
public class LogNotificator implements SmartWatchNotificator {

	@Override
	public void update(EventBean[] arg0, EventBean[] arg1) {
		StringBuilder messageBuilder = new StringBuilder();
		messageBuilder.append("Log Notificator fired with properties : ");
		Arrays.asList(arg0).stream().forEach(eventBean -> messageBuilder.append(eventBean.getUnderlying()));
		log.info(messageBuilder.toString());
	}

	@Override
	public Notification getNotificatorType() {
		return Notification.LOG;
	}

}
