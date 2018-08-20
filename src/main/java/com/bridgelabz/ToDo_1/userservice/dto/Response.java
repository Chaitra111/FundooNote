package com.bridgelabz.ToDo_1.userservice.dto;

import org.springframework.http.HttpStatus;

/**
 * @author Chaitra Ankolekar
 * Purpose :Response pojo class
 */
public class Response {
		int status;
		String message;
		
		public Response(String message, int status) {
			super();
			this.message=message;
			this.status=status;			
		}
		
		public Response() {
			// TODO Auto-generated constructor stub
		}
		/**
		 * @return integer
		 */
		public int getStatus() {
			return status;
		}
		/**
		 * @param status
		 */
		public void setStatus(int status) {
			this.status = status;
		}
		/**
		 * @return String
		 */
		public String getMessage() {
			return message;
		}
		/**
		 * @param response
		 */
		public void setMessage(String response) {
			this.message = response;
		}

		@Override
		public String toString() {
			return "Response [status=" + status + ", message=" + message + "]";
		}		
	}