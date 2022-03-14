package com.adp.rest.controller;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adp.rest.bean.SomeBean;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class SomeBeanFilterController {

	@GetMapping("/all")
	public MappingJacksonValue filterFieldsDynamically() {
		SomeBean bean = new SomeBean("value1", "value2", "value3");

		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field2");
		FilterProvider filters = new SimpleFilterProvider().addFilter("someBeanFilter", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(bean);
		mapping.setFilters(filters);
		return mapping;
	}
}
