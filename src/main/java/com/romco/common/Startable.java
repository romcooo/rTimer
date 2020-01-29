package com.romco.common;

public interface Startable {
    boolean start();
    boolean stop();
    boolean pause();
    boolean reset();
}