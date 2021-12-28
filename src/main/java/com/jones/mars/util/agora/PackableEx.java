package com.jones.mars.util.agora;

public interface PackableEx extends Packable {
    void unmarshal(ByteBuf in);
}
