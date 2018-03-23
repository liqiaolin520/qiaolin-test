package indi.qiaolin.boot.helloword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *  spring boot Hello word 测试Controller
 *  @author  qiaolin
 *  @date 2018年3月23日
 */

@Controller
public class TestController {

    @RequestMapping("hello")
    @ResponseBody
    public String hello(){
        return "hello word!";
    }
}
