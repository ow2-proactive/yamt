package org.ow2.proactive.smart.watch.notificators;

import com.espertech.esper.client.UpdateListener;
import org.ow2.proactive.smart.watch.model.Notification;

public interface SmartWatchNotificator extends UpdateListener {

	public Notification getNotificatorType();
}
