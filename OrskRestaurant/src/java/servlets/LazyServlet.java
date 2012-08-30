package servlets;

import ejb.FacilityManagerLocal;
import entity.Facility;
import entity.Feature;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.view.FacilityViewBean;

/**
 * @author rogvold
 */
public class LazyServlet extends HttpServlet {

    public static int CARD_PER_REQUEST = 3;
    @EJB
    FacilityManagerLocal facMan;

    public String generateHtmlForOneFacility(Facility facility) {
        String html = "&lt;div class='tab-wrapper1' id='" + facility.getId() + "'&gt;" + "\n"
                + "&lt;div class='tab-content' style='border-radius: 10px; background-color: #DEC79D;' &gt;" + "\n"
                + " &lt;div class='tab' style='display: block; '&gt;" + "\n"
                + "  &lt;h3&gt;" + "\n"
                + "    &lt;a style='cursor: pointer; color: #A12124;' href='info.xhtml?id=" + facility.getId() + "' &gt;" + "\n"
                + facility.getName() + "" + "\n"
                + "    &lt;/a&gt;" + "\n"
                + "    &lt;span style='font-style: italic; font-family: Georgia, Times New Roman, Times, serif; font-size: 12px; color: #565656; padding-left: 5px;'&gt;" + "\n"
                + stringOfFacilityTypes(facility.getId()) + "" + "\n"
                + "    &lt;/span&gt;" + "\n"
                + "  &lt;/h3&gt;" + "\n"
                + "   &lt;p&gt;" + "\n"
                + "     &lt;span style='float:right; width: 200px;  display: block; margin-left:20px;'&gt;" + "\n"
                + "       &lt;span style='display: inline-block; height: 150px; margin: 0 auto; float: right; background-color: yellow;'&gt;" + "\n"
                + "           &lt;a style='cursor: pointer;' href='info.xhtml?id=" + facility.getId() + "'&gt;" + "\n"
                + "               &lt;img src='" + avatar(facility.getId()) + "'  style='height: 150px; max-width: 230px; '  alt=''  /&gt;" + "\n"
                + "           &lt;/a&gt;" + "\n"
                + "       &lt;/span&gt;" + "\n"
                + "     &lt;/span&gt;" + "\n"
                + ""
                + "     &lt;span style='width:400px; display: block; color: black; font-size: 14px; font-weight: bold;'&gt;" + "\n"
                + "           &lt;img src='http://www.restosapiens.ru/img/icons/home.gif' style='width:16px; height:16px; ' /&gt;" + "\n"
                + facility.getAddress() + "\n"
                + "     &lt;/span&gt;" + "\n"
                + "" + "\n"
                + "     &lt;span style='width:400px; display: block; color: black; font-size: 14px; font-weight: bold;'&gt;" + "\n"
                + "           &lt;img src='http://www.restosapiens.ru/img/icons/phone.gif' style='width:16px; height:16px; ' /&gt;" + "\n"
                + facility.getPhone() + "\n"
                + "     &lt;/span&gt; &lt;br/&gt;" + "\n"
                + "" + "\n"
                + "  <!--     &lt;span style='width:400px; display: block; color: black; font-size: 14px; font-style: italic;'&gt;" + "\n"
                + "       &lt;img src='http://www.restosapiens.ru/img/openbull.gif' style='width:10px; height:10px;' /&gt;" + "\n"
                + "           Работает ещё mm минуты, до HH:MM" + "\n"
                + "     &lt;/span&gt; -->" + "\n"
                + ""
                + "     &lt;span style='width:400px; display: block; color: black; font-size: 14px; '&gt;" + "\n"
                + shortDescription(facility.getId()) + "\n"
                + "     &lt;/span&gt;" + "\n"
                + "     &lt;span style='width:400px; display: block; color: black; font-size: 14px; text-align:  right;'&gt;" + "\n"
                + "           &lt;a style='cursor: pointer;' href='info.xhtml?id=" + facility.getId() + "'&gt;Подробнее&lt;/a&gt;" + "\n"
                + "     &lt;/span&gt;" + "\n"
                + "   &lt;/p&gt;" + "\n"
                + "  &lt;/div&gt;" + "\n"
                + " &lt;/div&gt;" + "\n"
                + "&lt;/div&gt;";

        return html;
    }


    public String shortDescription(Long facId) {
        String s = facMan.getDescription(facId);
        if (s.length() >= FacilityViewBean.MAX_DESCRIPTION_LENGTH) {
            s = s.substring(0, FacilityViewBean.MAX_DESCRIPTION_LENGTH) + " ...";
        }
        return s;
    }

    public String avatar(Long facId) {
        return facMan.getAvatarSrc(facId);
    }

    public String stringOfFacilityTypes(Long facId) {
        List<Feature> list = facMan.getFeaturesByFacilityIdAndType(facId, Feature.TYPE_FACILITY_TYPE);
        String s = "";
        int last = list.size() - 1;
        if (last < 0) {
            return s;
        }
        if (last == 0) {
            return list.get(0).getName();
        }
        s = list.get(0).getName();
        for (int i = 1; i <= last; i++) {
            s += ", " + list.get(i).getName();
        }
        return s;
    }

    private String generateHtml(List<Long> exceptList) {
        List<Facility> list = facMan.getAllFacilitiesExceptForList(CARD_PER_REQUEST, exceptList);
        String res = "";
        for (Facility f : list) {
            res += generateHtmlForOneFacility(f);
        }

        return res;
    }

    private String generateHtml(List<Long> exceptList, List<Long> featuresId) {
        List<Facility> list = facMan.getFilteredFacilitiesExceptForList(CARD_PER_REQUEST, exceptList, featuresId);
        String res = "";
        for (Facility f : list) {
            res += generateHtmlForOneFacility(f);
        }

        return res;
    }

    private List<Long> getExceptionList(String input) {
        System.out.println("input = " + input);
        String[] arr = input.split("\\_");
        List<String> slist = Arrays.asList(arr);
        List<Long> list = new ArrayList();
        for (String s : slist) {
            System.out.println("s = " + s);
            list.add(Long.parseLong(s));
        }
        System.out.println("excList = " + list);
        return list;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String resp = "";
            resp = generateHtml(getExceptionList(request.getParameter("list")), getExceptionList(request.getParameter("features")));
            out.write(resp);
        } finally {
            out.close();
        }
    }

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet for Lazy Scroll";
    }
}
