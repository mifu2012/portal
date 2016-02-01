package com.infosmart.controller;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.infosmart.po.Example;
import com.infosmart.service.ExampleService;
import com.infosmart.util.Tools;

@Controller
@RequestMapping("/example")
public class ExampleController {
	private static final Logger logger = Logger
			.getLogger(ExampleController.class);
	private final String LIST_ACTION = "redirect:/example.html";
	private final String SUCCESS_ACTION = "/common/save_result";
	@Autowired
	private ExampleService exampleService;

	/**
	 * 方法都可以接受的参数(参数数量和顺序没有限制)：
	 * HttpServletRequest,HttpServletResponse,HttpSession(session必须是可用的) ,
	 * PrintWriter,Map,Model,
	 * 
	 * @PathVariable(任意多个)， @RequestParam（任意多个）， @CookieValue （任意多个），
	 * @RequestHeader，Object（pojo对象） ,BindingResult等等
	 * 
	 *                               返回值可以是：String(视图名)，void（用于直接response），
	 *                               ModelAndView，Map
	 *                               ，Model，任意其它任意类型的对象（默认放入model中
	 *                               ，名称即类型的首字母改成小写），视图名默认是请求路径
	 */
	@RequestMapping("/helloWorld")
	public ModelAndView helloWorld(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, PrintWriter out) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/example/welcome");
		mav.addObject("message", "欢迎访问springMVC的demo!");
		return mav;
	}

	/** 列表 */
	@RequestMapping
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response, Example example) {
		List<Example> examples = exampleService.getExamplePages(example);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/example/example");
		mav.addObject("examples", examples);
		mav.addObject("example", example);
		return mav;
	}

	/** 显示 */
	@RequestMapping(value = "/{id}")
	public ModelAndView show(@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(id == null){
			this.logger.warn("显示对应id时传递的id为null");
		}
		Example example = (Example) exampleService.getExample(id);
		return new ModelAndView("/example/show", "example", example);
	}

	/** 进入新增 */
	@RequestMapping(value = "/add")
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView("/example/example_info");
	}

	/** 进入更新 */
	@RequestMapping(value = "/edit{id}")
	public ModelAndView edit(@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(id == null){
			this.logger.warn("编辑example时传递的id为null");
		}
		Example example = (Example) exampleService.getExample(id);
		return new ModelAndView("/example/example_info", "example", example);
	}

	/** 保存 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveExample(HttpServletRequest request,
			HttpServletResponse response, Example example) {
		if(example == null){
			this.logger.warn("保存时传递的example对象为null");
		}
		ModelAndView mv = new ModelAndView();
		if(example != null){
			if (Tools.isEmpty(example.getId())) {
				if (exampleService.getExample(example.getId()) != null) {
					mv.addObject("msg", "failed");
				} else {
					exampleService.save(example);
					mv.addObject("msg", "success");
				}
			} else {
				Example oexample = (Example) exampleService.getExample(example
						.getId());
				BeanUtils.copyProperties(example, oexample);
				exampleService.update(oexample);
			}
		}
		mv.setViewName(SUCCESS_ACTION);
		return mv;
	}

	/** 删除 */
	@RequestMapping(value = "/delete{id}")
	public void delete(@PathVariable Long id, PrintWriter out) {
		if(id == null){
			this.logger.warn("删除example时传递的id为null");
		}
		exampleService.delete(id);
		out.write("success");
		out.close();
	}

	/** 批量删除 */
	@RequestMapping("/delete")
	public ModelAndView batchDelete(
			@RequestParam(value = "ids", required = false) Long[] ids,
			HttpServletRequest request, HttpServletResponse response) {
		if(ids == null){
			this.logger.warn("批量删除时传递的id集合为null");
		}
		if (ids != null && ids.length > 0)
			exampleService.deletes(Arrays.asList(ids));
		return new ModelAndView(LIST_ACTION);
	}

}
