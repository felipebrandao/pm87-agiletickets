package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;
		
		TipoDeEspetaculo tipo = sessao.getEspetaculo().getTipo();
		
		if(tipo.equals(TipoDeEspetaculo.CINEMA) || tipo.equals(TipoDeEspetaculo.SHOW)) {
			preco = calculaPrecoPorQuantidadeDisponivel(sessao, 0.05, 0.10);
		} else if(tipo.equals(TipoDeEspetaculo.BALLET) || tipo.equals(TipoDeEspetaculo.ORQUESTRA)) {
			preco = calculaPrecoPorQuantidadeDisponivel(sessao, 0.50, 0.20);
			
			if(sessao.getDuracaoEmMinutos() > 60){
				preco = preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
			}
		}  else {
			//nao aplica aumento para teatro (quem vai é pobretão)
			preco = sessao.getPreco();
		} 

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

	private static BigDecimal calculaPrecoPorQuantidadeDisponivel(
			Sessao sessao, double ocupacao, double ajuste) {
		BigDecimal preco;
		if (porcetagemDeLugaresDisponiveis(sessao) <= ocupacao) {
			preco = reajustaPreco(sessao, ajuste);
		} else {
			preco = sessao.getPreco();
		}
		return preco;
	}

	private static BigDecimal reajustaPreco(Sessao sessao, double reajuste) {
		return sessao.getPreco().add(
				sessao.getPreco().multiply(BigDecimal.valueOf(reajuste)));
	}

	private static double porcetagemDeLugaresDisponiveis(Sessao sessao) {
		return (sessao.getTotalIngressos() - sessao.getIngressosReservados())
				/ sessao.getTotalIngressos().doubleValue();
	}

}