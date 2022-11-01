package servlets;

import dataaccess.RoleDB;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Role;
import models.User;
import services.RoleService;
import services.UserService;

public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserService us = new UserService();
        RoleService rs = new RoleService();

        String email = request.getParameter("email");

        String action = request.getParameter("action");
        action = action == null ? "" : action;

        switch (action) {
            case "clearEdit": {
                request.setAttribute("edit", false);
                request.setAttribute("user", null);
                break;
            }
            case "edit": {
                if (checkIsValid(new String[]{email})) {
                    request.setAttribute("isEdit", true);
                    try {
                        request.setAttribute("user", us.get(email));
                    } catch (Exception e) {
                        request.setAttribute("error", "Could not retrieve user.");
                    }
                } else {
                    request.setAttribute("error", "Could not retrieve user.");
                    return;
                }
                break;
            }
            case "delete": {
                if (checkIsValid(new String[]{email})) {
                    try {
                        us.delete(email);
                    } catch (Exception e) {
                        request.setAttribute("error", "Could not delete user.");
                    }
                } else {
                    request.setAttribute("error", "Could not delete user.");
                    return;
                }
                break;
            }
        }

        try {
            request.setAttribute("users", us.getAll());
            request.setAttribute("roles", rs.getAll());
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
        }

        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserService us = new UserService();
        RoleService rs = new RoleService();

        String email = request.getParameter("email");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String password = request.getParameter("password");
        String roleIdStr = request.getParameter("roleId");

        String action = request.getParameter("action");
        action = action == null ? "" : action;

        try {
            switch (action) {
                case "add":
                    if (checkIsValid(new String[]{email, firstName, lastName, password})) {
                        int roleId = Integer.parseInt(roleIdStr);
                        RoleDB roleDB = new RoleDB();
                        Role role = roleDB.getRole(roleId);
                        us.insert(email, firstName, lastName, password, roleId);
                    } else {
                        request.setAttribute("error", "All fields are required");
                    }
                case "edit":
                    if (checkIsValid(new String[]{email, firstName, lastName})) {
                        int roleId = Integer.parseInt(roleIdStr);
                        RoleDB roleDB = new RoleDB();
                        Role role = roleDB.getRole(roleId);
                        us.update(email, firstName, lastName, password, roleId);
                    } else {
                        request.setAttribute("error", "All fields are required");
                    }
            }
        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
        }

        try {
            request.setAttribute("users", us.getAll());
            request.setAttribute("roles", rs.getAll());
        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
        }

        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp")
                .forward(request, response);
    }

    private boolean checkIsValid(String[] values) {
        // check each elemenet in array for null or empty string
        // return false if one is found
        for (String s : values) {
            if (s == null || s.equals("")) {
                return false;
            }
        }
        return true;
    }
}
