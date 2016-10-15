package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco = sessao.getPreco();
		
		TipoDeEspetaculo tipo = sessao.getEspetaculo().getTipo();
		
		if(tipo.equals(TipoDeEspetaculo.CINEMA) || tipo.equals(TipoDeEspetaculo.SHOW)) {
			preco = ajustaPrecoPorQuantidadeDisponivel(preco, sessao, 0.05, 0.10);
		} else if(tipo.equals(TipoDeEspetaculo.BALLET) || tipo.equals(TipoDeEspetaculo.ORQUESTRA)) {
			preco = ajustaPrecoPorQuantidadeDisponivel(preco, sessao, 0.50, 0.20);
			
			if(sessao.getDuracaoEmMinutos() > 60){
				preco = preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
			}
		} 

		return preco.multiply(BigDecimal.valueOf(quantidade));
	}

	private static BigDecimal ajustaPrecoPorQuantidadeDisponivel(
			BigDecimal preco, Sessao sessao, double ocupacao, double ajuste) {
		if (porcetagemDeLugaresDisponiveis(sessao) <= ocupacao) {
			preco = sessao.reajustaPreco(ajuste);
		}
		return preco;
	}

	private static double porcetagemDeLugaresDisponiveis(Sessao sessao) {
		return (sessao.getTotalIngressos() - sessao.getIngressosReservados())
				/ sessao.getTotalIngressos().doubleValue();
	}

}