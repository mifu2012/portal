package com.infosmart.service;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infosmart.orm.mybatis.MyBatisDao;
import com.infosmart.po.Example;

@Service
public class ExampleService {
	private static final Logger logger = Logger.getLogger(ExampleService.class);
	@Autowired
	private MyBatisDao myBatisDao;

	public List<Example> getExamplePages(Object param) {
		return myBatisDao.getList("exampleMapper.listPageExample", param);
	}

	public List<Example> getExamples(Object param) {
		return myBatisDao.getList("exampleMapper.listAllExample", param);
	}

	public Example getExample(Serializable id) {
		return myBatisDao.get("exampleMapper.getExampleById", id);
	}

	public void save(Object param) {
		myBatisDao.save("exampleMapper.insertExample", param);
	}

	public void update(Object param) {
		myBatisDao.save("exampleMapper.updateExample", param);
	}

	public void delete(Serializable id) {
		myBatisDao.delete("exampleMapper.deleteExample", id);
	}

	public void deletes(List<Long> ids) {
		myBatisDao.delete("exampleMapper.deleteExamples", ids);
	}
	public void batchExample(List<Example> list){
		myBatisDao.save("exampleMapper.batchInsertExample",list);
	}
}
