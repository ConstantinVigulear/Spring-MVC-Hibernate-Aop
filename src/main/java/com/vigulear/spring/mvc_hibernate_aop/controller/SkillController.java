package com.vigulear.spring.mvc_hibernate_aop.controller;

import com.vigulear.spring.mvc_hibernate_aop.entity.Skill;
import com.vigulear.spring.mvc_hibernate_aop.entity.SkillDomain;
import com.vigulear.spring.mvc_hibernate_aop.entity.SkillLevel;
import com.vigulear.spring.mvc_hibernate_aop.service.SkillService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author Constantin Vigulear
 */
@Controller
@RequestMapping({"skills", "skills/"})
public class SkillController {

  private final SkillService skillService;
  private final SkillDomain[] domains;
  private final SkillLevel[] levels;

  /**
   * this variable is used for redirecting back to persons skills page after modifying their set of
   * skills
   */
  private Long chosenPersonId = 0L;

  public SkillController(SkillService service) {
    this.skillService = service;
    this.levels = SkillLevel.values();
    this.domains = SkillDomain.values();
  }

  @GetMapping()
  public String showAllSkills(Model model) {

    List<Skill> skills = skillService.findAll();
    model.addAttribute("skills", skills);

    return "skills/all-skills";
  }

  @RequestMapping(value = "/saveAddedSkill")
  public ModelAndView saveAddedSkill(@ModelAttribute("skill") Skill skill, ModelMap modelMap) {

    Skill persistedSkill = skillService.persist(skill);

    modelMap.addAttribute("skillId", persistedSkill.getId());
    modelMap.addAttribute("skillCost", skill.getSkillCost());
    modelMap.addAttribute("personId", chosenPersonId);

    return new ModelAndView("redirect:/people/addSkillToPerson", modelMap);
  }

  @RequestMapping(value = "/saveNewSkill")
  public ModelAndView saveNewSkill(@ModelAttribute("skill") Skill skill, ModelMap modelMap) {
    skillService.persist(skill);

    return new ModelAndView("redirect:/skills", modelMap);
  }

  @RequestMapping("/addNewSkill")
  public String addNewSkill(@RequestParam("personId") String id, Model model) {
    Skill skill = new Skill();
    chosenPersonId = Long.parseLong(id);

    model.addAttribute("skill", skill);
    model.addAttribute("domains", domains);
    model.addAttribute("levels", levels);

    return "skills/skill-info";
  }

  @RequestMapping("/createNewSkill")
  public String createNewSkill(Model model) {
    Skill skill = new Skill();

    model.addAttribute("skill", skill);
    model.addAttribute("domains", domains);
    model.addAttribute("levels", levels);

    return "skills/skill-info";
  }

  @RequestMapping(
      value = "/updateSkill",
      params = {"skillId", "personId"})
  public String updateSkill(
      @RequestParam("skillId") String skillId,
      @RequestParam("personId") String personId,
      Model model) {

    chosenPersonId = Long.parseLong(personId);
    Skill skill = skillService.findById(Long.parseLong(skillId));

    model.addAttribute("skill", skill);
    model.addAttribute("domains", domains);
    model.addAttribute("levels", levels);

    return "skills/skill-info";
  }

  @RequestMapping("/deleteSkill")
  public String deleteSkill(@RequestParam("skillId") Long id) {
    skillService.deleteById(id);

    return "redirect:/skills";
  }
}
