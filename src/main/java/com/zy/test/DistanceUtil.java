package com.zy.test;

import java.text.DecimalFormat;


/**
 * 经纬度距离运算工具
 * 
 * @author 劳
 * @version 1.0
 * @since 2015-12-16
 * description
 * <p>创建类 -2015-12-16</p> 
 */
public class DistanceUtil {
	
	/**
	 * 地球半径
	 */
	private static double EARTH_RADIUS = 6378137;
	
	private static int DISTANCEUNIT = 1000;
	
	private static DecimalFormat df = new DecimalFormat("#.0");
	
	/**
	 * getRad
	 * 
	 * @param d double
	 * @return double
	 */
	private static double rad(double d){
	   return d * Math.PI / 180.0;
	}


	/**
	 * 获取两点的直线距离
	 * 
	 * @param lat1 double
	 * @param lng1 double
	 * @param lat2 double
	 * @param lng2 double
	 * @return Integer 两点间的距离
	 */
	public static double GetDistance(double lat1, double lng1, double lat2,
			double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		//s = Math.round(s * 10000) / 1000;
		return s;
	}
	
	
	/**
	 * <p>根据经纬度串运算多点经纬度的总里程</p>
	 * 
	 * @param latlonStr   经纬度串
	 * @param split1            分割每隔经纬度的分隔符
	 * @param split2            分割经度跟纬度的分隔符
	 * @param maxDistance 两点间的最大距离
	 * 
	 * @return distanceAll Integer 返回最后运算的距离[开始经纬度至结束经纬度间的距离]
	 */
	public static double GetFilterDistance(String latlonStr, String split1,
			String split2, int maxDistance,int precision) {
		//总里程，开始经度，开始纬度
		double distanceAll = 0;
		int starLat = 0, starlon = 0,invalidlatlon = 0; 
		String[] latlonAry = latlonStr.split(split1);
		// 球两点间的距离
		for (int i = 0; i < latlonAry.length; i++) {
			String[] latlon = latlonAry[i].split(split2);
			if (i == 0) {
				starLat = Integer.parseInt(latlon[1]);
				starlon = Integer.parseInt(latlon[0]);
				continue;
			}
			Integer currentLat = Integer.parseInt(latlon[1]);
			Integer currentLon = Integer.parseInt(latlon[0]);
			double distance = GetDistance(starLat, starlon, currentLat, currentLon);
			if(distance<=0)continue;
			/***/
			// 里程转为km double
			double distance_d_km = Double.parseDouble(String.valueOf(distance));
			//double distance_d_km = Double.parseDouble(String.valueOf(distance))/precision;
			double maxDistance_km = Double.parseDouble(String.valueOf(maxDistance*(invalidlatlon+1)))/DISTANCEUNIT;
			if(distance_d_km>maxDistance_km){//非法距离直接舍弃该点，跳至下一个点
				invalidlatlon++;
				continue;
			}
			//无效经纬度统计重置
			invalidlatlon=0;
			// 里程求和
			distanceAll+=distance;
			starLat = currentLat;
			starlon = currentLon;
		}
		return distanceAll;
	}
	
