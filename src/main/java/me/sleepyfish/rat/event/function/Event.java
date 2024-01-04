package me.sleepyfish.rat.event.function;

import me.sleepyfish.rat.Rat;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public abstract class Event {

	private boolean cancel;

	public Event call() {
		this.cancel = false;
		Event.call(this);
		return this;
	}

	public boolean isCancelled() {
		return this.cancel;
	}

	public void setCancelled(boolean cancelled) {
		this.cancel = cancelled;
	}

	private static void call(Event event) {
		if (Rat.instance.eventManager != null) {
			ArrayHelper<EventHelp> dataList = Rat.instance.eventManager.getEvent(event.getClass());
			if (dataList != null) {
				for (EventHelp data : dataList) {
					try {
						data.meth.invoke(data.obj, event);
					} catch (Exception ignored) {
					}
				}
			}
		}
	}

}