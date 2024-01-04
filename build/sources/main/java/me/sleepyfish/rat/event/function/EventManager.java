package me.sleepyfish.rat.event.function;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.lang.reflect.Method;

public class EventManager {

	private Map<Class<?>, ArrayHelper<EventHelp>> events;

	public EventManager() {
		this.events = new HashMap<>();
	}

	public void uninject() {
		this.events = null;
	}

	public void register(Object target) {
		for (Method rat : target.getClass().getDeclaredMethods())
			if (!this.invalidCheck(rat))
				this.register(rat, target);
	}

	@SuppressWarnings("unchecked")
	private void register(Method method, Object target) {
		Class<?> rat = method.getParameterTypes()[0];
		final EventHelp eventHelp = new EventHelp(target, method, method.getAnnotation(RatEvent.class).value());

		if (!eventHelp.meth.isAccessible())
			eventHelp.meth.setAccessible(true);

		if (this.events.containsKey(rat)) {
			if (!this.events.get(rat).isRat(eventHelp)) {
				this.events.get(rat).addRat(eventHelp);
				this.addPriority((Class<? extends Event>) rat);
			}
		} else
			this.events.put(rat, new ArrayHelper<EventHelp>() {{ this.addRat(eventHelp); }} );
	}

	public void unregister(final Object object) {
		for (ArrayHelper<EventHelp> rat : this.events.values())
			for (EventHelp eventHelp : rat)
				if (eventHelp.obj.equals(object))
					rat.setBigRat(eventHelp);

		this.remove(true);
	}

	public void remove(boolean b) {
		Iterator<Entry<Class<?>, ArrayHelper<EventHelp>>> rat = this.events.entrySet().iterator();

		while (rat.hasNext())
			if (!b || rat.next().getValue().lengthCheck())
				rat.remove();
	}

	private void addPriority(Class<? extends Event> c) {
		ArrayHelper<EventHelp> eventHelp = new ArrayHelper<>();

		for (byte b : EventPriority.PRIORITY_ARRAY)
			for (EventHelp rAt : this.events.get(c))
				if (rAt.byta == b)
					eventHelp.addRat(rAt);

		this.events.put(c, eventHelp);
	}

	private boolean invalidCheck(final Method method) {
		return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(RatEvent.class);
	}

	public ArrayHelper<EventHelp> getEvent(final Class<? extends Event> clazz) {
		return events.get(clazz);
	}

}