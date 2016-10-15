package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;
		
		
		TipoDeEspetaculo tipo = sessao.getEspetaculo().getTipo();
		
		if(tipo.equals(TipoDeEspetaculo.CINEMA) || tipo.equals(TipoDeEspetaculo.SHOW)) {
			//quando estiver acabando os ingressos... 
			if(porcetagemDeLugaresDisponiveis(sessao) <= 0.05) { 
				preco = reajustaPreco(sessao, 0.10);
			} else {
				preco = sessao.getPreco();
			}
		} else if(tipo.equals(TipoDeEspetaculo.BALLET) || tipo.equals(TipoDeEspetaculo.ORQUESTRA)) {
			if(porcetagemDeLugaresDisponiveis(sessao) <= 0.50) { 
				preco = reajustaPreco(sessao,0.20);
			} else {
				preco = sessao.getPreco();
			}
			
			if(sessao.getDuracaoEmMinutos() > 60){
				preco = preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
			}
		}  else {
			//nao aplica aumento para teatro (quem vai é pobretão)
			preco = sessao.getPreco();
		} 

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

	private static BigDecimal reajustaPreco(Sessao sessao, double reajuste) {
		return sessao.getPreco().add(sessao.getPreco().multiply(BigDecimal.valueOf(reajuste)));
	}

	private static double porcetagemDeLugaresDisponiveis(Sessao sessao) {
		return (sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue();
	}

}