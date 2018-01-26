package cn.appsys.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.tools.PageSupport;

/**
 * 
 * 开发者用户业务层接口
 * @author whj
 *
 */
public interface DevUserService {

	DevUser login(String devCode,String devPassword) throws Exception;
	List<DataDictionary> getDictionary(String typeCode)throws Exception;
	List<AppCategory> getType(int parentId)throws Exception;
	PageSupport getappinfolistselect(AppInfo appinfo,int currentPageNo,int pageSize)throws Exception;
	int count(AppInfo appinfo);
}
