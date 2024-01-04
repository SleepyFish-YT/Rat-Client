package me.sleepyfish.rat.event.function;

import java.lang.reflect.Method;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author Nexuscript 2024
 */
public class EventHelp {

	public final Object obj;
	public final Method meth;
	public final byte byta;

	public EventHelp(Object obj, Method meth, byte byta) {
		this.obj = obj;
		this.meth = meth;
		this.byta = byta;
	}

}