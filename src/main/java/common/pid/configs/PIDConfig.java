package common.pid.configs;

import common.pid.configs.interfaces.IPIDConfig;

public class PIDConfig implements IPIDConfig {
    protected final double p;
    protected final double i;
    protected final double d;
    protected final double iZone;

    public PIDConfig(double p, double i, double d, double iZone) {
        this.p = p;
        this.i = i;
        this.d = d;
        this.iZone = iZone;
    }

    @Override
    public double getP() {
        return p;
    }

    @Override
    public double getI() {
        return i;
    }

    @Override
    public double getD() {
        return d;
    }

    @Override
    public double getIZone() {
        return iZone;
    }
}