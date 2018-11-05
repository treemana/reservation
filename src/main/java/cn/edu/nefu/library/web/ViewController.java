package cn.edu.nefu.library.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author : Hunter
 * @date : 18-11-4 下午4:42
 * @since : Java 8
 */
@Controller
public class ViewController {
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String getIndex() {
        return "index";
    }
}
