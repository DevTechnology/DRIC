package com.devtechnology.api.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Enum used to represent and create the Open FDA filter for 'report_date'
 * @author jbnimble
 */
public enum FdaReportDateFilter {
	ONEMONTH,
	SIXMONTH,
	ALL;
	
	public String getFilter() {
		String f = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Calendar today = Calendar.getInstance();
		String todayStr = formatter.format(today.getTime());
		if (this == ONEMONTH) {
			Calendar oneMonth = Calendar.getInstance();
			oneMonth.add(Calendar.MONTH, -1);
			String oneMonthStr = formatter.format(oneMonth.getTime());
			f = "report_date:["+oneMonthStr+"+TO+"+todayStr+"]";
		} else if (this == SIXMONTH) {
			Calendar sixMonth = Calendar.getInstance();
			sixMonth.add(Calendar.MONTH, -6);
			String sixMonthStr = formatter.format(sixMonth.getTime());
			f = "report_date:["+sixMonthStr+"+TO+"+todayStr+"]";
		}
		return f;
	}
}
