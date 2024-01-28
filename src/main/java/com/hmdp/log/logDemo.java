package com.hmdp.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class logDemo {
    public static void main(String[] args)
    {
        Logger logger = LogManager.getLogger(logDemo.class);
        logger.trace("trace level");
        logger.debug("debug level");
        logger.info("info level");
        logger.warn("warn level");
        logger.error("error level");
        logger.fatal("fatal level");
    }
}
