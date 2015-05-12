package com.zhubao.b2b.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhubao.b2b.platform.model.Customer;
import com.zhubao.b2b.platform.model.ShipAddress;
import com.zhubao.b2b.platform.service.ShipAddressService;

@Controller
@RequestMapping(value = "/contacter")
public class ContacterController extends AbstractController {

	@Autowired
	private ShipAddressService shipAddressService;

	@RequestMapping(value = "/add")
	@RequiresAuthentication
	public String add(HttpServletRequest request, HttpServletResponse response, Model model) {
		Customer user = getLoginUser();
		model.addAttribute("addresses", shipAddressService.getAddresses(user.getId()));

		return "user/contacter/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@RequiresAuthentication
	public @ResponseBody
	Map<String, Object> add(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute("shipAddress") ShipAddress shipAddress) {
		Map<String, Object> data = new HashMap<String, Object>();
		Customer user = getLoginUser();

		try {
			shipAddress.setCustomerId(user.getId());
			shipAddressService.createShipAddress(shipAddress);
			data.put("state", true);
		} catch (Exception e) {
			data.put("state", false);
			data.put("message", e.getMessage());
		}

		return data;
	}

	@RequestMapping(value = "/edit/{id}")
	@RequiresAuthentication
	public String edit(HttpServletRequest request, HttpServletResponse response, Model model,
			@PathVariable("id") String id) {
		Customer user = getLoginUser();
		model.addAttribute("addresses", shipAddressService.getAddresses(user.getId()));

		return "user/contacter/add";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@RequiresAuthentication
	public @ResponseBody
	Map<String, Object> edit(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute("shipAddress") ShipAddress shipAddress) {
		Map<String, Object> data = new HashMap<String, Object>();
		Customer user = getLoginUser();

		try {
			shipAddress.setCustomerId(user.getId());
			shipAddressService.createShipAddress(shipAddress);
			data.put("state", true);
		} catch (Exception e) {
			data.put("state", false);
			data.put("message", e.getMessage());
		}

		return data;
	}

}