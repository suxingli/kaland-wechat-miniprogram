package com.kaland.wechat.miniprogram.factory;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * 日志工厂
 * @author 苏行利
 * @date 2019-10-09 09:49:18
 */
public class LoggerFactory {
	private static Logger logger; // 日志记录器

	/**
	 * 获取日志记录器
	 * @author 苏行利
	 * @return 日志记录器
	 * @date 2019-10-09 09:50:48
	 */
	public static final Logger getLogger() {
		if (logger == null) {
			logger = LogManager.getLogger("com.kaland.wechat.miniprogram");
			logger.setLevel(Level.DEBUG);
			logger.setAdditivity(false);
			PatternLayout console_layout = new PatternLayout();
			console_layout.setConversionPattern("[kaland-wechat-miniprogram] %d{yyyy-MM-dd HH:mm:ss,SSS} %p %l - %m%n");
			ConsoleAppender console_appender = new ConsoleAppender(console_layout);
			console_appender.activateOptions();
			PatternLayout file_layout = new PatternLayout();
			file_layout.setConversionPattern("[kaland-wechat-miniprogram] %d{yyyy-MM-dd HH:mm:ss,SSS} %p %l - %m%n");
			DailyRollingFileAppender appender = new DailyRollingFileAppender();
			appender.setLayout(file_layout);
			appender.setFile("../logs/kaland-wechat-miniprogram/kaland-wechat-miniprogram.log");
			appender.setDatePattern("'.'yyyy-MM-dd'.log'");
			appender.setEncoding("UTF-8");
			appender.activateOptions();
			logger.addAppender(console_appender);
			logger.addAppender(appender);
		}
		return logger;
	}

}
