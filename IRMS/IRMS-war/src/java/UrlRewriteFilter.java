/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import exception.ExistException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author wujingyun
 */
@WebFilter(filterName = "UrlRewriteFilter", urlPatterns = {"*.xhtml"})
public class UrlRewriteFilter implements Filter {

    private static final boolean debug = true;
    private FilterConfig filterConfig = null;

  

    public UrlRewriteFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
        }
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
        }
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String urlPath = req.getServletPath();
        doBeforeProcessing(request, response);
        if (req.getSession(true).getAttribute("isLogin") == null) {
            req.getSession(true).setAttribute("isLogin", false);
        }
        Boolean isLogin = (Boolean) req.getSession(true).getAttribute("isLogin"); Throwable problem = null;


        try {

            System.err.println("======================================Start Filter");

            if (!checkRequireLogin(urlPath)) {//if pages do require login
 System.err.println("======================================checked require login");
                if (isLogin == true) {//user already login
                    //check role
                    String role = (String) req.getSession().getAttribute("role");
                     //if page do require login and is not super admin in subsystem 
                     System.err.println("======================================checked require login"+role);
                    if (!checkSuperAdmin(role, urlPath)){

                       //if (checkAccessRight(role, urlPath)) {
                         //  chain.doFilter(request, response);
                        //} else {
                        if (role.equalsIgnoreCase("customer"))
                            req.getRequestDispatcher("/crmAccessDenied.xhtml").forward(req, resp);
                        else 
                             req.getRequestDispatcher("/accessDenied.xhtml").forward(req, resp);
                        //}
                    } else {//if it's super admin, can access all pages under the subsystem 
                        System.out.println("superadmin ==============");
                        chain.doFilter(request, response);
                    }

                } else {
                    req.getSession(true).setAttribute("lastVisit", urlPath);
                     if (urlPath.contains("crm")) {
                    req.getRequestDispatcher("/index.xhtml").forward(req, resp);}
                      else {
                    req.getRequestDispatcher("/loginInternalUser.xhtml").forward(req, resp);}
                }


            } else {
                chain.doFilter(request, response);
            }


        } catch (Throwable t) {
           
            problem = t;
            t.printStackTrace();
        }

        doAfterProcessing(request, response);

        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    private Boolean checkSuperAdmin(String role, String path) throws ExistException {

        System.err.println("===================check if it's a super Admin"+role);

        if (role.equalsIgnoreCase("SuperAdmin")) {
            if (!path.contains("crm")) {
                return true;
            }
        }
        if (!role.contains("customer")) {
            if (path.contains("internal")) {
                return true;
            }
        }
      
        
        
         if (role.contains("customer")) {
            if (path.contains("crm")) {
                return true;
            }
        }
        if (role.contains("acmSuperAdmin")) {
            if (path.contains("acm")) {
                return true;
            }
        }
        if (role.contains("spmSuperAdmin")) {
             System.err.println("===================check "+path);
            if (path.contains("smp")) {
                 System.err.println("===================check if it's a super Admin"+role);
                  System.err.println("===================check if it's a super Admin"+path);
                return true;
            }
        }
       else {
            return false;
        }
        return false;

    }

   

    private Boolean checkRequireLogin(String path) {
        if (path.contains("index.xhtml") 
                || path.contains("loginCustomer.xhtml") || path.contains("loginInternalUser.xhtml") 
                || path.contains("passwordReset.xhtml")|| path.contains("crmPasswordReset.xhtml")
                || path.contains("ResetResult.xhtml") || path.contains("crmResetResult.xhtml")
                || path.contains("customerRegister.xhtml")|| path.contains("logout.xhtml")
                || path.contains("accessDeniedPage.xhtml")|| path.contains("smpTest.xhtml")
                || path.startsWith("/javax.faces.resource")|| path.contains("webSearch")
                || path.startsWith("/resources")  
                || path.endsWith("/")) {
           System.out.println("doesn't reqiure login=================================");
           return true;
              
        } else {
            System.out.println("reqiure login=================================");
            return false;
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("UrlRewriteFilter()");
        }
        StringBuffer sb = new StringBuffer("UrlRewriteFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); 

                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); 
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }
}
