package cn.appsys.dao.devuser;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;

/**
 * 
 * 开发者用户映射接口
 * @author whj
 *
 */
public interface DevUserMapper {

	/**
	 * 根据用户编码获取用户信息
	 * @param devCode
	 * @return
	 */
	DevUser getLoginUser(@Param("devCode")String devCode)throws Exception;
	/**
	 * 查询条件
	 */
	List<DataDictionary> getDictionary(@Param("typeCode")String typeCode)throws Exception;
	
	/**
	 * 分类
	 */
	List<AppCategory> getType(@Param("parentId")int parentId)throws Exception;
	/**
	 * 查询
	 * 
	 */
	List<AppInfo> getappinfolistselect(@Param("appInfo")AppInfo appinfo,@Param("currentPageNo")int currentPageNo,@Param("pageSize")int pageSize)throws Exception;
	/**
	 * 总数
	 */
	int count(@Param("appInfo")AppInfo appinfo);
}
