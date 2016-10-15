package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco = sessao.getPreco();
		
		TipoDeEspetaculo tipo = sessao.getEspetaculo().getTipo();
		
		if(tipo.equals(TipoDeEspetaculo.CINEMA) || tipo.equals(TipoDeEspetaculo.SHOW)) {
			preco = sessao.ajustaPrecoPorQuantidadeDisponivel(preco, 0.05, 0.10);
		} else if(tipo.equals(TipoDeEspetaculo.BALLET) || tipo.equals(TipoDeEspetaculo.ORQUESTRA)) {
			preco = sessao.ajustaPrecoPorQuantidadeDisponivel(preco, 0.50, 0.20);
			
			preco = sessao.ajustaPrecoPorDuracao(preco);
		} 

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}
}