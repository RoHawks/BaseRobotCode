package common.encoders.configs;

import common.encoders.configs.interfaces.IEncoderConfig;

public class BaseEncoderConfig implements IEncoderConfig {
    protected final boolean reversed;
    protected final int offset;

    public BaseEncoderConfig(int offset, boolean reversed) {
        this.reversed = reversed;
        this.offset = offset;
    }

    @Override
    public boolean getReversed() {
        return reversed;
    }

    @Override
    public int getOffset() {
        return offset;
    }
}