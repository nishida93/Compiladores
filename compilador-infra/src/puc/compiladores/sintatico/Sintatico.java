package puc.compiladores.sintatico;

import puc.compiladores.lexico.Lexico;
import puc.compiladores.lexico.Token;

public class Sintatico {

	private int controle;
	private Lexico lx;

	public Sintatico() throws Exception {
		Token tk;
		lx = new Lexico();
		System.out.println("Entrou no Sintatico");
		controle = 0;

		while (lx.isTokenValid(controle)) {
			tk = lx.getToken(controle);
			System.out.println("SINTATICO > o token eh: " + tk.toString());
			controle++;
		}
	}
}
