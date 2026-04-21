package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ルートURL（/）へのアクセスを制御するコントローラー。
 */
@Controller
public class RootController {

  /**
   * ルートURLにアクセスされた際、自動的にタスク一覧へリダイレクトします。
   *
   * @return タスク一覧へのリダイレクト
   */
  @GetMapping("/")
  public String root() {
    return "redirect:/tasks";
  }
}
