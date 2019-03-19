package ir.aziz.karam.filter;

import java.util.HashMap;
import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
 
final class MutableHttpServletResponse extends HttpServletResponseWrapper {
    // holds custom header and value mapping
    private final Map<String, String> customHeaders;
 
    public MutableHttpServletResponse(HttpServletResponse request){
        super(request);
        this.customHeaders = new HashMap<>();
    }
    
    public void putHeader(String name, String value){
        this.customHeaders.put(name, value);
    }
 
    @Override
    public String getHeader(String name) {
        // check the custom headers first
        String headerValue = customHeaders.get(name);
        
        if (headerValue != null){
            return headerValue;
        }
        // else return from into the original wrapped object
        return ((HttpServletRequest) getResponse()).getHeader(name);
    }
    
}