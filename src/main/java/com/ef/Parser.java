package com.ef;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import xyz.vtools.Blocked;
import xyz.vtools.HelpCoreDB;

/**
 * The Class Parser.
 */
public class Parser {

	/**
	 * The main method.
	 *
	 * @param sarg
	 *            the arguments
	 */
	public static void main(String[] args) {

		Logger logger = Logger.getLogger("Test");

		Options options = new Options();

		Option accesslog = new Option("l", "accesslog", true, "Full path log file");
		accesslog.setRequired(false);
		options.addOption(accesslog);

		Option startDate = new Option("s", "startDate", true, "is of \"yyyy-MM-dd.HH:mm:ss\" format");
		startDate.setRequired(true);
		options.addOption(startDate);

		Option duration = new Option("d", "duration", true, "can take only [hourly|daily]");
		duration.setRequired(true);
		options.addOption(duration);

		Option threshold = new Option("t", "threshold", true, "as inputs and \"threshold\" can be an integer");
		threshold.setRequired(true);
		options.addOption(threshold);

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd = null;

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("parse tools", options);
			System.exit(1);
		}
		String saccesslog = cmd.getOptionValue("accesslog");
		String sstartDate = cmd.getOptionValue("startDate");
		String sduration = cmd.getOptionValue("duration");
		String sthreshold = cmd.getOptionValue("threshold");

		if (saccesslog != null) {
			File fileLog = new File(saccesslog);
			if (!fileLog.exists()) {
				System.out.println("File not found");
				System.exit(1);
			} else {
				System.out.println(">>>Begin insert data to MySQL");
				try {
					HelpCoreDB.excuteSQLQuery("TRUNCATE logrecords", null);

					String me = "LOAD DATA LOCAL INFILE '" + saccesslog + "' REPLACE INTO TABLE `logrecords" + "`\n"
							+ "FIELDS TERMINATED BY \'|\'\n" + "ENCLOSED BY \'\"\'\n" + "ESCAPED BY		 \'\\\\\'\n"
							+ "LINES TERMINATED BY \'\\r\\n\'(\n" + "`startDate` ,\n" + "`IP` ,\n" + "`request` ,\n"
							+ "`status` ,\n" + "`useragent`\n" + ")";
					HelpCoreDB.excuteSQLQuery(me, null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("End insert data to MySQL<<<");
			}
		}

		if (sduration == null || (!sduration.equals("hourly") && !sduration.equals("daily"))) {
			System.out.println("can take only [hourly|daily]");
			System.exit(1);
		}

		Date dstartDate = null;
		if (sstartDate == null) {
			System.out.println("sstartDate is not null");
			System.exit(1);
		} else {
			try {
				dstartDate = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss").parse(sstartDate);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("sstartDate is of \"yyyy-MM-dd.HH:mm:ss\" format");
				System.exit(1);
			}

		}
		int ithreshold = 0;
		if (sthreshold == null) {
			System.out.println("sthreshold is not null");
			System.exit(1);
		} else {
			try {
				ithreshold = Integer.valueOf(sthreshold);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("as inputs and \"threshold\" can be an integer");
				System.exit(1);
			}

		}

		Calendar calendarDuration = Calendar.getInstance();
		calendarDuration.setTimeInMillis(dstartDate.getTime());
		calendarDuration.add(Calendar.HOUR, sduration.equals("hourly") ? 1 : 24);

		Date tDate = calendarDuration.getTime();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sDate", dstartDate);
		params.put("tDate", tDate);
		params.put("ithreshold", Long.valueOf(ithreshold));

		String sQuery = "SELECT ip as ip, count(ip) as count " + " FROM Logrecords where"
				+ " startDate between :sDate and :tDate" + " group by ip having count(ip) > :ithreshold";
		try {
			List<Map<String, Object>> datas = HelpCoreDB.findByMapQuery(sQuery, params, 0, 0);

			if (datas != null) {
				List<Blocked> blockeds = new ArrayList<Blocked>();

				for (Map<String, Object> map : datas) {
					System.out.println("IP: " + map.get("ip") + " -> count: " + map.get("count"));
					Blocked blocked = new Blocked(map.get("ip").toString(), new Date(),
							"This IP made more than " + map.get("count") + " requests starting from "
									+ new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss").format(dstartDate) + " to "
									+ new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss").format(tDate)
									+ ". This IP has exceeded the allowable threshold " + ithreshold);
					blockeds.add(blocked);
				}

				HelpCoreDB.saveList(blockeds);
			}

		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Finished!");

		// System.out.println(saccesslog);
		// System.out.println(dstartDate);
		// System.out.println(sduration);
		// System.out.println(ithreshold);

	}
}
