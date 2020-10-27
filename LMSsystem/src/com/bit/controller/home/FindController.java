package com.bit.controller.home;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit.model.dao.EmployeeDao;
import com.bit.model.dao.StudentDao;

@WebServlet("/find.bit")
public class FindController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("find.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String userName = req.getParameter("name");
		String contact = req.getParameter("contact");
		String userId ="";
		
		String userId2 = req.getParameter("userId2");
		String userName2 = req.getParameter("name2");
		String contact2 = req.getParameter("contact2");
		
		if(userName!=null) {
		try {
			StudentDao dao = new StudentDao();
			userId = dao.findUserId(userName, contact);
			if(userId != null && userId != "") {
				resp.setContentType("text/html; charset=UTF-8");
				PrintWriter out = resp.getWriter();
				out.println("<script>alert('" + userName + "님의 아이디는 \"" + userId + "\" 입니다.');location.href='find.bit'</script>");
				
				out.flush();
			} else {
				EmployeeDao empDao = new EmployeeDao();
				userId = empDao.findUserId(userName, contact);
				
				if(userId != null && userId != "") {
					resp.setContentType("text/html; charset=UTF-8");
					PrintWriter out = resp.getWriter();
					out.println("<script>alert('관리자 " + userName + "님의 아이디는 \"" + userId + "\" 입니다.');location.href='find.bit'</script>");
				
					out.flush();
				} else {
					resp.setContentType("text/html; charset=UTF-8");
				PrintWriter out = resp.getWriter();
			
				out.println("<script>alert('존재하지 않는 계정입니다.11 다시 한번 확인 바랍니다.');location.href='find.bit'</script>");
				out.flush();
				}

				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}else {
			try {
				StudentDao dao = new StudentDao();
				String id = dao.findPassword(userId2, userName2, contact2);
				if(id != null && id != ""){
					String newPw = randomPassword(8);
					dao = new StudentDao();
					int result = dao.updatePassword(id, newPw);
					resp.setContentType("text/html; charset=UTF-8");
					PrintWriter out = resp.getWriter();
					if(result >= 1) {
						out.println("<script>alert('" + userName + "님의 임시 비밀번호는 \"" + newPw + "\" 입니다.\\n 로그인 후, 비밀번호를 변경해주세요.');location.href='index.bit'</script>");
						out.flush();
					} else{
						out.println("<script>alert('비밀번호 생성에 실패하였습니다.');location.href='index.bit'</script>");
						out.flush();
					}
				} else {
					EmployeeDao empDao = new EmployeeDao();
					String empId = empDao.findPassword(userId2, userName2, contact2);
					if(empId != null && empId != "") {
						String newPw = randomPassword(8);
						empDao = new EmployeeDao();
						int result = dao.updatePassword(id, newPw);
						resp.setContentType("text/html; charset=UTF-8");
						PrintWriter out = resp.getWriter();
						if(result >= 1) {
							out.println("<script>alert('" + userName + "님의 임시 비밀번호는 \"" + newPw + "\" 입니다.\\n 로그인 후, 비밀번호를 변경해주세요.');location.href='index.bit'</script>");
							out.flush();
						} else{
							out.println("<script>alert('비밀번호 생성에 실패하였습니다.');location.href='index.bit'</script>");
							out.flush();
						}
					}
					PrintWriter out = resp.getWriter();
					out.println("<script>alert('존재하지 않는 계정입니다.\n 다시 한번 확인 바랍니다.');</script>");
					out.flush();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		}
		
		public String randomPassword(int length) {
			int index = 0;
			char[] charSet = new char[]{
					'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
					,'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L'
					,'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'X', 'Y'
					,'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k'
					,'m', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x'
					,'y', 'z'};
			
			StringBuffer sb = new StringBuffer();
			for(int i=0; i<length; i++) {
				index = (int)(charSet.length * Math.random());
				sb.append(charSet[index]);
			}
			return sb.toString();
		}
		
		
		
	}

