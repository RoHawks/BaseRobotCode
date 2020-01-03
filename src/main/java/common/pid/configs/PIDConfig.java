package common.pid.configs;

import common.pid.configs.interfaces.IPIDConfig;

public class PIDConfig implements IPIDConfig {
    protected final double p;
    protected final double i;
    protected final double d;

    public PIDConfig(double p, double i, double d) {
        this.p = p;
        this.i = i;
        this.d = d;
    }

    public double getP() {
        return p;
    }

    public double getI() {
        return i;
    }

    public double getD() {
        return d;
    }
}