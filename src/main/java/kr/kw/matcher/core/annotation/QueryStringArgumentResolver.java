package kr.kw.matcher.core.annotation;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;

@Component
@Slf4j
public class QueryStringArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private ObjectMapper mapper;


    @Override
    public boolean supportsParameter(final MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(QueryStringArgResolver.class) != null;
    }


    @Override
    public Object resolveArgument(final MethodParameter methodParameter,
                                  final ModelAndViewContainer modelAndViewContainer,
                                  final NativeWebRequest nativeWebRequest,
                                  final WebDataBinderFactory webDataBinderFactory) throws Exception {
        try{
            final HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();

            final String s = request.getQueryString().replaceAll("%3A", ":");

            final String json = qs2json(URLDecoder.decode(s, "UTF-8"));

            final Object a = mapper.readValue(json, methodParameter.getParameterType());

            return a;
        }catch (Exception e){
         throw new Exception();
        }
    }


    private String qs2json(String a) {
        String res = "{\"";

        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == '=') {
                res += "\"" + ":" + "\"";
            } else if (a.charAt(i) == '&') {
                res += "\"" + "," + "\"";
            } else {
                res += a.charAt(i);
            }
        }
        res += "\"" + "}";
        return res;
    }
}