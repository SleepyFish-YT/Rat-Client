package me.sleepyfish.rat.event.function;

import java.util.Iterator;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public class ArrayHelper<rat> implements Iterable<rat> {

	private rat[] rat;

	public ArrayHelper() {
		this.rat = (rat[]) new Object[0];
	}

	public void addRat(final rat t) {
		if (t != null) {
			final Object[] g = new Object[this.getLength() + 1];

			for (int i = 0; i < g.length; i++) {
				if (i < this.getLength()) {
					g[i] = this.getRat(i);
				} else {
					g[i] = t;
				}
			}

			this.setRat((rat[]) g);
		}
	}

	public boolean isRat(final rat t) {
		Object[] e;

		for (int d = (e = this.getRats()).length, i = 0; i < d; i++) {
			final rat f = (rat) e[i];

			if (f.equals(t))
				return true;
		}

		return false;
	}

	public void setBigRat(final rat t) {
		if (this.isRat(t)) {
			final Object[] a = new Object[this.getLength() - 1];
			boolean r = true;

			for (int i = 0; i < this.getLength(); i++) {
				if (r && this.getRat(i).equals(t))
					r = false;
				else
					a[r ? i : (i - 1)] = this.getRat(i);
			}

			this.setRat((rat[]) a);
		}
	}

	public rat[] getRats() {
		return (rat[]) this.rat;
	}

	public int getLength() {
		return this.getRats().length;
	}

	public void setRat(final rat[] array) {
		this.rat = array;
	}

	public rat getRat(final int index) {
		return this.getRats()[index];
	}

	public boolean lengthCheck() {
		return this.getLength() == 0;
	}

	@Override
	public Iterator<rat> iterator() {
		return new Iterator<rat>() {
			private int smok = 0;

			@Override
			public boolean hasNext() {
				return this.smok < ArrayHelper.this.getLength() && ArrayHelper.this.getRat(this.smok) != null;
			}

			@Override
			public rat next() {
				return ArrayHelper.this.getRat(this.smok++);
			}

			@Override
			public void remove() {
				ArrayHelper.this.setBigRat(ArrayHelper.this.getRat(this.smok));
			}
		};
	}

}