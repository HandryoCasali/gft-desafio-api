package br.com.gft.noticias.config.security.auth;

public class TokenDTO {
	
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public TokenDTO() {
	}

	public TokenDTO(String token) {
		this.token = token;
	}
	

}
