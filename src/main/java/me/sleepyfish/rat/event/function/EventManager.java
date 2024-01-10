package me.sleepyfish.rat.event.function;

import java.util.*;
import java.util.Map.Entry;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class EventManager {

	private final Map<Class<?>, ArrayHelper<EventHelp>> events;

	public EventManager() {
		this.events = new ConcurrentHashMap<>();
	}

	public void register(Object target) {
		for (final Method rat : target.getClass().getDeclaredMethods())
			if (!this.invalidCheck(rat))
				this.register(rat, target);
	}

	public void unregister(final Object object) {
		for (final ArrayHelper<EventHelp> rat : this.events.values())
			for (final EventHelp eventHelp : rat)
				if (eventHelp.obj.equals(object))
					rat.setBigRat(eventHelp);

		this.remove(true);
	}

	@SuppressWarnings("unchecked")
	private void register(Method method, Object target) {
		final Class<?> eventClass = method.getParameterTypes()[0];
		final EventHelp eventHelp = new EventHelp(target, method, method.getAnnotation(RatEvent.class).value());
		eventHelp.meth.setAccessible(true);

		events.computeIfAbsent(eventClass, k -> new ArrayHelper<>()).addRat(eventHelp);
		this.addPriority((Class<? extends Event>) eventClass);
	}

	public void remove(boolean b) {
		events.entrySet().removeIf(entry -> b && entry.getValue().lengthCheck());
	}

	private void addPriority(Class<? extends Event> c) {
		PriorityQueue<EventHelp> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(eventHelp -> eventHelp.byta));

		for (final EventHelp rAt : this.events.getOrDefault(c, new ArrayHelper<>()))
			priorityQueue.add(rAt);

		final ArrayHelper<EventHelp> sortedEventHelp = new ArrayHelper<>();
		while (!priorityQueue.isEmpty())
			sortedEventHelp.addRat(priorityQueue.poll());

		this.events.put(c, sortedEventHelp);
	}

	private boolean invalidCheck(final Method method) {
		return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(RatEvent.class);
	}

	public ArrayHelper<EventHelp> getEvent(final Class<? extends Event> clazz) {
		return events.get(clazz);
	}

}