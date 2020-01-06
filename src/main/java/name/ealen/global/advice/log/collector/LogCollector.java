package name.ealen.global.advice.log.collector;

import name.ealen.global.advice.log.GloLog;

/**
 * @author EalenXie Created on 2020/1/6 13:21.
 * 此接口主要是提供日志收集
 */
public interface LogCollector {
    void collect(GloLog gloLog) throws Exception;
}
