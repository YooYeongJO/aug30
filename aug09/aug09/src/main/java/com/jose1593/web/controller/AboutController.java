package com.jose1593.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {

	@GetMapping("/about")
	public String about(Model model) {
		String names = new String("짱구, 맹구, 철수, 유리, 훈이, 신형만, 봉미선, 나미리, 채성아, 두목님, "
				 + "봉미소, 유영조, 유종휘, 이대원, 이상화, 이승현, 이지선, 정대규, 정준식, 최범식, "
				 + "최지은, 표해현, 차승리, 황선우, 박채아");
		List<String> members = new ArrayList<String>(Arrays.asList(names.split(",")));
		Collections.shuffle(members);
		Collections.shuffle(members);
		Collections.shuffle(members);
		
		model.addAttribute("list", members);
		
		return "about";
	}
	

}
