package name.ealen.global.advice.log.collector;

import name.ealen.global.advice.log.GloLog;

/**
 * @author EalenXie Created on 2020/1/6 13:25.
 */
public class EmptyLogCollector implements LogCollector {
    @Override
    public void collect(GloLog gloLog) {
        //empty log collector do nothing
    }
}
