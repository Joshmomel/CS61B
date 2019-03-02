package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private int period;
    private int state;

    public SawToothGenerator(int period) {
        this.state = 0;
        this.period = period;
    }

    private double normalize(int toConvert) {
        return (double) toConvert;
    }

    @Override
    public double next() {
        double s = state % this.period;

        double gradient = 2.0 / this.period;
        System.out.println(gradient);
        double out = gradient * s - 1;

        state += 1;
        return out;

    }
}
