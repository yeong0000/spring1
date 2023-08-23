package com.itwill.spring1.web;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itwill.spring1.dto.UserDto;

import lombok.extern.slf4j.Slf4j;

//POJO(Plain Old Java Object): 평범한 자바 객체
//특정 클래스를 상속해야만 하거나, 상속 후에 메서드들을 override해야만 하는 클래스가 아님.
//스프링 프레임워크는 POJO로 작성된 클래스를 컨트롤러로 사용할 수 있음.


@Slf4j //Logger 객체(log)를 생성.
@Controller
//Spring MVC 컴포넌트 애너테이션(@Component, @Controller, @Service, @Repository, ...) 중 하나. 
//Controller 컴포넌트라는 것을 dispatcherServlet에게 알려줌.
public class ExampleController {

    @GetMapping("/") //GET 방식의 요청주소가 "/"(context root)인 요청을 처리하는 메서드./컨트롤러에서 뷰로 보낼때는 model
    public String home(Model model) { //리턴값은 jsp 파일이름 -> string
        log.info("home()");
        
        LocalDateTime now = LocalDateTime.now();
        model.addAttribute("now", now); //뷰에 전달할 데이터를 세팅.
        
        return "index"; //뷰의 이름(/WEB-INF/views/index.jsp)
    }
    
    @GetMapping("/ex1")
    public void example1() {
        log.info("example1() 호출");
        //컨트롤러 메서드가 void인 경우(뷰의 이름을 리턴하지 않는 경우)
        //요청주소의 이름이 뷰의 이름이 됨.
    }
    
    @GetMapping("/ex2")
    public void getParamEx(String username, int age) {
        log.info("getParamEx(username={}, age={})", username, age);
    }
    
    @PostMapping("/ex3") //결과처리 페이지로만 가면 되니까 void로 하면 ex3.jsp 또 만들어야되는데 string하면 위의 ex2.jsp 이용가능.
    public String getParamEx2(
            @RequestParam(name = "username") String name, //(1) (리퀘스트파라미터)요청 파라미터에 username있지만 name없어서 애너테이션을 이용.
            @RequestParam(defaultValue = "0") int age //(2) int라서 값 입력 안하면 오류남. 애너테이션이용해서 기본값을 "0" 문자열로 선언.(리퀘스트파라미터는 문자열)
    ) {
     // 컨트롤러 메서드를 선언할 때, 파라미터 선언 앞에 @RequestParam 애너테이션을 사용하는 경우:
        // (1) 메서드 파라미터와 요청 파라미터 이름이 다를 때, name 속성으로 요청 파라미터 이름을 설정.
        // (2) 요청 파라미터 값이 없거나 비어있을 때, defaultValue 속성 설정.
        
        
        log.info("getParamEx2(name={}, age={})", name, age);
        
        
        return "ex2";
    }
    
    @GetMapping("/ex4")
    public String getParamEx3(UserDto user) {
        log.info("getParamEx3(user={})", user);
        // DispatcherServlet은 컨트롤러의 메서드를 호출하기 위해서,
        // 1. 요청 파라미터들을 분석(request.getParameter()).
        // 2. UserDto의 기본 생성자를 호출해서 객체를 생성.
        // 3. 요청 파라미터들의 이름으로 UserDto의 setter 메서드를 찾아서 호출.
        // 4. UserDto 객체를 컨트롤러 메서드를 호출할 때 argument로 전달.
        
        return "ex2";
    }
    
    @GetMapping("/sample")
    public void sample() {
        log.info("sample()");
    }
    
    @GetMapping("/forward")
    public String fowardTest() {
        log.info("fowardTest()");
        
        //컨트롤러 메서드에서 다른 페이지(요청 주소)로 forward하는 방법:
        //"forward:"으로 시작하는 문자열을 리턴하면,
        //DispatcherServlet은 리턴값이 뷰의 이름이 아니라 포워드 이동할 페이지 주소로 인식.
        return "forward:/sample";
        
    }
    
    @GetMapping("/redirect")
    public String redirectTest(RedirectAttributes attrs) { //첫번째 요청과 다른 새로운 요청까지 유지가 될 수 있는 객체가 필요 -> RedirectAttributes 
        log.info("redirectTest()");
        
        //컨트롤러 메서드에서 리다이렉트되는 페이지까지 유지되는 정보를 저장할 때:
        attrs.addFlashAttribute("redAttr", "테스트");
        
        
        //컨트롤러 메서드에서 다른 페이지(요청 주소)로 redirect하는 방법:
        //"redirect:"으로 시작하는 문자열을 리턴하면,
        //DispatcherServlet은 리턴값이 뷰의 이름이 아니라 리다이렉트 이동할 페이지 주소로 인식.
        
        
        return "redirect:/sample";
    }
    
}
