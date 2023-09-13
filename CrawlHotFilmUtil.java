package com.bookmarkchina.module.supervision.util;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.utils.DateUtils;

import com.bookmarkchina.base.util.JSONUtils;
import com.bookmarkchina.module.supervision.bean.HotResource;
import com.github.kevinsawicki.http.HttpRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 
 * 
 * @author hui
 * 
 */
public class CrawlHotFilmUtil{

	
	public static List<HotResource> getCboooData(){

		List<HotResource> list =new ArrayList<HotResource>();
		try{
			String url="https://ys.endata.cn/enlib-api/api/home/getrank_singleday.do";
			HttpRequest httpRequest = new HttpRequest(url, "POST");
			httpRequest.connectTimeout(3000);
			httpRequest.readTimeout(3000);
			String result=httpRequest.body();
			if(result==null){return null;}

			JSONObject jObject=JSONUtils.toJSONObject(result);
			JSONArray jArray= jObject.getJSONObject("data").getJSONArray("table0");
			for(int i=0;jArray!=null&&i<jArray.size();i++){
				try{
					
					HotResource  hotResource =new HotResource();
					JSONObject json =jArray.getJSONObject(i);

					String name=json.getString("MovieName");
					if("其它".equals(name)){continue;}
					
					hotResource.setName(name);
					hotResource.setUpdateTime(DateUtils.parseDate(json.getString("InsertDate")));
				
					list.add(hotResource);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<HotResource> getMaoyanData(){

		List<HotResource> list =new ArrayList<HotResource>();
		try{
			String url="https://piaofang.maoyan.com/dashboard-ajax?orderType=0";
			HttpRequest httpRequest = new HttpRequest(url, "GET");
			httpRequest.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36");
			httpRequest.connectTimeout(3000);
			httpRequest.readTimeout(3000);
			String result=httpRequest.body();
			if(result==null){return null;}

			JSONObject jObject=JSONUtils.toJSONObject(result);
			JSONArray jArray= jObject.getJSONObject("movieList").getJSONObject("data").getJSONArray("list");
			for(int i=0;jArray!=null&&i<jArray.size();i++){
				try{
					
					HotResource  hotResource =new HotResource();
					JSONObject json =jArray.getJSONObject(i);

					String name=json.getJSONObject("movieInfo").getString("movieName");
					if("其它".equals(name)){continue;}
					
					hotResource.setName(name);
					hotResource.setUpdateTime(new Date());
				
					list.add(hotResource);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<HotResource> getMTimeData(){

		List<HotResource> list =new ArrayList<HotResource>();
		try{
			String url="http://front-gateway.mtime.com/ticket/schedule/showing/movies.api?locationId=290";
			HttpRequest httpRequest = new HttpRequest(url, "GET");
			httpRequest.connectTimeout(3000);
			httpRequest.readTimeout(3000);
			String result=httpRequest.body();
			if(result==null){return null;}

			JSONObject jObject=JSONUtils.toJSONObject(result);
			JSONArray jArray= jObject.getJSONObject("data").getJSONArray("ms");
			for(int i=0;jArray!=null&&i<jArray.size();i++){
				try{
					
					HotResource  hotResource =new HotResource();
					JSONObject json =jArray.getJSONObject(i);

					String name=json.getString("t");
					if("其它".equals(name)){continue;}
					
					hotResource.setName(name);
					hotResource.setUpdateTime(DateUtils.parseDate(json.getString("rd")));
				
					list.add(hotResource);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	
	public static void main(String[] args)throws Exception {
		System.out.println(getCboooData());
		System.out.println(getMaoyanData());
		System.out.println(getMTimeData());
	}
}
