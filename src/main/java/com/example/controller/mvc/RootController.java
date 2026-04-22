package com.example.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * {@code /} をタスク一覧へリダイレクト。
 */
@Controller
public class RootController {

  @GetMapping("/")
  public String root() {
    return "redirect:/tasks";
  }
}
