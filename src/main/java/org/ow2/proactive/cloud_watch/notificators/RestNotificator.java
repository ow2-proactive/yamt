package org.ow2.proactive.cloud_watch.notificators;

import com.espertech.esper.client.EventBean;
import org.ow2.proactive.cloud_watch.model.Notification;
import org.springframework.stereotype.Component;

@Component
public class RestNotificator implements SmartWatchNotificator {

//	@Autowired
//	private ElesticityRestClient elesticityRestClient;

	@Override
	public void update(EventBean[] arg0, EventBean[] arg1) {
//		elesticityRestClient.sendNotification("proactiveRule", arg0[0].getUnderlying().toString());
	}

	@Override
	public Notification getNotificatorType() {
		return Notification.REST;
	}

}
