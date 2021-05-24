package com.single.jpaProjct.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GeoUtil {
	private final static Logger logger = LoggerFactory.getLogger(GeoUtil.class);

	private static double RE = 6371.00877; // 지구 반경(km)
	private static double GRID = 5.0; // 격자 간격(km)
	private static double SLAT1 = 30.0; // 투영 위도1(degree)
	private static double SLAT2 = 60.0; // 투영 위도2(degree)
	private static double OLON = 126.0; // 기준점 경도(degree)
	private static double OLAT = 38.0; // 기준점 위도(degree)
	private static double XO = 43; // 기준점 X좌표(GRID)
	private static double YO = 136; // 기1준점 Y좌표(GRID)

	public Map<String, String> dfs_xy_conv(String code, String sv1, String sv2) {
		logger.info("geoutil 파라미터 code={},sv1={},sv2={}", code, sv1, sv2);

		double v1 = Double.parseDouble(sv1);
		double v2 = Double.parseDouble(sv2);
		double DEGRAD = Math.PI / 180.0;
		double RADDEG = 180.0 / Math.PI;

		double re = RE / GRID;
		double slat1 = SLAT1 * DEGRAD;
		double slat2 = SLAT2 * DEGRAD;
		double olon = OLON * DEGRAD;
		double olat = OLAT * DEGRAD;

		double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
		sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
		
		double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
		sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
		
		double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
		ro = re * sf / Math.pow(ro, sn);
		
		Map<String, String> rs = new HashMap<String, String>();
		
		if (code.equals("toXY")) {
			logger.info("code={}",code);
			rs.put("lat", v1 + "");
			rs.put("lng", v2 + "");
			double ra = Math.tan(Math.PI * 0.25 + (v1) * DEGRAD * 0.5);
			ra = re * sf / Math.pow(ra, sn);
			double theta = v2 * DEGRAD - olon;
			if (theta > Math.PI)
				theta -= 2.0 * Math.PI;
			if (theta < -Math.PI)
				theta += 2.0 * Math.PI;
			theta *= sn;
			double x = Math.floor(ra * Math.sin(theta) + XO + 0.5);
			double y = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
			logger.info("x={}, y={}",x,y);
			int xx=(int) x;
			int yy=(int) y;
			logger.info("x={}, y={}",x,y);
			rs.put("x", Integer.toString(xx));
			rs.put("y", Integer.toString(yy));
		} else if(code=="toLanLog"){
			double theta = 0.0;
			rs.put("x", v1 + "");
			rs.put("y", v2 + "");
			double xn = v1 - XO;
			double yn = ro - v2 + YO;
			double ra = Math.sqrt(xn * xn + yn * yn);
			// if (sn < 0.0) - ra;
			ra = Math.abs(ra);
			double alat = Math.pow((re * sf / ra), (1.0 / sn));
			alat = 2.0 * Math.atan(alat) - Math.PI * 0.5;

			if (Math.abs(xn) <= 0.0) {
				theta = 0.0;
			} else {
				if (Math.abs(yn) <= 0.0) {
					theta = Math.PI * 0.5;
					// if (xn < 0.0) - theta;
					theta = Math.abs(theta);
				} else
					theta = Math.atan2(xn, yn);
			}
			double alon = theta / sn + olon;
			double lat = alat * RADDEG;
			double lng = alon * RADDEG;
			rs.put("lat", lat + "");
			rs.put("lng", lng + "");
		}
		return rs;
	}
	
	
	public Map<String, String> getStandardHour(Date date) {
		Map<String, String> map=new HashMap<String, String>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2=new SimpleDateFormat("HHmm");
		//날짜
		String currentDay=sdf.format(date);
		//시간
		String currentHour=sdf2.format(date);
		int cHour=Integer.parseInt(currentHour);
		
		if(cHour>0 && cHour<210) {
			Calendar cal=Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DATE, -1);
			currentDay=sdf.format(cal.getTime());
			currentHour="2300";
			map.put("currentDay", currentDay);
			map.put("currentHour", currentHour);
		}else if(cHour>=210 && cHour<510) {
			currentHour="0200";
			map.put("currentDay", currentDay);
			map.put("currentHour", currentHour);
		}else if(cHour>=510 && cHour<810) {
			currentHour="0500";
			map.put("currentDay", currentDay);
			map.put("currentHour", currentHour);
		}else if(cHour>=810 && cHour<1110) {
			currentHour="0800";
			map.put("currentDay", currentDay);
			map.put("currentHour", currentHour);
		}else if(cHour>=1110 && cHour<1410) {
			currentHour="1100";
			map.put("currentDay", currentDay);
			map.put("currentHour", currentHour);
		}else if(cHour>=1410 && cHour<1710) {
			currentHour="1400";
			map.put("currentDay", currentDay);
			map.put("currentHour", currentHour);
		}else if(cHour>=1710 && cHour<2010) {
			currentHour="1700";
			map.put("currentDay", currentDay);
			map.put("currentHour", currentHour);
		}else if(cHour>=2010 && cHour<2310) {
			currentHour="2000";
			map.put("currentDay", currentDay);
			map.put("currentHour", currentHour);
		}
		
		
		
		return map;
	}

}
