package com.team1.iss.trial.component;

import com.team1.iss.trial.domain.Employee;
import com.team1.iss.trial.domain.LA;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

public class LaArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestLA.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();

        char[] buf = new char[1024];
        int rd;
        while((rd = reader.read(buf)) != -1){
            sb.append(buf, 0, rd);
        }
        System.out.println(sb.toString());
        //fromTime=1593561600000&toTime=1595376000000&dissemination=&type=ANNUAL_LEAVE&reasons=this+is+a+reason&contact=
        String[] params = sb.toString().split("&");
        LA la = new LA();
        for (String param : params) {
            String key = param.substring(0, param.indexOf("="));;
            String value = param.substring(param.indexOf("=")+1, param.length());
            switch (key){
                case "uid":
                    la.setUid(Integer.valueOf(value));
                    break;
                case "fromTime":
                    la.setFromTime(Long.valueOf(value));
                    break;
                case "toTime":
                    la.setToTime(Long.valueOf(value));
                    break;
                case "dissemination":
                    if(StringUtils.isEmpty(value)){
                        la.setDissemination(null);
                    }else{
                        la.setDissemination(new Employee(Integer.valueOf(value)));
                    }
                    break;
                case "type":
                    la.setType(value);
                    break;
                case "reasons":
                    la.setReasons(value.replace("+"," "));
                    break;
                case "contact":
                    la.setContact(value);
                    break;
            }
        }

        return la;

    }



}

