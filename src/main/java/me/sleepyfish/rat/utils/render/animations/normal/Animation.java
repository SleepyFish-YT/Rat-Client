package me.sleepyfish.rat.utils.render.animations.normal;

import me.sleepyfish.rat.utils.misc.TimerUtils;

/**
 * This class is from Rat Client.
 * WARNING: Unauthorized reproduction, skidding, or decompilation of this code is strictly prohibited.
 * @author SleepyFish 2024
 */
public abstract class Animation {

    public TimerUtils timer = new TimerUtils();
    protected int duration;
    protected double endPoint;
    protected Direction direction;

    public Animation(final int duration, final double endPoint) {
        this.duration = duration;
        this.endPoint = endPoint;
        this.direction = Direction.FORWARDS;
    }

    public Animation(final int ms, final double endPoint, final Direction direction) {
        this.duration = ms;
        this.endPoint = endPoint;
        this.direction = direction;
    }

    public boolean isDone(final Direction direction) {
        return this.isDone() && this.direction.equals(direction);
    }

    public double getLinearOutput() {
        return 1.0 - (double) this.timer.getTime() / (double) this.duration * this.endPoint;
    }

    public void reset() {
        this.timer.reset();
    }

    public boolean isDone() {
        return this.timer.delay(this.duration);
    }

    protected boolean correctOutput() {
        return false;
    }

    public double getValue() {
        if (this.direction == Direction.FORWARDS) {
            return this.isDone() ? this.endPoint : this.getEquation((double) this.timer.getTime()) * this.endPoint;
        } else if (this.isDone()) {
            return 0.0;
        } else {
            return this.correctOutput() ? this.getEquation((double) Math.min(this.duration, Math.max(0L, (long) this.duration - this.timer.getTime()))) * this.endPoint : (1.0 - this.getEquation((double) this.timer.getTime())) * this.endPoint;
        }
    }

    public float getValueF() {
        return (float) this.getValue();
    }

    public void setValue(final double value) {
        this.endPoint = value;
    }

    protected abstract double getEquation(double var1);

    public double getEndPoint() {
        return this.endPoint;
    }

    public void setEndPoint(final double endPoint) {
        this.endPoint = endPoint;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(final Direction direction) {
        if (this.direction != direction) {
            this.direction = direction;
            this.timer.setTime(System.currentTimeMillis() - ((long) this.duration - Math.min((long) this.duration, this.timer.getTime())));
        }
    }

    public void changeDirection() {
        this.setDirection(this.direction.opposite());
    }
}