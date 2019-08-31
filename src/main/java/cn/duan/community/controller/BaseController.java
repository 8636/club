package cn.duan.community.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 基类的 protected 成员是包内可见的，并且对子类可见；
 */
public class BaseController {

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;
    @Autowired
    protected HttpSession session;
}