	/**
	 * 获取两点的直线距离
	 * 
	 * @param lat1 double
	 * @param lng1 double
	 * @param lat2 double
	 * @param lng2 double
	 * @return Integer 两点间的距离
	 */
	public static double GetDistance(int lat1, int lng1, int lat2, int lng2) {
		if(lat1 != 0 && lng1 != 0 && lat2 != 0 && lng2 != 0){
			 double lat1_ = Double.parseDouble(String.valueOf(lat1))/1000000;
			 double lng1_ = Double.valueOf(String.valueOf(lng1))/1000000;
			 double lat2_ = Double.valueOf(String.valueOf(lat2))/1000000;
			 double lng2_ = Double.valueOf(String.valueOf(lng2))/1000000;
			 double distance = GetDistance(lat1_,lng1_,lat2_,lng2_);
			 return distance;
		}
		return 0;
		 //return  Integer.valueOf(String.valueOf(Math.round(distance)));
	}
 
	
	/**
	 * <p>根据经纬度串运算多点经纬度的总里程</p>
	 * 
	 * @param latlonStr   经纬度串
	 * @param split1            分割每隔经纬度的分隔符
	 * @param split2            分割经度跟纬度的分隔符
	 * @param maxDistance 两点间的最大距离
	 * 
	 * @return distanceAll Integer 返回最后运算的距离[开始经纬度至结束经纬度间的距离]
	 */
	public static double GetDistance(String latlonStr, String split1,
			String split2, int maxDistance,int precision) {
		//总里程，开始经度，开始纬度
		double distanceAll = 0;
		int starLat = 0, starlon = 0,invalidlatlon = 0; 
		String[] latlonAry = latlonStr.split(split1);
		// 球两点间的距离
		for (int i = 0; i < latlonAry.length; i++) {
			String[] latlon = latlonAry[i].split(split2);
			if (i == 0) {
				starLat = Integer.parseInt(latlon[1]);
				starlon = Integer.parseInt(latlon[0]);
				continue;
			}
			Integer currentLat = Integer.parseInt(latlon[1]);
			Integer currentLon = Integer.parseInt(latlon[0]);
			double distance = GetDistance(starLat, starlon, currentLat, currentLon);
			
			if(distance<=0)continue;
			// 里程转为km double
			double distance_d_km = Double.parseDouble(String.valueOf(distance))/precision;
			//将最大的点距转为 double
//			double maxDistance_km = Double.parseDouble(String.valueOf(maxDistance*(invalidlatlon+1)))/DISTANCEUNIT;
//			if(distance_d_km>maxDistance_km){//非法距离直接舍弃该点，跳至下一个点
//				invalidlatlon++;
//				continue;
//			}
			//无效经纬度统计重置
			invalidlatlon=0;
			// 里程求和
			distanceAll+=distance;
			starLat = currentLat;
			starlon = currentLon;
		}
		return distanceAll;
	}
	
	public static double GetDistance(String[] points) {
		try{
			String point1 = points[0];
			String point2 = points[points.length - 1];
			if("0".equals(point2.split(",")[1])){
				point2 = points[points.length - 2];
			}
			
			return GetDistance(Double.parseDouble(point1.split(",")[1]) / 1000000,
					Double.parseDouble(point1.split(",")[0]) / 1000000,
					Double.parseDouble(point2.split(",")[1]) / 1000000,
					Double.parseDouble(point2.split(",")[0]) / 1000000);
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	
	public static double Distance(double long1, double lat1, double long2,  
	        double lat2) {  
	    double a, b, R;  
	    R = 6378137; // 地球半径  
	    lat1 = lat1 * Math.PI / 180.0;  
	    lat2 = lat2 * Math.PI / 180.0;  
	    a = lat1 - lat2;  
	    b = (long1 - long2) * Math.PI / 180.0;  
	    double d;  
	    double sa2, sb2;  
	    sa2 = Math.sin(a / 2.0);  
	    sb2 = Math.sin(b / 2.0);  
	    d = 2  
	            * R  
	            * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)  
	                    * Math.cos(lat2) * sb2 * sb2));  
	    return d;  
	}  
	
	public static void main(String[] args){
//		String latlon = "23125589,113328175;23127103,113328078";
//		Integer allDistance = GetDistance(latlon,";",",",700,10);
//		System.out.println(allDistance);
		//System.out.println(Double.parseDouble(String.valueOf(23113689))/1000000);
//		double distance = GetDistance(26901352,106706701,2690137,106703575);
//		double distance = DistanceUtil.Distance(107.536836, 25.852118, 106.567566, 29.481802);
		double distance = DistanceUtil.GetDistance(25.852118, 107.536836, 29.481802, 106.567566);
		System.out.println(distance);
	}
}
