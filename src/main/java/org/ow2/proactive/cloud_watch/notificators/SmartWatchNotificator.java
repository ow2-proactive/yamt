package org.ow2.proactive.cloud_watch.notificators;

import com.espertech.esper.client.UpdateListener;
import org.ow2.proactive.cloud_watch.model.Notification;

public interface SmartWatchNotificator extends UpdateListener {

	public Notification getNotificatorType();
}
