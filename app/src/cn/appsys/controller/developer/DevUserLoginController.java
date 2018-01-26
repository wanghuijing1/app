package cn.appsys.controller.developer;

import java.util.List;

import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.DevUserService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;

@Controller
public class DevUserLoginController {

	private Logger logger=Logger.getLogger(DevUserLoginController.class);
	@Resource
	private DevUserService devUserService;
	/**
	 * 用户登录
	 * @return
	 */
	@RequestMapping(value="/login.do")
	public String login(){
		logger.info("进入登陆页面==================================》");
		return "login";
	}
	@RequestMapping(value="/devlogin.do")
	public String devlogin(){
		logger.info("进入开发者平台登陆页面============================》");
		return "devlogin";
	}
	@RequestMapping(value="/devlogin2.do",method=RequestMethod.GET)
	public String devlogin2(@RequestParam(value="devCode",required=false) String devCode,@RequestParam(value="devPassword",required=false) String devPassword,HttpSession session,HttpServletRequest request){
		logger.info("进入开发者平台页面============================》");
		try {
			DevUser devuser=devUserService.login(devCode, devPassword);
			if (null!=devuser) {//登陆成功
				session.setAttribute(Constants.USER_SESSION,devuser);
				
				
				return "/developer/main";
			}else{
				//登陆失败
				logger.info("用户名或密码不正确============================》");
				request.setAttribute("error", "用户名或密码不正确");
				return "/devlogin";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/developer/main";
	}
	@RequestMapping(value="logout.do")
	public String logout(HttpSession session){
		logger.info("退出============================》");
		session.removeAttribute(Constants.USER_SESSION);
		return "devlogin";
	}
	@RequestMapping(value="appinfolist.do",method=RequestMethod.GET)
	public String appinfolist(HttpServletRequest request){
		logger.info("进入APP维护============================》");
		List<DataDictionary> statusList;
		List<DataDictionary> flatFormList;
		List<AppCategory> categoryLevel1List;
		try {
			//app状态
			statusList = devUserService.getDictionary("APP_STATUS");
			request.setAttribute("statusList", statusList);
//			for (int i = 0; i < statusList.size(); i++) {
//				logger.info(statusList.get(i).getValueName());
//			}
			//所属平台
			flatFormList=devUserService.getDictionary("APP_FLATFORM");
			request.setAttribute("flatFormList", flatFormList);
			//一级分类
			categoryLevel1List=devUserService.getType(0);
			request.setAttribute("categoryLevel1List", categoryLevel1List);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/developer/appinfolist";
	}
	//二级菜单
	@ResponseBody
	@RequestMapping(value="categorylevellist.do")
	public String categorylevellist(@RequestParam("pid")int pid) throws Exception{
		return JSONArray.toJSONString(devUserService.getType(pid));
	}
	//新增页面
	@RequestMapping(value="appinfoadd.do")
	public String appinfoadd(){
		return "appinfoadd";
	}
	//查询
	@RequestMapping(value="appinfolist.do",method=RequestMethod.POST)
	public String getappinfolistselect(AppInfo appInfo,@RequestParam(value="pageIndex",required=false) Integer pageIndex
		,Model m) throws Exception {
		System.out.println("===>>>"+appInfo.getSoftwareName());
		//设置页面容量
		int pageSize=Constants.pageSize;
		if(pageIndex==null){pageIndex=1;}
		PageSupport p=devUserService.getappinfolistselect(appInfo, pageIndex, pageSize);
	    m.addAttribute("appInfoList",p.getList());
	    m.addAttribute("pages",p);
	    List<DataDictionary> statusList;
		List<DataDictionary> flatFormList;
		List<AppCategory> categoryLevel1List;
		try {
			//app状态
			statusList = devUserService.getDictionary("APP_STATUS");
			m.addAttribute("statusList", statusList);
			flatFormList=devUserService.getDictionary("APP_FLATFORM");
			m.addAttribute("flatFormList", flatFormList);
			//一级分类
			categoryLevel1List=devUserService.getType(0);
			m.addAttribute("categoryLevel1List", categoryLevel1List);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/developer/appinfolist";
	}
}
