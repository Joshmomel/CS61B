package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private double factor;

    public AcceleratingSawToothGenerator(int period, double factor) {
        this.period = period;
        this.factor = factor;
        this.state = 0;
    }

    @Override
    public double next() {
        if (state < period) {
            state++;
            return (double) state / (period / 2) - 1;
        }
        state = 0;
        state++;
        period = (int) Math.floor(period * factor);
        return (double) state / (period / 2) - 1;

    }

}
